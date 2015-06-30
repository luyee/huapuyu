DROP TABLE IF EXISTS `rlt_company_account`;
CREATE TABLE `rlt_company_account` (
  `company_id` bigint(20) NOT NULL,
  `account_id` bigint(20) NOT NULL,
  KEY `idx_companyid` (`company_id`),
  KEY `idx_accountid` (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `enable` bit(1) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_company`;
CREATE TABLE `tb_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `company_info_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_companyinfoid` (`company_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_company_info`;
CREATE TABLE `tb_company_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `info` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_department`;
CREATE TABLE `tb_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_companyid` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `tb_company_info` VALUES ('1', 'maizu');

INSERT INTO `tb_company` VALUES ('1', 'anders', '1');

INSERT INTO `tb_department` VALUES ('1', 'soft', '1');
INSERT INTO `tb_department` VALUES ('2', 'sell', '1');

INSERT INTO `tb_account` VALUES ('1', true, 'zhuzhen');
INSERT INTO `tb_account` VALUES ('2', true, 'guolili');

INSERT INTO `rlt_company_account` VALUES ('1', '2');
INSERT INTO `rlt_company_account` VALUES ('1', '1');