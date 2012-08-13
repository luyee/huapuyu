SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS tb_poll;
CREATE TABLE tb_poll (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  title varchar(50) NOT NULL,
  remark varchar(50) NOT NULL,
  enabled bit(1) NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tb_poll_item;
CREATE TABLE tb_poll_item (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  title varchar(50) NOT NULL,
  enabled bit(1) NOT NULL DEFAULT TRUE,
  poll_id bigint(20) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
