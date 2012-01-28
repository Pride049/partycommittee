ALTER TABLE  `pc_agency` ADD INDEX (  `code_id` ) ;

ALTER TABLE  `pc_agency_relation` ADD INDEX (  `agency_id` ) ;
ALTER TABLE  `pc_agency_relation` ADD INDEX (  `parent_id` ) ;

ALTER TABLE  `pc_workplan` ADD INDEX (  `agency_id` ,  `type_id` ,  `year` ,  `quarter` ) ;
ALTER TABLE  `pc_meeting` ADD INDEX (  `agency_id` ,  `year` ,  `quarter` ,  `type_id` ) ;

将pc_workplan status = 2 变成  status = 1  
将pc_workplan status = 1 变成  status = 2

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

  DECLARE rows int default 0;
  DECLARE row int default 0;
  DECLARE i int;
  DECLARE s tinyint;
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, b.parent_id FROM  pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a. code_id = 10;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
		
	SET y = year(now());
	SET q = quarter(now());		
	set i = 1;
	while i < 10 do	
		open s_cursor; 
	  SELECT FOUND_ROWS() into rows;
	  SET row = 0;
		cursor_loop:loop
				FETCH s_cursor into id, name, code_id, parent_id;
				SET s = 0;
				if row >= rows then
					leave cursor_loop;
				end if;			
				
				IF i <= 4 THEN
		   				SELECT status_id into s FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = 0 AND type_id = i;
		   				IF  s IS NULL THEN
			   				SET s = 0;
							END IF;
							
				ELSE 
						 SELECT max(status_id) into s FROM pc_meeting WHERE agency_id = id AND year = y AND quarter = q AND type_id = i;
						 IF  s IS NULL THEN
			   				SET s = 0;
							END IF;
				END IF;				
				
				SET q = quarter(now());
				IF i = 1 THEN
				   SET q = 0;
				END IF;
				INSERT INTO  pc_remind (agency_id, name, code_id, parent_id, year, quarter, type_id, status) 
				VALUES (id, name, code_id, parent_id, y, q, i, s) 			
				ON DUPLICATE KEY UPDATE status = s;
				
				SET row = row + 1;
		end loop cursor_loop;
		close s_cursor;
	  
	  set i=i+1;
	end while;  
	
end;
//
delimiter ;


delimiter //
DROP procedure IF EXISTS stat_remind_stat//
CREATE PROCEDURE stat_remind_stat()
begin

	DECLARE y year(4);

	SET y = year(now());

	INSERT INTO pc_remind_stat (agency_id ,name ,code_id ,parent_id ,year ,quarter ,type_id ,status ,c)
	SELECT T1.parent_id as agency_id, T2.name, T1.code_id, T3.parent_id, T1.year, T1.quarter, T1.type_id, T1.status, T1.c FROM (SELECT code_id, parent_id, year ,quarter, type_id, status, count(*) as c FROM pc_remind WHERE year = y GROUP BY code_id, parent_id, year ,quarter, type_id, status) as T1
	LEFT JOIN pc_agency as T2 ON T1.parent_id = T2.id
	LEFT JOIN pc_agency_relation as T3 ON T1.parent_id = T3.agency_id
	ON DUPLICATE KEY UPDATE c = T1.c;

end;
//
delimiter ;


delimiter //
DROP PROCEDURE IF EXISTS `stats_process`//
CREATE PROCEDURE `stats_process`()
BEGIN
    SELECT IS_FREE_LOCK('event_stats_lock') INTO @event_stats_lock_isfree;
    IF (@event_stats_lock_isfree = 1) THEN
        SELECT GET_LOCK('event_stats_lock_isfree', 10) INTO @event_stats_lock_isfree;
        IF (@event_stats_lock_isfree = 1) THEN
            CALL stat_remind();
            CALL stat_remind_stat();
        END IF;
        SELECT RELEASE_LOCK('event_stats_lock_isfree');
    END IF;
END //
delimiter ;


delimiter //
SET GLOBAL event_scheduler = OFF //
DROP EVENT IF EXISTS `event_stats`//
CREATE EVENT IF NOT EXISTS `event_stats`
ON SCHEDULE EVERY 600 SECOND
ON COMPLETION PRESERVE
DO
   BEGIN
       call stats_process;
   END //
delimiter ;

SET GLOBAL event_scheduler = ON;

