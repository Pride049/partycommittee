﻿delimiter //
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
	
	while i < 9 do	
		open s_cursor; 
	  SELECT FOUND_ROWS() into rows;
	  SET row = 0;
		cursor_loop:loop
				FETCH s_cursor into id, name, code_id, parent_id;
				SET s = 0;
				if row >= rows then
					leave cursor_loop;
				end if;			
				SET q = quarter(now());
				IF i = 1 THEN
				   SET q = 0;
				ELSEIF i = 4 THEN
				   SET q = 0;
				END IF;

				IF i <= 4 THEN
		   				SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = q AND type_id = i;
		   				IF  s IS NULL THEN
			   				SET s = 0;
							END IF;
						
				ELSE 
						 SELECT max(status_id) into s FROM pc_meeting WHERE agency_id = id AND year = y AND quarter = q AND type_id = i;
						 IF  s IS NULL THEN
			   				SET s = 0;
							END IF;
				END IF;	
				IF parent_id is not null THEN
					INSERT INTO  pc_remind (agency_id, name, code_id, parent_id, year, quarter, type_id, status) 
					VALUES (id, name, code_id, parent_id, y, q, i, s) 			
					ON DUPLICATE KEY UPDATE status = s;
			  END IF;
				SET row = row + 1;
		end loop cursor_loop;
		close s_cursor;
	  
	  

	  
	  set i=i+1;
	end while;  
	
		SET q = quarter(now());	
	  if q = 1 THEN
			open s_cursor; 
		  SELECT FOUND_ROWS() into rows;
		  SET row = 0;
			cursor_loop:loop	  
				FETCH s_cursor into id, name, code_id, parent_id;
				SET s = 0;
				if row >= rows then
					leave cursor_loop;
				end if;	  
		  	SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y -1 AND quarter = 4 AND type_id = 3;
				IF  s IS NULL THEN
	 				SET s = 0;
				END IF;	 
				IF parent_id is not null THEN
					INSERT INTO  pc_remind (agency_id, name, code_id, parent_id, year, quarter, type_id, status) 
					VALUES (id, name, code_id, parent_id, y -1, 4, 3, s) 			
					ON DUPLICATE KEY UPDATE status = s;				
				END IF;
		  	SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y -1 AND quarter = 0 AND type_id = 4;
				IF  s IS NULL THEN
	 				SET s = 0;
				END IF;	 
				IF parent_id is not null THEN
					INSERT INTO  pc_remind (agency_id, name, code_id, parent_id, year, quarter, type_id, status) 
					VALUES (id, name, code_id, parent_id, y -1, 0, 4, s) 			
					ON DUPLICATE KEY UPDATE status = s;					
			  END IF;
			SET row = row + 1;
			end loop cursor_loop;
			close s_cursor;			
	 	
	  END IF;	
	
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
	SELECT T1.parent_id as agency_id, T2.name, T2.code_id, T3.parent_id, T1.year, T1.quarter, T1.type_id, T1.status, T1.c FROM (SELECT code_id, parent_id, year ,quarter, type_id, status, count(*) as c FROM pc_remind WHERE year = y GROUP BY code_id, parent_id, year ,quarter, type_id, status) as T1
	LEFT JOIN pc_agency as T2 ON T1.parent_id = T2.id
	LEFT JOIN pc_agency_relation as T3 ON T1.parent_id = T3.agency_id
	ON DUPLICATE KEY UPDATE c = T1.c;
	
	INSERT INTO pc_remind_stat (agency_id ,name ,code_id ,parent_id ,year ,quarter ,type_id ,status ,c)
	SELECT T1.parent_id as agency_id, T2.name, T2.code_id, T3.parent_id, T1.year, T1.quarter, T1.type_id, T1.status, T1.c FROM (SELECT code_id, parent_id, year ,quarter, type_id, status, count(*) as c FROM pc_remind WHERE year = y -1 GROUP BY code_id, parent_id, year ,quarter, type_id, status) as T1
	LEFT JOIN pc_agency as T2 ON T1.parent_id = T2.id
	LEFT JOIN pc_agency_relation as T3 ON T1.parent_id = T3.agency_id
	ON DUPLICATE KEY UPDATE c = T1.c;	

	INSERT INTO pc_remind_stat (agency_id ,name ,code_id ,parent_id ,year ,quarter ,type_id ,status ,c)
	SELECT parent_id as agency_id, name, code_id, 1 as parent_id,  YEAR, quarter, type_id, status, SUM( c ) as c FROM  `pc_remind_stat` WHERE parent_id not in (1, 0) GROUP BY agency_id, name, code_id, parent_id,  YEAR, quarter, type_id, status
	ON DUPLICATE KEY UPDATE c = c;	
end;
//
delimiter ;


delimiter //
DROP procedure IF EXISTS check_remind_lock//
CREATE PROCEDURE check_remind_lock()
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

	while i < 9 do	
		open s_cursor; 
	  SELECT FOUND_ROWS() into rows;
	  SET row = 0;
		cursor_loop:loop
				FETCH s_cursor into id, name, code_id, parent_id;
				
				SET s = 0;
				if row >= rows then
					leave cursor_loop;
				end if;			
				
				
				
				SET q = quarter(now());
				
				if q = 1 THEN
				
					if i = 1 THEN
						SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = 0 AND type_id = i;
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y  , 0, 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
							  END IF;
							END IF;
						END IF;
						
					END if;
					
					if i = 2 THEN
					  SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y - 1 AND quarter = QUARTER(DATE_SUB(now(),interval 1 QUARTER)) AND type_id = i;

						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y - 1 , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
							  END IF;
							END IF;		
						END IF;
															  
					END IF;
					
					if i = 3 THEN
						SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y - 1 AND quarter = QUARTER(DATE_SUB(now(),interval 1 QUARTER)) AND type_id = i;
						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y - 1 , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
								END IF;
							END IF;
						END IF;
																		
					END IF;
					
				  IF i = 4 THEN
						SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y - 1 AND quarter = 0 AND type_id = i;
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							IF s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y -1, 0, 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;						
								END IF;
							END IF;
						END IF;						
						
					END IF;
					
					IF i > 4 and i < 8 THEN
					
						SELECT max(status_id) into s FROM pc_meeting WHERE agency_id = id AND year = y -1  AND quarter = QUARTER(DATE_SUB(now(),interval 1 QUARTER)) AND type_id = i;
						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y-1 , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
								END IF;
							END IF;					
						END IF;
												
					END IF;
					

					
				ELSE

					
					if i = 2 THEN
					  SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = QUARTER(DATE_SUB(now(),interval 1 QUARTER)) AND type_id = i;
					  
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
								END IF;
							END IF;					  
						END IF;					  
					END IF;
					
					if i = 3 THEN
						SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = QUARTER(DATE_SUB(now(),interval 1 QUARTER)) AND type_id = i;
						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
								END IF;
							END IF;
						END IF;
																		
					END IF;
										
					IF i > 4 AND i < 8 THEN
						SELECT max(status_id) into s FROM pc_meeting WHERE agency_id = id AND year = y  AND quarter = QUARTER(DATE_SUB(now(),interval 1 QUARTER)) AND type_id = i;
						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
								END IF;
							END IF;	
						END IF;
												
					END IF;	
				END IF;
				
				
				if i = 8 THEN
					if month(now()) = 1 THEN
				
						SELECT max(status_id) into s FROM pc_meeting WHERE agency_id = id AND year = y -1  AND month = 12 AND type_id = i;
						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y -1 , 0, 12, i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
								END IF;
							END IF;		
						END IF;
																	
					ELSE
						
						SELECT max(status_id) into s FROM pc_meeting WHERE agency_id = id AND year = y  AND month = month(now()) -1  AND type_id = i;
						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y , 0, month(now()) -1 , i, s)	ON DUPLICATE KEY UPDATE agency_id = id;
								END IF;
							END IF;							
						END IF;						
						
					END IF;				
				
			  END IF;
				
			  
				SET row = row + 1;
		end loop cursor_loop;
		close s_cursor;
	  
	  set i=i+1;
	end while;  
	
end;
//
delimiter ;



delimiter //
DROP procedure IF EXISTS set_remind_status//
CREATE PROCEDURE set_remind_status(t_id int, OUT status tinyint(1))
begin
	DECLARE y year(4);
	DECLARE e_y tinyint(1);
	DECLARE e_q tinyint(1) unsigned;
	DECLARE e_m smallint(5) unsigned;
	DECLARE e_d smallint(5) unsigned;
	DECLARE e_t varchar(20);
	SET y = year(now());
	SELECT end_year, end_quarter, end_month, end_day into e_y, e_q, e_m, e_d FROM pc_remind_config WHERE type_id = t_id;

	
	IF t_id  = 1  THEN SET e_t = CONCAT(y, '-', e_m, '-', e_d, ' 23:59:59');
	ELSEIF t_id = 4 THEN SET e_t = CONCAT(y, '-', e_m, '-', e_d, ' 23:59:59');
	END IF;
	
	IF t_id = 2 THEN SET e_t = concat(date_format(LAST_DAY(MAKEDATE(EXTRACT(YEAR FROM CURDATE()),1) + interval QUARTER(CURDATE())*3-3 month),'%Y-%m-'), e_d, ' 23:59:59');
	ELSEIF t_id = 3 THEN SET e_t = concat(date_format(LAST_DAY(MAKEDATE(EXTRACT(YEAR FROM CURDATE()),1) + interval QUARTER(CURDATE())*3-3 month),'%Y-%m-'), e_d, ' 23:59:59');
  END IF;
  
	IF t_id > 4 AND t_id < 8 THEN
		SET e_t = CONCAT(LAST_DAY(MAKEDATE(EXTRACT(YEAR FROM CURDATE()),1) + interval QUARTER(CURDATE())*3-4 month), ' 23:59:59');
	END IF;
	
	IF t_id = 8 THEN
		SET e_t = CONCAT(LAST_DAY(now() - interval 1 month), ' 23:59:59');
	END IF;
	
	IF unix_timestamp(now()) > unix_timestamp(e_t) THEN
		SET status = 9;
	ELSE 
		SET status = 0;
	END IF;
	
end;
//
delimiter ;

delimiter //
DROP procedure IF EXISTS stat_agency//
CREATE PROCEDURE stat_agency()
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
	DECLARE stat_reported int(10) unsigned;
	DECLARE stat_delay int(10) unsigned;
	DECLARE stat_attend int(10) unsigned;
	DECLARE stat_asence int(10) unsigned;
	DECLARE stat_p_count int(10) unsigned;
	DECLARE stat_zb_num int(10) unsigned;
	
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, b.parent_id, a.p_count, a.zb_num FROM  pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a. code_id = 10;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;

	SET y = year(now());
	SET q = quarter(now());		
	set i = 1;
	
	while i < 9 do	
	  open s_cursor; 
	  SELECT FOUND_ROWS() into rows;
	  SET row = 0;
		cursor_loop:loop
				FETCH s_cursor into id, name, code_id, parent_id, stat_p_count, stat_zb_num;
				SET stat_reported = 0;
				SET stat_delay = 0;
				SET stat_attend = 0;
				SET stat_asence = 0;
				
				IF row >= rows then
					leave cursor_loop;
				end if;
				IF parent_id is not null THEN
--					SET q = quarter(now());		
					IF i = 1 THEN
					   SET q = 0;
					ELSEIF i = 4 THEN
					   SET q = 0;
					END IF;				
--					SELECT CONCAT('i=', i, ' q=', q);
					IF i <= 4 THEN
						SELECT COUNT(*) into stat_reported FROM pc_workplan WHERE agency_id = id AND type_id = i AND year =y AND quarter = q AND status_id >= 2;
						SELECT COUNT(*) into stat_delay FROM pc_remind_lock WHERE agency_id = id  AND year =y AND quarter = q AND type_id = i;
					END IF;
					
					IF i > 4 THEN
						SELECT COUNT(*), sum(attend), sum(stat_asence) into stat_reported, stat_attend, stat_asence FROM pc_meeting WHERE agency_id = id AND type_id = i AND year =y AND quarter = q AND status_id >= 2;
						SELECT COUNT(*) into stat_delay FROM pc_remind_lock WHERE agency_id = id  AND year =y AND quarter = q AND type_id = i;
						
					END IF;
					
--					SELECT CONCAT('stat_reported =', stat_reported, '  stat_delay=', stat_delay, ' stat_attend = ', stat_attend, ' stat_asence=', stat_asence);
					
					IF stat_reported is null THEN
						SET stat_reported = 0;
					END IF;
					
					IF stat_delay is null THEN
						SET stat_delay = 0;
					END IF;
					
					IF stat_attend is null THEN
						SET stat_attend = 0;
					END IF;
					
					IF stat_asence is null THEN
						SET stat_asence = 0;
					END IF;
					
					INSERT INTO pc_agency_stat (agency_id, name, code_id, parent_id, year, quarter, type_id, reported, delay, attend, asence, p_count, zb_num)
					VALUES (id, name, code_id, parent_id, y, q, i, stat_reported, stat_delay, stat_attend, stat_asence, stat_p_count, stat_zb_num)	
					ON DUPLICATE KEY UPDATE reported = stat_reported, delay= stat_delay, attend = stat_attend, asence = stat_asence, p_count = stat_p_count, zb_num = stat_zb_num;

				END IF;
				SET row = row + 1;
		end loop cursor_loop;
		close s_cursor;
	  set i=i+1;
	end while;
end;
//
delimiter ;


delimiter //
DROP procedure IF EXISTS stat_agency_stat//
CREATE PROCEDURE stat_agency_stat()
begin

	INSERT INTO pc_agency_stat (agency_id, name, code_id, parent_id, year, quarter, type_id, reported, delay, attend, asence, p_count, zb_num)
	SELECT T1.parent_id as agency_id, T2.name, T2.code_id, T3.parent_id, T1.year, T1.quarter, T1.type_id, T1.reported, T1.delay, T1.attend, T1.asence, T1.p_count, T1.zb_num FROM 
	(SELECT parent_id, YEAR, quarter, type_id, SUM(  reported ) as reported , SUM(  delay )  as delay , SUM(  attend ) as attend , SUM(  asence ) as asence , SUM(  p_count ) as p_count , 
	SUM(  zb_num )  as zb_num FROM  pc_agency_stat GROUP BY parent_id, YEAR, quarter, type_id) as T1
	LEFT JOIN pc_agency as T2 ON T1.parent_id = T2.id
	LEFT JOIN pc_agency_relation as T3 ON T1.parent_id = T3.agency_id
	ON DUPLICATE KEY UPDATE reported = T1.reported, delay= T1.delay, attend = T1.attend, asence = T1.asence, p_count = T1.p_count, zb_num = T1.zb_num;
	
	INSERT INTO pc_agency_stat (agency_id, name, code_id, parent_id, year, quarter, type_id, reported, delay, attend, asence, p_count, zb_num)
	SELECT T1.parent_id as agency_id, T2.name, T2.code_id, T3.parent_id, T1.year, T1.quarter, T1.type_id, T1.reported, T1.delay, T1.attend, T1.asence, T1.p_count, T1.zb_num FROM 
	(SELECT parent_id, YEAR, quarter, type_id, SUM(  reported ) as reported , SUM(  delay )  as delay , SUM(  attend ) as attend , SUM(  asence ) as asence , SUM(  p_count ) as p_count , 
	SUM(  zb_num )  as zb_num FROM  pc_agency_stat WHERE parent_id <> 1 AND code_id = 7 GROUP BY parent_id, YEAR, quarter, type_id) as T1
	LEFT JOIN pc_agency as T2 ON T1.parent_id = T2.id
	LEFT JOIN pc_agency_relation as T3 ON T1.parent_id = T3.agency_id
	ON DUPLICATE KEY UPDATE reported = T1.reported, delay= T1.delay, attend = T1.attend, asence = T1.asence, p_count = T1.p_count, zb_num = T1.zb_num;
	
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
DROP PROCEDURE IF EXISTS `remind_lock_process`//
CREATE PROCEDURE `remind_lock_process`()
BEGIN
    SELECT IS_FREE_LOCK('event_remind_lock_lock') INTO @event_remind_lock_lock_isfree;
    IF (@event_remind_lock_lock_isfree = 1) THEN
        SELECT GET_LOCK('event_remind_lock_lock', 10) INTO @event_remind_lock_lock_isfree;
        IF (@event_remind_lock_lock_isfree = 1) THEN
            CALL check_remind_lock();
        END IF;
        SELECT RELEASE_LOCK('event_remind_lock_lock');
    END IF;
END //
delimiter ;


delimiter //
SET GLOBAL event_scheduler = OFF //
DROP EVENT IF EXISTS `event_stats`//
CREATE EVENT IF NOT EXISTS `event_stats`
ON SCHEDULE EVERY 120 SECOND
ON COMPLETION PRESERVE
DO
   BEGIN
       call stats_process;
   END //
delimiter ;

SET GLOBAL event_scheduler = ON;


delimiter //
SET GLOBAL event_scheduler = OFF //
DROP EVENT IF EXISTS `event_remind_lock`//
CREATE EVENT IF NOT EXISTS `event_remind_lock`
on schedule EVERY 1 DAY
STARTS '2012-02-07 15:55:00' 
ON COMPLETION PRESERVE
DO
   BEGIN
       call check_remind_lock();
   END //
delimiter ;

SET GLOBAL event_scheduler = ON;

