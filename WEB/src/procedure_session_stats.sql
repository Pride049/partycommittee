SET GLOBAL event_scheduler = OFF;
delimiter //
DROP PROCEDURE IF EXISTS stats_reset //
CREATE PROCEDURE stats_reset()
BEGIN
    SELECT IS_FREE_LOCK('netaudit_event_stats_lock') INTO @netaudit_event_stats_lock_isfree;
    IF (@netaudit_event_stats_lock_isfree = 1) THEN
        SELECT GET_LOCK('netaudit_event_stats_lock', 10) INTO @netaudit_event_stats_lock_islocked;
        IF (@netaudit_event_stats_lock_islocked = 1) THEN
            truncate session_stats;
            truncate session_netobject_stats;
            truncate traffic_behavior_stats;
            truncate alert_netobject_stats;
            truncate alert_stats;
            update stats_info set stats_counter = 0;
            SELECT "!!! success !!!";
        END IF;
        SELECT RELEASE_LOCK('netaudit_event_stats_lock');
    ELSE
        SELECT "!!! locked by another process, please try again later !!!";
    END IF;
END
//
delimiter ;
SET GLOBAL event_scheduler = ON;

delimiter //
DROP PROCEDURE IF EXISTS stats_session_stats //
CREATE PROCEDURE stats_session_stats
(session_table_name varchar(64), from_id int unsigned, to_id int unsigned)
BEGIN
    SET @stats_sql = CONCAT("INSERT INTO session_stats 
        (record_date, 
        record_hour, 
        behavior_type_id, 
        record_minute, 
        session_size, 
        session_count,
        event_count,
        policy_type,
        policy_id,
        alert_id)
        SELECT * FROM (
            SELECT t.record_date,
            HOUR(t.record_time) AS record_hour,
            t.behavior_type_id, 
            MINUTE(t.record_time - ((t.record_time + 0) % 500)) AS record_minute,
            SUM(t.session_size) AS new_session_size, 
            COUNT(t.session_id) AS new_session_count,
            COUNT(CASE WHEN t.alert_id > 0 THEN t.alert_id END) AS new_event_count,
            t.policy_type,
            t.policy_id,
            t.alert_id
            FROM ", session_table_name, " AS t  
            WHERE t.session_id >= ", from_id, " 
            AND t.session_id <=", to_id, " 
            AND t.record_date <> '0000-00-00'
            GROUP BY record_hour, 
            record_minute, 
            t.behavior_type_id, 
            t.policy_type,
            t.policy_id,
            t.alert_id) as TT 
        ON duplicate KEY UPDATE
        session_size = session_size+ TT.new_session_size,  
        session_count= session_count + TT.new_session_count,
        event_count = event_count + TT.new_event_count "); 
    PREPARE stats_stmt FROM @stats_sql;
    EXECUTE stats_stmt;
    DEALLOCATE PREPARE stats_stmt;
END
//
delimiter ;


delimiter //
DROP PROCEDURE IF EXISTS stats_session_netobject_stats //
CREATE PROCEDURE stats_session_netobject_stats
(session_table_name varchar(64), from_id int unsigned, to_id int unsigned)
BEGIN
    SET @stats_sql = CONCAT("INSERT INTO session_netobject_stats 
        (netobject,
        ip,
        record_date, 
        record_hour, 
        record_minute,
        behavior_type_id, 
        session_size, 
        session_count,
        event_count,
        policy_type,
        policy_id,
        alert_id) 
        SELECT * FROM (
            SELECT t.netobject_a as netobject,
            t.ip_a as ip,
            t.record_date, 
            '00' AS record_hour,
            '00' AS record_minute, 
            t.behavior_type_id,
            SUM(t.session_size) AS new_session_size, 
            COUNT(t.session_id) AS new_session_count,
            COUNT(CASE WHEN t.alert_id > 0 THEN t.alert_id END) AS new_event_count,
            t.policy_type,
            t.policy_id,
            t.alert_id
            FROM ", session_table_name, " AS t  
            WHERE t.session_id >= ", from_id, " 
            AND t.session_id <=", to_id, " 
            AND t.netobject_a > 1
            AND t.record_date <> '0000-00-00'
            GROUP BY 
            t.netobject_a,
            t.ip_a,
            t.behavior_type_id,
            t.policy_type,
            t.policy_id,
            t.alert_id) AS TT
        ON duplicate KEY UPDATE  
        session_size = session_size+ TT.new_session_size,  
        session_count= session_count + TT.new_session_count,
        event_count = event_count + TT.new_event_count;");
    PREPARE stats_stmt FROM @stats_sql;
    EXECUTE stats_stmt;
    DEALLOCATE PREPARE stats_stmt;
    
    
    SET @stats_sql = CONCAT("INSERT INTO session_netobject_stats 
        (netobject,
        ip,
        record_date, 
        record_hour, 
        record_minute,
        behavior_type_id, 
        session_size, 
        session_count,
        event_count,
        policy_type,
        policy_id,
        alert_id) 
        SELECT * FROM (
            SELECT t.netobject_b as netobject,
            t.ip_b as ip,
            t.record_date, 
            '00' AS record_hour,
            '00' AS record_minute, 
            t.behavior_type_id,
            SUM(t.session_size) AS new_session_size, 
            COUNT(t.session_id) AS new_session_count,
            COUNT(CASE WHEN t.alert_id > 0 THEN t.alert_id END) AS new_event_count,
            t.policy_type,
            t.policy_id,
            t.alert_id
            FROM ", session_table_name, " AS t  
            WHERE t.session_id >= ", from_id, " 
            AND t.session_id <=", to_id, " 
            AND t.netobject_b > 1
            AND t.record_date <> '0000-00-00'
            GROUP BY 
            t.netobject_b,
            t.ip_b,
            t.behavior_type_id,
            t.policy_type,
            t.policy_id,
            t.alert_id) AS TT
        ON duplicate KEY UPDATE  
        session_size = session_size+ TT.new_session_size,  
        session_count= session_count + TT.new_session_count,
        event_count = event_count + TT.new_event_count;");

    PREPARE stats_stmt FROM @stats_sql;
    EXECUTE stats_stmt;
    DEALLOCATE PREPARE stats_stmt;    
    
END
//
delimiter ;


delimiter //
DROP PROCEDURE IF EXISTS stats_traffic_behavior_stats //
CREATE PROCEDURE stats_traffic_behavior_stats
(session_table_name varchar(64), from_id int unsigned, to_id int unsigned)
BEGIN
    SET @stats_sql = CONCAT("INSERT INTO traffic_behavior_stats
        (record_date,
  	    record_hour,
  	    behavior_type_id,
  	    record_minute,
  	    session_count,
  	    event_count)
        SELECT * FROM (
            SELECT t.record_date, 
            HOUR(t.record_time) AS record_hour, 
            t.behavior_type_id,
			MINUTE(t.record_time - ((t.record_time + 0) % 500)) AS record_minute,
			COUNT(t.session_id) AS new_session_count,
            COUNT(CASE WHEN t.alert_id > 0 THEN t.alert_id END) AS new_event_count
            FROM ", session_table_name, " AS t
            WHERE t.session_id >= ", from_id, "
            AND t.session_id <= ", to_id, " 
            AND t.record_date <> '0000-00-00'
            GROUP BY record_hour, 
            record_minute, 
            t.behavior_type_id) as TT 
        ON duplicate KEY UPDATE 
        session_count= session_count + TT.new_session_count,
        event_count = event_count + TT.new_event_count ");
    PREPARE stats_stmt FROM @stats_sql;
    EXECUTE stats_stmt;
    DEALLOCATE PREPARE stats_stmt;
END
//
delimiter ;

delimiter //
DROP PROCEDURE IF EXISTS stats_alert_stats //
CREATE PROCEDURE stats_alert_stats
(session_table_name varchar(64), from_id int unsigned, to_id int unsigned)
BEGIN
    SET @stats_sql = CONCAT("INSERT INTO alert_stats 
        (record_date, 
        record_hour,
        behavior_type_id, 
        record_minute, 
        event_count,
        policy_type,
        policy_id,
        alert_id)
        SELECT * FROM (
            SELECT
            t.record_date, 
            HOUR(t.record_time) AS record_hour,
            t.behavior_type_id,
            MINUTE(t.record_time - ((t.record_time + 0) % 500)) AS record_minute, 
            COUNT(CASE WHEN t.alert_id > 0 THEN t.alert_id END) AS new_event_count,
            t.policy_type,
            t.policy_id,
            t.alert_id
            FROM ", SUBSTRING(session_table_name, 1, 10), "events AS t  
            WHERE t.session_id >= ", from_id, " 
            AND t.session_id <=", to_id, " 
            AND t.record_date <> '0000-00-00'
            GROUP BY
            record_hour, 
            record_minute, 
            t.behavior_type_id,
            t.policy_type,
            t.policy_id,
            t.alert_id) AS TT
        ON duplicate KEY UPDATE
        event_count = event_count + TT.new_event_count;");
    PREPARE stats_stmt FROM @stats_sql;
    EXECUTE stats_stmt;
    DEALLOCATE PREPARE stats_stmt;
END
//
delimiter ;

delimiter //
DROP PROCEDURE IF EXISTS stats_alert_netobject_stats //
CREATE PROCEDURE stats_alert_netobject_stats
(session_table_name varchar(64), from_id int unsigned, to_id int unsigned)
BEGIN
    SET @stats_sql = CONCAT("INSERT INTO alert_netobject_stats 
        (netobject,
        ip,
        record_date, 
        record_hour, 
        record_minute,
        behavior_type_id,
        event_count,
        policy_type,
        policy_id,
        alert_id) 
        SELECT * FROM (
            SELECT t.netobject_a as netobject,
            t.ip_a as ip,
            t.record_date, 
            '00' AS record_hour,
            '00' AS record_minute,
            t.behavior_type_id,
            COUNT(CASE WHEN t.alert_id > 0 THEN t.alert_id END) AS new_event_count,
            t.policy_type,
            t.policy_id,
            t.alert_id
            FROM ", SUBSTRING(session_table_name, 1, 10), "events AS t  
            WHERE t.session_id >= ", from_id, " 
            AND t.session_id <=", to_id, " 
            AND t.netobject_a > 1
            AND t.record_date <> '0000-00-00'
            GROUP BY 
            t.netobject_a,
            t.ip_a,
            t.behavior_type_id,
            t.policy_type,
            t.policy_id,
            t.alert_id) AS TT
        ON duplicate KEY UPDATE
        event_count = event_count + TT.new_event_count;");
    PREPARE stats_stmt FROM @stats_sql;
    EXECUTE stats_stmt;
    DEALLOCATE PREPARE stats_stmt;

    SET @stats_sql = CONCAT("INSERT INTO alert_netobject_stats 
        (netobject,
        ip,
        record_date, 
        record_hour, 
        record_minute,
        behavior_type_id, 
        event_count,
        policy_type,
        policy_id,
        alert_id) 
        SELECT * FROM (
            SELECT t.netobject_b as netobject,
            t.ip_b as ip,
            t.record_date, 
            '00' AS record_hour,
            '00' AS record_minute,
            t.behavior_type_id,
            COUNT(CASE WHEN t.alert_id > 0 THEN t.alert_id END) AS new_event_count,
            t.policy_type,
            t.policy_id,
            t.alert_id
            FROM ", SUBSTRING(session_table_name, 1, 10), "events AS t  
            WHERE t.session_id >= ", from_id, " 
            AND t.session_id <=", to_id, " 
            AND t.netobject_b > 1
            AND t.record_date <> '0000-00-00'
            GROUP BY 
            t.netobject_b,
            t.ip_b,
            t.behavior_type_id,
            t.policy_type,
            t.policy_id,
            t.alert_id) AS TT
        ON duplicate KEY UPDATE
        event_count = event_count + TT.new_event_count;");
    PREPARE stats_stmt FROM @stats_sql;
    EXECUTE stats_stmt;
    DEALLOCATE PREPARE stats_stmt;

END
//
delimiter ;

delimiter //
DROP PROCEDURE IF EXISTS `stats_controller`//
CREATE PROCEDURE `stats_controller`()
BEGIN
#sync data table and stats_info -------------------------------------------------------------------
    DELETE FROM stats_info WHERE table_name NOT IN (
        SELECT TABLE_NAME FROM information_schema.TABLES 
        WHERE TABLE_SCHEMA = 'netaudit_db' AND table_name regexp "z[0-9]{8}_sessions");
    INSERT INTO stats_info SELECT table_name, 0 FROM information_schema.TABLES
        WHERE TABLE_SCHEMA = 'netaudit_db' AND table_name regexp "z[0-9]{8}_sessions"
        ON DUPLICATE KEY UPDATE stats_counter = stats_counter;
#for every row in stats_info ----------------------------------------------------------------------
    SELECT 0, count(*) INTO @stats_process_i, @stats_process_n FROM `stats_info`;
    WHILE(@stats_process_i < @stats_process_n)
    DO
        select '-----', @stats_process_i, @stats_process_n;
#get table name and stats_count -------------------------------------------------------------------
        PREPARE stats_process_stmt FROM "
            SELECT table_name, stats_counter 
            INTO @stats_process_table_name, @stats_process_stats_last_id
            FROM stats_info limit ?,1;";
        EXECUTE stats_process_stmt USING @stats_process_i;
        DEALLOCATE PREPARE stats_process_stmt;
#check table ---------------------------------------------------------------------------------------
        SET @stats_process_row_count = NULL;
        SET @stats_process_sql = CONCAT('select session_id
            into @stats_process_row_count
            from ', @stats_process_table_name, ' limit 1');
        PREPARE stats_process_stmt FROM @stats_process_sql;
        EXECUTE stats_process_stmt;
        DEALLOCATE PREPARE stats_process_stmt;
        select '-----', @stats_process_table_name, @stats_process_row_count;
        IF (@stats_process_row_count is not null)
        THEN
#get alert_count ------------------------------------------------------------------------------------
            SET @stats_process_sql = CONCAT('select alert_counter
                into @stats_process_alert_last_id
                from alert_info 
                where table_name = \'', @stats_process_table_name,'\'');
            PREPARE stats_process_stmt FROM @stats_process_sql;
            EXECUTE stats_process_stmt;
            DEALLOCATE PREPARE stats_process_stmt;
#get from_id and to_id ------------------------------------------------------------------------------
            SET @stats_process_from_id = @stats_process_stats_last_id + 1;
            SET @stats_process_to_id = @stats_process_alert_last_id ;
            select '-----', @stats_process_to_id, @stats_process_from_id;
            IF (@stats_process_to_id >= @stats_process_from_id)
            THEN
                IF (@stats_process_to_id - @stats_process_from_id > 100000)
                THEN
                    SET @stats_process_to_id = @stats_process_from_id + 100000;
                END IF;
#call every stats procedure -------------------------------------------------------------------------
                select '----- ', @stats_process_table_name, @stats_process_from_id, @stats_process_to_id, ' -----';
                select '----- call stats_session_stats';
                call stats_session_stats(@stats_process_table_name, @stats_process_from_id, @stats_process_to_id);
                select '----- call stats_session_netobject_stats';
                call stats_session_netobject_stats(@stats_process_table_name, @stats_process_from_id, @stats_process_to_id);
                select '----- call stats_traffic_behavior_stats';
                call stats_traffic_behavior_stats(@stats_process_table_name, @stats_process_from_id, @stats_process_to_id);
                select '----- call stats_alert_stats';
                call stats_alert_stats(@stats_process_table_name, @stats_process_from_id, @stats_process_to_id);
                select '----- call stats_alert_netobject_stats';
                call stats_alert_netobject_stats(@stats_process_table_name, @stats_process_from_id, @stats_process_to_id);
#update counter --------------------------------------------------------------------------------------
                SET @stats_process_sql = CONCAT('update stats_info
                    set stats_counter = @stats_process_to_id 
                    where table_name = \'', @stats_process_table_name,'\'');
                PREPARE stats_process_stmt FROM @stats_process_sql;
                EXECUTE stats_process_stmt;
                DEALLOCATE PREPARE stats_process_stmt;
            END IF;
        END IF;
#move to next session table ---------------------------------------------------------------------------
        SET @stats_process_i = @stats_process_i + 1;
    END WHILE;
END
//
delimiter ;

delimiter //
DROP PROCEDURE IF EXISTS `stats_process`//
CREATE PROCEDURE `stats_process`()
BEGIN
    SELECT IS_FREE_LOCK('netaudit_event_stats_lock') INTO @netaudit_event_stats_lock_isfree;
    IF (@netaudit_event_stats_lock_isfree = 1) THEN
        SELECT GET_LOCK('netaudit_event_stats_lock', 10) INTO @netaudit_event_stats_lock_islocked;
        IF (@netaudit_event_stats_lock_islocked = 1) THEN
            CALL stats_controller();
        END IF;
        SELECT RELEASE_LOCK('netaudit_event_stats_lock');
    END IF;
END //
delimiter ;

delimiter //
DROP PROCEDURE IF EXISTS `stats_delete_past_data`//
CREATE PROCEDURE `stats_delete_past_data`()
BEGIN

#delete past data ---------------------------------------------------------------------------------
    delete from session_stats where to_days(now()) - to_days(record_date) > (
        select value from configs where name='data_save_days');
    delete from session_netobject_stats where to_days(now()) - to_days(record_date) > (
        select value from configs where name='data_save_days');
    delete from traffic_behavior_stats where to_days(now()) - to_days(record_date) > (
        select value from configs where name='data_save_days');
    delete from traffic_stats where to_days(now()) - to_days(record_date) > (
        select value from configs where name='data_save_days');
    delete from alert_stats where to_days(now()) - to_days(record_date) > (
        select value from configs where name='data_save_days');
    delete from alert_netobject_stats where to_days(now()) - to_days(record_date) > (
        select value from configs where name='data_save_days');
END
// 
delimiter ;

delimiter //
DROP PROCEDURE IF EXISTS `stats_process`//
CREATE PROCEDURE `stats_process`()
BEGIN
    SELECT IS_FREE_LOCK('netaudit_event_stats_lock') INTO @netaudit_event_stats_lock_isfree;
    IF (@netaudit_event_stats_lock_isfree = 1) THEN
        SELECT GET_LOCK('netaudit_event_stats_lock', 10) INTO @netaudit_event_stats_lock_islocked;
        IF (@netaudit_event_stats_lock_islocked = 1) THEN
            CALL stats_controller();
        END IF;
        SELECT RELEASE_LOCK('netaudit_event_stats_lock');
    END IF;
END //
delimiter ;

delimiter //
SET GLOBAL event_scheduler = OFF //
DROP EVENT IF EXISTS `event_stats`//
CREATE EVENT IF NOT EXISTS `event_stats`
ON SCHEDULE EVERY 10 SECOND
ON COMPLETION PRESERVE
DO
   BEGIN
       call stats_process;
   END //
delimiter ;

SET GLOBAL event_scheduler = ON;
