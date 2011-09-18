CREATE DATABASE vote;
CREATE DATABASE vote_dev;
CREATE DATABASE vote_test;

DROP TABLE IF EXISTS tb_poll;
CREATE TABLE tb_poll (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  title varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tb_poll_item;
CREATE TABLE tb_poll_item (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  title varchar(50) DEFAULT NULL,
  poll_id bigint(20) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY (poll_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;