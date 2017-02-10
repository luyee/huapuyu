
DROP TABLE IF EXISTS `rlt_cust_to_contact`;
CREATE TABLE `rlt_cust_to_contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cust_id` bigint(20) NOT NULL,
  `contact_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `k_contactid` (`contact_id`),
  KEY `k_custid` (`cust_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_contact`;
CREATE TABLE `tb_contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `add_time` datetime NOT NULL,
  `enabled` bit(1) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `add_user_id` bigint(20) DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `k_updateuserid` (`update_user_id`),
  KEY `k_adduserid` (`add_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_contact_info`;
CREATE TABLE `tb_contact_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `add_time` datetime NOT NULL,
  `enabled` bit(1) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `info` varchar(50) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `add_user_id` bigint(20) DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  `contact_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `k_contactid` (`contact_id`),
  KEY `k_updateuserid` (`update_user_id`),
  KEY `k_adduserid` (`add_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_cust`;
CREATE TABLE `tb_cust` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `add_time` datetime NOT NULL,
  `enabled` bit(1) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `type` int(11) NOT NULL,
  `website` varchar(100) DEFAULT NULL,
  `add_user_id` bigint(20) DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `k_updateuserid` (`update_user_id`),
  KEY `k_adduserid` (`add_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


