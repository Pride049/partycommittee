delimiter //
DROP procedure IF EXISTS stat_remind//
CREATE PROCEDURE stat_remind()
begin
	DECLARE c_id int(11) unsigned;
	DECLARE c_parent_id int(11) unsigned;
	DECLARE c_name VARCHAR(255);
	DECLARE c_parent_name VARCHAR(255);
	DECLARE c_code VARCHAR(20);
	DECLARE c_code_id int(11) unsigned;
	DECLARE done int default 0;
	
	DECLARE y year(4);
	DECLARE q tinyint(1) unsigned;

  DECLARE rows int default 0;
  DECLARE row int default 0;
  DECLARE i int;
  DECLARE s tinyint;
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, a.code, b.parent_id FROM  pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a. code_id = 10;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
		
	SET y = year(now());
	SET q = quarter(now());		
	set i = 1;
	
	
		open s_cursor; 
	  SELECT FOUND_ROWS() into rows;
	  SET row = 0;
		cursor_loop:loop
				FETCH s_cursor into c_id, c_name, c_code_id, c_code, c_parent_id;
				SELECT name into c_parent_name FROM pc_agency where id = c_parent_id;
				if row >= rows then
					leave cursor_loop;
				end if;		
				set i = 1;
				while i < 9 do	
				
					SET s = 0;
					SET q = quarter(now());
					IF (i = 1 OR i = 4) THEN
					   SET q = 0;
					END IF;
	
					IF i <= 4 THEN
			   				SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = c_id AND year = y AND quarter = q AND type_id = i;
			   				IF  s IS NULL THEN
				   				SET s = 0;
								END IF;
							
					ELSE 
							 SELECT max(status_id) into s FROM pc_meeting WHERE agency_id = c_id AND year = y AND quarter = q AND type_id = i;
							 IF  s IS NULL THEN
				   				SET s = 0;
								END IF;
					END IF;	
					IF c_parent_id is not null THEN
						INSERT INTO  pc_remind (agency_id, name, code_id, code, parent_id, parent_name, year, quarter, type_id, status) 
						VALUES (c_id, c_name, c_code_id,c_code, c_parent_id, c_parent_name,  y, q, i, s) 			
						ON DUPLICATE KEY UPDATE status = s, name =c_name, code = c_code, parent_name = c_parent_name;
				  END IF;
			  
			  set i=i+1;
				end while;  
			  
				SET row = row + 1;
		end loop cursor_loop;
		close s_cursor;
		

	
		SET q = quarter(now());	
	  if q = 1 THEN
			open s_cursor; 
		  SELECT FOUND_ROWS() into rows;
		  SET row = 0;
			cursor_loop:loop	  
				FETCH s_cursor into c_id, c_name, c_code_id, c_code, c_parent_id;

				SELECT name into c_parent_name FROM pc_agency where id = c_parent_id;
			
				SET s = 0;
				if row >= rows then
					leave cursor_loop;
				end if;	  
		  	SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = c_id AND year = y -1 AND quarter = 4 AND type_id = 3;
				IF  s IS NULL THEN
	 				SET s = 0;
				END IF;	 
				IF c_parent_id is not null THEN
					INSERT INTO  pc_remind (agency_id, name, code_id, code, parent_id, parent_name, year, quarter, type_id, status) 
					VALUES (c_id, c_name, c_code_id, c_code, c_parent_id, c_parent_name, y -1, 4, 3, s) 			
					ON DUPLICATE KEY UPDATE status = s, name =name, code = c_code, parent_name = c_parent_name;				
				END IF;
				
		  	SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = c_id AND year = y -1 AND quarter = 0 AND type_id = 4;
				IF  s IS NULL THEN
	 				SET s = 0;
				END IF;	 
				IF c_parent_id is not null THEN
					INSERT INTO  pc_remind (agency_id, name, code_id, code, parent_id, parent_name, year, quarter, type_id, status) 
					VALUES (c_id, c_name, c_code_id, c_code, c_parent_id, c_parent_name, y -1, 0, 4, s) 			
					ON DUPLICATE KEY UPDATE status = s, name =c_name, code = c_code, parent_name = c_parent_name;					
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
	truncate pc_remind_stat;
	INSERT INTO pc_remind_stat (agency_id ,name ,code_id ,parent_id ,year ,quarter ,type_id ,status ,c)
	SELECT T1.parent_id as agency_id, T2.name, T2.code_id, T3.parent_id, T1.year, T1.quarter, T1.type_id, T1.status, T1.c FROM (SELECT code_id, parent_id, year ,quarter, type_id, status, count(*) as c FROM pc_remind WHERE year = y GROUP BY code_id, parent_id, year ,quarter, type_id, status) as T1
	LEFT JOIN pc_agency as T2 ON T1.parent_id = T2.id
	LEFT JOIN pc_agency_relation as T3 ON T1.parent_id = T3.agency_id
	ON DUPLICATE KEY UPDATE c = T1.c, name = T2.name;
	
	INSERT INTO pc_remind_stat (agency_id ,name ,code_id ,parent_id ,year ,quarter ,type_id ,status ,c)
	SELECT T1.parent_id as agency_id, T2.name, T2.code_id, T3.parent_id, T1.year, T1.quarter, T1.type_id, T1.status, T1.c FROM (SELECT code_id, parent_id, year ,quarter, type_id, status, count(*) as c FROM pc_remind WHERE year = y -1 GROUP BY code_id, parent_id, year ,quarter, type_id, status) as T1
	LEFT JOIN pc_agency as T2 ON T1.parent_id = T2.id
	LEFT JOIN pc_agency_relation as T3 ON T1.parent_id = T3.agency_id
	ON DUPLICATE KEY UPDATE c = T1.c, name = T2.name;	

	INSERT INTO pc_remind_stat (agency_id ,name ,code_id ,parent_id ,year ,quarter ,type_id ,status ,c)
	SELECT T1.agency_id, T2.name, T2.code_id, T3.parent_id, T1.year, T1.quarter, T1.type_id, T1.status, T1.c FROM
	(SELECT parent_id as agency_id, YEAR, quarter, type_id, status, SUM( c ) as c FROM  `pc_remind_stat` WHERE parent_id <>1 GROUP BY parent_id, YEAR, quarter, type_id, status) as T1
	LEFT JOIN pc_agency as T2 ON T1.agency_id = T2.id
	LEFT JOIN pc_agency_relation as T3 ON T1.agency_id = T3.agency_id
	ON DUPLICATE KEY UPDATE c = pc_remind_stat.c + T1.c, name = T2.name;	
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
	DECLARE c_code VARCHAR(20);
	DECLARE code_id int(11) unsigned;
	DECLARE done int default 0;
	
	DECLARE y year(4);
	DECLARE q tinyint(1) unsigned;

  DECLARE rows int default 0;
  DECLARE row int default 0;
  DECLARE i int;
  DECLARE s tinyint;
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, a.code, b.parent_id FROM  pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a. code_id = 10;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
		
	SET y = year(now());
	SET q = quarter(now());		
	set i = 1;

	while i < 9 do	
		open s_cursor; 
	  SELECT FOUND_ROWS() into rows;
	  SET row = 0;
		cursor_loop:loop
				FETCH s_cursor into id, name, code_id, c_code, parent_id;
				
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
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, c_code, parent_id, y  , 0, 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
							  END IF;
							END IF;
						END IF;
						
					END if;
					
					if i = 2 THEN
					  SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = q AND type_id = i;

						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, c_code, parent_id, y , q, 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
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
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, parent_id, y - 1 , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
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
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, c_code, parent_id, y -1, 0, 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;						
								END IF;
							END IF;
						END IF;						
						
					END IF;
					
					IF (i > 4 and i < 8) THEN
					
						SELECT max(status_id) into s FROM pc_meeting WHERE agency_id = id AND year = y -1  AND quarter = QUARTER(DATE_SUB(now(),interval 1 QUARTER)) AND type_id = i;
						
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, c_code, parent_id, y-1 , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
								END IF;
							END IF;					
						END IF;
												
					END IF;
					

					
				ELSE

					
					if i = 2 THEN
					  SELECT max(status_id) into s FROM pc_workplan WHERE agency_id = id AND year = y AND quarter = q AND type_id = i;
					  
						IF  s IS NULL THEN
				   			SET s = 0;
						END IF;
						if s < 2 THEN
							CALL set_remind_status(i, s);
							if s = 9 THEN
								IF parent_id is not null then
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, c_code, parent_id, y , q, 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
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
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, c_code, parent_id, y , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
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
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, c_code, parent_id, y , QUARTER(DATE_SUB(now(),interval 1 QUARTER)), 0, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
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
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id, c_code, parent_id, y -1 , 4, 12, i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
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
									INSERT INTO  pc_remind_lock (agency_id, name, code_id, code_id, parent_id, year, quarter, month, type_id, status) 
									VALUES (id, name, code_id , c_code, parent_id, y , q, month(now()) -1 , i, s)	ON DUPLICATE KEY UPDATE agency_id = id, name = name;
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

	
	IF (t_id  = 1 OR t_id = 4)  THEN 
		SET e_t = CONCAT(y, '-', e_m, '-', e_d, ' 23:59:59');
	END IF;
	
	IF (t_id = 2 OR t_id = 3)  THEN 
		SET e_t = concat(date_format(LAST_DAY(MAKEDATE(EXTRACT(YEAR FROM CURDATE()),1) + interval QUARTER(CURDATE())*3-3 month),'%Y-%m-'), e_d, ' 23:59:59');
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
DROP procedure IF EXISTS proc_agency_stats//
CREATE PROCEDURE proc_agency_stats()
begin
	DECLARE c_id int(11) unsigned;
	DECLARE c_name VARCHAR(255);
	DECLARE c_code_id int(11) unsigned;
	DECLARE c_code VARCHAR(20);
	DECLARE c_parent_id int(11) unsigned;
	DECLARE c_setup_datetime datetime;
	DECLARE done int default 0;
	
	DECLARE rows int default 0;
	DECLARE row int default 0;
	DECLARE i int;
	
  DECLARE stat_ejdw_num int(10) unsigned;
  DECLARE stat_dzj_num int(10) unsigned;
  DECLARE stat_dzb_num int(10) unsigned;
  DECLARE stat_2year_num int(10) unsigned;
  DECLARE stat_less7_num int(10) unsigned;
  DECLARE stat_no_fsj_zbwy_num int(10) unsigned;
  DECLARE stat_dxz_num int(10) unsigned;
  DECLARE stat_dy_num int(10) unsigned;
  DECLARE stat_zbsj_num int(10) unsigned;
  DECLARE stat_zbfsj_num int(10) unsigned;
  DECLARE stat_zzwy_num int(10) unsigned;
  DECLARE stat_xcwy_num int(10) unsigned;
  DECLARE stat_jjwy_num int(10) unsigned;
  DECLARE stat_qnwy_num int(10) unsigned;
  DECLARE stat_ghwy_num int(10) unsigned;
  DECLARE stat_fnwy_num int(10) unsigned;
  DECLARE stat_bmwy_num int(10) unsigned;

	
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, a.code, b.parent_id, a.setup_datetime,  a.p_count, a.zb_num FROM  pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a.code_id = 10;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
  
  open s_cursor; 
  SELECT FOUND_ROWS() into rows;
  SET row = 0;
	cursor_loop:loop
			FETCH s_cursor into c_id, c_name, c_code_id, c_code, c_parent_id, c_setup_datetime, stat_dxz_num, stat_dy_num;

		  SET stat_ejdw_num = 0;
		  SET stat_dzj_num = 0;
		  SET stat_dzb_num = 0;
		  SET stat_2year_num = 0;
		  SET stat_less7_num = 0;
		  SET stat_no_fsj_zbwy_num = 0;
		  SET stat_zbsj_num = 0;
		  SET stat_zbfsj_num = 0;
		  SET stat_zzwy_num = 0;
		  SET stat_xcwy_num = 0;
		  SET stat_jjwy_num = 0;
		  SET stat_qnwy_num = 0;
		  SET stat_ghwy_num = 0;
		  SET stat_fnwy_num = 0;
		  SET stat_bmwy_num = 0;
			
			IF row >= rows then
				leave cursor_loop;
			end if;
			
			
			
--			党支部数量			
				SET stat_dzb_num = 1;
				
--      截止统计时间支部改选时间满两年的支部数				
				IF unix_timestamp(now()) > unix_timestamp(DATE_ADD(c_setup_datetime,INTERVAL 2 YEAR)) THEN
					SET stat_2year_num = 1;
			  END IF;
			  
--			党员人数不足7人的党支部数量			  
				SELECT (CASE WHEN COUNT(*) < 7 THEN 1 ELSE 0 END) INTO stat_less7_num FROM pc_member where agency_id = c_id;
				
				SELECT COUNT(*) INTO stat_zbsj_num FROM pc_member where agency_id = c_id AND duty_id = 1;
				SELECT COUNT(*) INTO stat_zbfsj_num FROM pc_member where agency_id = c_id AND duty_id = 2;
				SELECT COUNT(*) INTO stat_zzwy_num FROM pc_member where agency_id = c_id AND duty_id = 3;
				SELECT COUNT(*) INTO stat_xcwy_num FROM pc_member where agency_id = c_id AND duty_id = 4;
				SELECT COUNT(*) INTO stat_jjwy_num FROM pc_member where agency_id = c_id AND duty_id = 5;
				SELECT COUNT(*) INTO stat_qnwy_num FROM pc_member where agency_id = c_id AND duty_id = 6;
				SELECT COUNT(*) INTO stat_fnwy_num FROM pc_member where agency_id = c_id AND duty_id = 10;
				SELECT COUNT(*) INTO stat_ghwy_num FROM pc_member where agency_id = c_id AND duty_id = 11;
				SELECT COUNT(*) INTO stat_bmwy_num FROM pc_member where agency_id = c_id AND duty_id = 8;
				
				
				
--			只设支部书记未设支部副书记、支部委员的支部数量
				SELECT COUNT(*) INTO stat_no_fsj_zbwy_num FROM pc_member where agency_id = c_id;
				IF (stat_no_fsj_zbwy_num = 1 AND stat_zbsj_num >= 1) THEN
					SET stat_no_fsj_zbwy_num = 1;
				ELSE
					SET stat_no_fsj_zbwy_num = 0;
				END IF;
				
			IF c_parent_id is not null THEN
				INSERT INTO pc_parent_stats (agency_id, name, code_id, code, parent_id, ejdw_num, dzj_num, dzb_num, 2year_num, less7_num, no_fsj_zbwy_num, dxz_num, dy_num, zbsj_num, zbfsj_num, zzwy_num, xcwy_num, jjwy_num, qnwy_num, ghwy_num, fnwy_num, bmwy_num) VALUES
				(c_id, c_name, c_code_id, c_code, c_parent_id, stat_ejdw_num, stat_dzj_num, stat_dzb_num, stat_2year_num, stat_less7_num, stat_no_fsj_zbwy_num, stat_dxz_num, stat_dy_num, stat_zbsj_num, stat_zbfsj_num, stat_zzwy_num, stat_xcwy_num, stat_jjwy_num, stat_qnwy_num, stat_ghwy_num, stat_fnwy_num, stat_bmwy_num)
				ON DUPLICATE KEY UPDATE 
				name = c_name, 
				code_id = c_code_id, 
				code = c_code, 
				parent_id = c_parent_id, 
				ejdw_num = stat_ejdw_num, 
				dzj_num = stat_dzj_num, 
				dzb_num = stat_dzb_num, 
				2year_num = stat_2year_num, 
				less7_num = stat_less7_num, 
				no_fsj_zbwy_num = stat_no_fsj_zbwy_num, 
				dxz_num = stat_dxz_num, 
				dy_num = stat_dy_num, 
				zbsj_num = stat_zbsj_num, 
				zbfsj_num = stat_zbfsj_num, 
				zzwy_num = stat_zzwy_num, 
				xcwy_num = stat_xcwy_num, 
				jjwy_num = stat_jjwy_num, 
				qnwy_num = stat_qnwy_num, 
				ghwy_num = stat_ghwy_num, 
				fnwy_num = stat_fnwy_num, 
				bmwy_num = stat_bmwy_num;
			END IF;
			SET row = row + 1;
	end loop cursor_loop;
	close s_cursor;
end;
//
delimiter ;

delimiter //
DROP procedure IF EXISTS proc_parent_stats//
CREATE PROCEDURE proc_parent_stats()
begin
	DECLARE c_id int(11) unsigned;
	DECLARE c_name VARCHAR(255);
	DECLARE c_code_id int(11) unsigned;
	DECLARE c_code VARCHAR(20);
	DECLARE c_parent_id int(11) unsigned;
	DECLARE c_setup_datetime datetime;
	DECLARE done int default 0;
	
	DECLARE rows int default 0;
	DECLARE row int default 0;
	DECLARE i int;
		
  DECLARE stat_ejdw_num int(10) unsigned;
  DECLARE stat_dzj_num int(10) unsigned;		
		
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, a.code, b.parent_id FROM  pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a.code_id in (7,8,15);
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;
  
  open s_cursor; 
  SELECT FOUND_ROWS() into rows;
  SET row = 0;
	cursor_loop:loop
			FETCH s_cursor into c_id, c_name, c_code_id, c_code, c_parent_id;

		  SET stat_ejdw_num = 0;
		  SET stat_dzj_num = 0;

			IF row >= rows then
				leave cursor_loop;
			end if;
			IF c_code_id = 7 THEN
				SELECT COUNT(*) INTO stat_ejdw_num  FROM pc_agency WHERE code like CONCAT (c_code, '%') and code_id = 15;
				SELECT COUNT(*) INTO stat_dzj_num FROM pc_agency WHERE code like CONCAT (c_code, '%') and code_id = 8;
			END IF;
			INSERT INTO pc_parent_stats (agency_id, name, code_id, code, parent_id, ejdw_num, dzj_num, dzb_num, 2year_num, less7_num, no_fsj_zbwy_num, dxz_num, dy_num, zbsj_num, zbfsj_num, zzwy_num, xcwy_num, jjwy_num, qnwy_num, ghwy_num, fnwy_num, bmwy_num)
			SELECT  c_id as agency_id, c_name as name, c_code_id as code_id, c_code as  code, c_parent_id as  parent_id,
			stat_ejdw_num as ejdw_num,
			stat_dzj_num as ejdw_num,
			SUM(dzb_num) as ejdw_num,
			SUM(2year_num) as ejdw_num,
			SUM(less7_num) as ejdw_num,
			SUM(no_fsj_zbwy_num) as ejdw_num,
			SUM(dxz_num) as ejdw_num,
			SUM(dy_num) as ejdw_num,
			SUM(zbsj_num) as ejdw_num,
			SUM(zbfsj_num) as ejdw_num,
			SUM(zzwy_num) as ejdw_num,
			SUM(xcwy_num) as ejdw_num, 
			SUM(jjwy_num) as ejdw_num,
			SUM(qnwy_num) as ejdw_num,
			SUM(ghwy_num) as ejdw_num,
			SUM(fnwy_num) as ejdw_num,
			SUM(bmwy_num) as ejdw_num
			FROM pc_parent_stats 
			WHERE code like CONCAT (c_code, '%')
			ON DUPLICATE KEY UPDATE 
			name = c_name, 
			code_id = c_code_id, 
			code = c_code, 
			parent_id = c_parent_id, 
			ejdw_num = stat_ejdw_num, 
			dzj_num = stat_dzj_num, 
			dzb_num = dzb_num, 
			2year_num = 2year_num, 
			less7_num = less7_num, 
			no_fsj_zbwy_num = no_fsj_zbwy_num, 
			dxz_num = dxz_num, 
			dy_num = dy_num, 
			zbsj_num = zbsj_num, 
			zbfsj_num = zbfsj_num, 
			zzwy_num = zzwy_num, 
			xcwy_num = xcwy_num, 
			jjwy_num = jjwy_num, 
			qnwy_num = qnwy_num, 
			ghwy_num = ghwy_num, 
			fnwy_num = fnwy_num, 
			bmwy_num = bmwy_num;

			SET row = row + 1;
	end loop cursor_loop;
	close s_cursor;

	INSERT INTO pc_parent_stats (agency_id, name, code_id, code, parent_id, ejdw_num, dzj_num, dzb_num, 2year_num, less7_num, no_fsj_zbwy_num, dxz_num, dy_num, zbsj_num, zbfsj_num, zzwy_num, xcwy_num, jjwy_num, qnwy_num, ghwy_num, fnwy_num, bmwy_num)
	SELECT  1 as agency_id, '北京市公安局党委' as name, 6 as code_id, '' as  code, 0 as  parent_id,
	SUM(ejdw_num) as ejdw_num,
	SUM(dzj_num) as ejdw_num,
	SUM(dzb_num) as ejdw_num,
	SUM(2year_num) as ejdw_num,
	SUM(less7_num) as ejdw_num,
	SUM(no_fsj_zbwy_num) as ejdw_num,
	SUM(dxz_num) as ejdw_num,
	SUM(dy_num) as ejdw_num,
	SUM(zbsj_num) as ejdw_num,
	SUM(zbfsj_num) as ejdw_num,
	SUM(zzwy_num) as ejdw_num,
	SUM(xcwy_num) as ejdw_num, 
	SUM(jjwy_num) as ejdw_num,
	SUM(qnwy_num) as ejdw_num,
	SUM(ghwy_num) as ejdw_num,
	SUM(fnwy_num) as ejdw_num,
	SUM(bmwy_num) as ejdw_num
	FROM pc_parent_stats 
	WHERE parent_id = 1
	ON DUPLICATE KEY UPDATE 
	ejdw_num = ejdw_num, 
	dzj_num = dzj_num, 
	dzb_num = dzb_num, 
	2year_num = 2year_num, 
	less7_num = less7_num, 
	no_fsj_zbwy_num = no_fsj_zbwy_num, 
	dxz_num = dxz_num, 
	dy_num = dy_num, 
	zbsj_num = zbsj_num, 
	zbfsj_num = zbfsj_num, 
	zzwy_num = zzwy_num, 
	xcwy_num = xcwy_num, 
	jjwy_num = jjwy_num, 
	qnwy_num = qnwy_num, 
	ghwy_num = ghwy_num, 
	fnwy_num = fnwy_num, 
	bmwy_num = bmwy_num;	

end;
//
delimiter ;



delimiter //
DROP procedure IF EXISTS stat_zzsh//
CREATE PROCEDURE stat_zzsh()
begin
	DECLARE c_id int(11) unsigned;
	DECLARE c_parent_id int(11) unsigned;
	DECLARE c_name VARCHAR(255);
	DECLARE c_code VARCHAR(20);
	DECLARE c_code_id int(11) unsigned;
	DECLARE done int default 0;
	
	DECLARE y year(4);
	DECLARE q tinyint(1) unsigned;
	DECLARE m smallint(5) unsigned;

	DECLARE rows int default 0;
	DECLARE row int default 0;
	DECLARE i int;
	
	DECLARE stat_reported int(10) unsigned;
	DECLARE stat_reported_rate DECIMAL(6,4) unsigned;	
	
	DECLARE stat_total int(10) unsigned;
	DECLARE stat_total_success int(10) unsigned;
	DECLARE stat_total_return int(10) unsigned;
	DECLARE stat_total_delay int(10) unsigned;
	
	DECLARE stat_attend int(10) unsigned;
	DECLARE stat_asence int(10) unsigned;
	DECLARE stat_attend_rate DECIMAL(6,4) unsigned;

	DECLARE stat_eva int(10) unsigned;
	DECLARE stat_eva_rate DECIMAL(6,4) unsigned;
	DECLARE stat_eva_1 int(10) unsigned;
	DECLARE stat_eva_2 int(10) unsigned;
	DECLARE stat_eva_3 int(10) unsigned;
	DECLARE stat_eva_4 int(10) unsigned;

	DECLARE stat_agency_goodjob int(10) unsigned;

	
	DECLARE s_cursor CURSOR FOR SELECT a.id, a.name, a.code_id, a.code, b.parent_id FROM  pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id WHERE a. code_id = 10;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=1;

	SET y = year(now());
	SET q = quarter(now());		
	SET m = month(now());		
	set i = 1;
	
	
	  open s_cursor; 
	  SELECT FOUND_ROWS() into rows;
	  SET row = 0;
		cursor_loop:loop
				FETCH s_cursor into c_id, c_name, c_code_id, c_code, c_parent_id;
--				SELECT c_id, c_name;

				SET  stat_reported = 0;
				SET  stat_reported_rate = 0;
				
				SET  stat_total = 0;
				SET  stat_total_success = 0;
				SET  stat_total_return = 0;
				SET  stat_total_delay = 0;
				
				SET  stat_attend = 0;
				SET  stat_asence = 0;
				SET  stat_attend_rate  = 0;
			
				SET  stat_eva = 0;
				SET  stat_eva_rate  = 0;
				SET  stat_eva_1 = 0;
				SET  stat_eva_2 = 0;
				SET  stat_eva_3 = 0;
				SET  stat_eva_4 = 0;

				
				IF row >= rows then
					leave cursor_loop;
				end if;
				SET i = 1;
				IF c_parent_id is not null THEN
					while i <= 9 do	
						SET q = quarter(now());		
						
						
	-- 				年度计划	q = 1 AND i = 1
	--			  年终总结	q = 4 AND i = 4					
	--				季度计划和季度执行情况					
						IF ( i = 2 OR i = 3 OR (q = 1 AND i = 1) OR  (q = 4 AND i = 4)) THEN
								IF (i = 1 OR i = 4) THEN
									SET q = 0;
								END IF;	
												SELECT COUNT(*) into stat_total FROM pc_workplan WHERE agency_id = c_id AND type_id = i AND year =y AND quarter = q AND status_id >= 3;
												SELECT COUNT(*) into stat_total_delay FROM pc_remind_lock WHERE agency_id = c_id  AND year =y AND quarter = q AND type_id = i;
												SELECT COUNT(*) into stat_total_return FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id >= 3 AND b.type = 2;
												SELECT Max(month(b.updatetime)) into m FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id >= 3 AND b.type = 1;
												SELECT COUNT(*) into stat_eva FROM pc_workplan WHERE agency_id = id AND type_id = i AND year = y AND quarter = q AND status_id = 5;
												SELECT COUNT(CASE WHEN b.content = 1 THEN a.id END) into stat_eva_1 FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id = 5 AND b.type = 4;
												SELECT COUNT(CASE WHEN b.content = 2 THEN a.id END) into stat_eva_2 FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id = 5 AND b.type = 4;
												SELECT COUNT(CASE WHEN b.content = 3 THEN a.id END) into stat_eva_3 FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id = 5 AND b.type = 4;
												SELECT COUNT(CASE WHEN b.content = 4 THEN a.id END) into stat_eva_4 FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id = 5 AND b.type = 4;

												
												IF (stat_total > 0) THEN
													SELECT 1 - stat_total_delay, ROUND( (1 - stat_total_delay)/1 , 4) into stat_reported, stat_reported_rate;
												END IF;
												
												IF (stat_eva > 0) THEN
													SET stat_eva = 1;
													SET stat_eva_rate = 1;
												ELSE 
													SET stat_eva = 0;
													SET stat_eva_rate = 0;
												END IF;
						END IF;
						
						IF i > 4 THEN
												SELECT COUNT(*) into stat_total FROM pc_meeting WHERE agency_id = c_id AND type_id = i AND year =y AND quarter = q AND status_id >= 3;
												SELECT COUNT(*) into stat_total_delay FROM pc_remind_lock WHERE agency_id = c_id  AND year =y AND quarter = q AND type_id = i;
												SELECT COUNT(*) into stat_total_return FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id >= 3 AND b.type = 2;
												SELECT Max(month(b.updatetime)) into m FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id >= 3 AND b.type = 1;
												SELECT COUNT(*) into stat_eva FROM pc_workplan WHERE agency_id = id AND type_id = i AND year = y AND quarter = q AND status_id = 5;
												SELECT COUNT(CASE WHEN b.content = 1 THEN a.id END) into stat_eva_1 FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id = 5 AND b.type = 4;
												SELECT COUNT(CASE WHEN b.content = 2 THEN a.id END) into stat_eva_2 FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id = 5 AND b.type = 4;
												SELECT COUNT(CASE WHEN b.content = 3 THEN a.id END) into stat_eva_3 FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id = 5 AND b.type = 4;
												SELECT COUNT(CASE WHEN b.content = 4 THEN a.id END) into stat_eva_4 FROM pc_workplan as a left join pc_workplan_content as b on a.id = b.workplan_id WHERE a.agency_id = c_id AND a.type_id = i AND a.year =y AND a.quarter = q AND a.status_id = 5 AND b.type = 4;

												
												IF (stat_total > 0) THEN
													SELECT 1 - stat_total_delay, ROUND( (1 - stat_total_delay)/1 , 4) into stat_reported, stat_reported_rate;
												END IF;
												
												IF (stat_eva > 0) THEN
													SET stat_eva = 1;
													SET stat_eva_rate = 1;
												ELSE 
													SET stat_eva = 0;
													SET stat_eva_rate = 0;
												END IF;						
						
						END IF;		
	
						set i=i+1;
					end while;

				END IF;
				SET row = row + 1;
		end loop cursor_loop;
		close s_cursor;

end;
//
delimiter ;






delimiter //
DROP PROCEDURE IF EXISTS `stats_remind_process`//
CREATE PROCEDURE `stats_remind_process`()
BEGIN
    SELECT IS_FREE_LOCK('event_stats_remind_lock') INTO @event_stats_remind_lock_isfree;
    IF (@event_stats_remind_lock_isfree = 1) THEN
        SELECT GET_LOCK('event_stats_remind_lock', 10) INTO @event_stats_remind_lock_isfree;
        IF (@event_stats_remind_lock_isfree = 1) THEN
            CALL stat_remind();
            CALL stat_remind_stat();            
        END IF;
        SELECT RELEASE_LOCK('event_stats_remind_lock_isfree');
    END IF;
END //
delimiter ;


delimiter //
DROP PROCEDURE IF EXISTS `stats_process`//
CREATE PROCEDURE `stats_process`()
BEGIN
    SELECT IS_FREE_LOCK('event_stats_lock') INTO @event_stats_lock_isfree;
    SELECT @event_stats_lock_isfree;
    IF (@event_stats_lock_isfree = 1) THEN
        SELECT GET_LOCK('event_stats_lock', 10) INTO @event_stats_lock_isfree;
        IF (@event_stats_lock_isfree = 1) THEN
            CALL stat_agency();
            CALL stat_agency_stat();            
        END IF;
        SELECT RELEASE_LOCK('event_stats_lock_isfree');
    END IF;
END //
delimiter ;



SET GLOBAL event_scheduler = ON;

delimiter //
SET GLOBAL event_scheduler = OFF //
DROP EVENT IF EXISTS `event_stats`//
CREATE EVENT IF NOT EXISTS `event_stats`
ON SCHEDULE EVERY 6 HOUR
ON COMPLETION PRESERVE
DO
   BEGIN
       call stats_process;
   END //
delimiter ;

SET GLOBAL event_scheduler = ON;


SET GLOBAL event_scheduler = ON;

delimiter //
SET GLOBAL event_scheduler = OFF //
DROP EVENT IF EXISTS `event_stats_remind`//
CREATE EVENT IF NOT EXISTS `event_stats_remind`
ON SCHEDULE EVERY 1 HOUR
ON COMPLETION PRESERVE
DO
   BEGIN
       call stats_remind_process;
   END //
delimiter ;

SET GLOBAL event_scheduler = ON;





