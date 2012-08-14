SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS rlt_role_to_url;
CREATE TABLE rlt_role_to_url (
  role_id bigint(20) NOT NULL,
  url_id bigint(20) NOT NULL,
  PRIMARY KEY (role_id,url_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS rlt_user_group_to_role;
CREATE TABLE rlt_user_group_to_role (
  user_group_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  PRIMARY KEY (user_group_id,role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS rlt_user_to_role;
CREATE TABLE rlt_user_to_role (
  user_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  PRIMARY KEY (user_id,role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS rlt_user_to_user_group;
CREATE TABLE rlt_user_to_user_group (
  user_id bigint(20) NOT NULL,
  user_group_id bigint(20) NOT NULL,
  PRIMARY KEY (user_id,user_group_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tb_role;
CREATE TABLE tb_role (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  role varchar(50) NOT NULL,
  enabled bit(1) NOT NULL DEFAULT TRUE,
  add_user_id bigint(20) NOT NULL,
  update_user_id bigint(20) DEFAULT NULL,
  add_time datetime NOT NULL,
  update_time datetime DEFAULT NULL,
  version int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name),
  UNIQUE KEY role (role),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tb_url;
CREATE TABLE tb_url (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  url varchar(50) NOT NULL,
  enabled bit(1) NOT NULL DEFAULT TRUE,
  add_user_id bigint(20) NOT NULL,
  update_user_id bigint(20) DEFAULT NULL,
  add_time datetime NOT NULL,
  update_time datetime DEFAULT NULL,
  version int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name),
  UNIQUE KEY url (url),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tb_user;
CREATE TABLE tb_user (
  id bigint(20) NOT NULL AUTO_INCREMENT, 
  user_name varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  enabled bit(1) NOT NULL DEFAULT TRUE,
  add_user_id bigint(20) NOT NULL,
  update_user_id bigint(20) DEFAULT NULL,
  add_time datetime NOT NULL,
  update_time datetime DEFAULT NULL,
  version int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY email (email),
  UNIQUE KEY user_name (user_name),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tb_user_group;
CREATE TABLE tb_user_group (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  enabled bit(1) NOT NULL DEFAULT TRUE,
  add_user_id bigint(20) NOT NULL,
  update_user_id bigint(20) DEFAULT NULL,
  add_time datetime NOT NULL,
  update_time datetime DEFAULT NULL,
  version int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY name (name),
  KEY idx_add_user_id (add_user_id),
  KEY idx_update_user_id (update_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;