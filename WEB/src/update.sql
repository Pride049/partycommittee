﻿ALTER TABLE  `pc_agency` ADD INDEX (  `code_id` ) ;

ALTER TABLE  `pc_agency_relation` ADD INDEX (  `agency_id` ) ;
ALTER TABLE  `pc_agency_relation` ADD INDEX (  `parent_id` ) ;

ALTER TABLE  `pc_workplan` ADD INDEX (  `agency_id` ,  `type_id` ,  `year` ,  `quarter` ) ;
ALTER TABLE  `pc_meeting` ADD INDEX (  `agency_id` ,  `year` ,  `quarter` ,  `type_id` ) ;

ALTER TABLE  `pc_remind_config` ADD  `start_year` TINYINT( 1 ) NOT NULL ,
ADD  `start_quarter` TINYINT( 1 ) UNSIGNED NOT NULL ,
ADD  `start_month` SMALLINT( 5 ) UNSIGNED NOT NULL ,
ADD  `start_day` SMALLINT( 5 ) UNSIGNED NOT NULL ,
ADD  `end_year` TINYINT( 1 ) NOT NULL ,
ADD  `end_quarter` TINYINT( 1 ) UNSIGNED NOT NULL ,
ADD  `end_month` SMALLINT( 5 ) UNSIGNED NOT NULL ,
ADD  `end_day` SMALLINT( 5 ) UNSIGNED NOT NULL

将pc_workplan status = 2 变成  status = 1  
将pc_workplan status = 1 变成  status = 2

设置my.ini
event_scheduler=ON

--
-- 表的结构 `pc_agency_stat`
--

DROP TABLE IF EXISTS `pc_agency_stat`;
CREATE TABLE IF NOT EXISTS `pc_agency_stat` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `agency_id` int(11) unsigned NOT NULL COMMENT '党支部ID',
  `name` varchar(255) NOT NULL COMMENT '党支部名称',
  `code_id` int(11) NOT NULL DEFAULT '0' COMMENT '党支部类型',
  `parent_id` int(11) unsigned NOT NULL COMMENT '上级党支部ID',
  `year` year(4) NOT NULL COMMENT '年度',
  `quarter` tinyint(1) unsigned NOT NULL COMMENT '季度',
  `type_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '年度计划状态',
  `reported` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '规范执行情况',
  `delay` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '补开补报情况',
  `attend` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '应出席人数',
  `asence` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '缺席人数',
  `p_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '党小组数',
  `zb_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '支部人数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `agency_id` (`agency_id`,`code_id`,`parent_id`,`year`,`quarter`,`type_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COMMENT='党支部提醒统计表' AUTO_INCREMENT=15529 ;



SELECT T2.parent_name as '上级党支部', T2.name as '党支部名称', T2.code as  '党支部代码',T1.name  as '姓名', T3.description as '性别', T4.description as '民族',
DATE_FORMAT( T1.birthday,  '%Y-%m-%d' )  as '出生日期', DATE_FORMAT( T1.workday,  '%Y-%m-%d' )  as '参加工作年月',  DATE_FORMAT( T1.joinday,  '%Y-%m-%d' ) as '入党时间',
T5.description as '文化程度', T6.description as '行政职务', DATE_FORMAT( T1.postday,  '%Y-%m-%d' ) as '任党内职务时间', T1.birthplace as '籍贯', T1.address as '现在家庭住址'
FROM pc_member as T1 LEFT JOIN 
(select d.name as parent_name, d.id as parent_id, c.name,c.id, c.code FROM
(select a.id, a.name, a.code, b.parent_id from pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id) as c 
LEFT JOIN  pc_agency as d on c.parent_id = d.id) as T2 ON T1.agency_id = T2.id
LEFT JOIN  pc_sex_code as T3 ON T1.sex_id = T3.id
LEFT JOIN  pc_nation_code as T4 on T1.nation_id = T4.id
LEFT JOIN  pc_edu_code as T5 on T1.edu_id = T5.id
LEFT JOIN  pc_duty_code as T6 on T1.duty_id = T6.id




SELECT T2.parent_name as '上级党支部', T2.name as '党支部名称', T2.code as  '党支部代码',T1.name  as '名称', T3.description as '性别', T4.description as '民族'  FROM pc_member as T1 LEFT JOIN 
(select d.name as parent_name, d.id as parent_id, c.name,c.id, c.code FROM
(select a.id, a.name, a.code, b.parent_id from pc_agency as a left join pc_agency_relation as b on a.id = b.agency_id) as c 
left join pc_agency as d on c.parent_id = d.id) as T2 ON T1.agency_id = T2.id
left join pc_sex_code` as T3 ON T1.sex_id = T3.id
left join pc_nation_code as T4 on T1.nation_id = T4.id
 

SELECT * FROM `pc_workplan` WHERE agency_id = 18 and type_id = 1

SELECT * FROM
(SELECT agency_id, YEAR, quarter, type_id, COUNT( * )  as c
FROM  `pc_workplan` 
WHERE type_id <=4
GROUP BY agency_id, YEAR, quarter, type_id) as T
where c > 1