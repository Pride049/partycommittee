ALTER TABLE  `pc_agency` ADD INDEX (  `code_id` ) ;

ALTER TABLE  `pc_agency_relation` ADD INDEX (  `agency_id` ) ;
ALTER TABLE  `pc_agency_relation` ADD INDEX (  `parent_id` ) ;

explain SELECT a.id, a.name, a.code_id, b.parent_id FROM  `pc_agency` as a 
left join pc_agency_relation as b on a.id = b.agency_id
WHERE a. code_id =10


explain SELECT a.id, b.parent_id FROM  `pc_agency` as a 
left join pc_agency_relation as b on a.id = b.agency_id





delimiter //
DROP procedure IF EXISTS stat_remind//
CREATE PROCEDURE stat_remind()
begin
	DECLARE agency int;
	DECLARE name VARCHAR;
	DECLARE minid int;
	DECLARE done int;
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, b.parent_id FROM  `pc_agency` as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a. code_id =10
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
	
	open s_cursor; 
	cursor_loop:loop
			FETCH s_cursor into sectionid;
			
			SELECT COUNT(*) into total FROM `cp_content` WHERE `cp_content`.`sectionid` = sectionid;
			
			IF total > 100 THEN
				DELETE FROM `cp_content` WHERE `cp_content`.`sectionid` = sectionid AND id NOT IN (SELECT id FROM (SELECT id  FROM `cp_content` WHERE `cp_content`.`sectionid` = sectionid ORDER BY created DESC LIMIT 100) as t );
			END IF;
			
			
			if done=1 then
			leave cursor_loop;
			end if;
	
	end loop cursor_loop;
	close s_cursor;
	truncate cp_feedgator_imports;
	
	DELETE FROM `cp_tuan` WHERE `cp_tuan`.`endtime` < current_timestamp();
end;
//
delimiter ;





