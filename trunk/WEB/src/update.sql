ALTER TABLE  `pc_agency` ADD INDEX (  `code_id` ) ;

ALTER TABLE  `pc_agency_relation` ADD INDEX (  `agency_id` ) ;
ALTER TABLE  `pc_agency_relation` ADD INDEX (  `parent_id` ) ;

ALTER TABLE  `pc_workplan` ADD INDEX (  `agency_id` ,  `type_id` ,  `year` ,  `quarter` ) ;
ALTER TABLE  `pc_meeting` ADD INDEX (  `agency_id` ,  `year` ,  `quarter` ,  `type_id` ) ;

explain SELECT a.id, a.name, a.code_id, b.parent_id FROM  `pc_agency` as a 
left join pc_agency_relation as b on a.id = b.agency_id
WHERE a. code_id =10


explain SELECT a.id, b.parent_id FROM  `pc_agency` as a 
left join pc_agency_relation as b on a.id = b.agency_id


DROP TABLE IF EXISTS `pc_remind`;
CREATE TABLE IF NOT EXISTS `pc_remind` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `agency_id` int(11) unsigned NOT NULL COMMENT '党支部ID',
  `name` varchar(255) NOT NULL COMMENT '党支部名称',
  `code_id` int(11) DEFAULT NULL COMMENT '党支部类型',
  `parent_id` int(11) unsigned NOT NULL COMMENT '上级党支部ID',
  `year` year(4) NOT NULL COMMENT '年度',
  `quarter` tinyint(1) unsigned NOT NULL COMMENT '季度',
  `status_year` tinyint(1) unsigned NOT NULL COMMENT '年度计划状态',
  `status_quarter` tinyint(1) unsigned NOT NULL COMMENT '季度计划状态',
  `status_quarter_end` tinyint(1) unsigned NOT NULL COMMENT '季度执行计划状态',
  `status_dk` tinyint(1) unsigned NOT NULL COMMENT '党课状态',
  `status_dydh` tinyint(1) unsigned NOT NULL COMMENT '支部党员大会状态',
  `status_mzshh` tinyint(1) unsigned NOT NULL COMMENT '支部民族生活会状态',
  `status_zbwyh` tinyint(1) unsigned NOT NULL COMMENT '支部委员会状态',
  `status_qt` tinyint(1) unsigned NOT NULL COMMENT '其他会议状态',
  `ext` varchar(255) DEFAULT 'beijing' COMMENT 'saas扩展字段',
  PRIMARY KEY (`id`),
  KEY `agency_id` (`agency_id`,`parent_id`,`year`,`quarter`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='党支部提醒统计表' AUTO_INCREMENT=1 ;






delimiter //
DROP procedure IF EXISTS stat_remind//
CREATE PROCEDURE stat_remind()
begin
	DECLARE id int(11) unsigned;
	DECLARE parent_id int(11) unsigned;
	DECLARE name VARCHAR(255);
	DECLARE code_id int(11) unsigned;
	DECLARE done int default 0;
	
	DECLARE y year(4);
	DECLARE q tinyint(1) unsigned;

  DECLARE status_year tinyint(1) unsigned;
  DECLARE status_year_end tinyint(1) unsigned;
  DECLARE status_quarter tinyint(1) unsigned;
  DECLARE status_quarter_end tinyint(1) unsigned;
  DECLARE status_dk tinyint(1) unsigned;
  DECLARE status_dydh tinyint(1) unsigned;
  DECLARE status_mzshh tinyint(1) unsigned;
  DECLARE status_zbwyh tinyint(1) unsigned;
  DECLARE status_qt tinyint(1) unsigned;	
  DECLARE rows int default 0;
  DECLARE row int default 0;
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, b.parent_id FROM  pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a. code_id = 10 and a.id = 4;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	SET y = year(now());
	SET q = quarter(now());
	open s_cursor; 
  SELECT FOUND_ROWS() into rows;
	cursor_loop:loop
			FETCH s_cursor into id, name, code_id, parent_id;
			
			SET status_year = 0;
			SET status_year_end = 0;
			SET status_quarter = 0;
			SET status_quarter_end = 0;
			SET status_dk = 0;
			SET status_dydh = 0;
			SET status_mzshh = 0;
			SET status_zbwyh = 0;
			SET status_qt = 0;
			if row > rows then
				leave cursor_loop;
			end if;			
			
			SELECT id;
			
			SELECT status_id into status_year FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = q AND type_id = 1;
			IF  status_year IS NULL THEN
			   SET status_year = 0;
			END IF;
			
			SELECT status_id into status_quarter FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = q AND type_id = 2;
			IF  status_quarter IS NULL THEN
			   SET status_quarter = 0;
			END IF;			
			
			
			SELECT status_id into status_quarter_end FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = q AND type_id = 3;
			IF  status_quarter_end IS NULL THEN
			   SET status_quarter_end = 0;
			END IF;			
			
			SELECT status_id into status_year_end FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = q AND type_id = 4;
			IF  status_year_end IS NULL THEN
			   SET status_year_end = 0;
			END IF;			
			
			
			CALL get_meeting_status(id, y, q, 5, status_dk);
			CALL get_meeting_status(id, y, q, 6, status_dydh);
			CALL get_meeting_status(id, y, q, 7, status_mzshh);
			CALL get_meeting_status(id, y, q, 8, status_zbwyh);
			CALL get_meeting_status(id, y, q, 9, status_qt);	
			
			
			INSERT INTO  partycommittee.pc_remind (agency_id, name, code_id, parent_id, year, quarter, status_year, status_quarter, status_quarter_end, status_dk, status_dydh, status_mzshh, status_zbwyh, status_qt) 
			VALUES (id, name, code_id, parent_id, y, q, status_year, status_quarter, status_quarter_end, status_dk, status_dydh, status_mzshh, status_zbwyh, status_qt) 			
			ON DUPLICATE KEY UPDATE status_year = status_year, status_quarter = status_quarter, status_quarter_end = status_quarter_end, status_dk = status_dk, status_dydh = status_dydh, status_mzshh = status_mzshh, status_zbwyh = status_zbwyh, status_qt = status_qt;
			
			
			SET row = row + 1;

	
	end loop cursor_loop;
	close s_cursor;
  SELECT rows, row;
end;
//
delimiter ;



delimiter //
DROP procedure IF EXISTS get_meeting_status//
CREATE PROCEDURE get_meeting_status(IN agency_id int(11), IN y year(4), IN q tinyint(1), IN type_id int(10), OUT status tinyint(1))
begin
		DECLARE done1 int default 0;
		DECLARE tmp_status tinyint(1) unsigned DEFAULT NULL;
		DECLARE s_cursor CURSOR FOR SELECT status_id FROM pc_meeting WHERE agency_id = agency_id AND year = y AND quarter = q AND type_id = type_id;
		DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1=1;
		
		open s_cursor; 
		cursor_loop:loop
		FETCH s_cursor into tmp_status;
			SELECT tmp_status;
			IF tmp_status IS NULL THEN
				SET status = 0;
			ELSEIF tmp_status = 4 THEN
				SET status = tmp_status;
		  	leave cursor_loop;
			ELSEIF tmp_status = 1 THEN
				SET status = tmp_status;
				leave cursor_loop;
		 	ELSE 
				SET status = tmp_status;
			END IF;
			IF done1 = 1 THEN
			  leave cursor_loop;
			END IF;
		end loop cursor_loop;
		close s_cursor;
		
		IF tmp_status IS NULL THEN
		  SET status = 0;
		END IF;
		
end;
//
delimiter ;



call stat_remind();

DECLARE status int default 0;
call get_meeting_status(3, 2011, 1, 9, status);
SELECT status;