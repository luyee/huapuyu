CREATE DATABASE vote;
CREATE DATABASE vote_dev;
CREATE DATABASE vote_test;

DROP TABLE IF EXISTS tb_poll;
CREATE TABLE tb_poll (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  title varchar(50) NOT NULL,
  remark varchar(255) DEFAULT NULL,
  create_time datetime NOT NULL,
  update_time datetime DEFAULT NULL,
  enable tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tb_poll_item;
CREATE TABLE tb_poll_item (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  title varchar(50) NOT NULL,
  enable tinyint(1) NOT NULL DEFAULT 1,
  poll_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY (poll_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tb_poll` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `create_time` datetime NOT NULL,
  `enable` bit(1) NOT NULL,
  `remark` varchar(100) NOT NULL,
  `title` varchar(50) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8