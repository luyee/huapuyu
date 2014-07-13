SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS rlt_role_to_url;
CREATE TABLE rlt_role_to_url (
  role_id bigint(20) NOT NULL COMMENT '角色ID',
  url_id bigint(20) NOT NULL  COMMENT 'URL ID',
  PRIMARY KEY (role_id,url_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色与URL对应关系';

DROP TABLE IF EXISTS rlt_user_group_to_role;
CREATE TABLE rlt_user_group_to_role (
  user_group_id bigint(20) NOT NULL COMMENT '用户组ID',
  role_id bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (user_group_id,role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户组与角色对应关系';

DROP TABLE IF EXISTS rlt_user_to_role;
CREATE TABLE rlt_user_to_role (
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  role_id bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (user_id,role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户与角色对应关系';

DROP TABLE IF EXISTS rlt_user_to_user_group;
CREATE TABLE rlt_user_to_user_group (
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  user_group_id bigint(20) NOT NULL COMMENT '用户组ID',
  PRIMARY KEY (user_id,user_group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户与用户组对应关系';

DROP TABLE IF EXISTS tb_role;
CREATE TABLE tb_role (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  name varchar(50) NOT NULL COMMENT '名称',
  role varchar(50) NOT NULL COMMENT '标签',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  UNIQUE KEY uk_name (name),
  UNIQUE KEY uk_role (role),
  KEY k_adduserid (add_user_id),
  KEY k_updateuserid (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色';

DROP TABLE IF EXISTS tb_url;
CREATE TABLE tb_url (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  name varchar(50) NOT NULL COMMENT '名称',
  url varchar(50) NOT NULL COMMENT 'URL',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  UNIQUE KEY uk_name (name),
  UNIQUE KEY uk_url (url),
  KEY k_adduserid (add_user_id),
  KEY k_updateuserid (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'URL';

DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键', 
  user_name varchar(50) NOT NULL COMMENT '用户名',
  password varchar(50) NOT NULL COMMENT '密码',
  name varchar(50) NOT NULL COMMENT '姓名',
  email varchar(50) NOT NULL COMMENT '邮箱',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  UNIQUE KEY uk_email (email),
  UNIQUE KEY uk_username (user_name),
  KEY k_adduserid (add_user_id),
  KEY k_updateuserid (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户';

DROP TABLE IF EXISTS tb_user_group;
CREATE TABLE tb_user_group (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  name varchar(50) NOT NULL COMMENT '名称',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  UNIQUE KEY uk_name (name),
  KEY k_adduserid (add_user_id),
  KEY k_updateuserid (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户组';



DROP TABLE IF EXISTS `rlt_cust_to_contact`;
CREATE TABLE `rlt_cust_to_contact` (
  `cust_id` bigint(20) NOT NULL COMMENT '客户ID',
  `contact_id` bigint(20) NOT NULL COMMENT '联系人ID',
  PRIMARY KEY (cust_id,contact_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '客户与联系人对应关系';

DROP TABLE IF EXISTS `tb_contact`;
CREATE TABLE `tb_contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `title` varchar(50) DEFAULT NULL COMMENT '称谓',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  KEY `k_adduserid` (`add_user_id`),
  KEY `k_updateuserid` (`update_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '联系人';

DROP TABLE IF EXISTS `tb_contact_info`;
CREATE TABLE `tb_contact_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` tinyint(4) NOT NULL COMMENT '类型',
  `info` varchar(50) NOT NULL COMMENT '内容',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `contact_id` bigint(20) DEFAULT NULL COMMENT '联系人ID',
  PRIMARY KEY (`id`),
  KEY `k_adduserid` (`add_user_id`),
  KEY `k_updateuserid` (`update_user_id`),
  KEY `k_contactid` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '联系方式';

DROP TABLE IF EXISTS `tb_cust`;
CREATE TABLE `tb_cust` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` tinyint(4) NOT NULL COMMENT '类型',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `website` varchar(100) DEFAULT NULL COMMENT '网站',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (`id`),
  KEY `k_adduserid` (`add_user_id`),
  KEY `k_updateuserid` (`update_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '客户';