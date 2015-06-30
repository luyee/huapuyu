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
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  UNIQUE KEY name (name),
  UNIQUE KEY role (role),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '角色';

DROP TABLE IF EXISTS tb_url;
CREATE TABLE tb_url (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  name varchar(50) NOT NULL COMMENT '名称',
  url varchar(50) NOT NULL COMMENT 'URL',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  UNIQUE KEY name (name),
  UNIQUE KEY url (url),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'URL';

DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键', 
  user_name varchar(50) NOT NULL COMMENT '账号',
  password varchar(64) NOT NULL COMMENT '密码',
  name varchar(50) NOT NULL COMMENT '姓名',
  email varchar(50) NOT NULL COMMENT '邮箱',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  UNIQUE KEY email (email),
  UNIQUE KEY user_name (user_name),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户';

DROP TABLE IF EXISTS tb_user_group;
CREATE TABLE tb_user_group (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  name varchar(50) NOT NULL COMMENT '名称',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  UNIQUE KEY name (name),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户组';

DROP TABLE IF EXISTS tb_poll;
CREATE TABLE tb_poll (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  title varchar(50) NOT NULL COMMENT '标题',
  remark varchar(500) NOT NULL COMMENT '备注',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (id),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '投票';

DROP TABLE IF EXISTS tb_poll_option;
CREATE TABLE tb_poll_option (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  title varchar(50) NOT NULL COMMENT '标题',
  enabled bit(1) NOT NULL DEFAULT TRUE COMMENT '启用（1或true：启用；0或false：禁用）',
  add_user_id bigint(20) NOT NULL COMMENT '新增人ID',
  update_user_id bigint(20) DEFAULT NULL COMMENT '更新人ID',
  add_time datetime NOT NULL COMMENT '新增时间',
  update_time datetime DEFAULT NULL COMMENT '更新时间',
  version int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁',
  poll_id bigint(20) NOT NULL COMMENT '投票ID',
  PRIMARY KEY (id),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '投票选项';
