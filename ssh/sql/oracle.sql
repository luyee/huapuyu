--创建表空间
CREATE TABLESPACE rems 
DATAFILE 'rems.dat' SIZE 10M
AUTOEXTEND ON NEXT 1M MAXSIZE UNLIMITED;

--创建用户
CREATE USER rems PROFILE DEFAULT 
IDENTIFIED BY "123" DEFAULT TABLESPACE rems
TEMPORARY TABLESPACE TEMP 
ACCOUNT UNLOCK;

--授权
GRANT CONNECT TO rems;

GRANT RESOURCE TO rems;

GRANT DBA TO rems;

--创建日志序列
CREATE SEQUENCE seq_log4j;

--创建日志表
CREATE TABLE tb_log4j
(
	id INT PRIMARY KEY,
	log_time VARCHAR2(50),
	log_logger VARCHAR2(50),
	log_level VARCHAR2(50),
	log_msg VARCHAR2(500),
	log_source VARCHAR2(50)
);

CREATE TABLE tb_user
(
	id INT PRIMARY KEY,
	name VARCHAR2(20),
	pwd VARCHAR2(20)
);

CREATE TABLE tb_depart
(
	id INT PRIMARY KEY,
	name VARCHAR2(20),
	enabled INT DEFAULT 1 NOT NULL
);
COMMENT ON COLUMN tb_depart.enabled IS '是否启用（1：启用；0：停用）';
