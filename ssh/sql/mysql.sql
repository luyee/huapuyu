CREATE DATABASE `rems`;

CREATE TABLE `cfg_area` 
(
  `id` int(11) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '类型（0：省、自治区、直辖市；1：城市；2：区、县、市）',
  `enable` bit(1) NOT NULL DEFAULT 1 COMMENT '启用符（1：启用；0：停用）',
  `parent_id` int(11) COMMENT '父编号',
  PRIMARY KEY (`id`)
) COMMENT '区域配置';

CREATE TABLE `cfg_data` 
(
  `id` int(11) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `type` tinyint(4) NOT NULL COMMENT '类型（1：产权性质；2：朝向；3：物业类型；4：住宅类别；5：建筑类别；6：房屋结构；7：装修程度；8：看房时间；9：配套设施；10：房源特色；11：建筑年代；12：合租方式；13：合租人性别要求；14：支付方式；15：入住时间；16：房屋配套）',
  `enable` bit(1) NOT NULL DEFAULT 1 COMMENT '启用符（1：启用；0：停用）',
  PRIMARY KEY (`id`)
) COMMENT '数据配置';

CREATE TABLE `tb_house` 
(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '楼盘名称',
  `province_id` int(11) NOT NULL COMMENT '省、自治区、直辖市编号（对应区域配置表类型0）',
  `city_id` int(11) NOT NULL COMMENT '城市编号（对应区域配置表类型1）',
  `district_id` int(11) NOT NULL COMMENT '区、县、市编号（对应区域配置表类型2）',
  `address` varchar(100) NOT NULL COMMENT '详细地址',
  `bedroom_count` tinyint(4) NOT NULL COMMENT '室',
  `living_room_count` tinyint(4) NOT NULL COMMENT '厅',
  `kitchen_count` tinyint(4) NOT NULL COMMENT '厨',
  `washroom_count` tinyint(4) NOT NULL COMMENT '卫',
  `balcony_count` tinyint(4) NOT NULL COMMENT '阳台',
  `prop_mgt_type_id` int(11) COMMENT '物业类型编号（对应数据配置表类型3）',
  `direction_id` int(11) COMMENT '朝向编号（对应数据配置表类型2）',
  `decoration_id` int(11) COMMENT '装修程度编号（对应数据配置表类型7）',
  `total_floor` tinyint(4) COMMENT '总楼层',
  `floor` tinyint(4) COMMENT '所在楼层',
  `construct_year_id` int(11) COMMENT '建筑年代编号（对应数据配置表类型11）',
  `transport` longtext COMMENT '交通状况',
  `environment` longtext COMMENT '周边配套',
  `remark` longtext COMMENT '房源描述',
  `rent_house_id` bigint(20) COMMENT '出租房编号',
  `second_hand_house_id` bigint(20) COMMENT '二手房编号',
  PRIMARY KEY (`id`)
) COMMENT '房屋';

CREATE TABLE `tb_rent_house` 
(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `price` decimal(7,2) COMMENT '租金',
  `area` decimal(7,2) COMMENT '出租面积',
  `type` tinyint(4) COMMENT '租赁方式（0：整租；1：合租）',
  `share_type_id` int(11) COMMENT '合租方式编号（对应数据配置表类型12）',
  `roommate_gender_id` int(11) COMMENT '合租人性别要求编号（对应数据配置表类型13）',
  `payment_id` int(11) COMMENT '支付方式编号（对应数据配置表类型14）',
  `check_in_id` int(11) COMMENT '入住时间编号（对应数据配置表类型15）',
  PRIMARY KEY (`id`)
) COMMENT '出租房';

CREATE TABLE `tb_second_hand_house` 
(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `price` decimal(7,2) COMMENT '售价',
  `building_area` decimal(7,2) COMMENT '建筑面积',
  `usable_area` decimal(7,2) COMMENT '使用面积',
  `propety_id` int(11) COMMENT '产权性质编号（对应数据配置表类型1）',
  `prop_type_id` int(11) COMMENT '住宅类别编号（对应数据配置表类型4）',
  `prop_struct_id` int(11) COMMENT '房屋结构编号（对应数据配置表类型6）',
  `construct_type_id` int(11) COMMENT '建筑类别编号（对应数据配置表类型5）',
  `visit_time_id` int(11) COMMENT '看房时间编号（对应数据配置表类型8）',
  PRIMARY KEY (`id`)
) COMMENT '二手房';
