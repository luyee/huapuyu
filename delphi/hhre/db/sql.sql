/*
夯夯房产中介管理系统 数据库
*/

/* 用户表 */
CREATE TABLE tb_user (
  id INTEGER NOT NULL,
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
);

/* 用户序列 */
CREATE SEQUENCE seq_user;
ALTER SEQUENCE seq_user RESTART WITH 1;

SET TERM ^ ;

CREATE TRIGGER trig_user FOR tb_user
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW.id IS NULL) THEN 
	NEW.id = GEN_ID(seq_user, 1);
END^

SET TERM ; ^

/* 买家表 */
CREATE TABLE tb_buyer
(
id INTEGER NOT NULL,
name VARCHAR(50) NOT NULL,
sex CHAR DEFAULT '1', /* 1：男；0：女 */
age SMALLINT,
province_id SMALLINT,
credential_id SMALLINT,
credential_number VARCHAR(50),
phone1 VARCHAR(20),
phone2 VARCHAR(20),
phone3 VARCHAR(20),
mobile1 VARCHAR(20),
mobile2 VARCHAR(20),
mobile3 VARCHAR(20),
email1 VARCHAR(50),
email2 VARCHAR(50),
email3 VARCHAR(50),
address1 VARCHAR(500),
address2 VARCHAR(500),
address3 VARCHAR(500),

price NUMERIC,
max_price NUMERIC,
min_price NUMERIC,
bedroom_count SMALLINT,
drawing_room_count SMALLINT,
kitchen_count SMALLINT,
washroom_count SMALLINT,
area NUMERIC,
max_area NUMERIC,
min_area NUMERIC,
structure_id SMALLINT,
floor_request_id SMALLINT,
是否要自行车库
是否要汽车库
是否要自行车位
是否要汽车位
是否是学区房
附件是否有大超市
附件是否有地铁
附件是否有公共交通

PRIMARY KEY (id)
)

/* 区域表 */
CREATE TABLE cfg_area
(
id SMALLINT NOT NULL,
name VARCHAR(50) NOT NULL,
type CHAR DEFAULT '0', /* 0：省、自治区、直辖市；1：城市；2：区、县、市 */
parent_id SMALLINT,
enable CHAR DEFAULT '1', /* 1：可用；0：不可用 */
PRIMARY KEY (id)
)

INSERT INTO cfg_area (id, name) VALUES (111, '上海');
INSERT INTO cfg_area (id, name) VALUES (112, '山东');
INSERT INTO cfg_area (id, name) VALUES (113, '广东');
INSERT INTO cfg_area (id, name) VALUES (114, '海南');
INSERT INTO cfg_area (id, name) VALUES (115, '内蒙古');
INSERT INTO cfg_area (id, name) VALUES (116, '陕西');
INSERT INTO cfg_area (id, name) VALUES (117, '青海');
INSERT INTO cfg_area (id, name) VALUES (118, '四川');
INSERT INTO cfg_area (id, name) VALUES (119, '黑龙江');
INSERT INTO cfg_area (id, name) VALUES (120, '湖北');
INSERT INTO cfg_area (id, name) VALUES (121, '湖南');
INSERT INTO cfg_area (id, name) VALUES (122, '吉林');
INSERT INTO cfg_area (id, name) VALUES (123, '辽宁');
INSERT INTO cfg_area (id, name) VALUES (124, '天津');
INSERT INTO cfg_area (id, name) VALUES (125, '山西');
INSERT INTO cfg_area (id, name) VALUES (126, '河北');
INSERT INTO cfg_area (id, name) VALUES (127, '新疆');
INSERT INTO cfg_area (id, name) VALUES (128, '河南');
INSERT INTO cfg_area (id, name) VALUES (129, '安徽');
INSERT INTO cfg_area (id, name) VALUES (130, '江苏');
INSERT INTO cfg_area (id, name) VALUES (131, '甘肃');
INSERT INTO cfg_area (id, name) VALUES (132, '宁夏');
INSERT INTO cfg_area (id, name) VALUES (133, '云南');
INSERT INTO cfg_area (id, name) VALUES (134, '浙江');
INSERT INTO cfg_area (id, name) VALUES (135, '福建');
INSERT INTO cfg_area (id, name) VALUES (136, '贵州');
INSERT INTO cfg_area (id, name) VALUES (137, '江西');
INSERT INTO cfg_area (id, name) VALUES (138, '广西');
INSERT INTO cfg_area (id, name) VALUES (139, '西藏');
INSERT INTO cfg_area (id, name) VALUES (140, '重庆');
INSERT INTO cfg_area (id, name) VALUES (141, '香港');
INSERT INTO cfg_area (id, name) VALUES (142, '台湾');
INSERT INTO cfg_area (id, name) VALUES (143, '澳门');


INSERT INTO cfg_area (id, name, type, parent_id) VALUES (251, '南京', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250002, '苏州', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250003, '无锡', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250004, '南通', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250005, '常州', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250006, '镇江', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250007, '扬州', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250008, '淮安', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250009, '连云港', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250010, '泰州', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250011, '徐州', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250012, '盐城', '2', 16);
INSERT INTO cfg_area (id, name, type, parent_id) VALUES (250013, '宿迁', '2', 16);

CREATE TABLE CFG_DATA (
  ID      SMALLINT NOT NULL,
  NAME    VARCHAR(50) NOT NULL,
  "TYPE"  VARCHAR(3) NOT NULL, /* 1:产权性质;2:朝向;3:物业类型;4:住宅类别;5:建筑类别;6:房屋结构;7:装修程度;8:看房时间;9:配套设施;10:房源特色;11:建筑年代 */
  ENABLE  CHAR DEFAULT '1',
  /* Keys */
  PRIMARY KEY (ID)
);

/* 证件表 */
CREATE TABLE cfg_credential
(
id SMALLINT NOT NULL,
name VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
)

INSERT INTO cfg_credential (id, name) VALUES (1, '省份证');

/* 楼层要求表 */
CREATE TABLE cfg_floor_request
(
id SMALLINT NOT NULL,
name VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
)

INSERT INTO cfg_floor_request (id, name) VALUES (1, '所有楼层都可以');
INSERT INTO cfg_floor_request (id, name) VALUES (2, '不要底楼');
INSERT INTO cfg_floor_request (id, name) VALUES (3, '不要顶楼');
INSERT INTO cfg_floor_request (id, name) VALUES (4, '不要底楼和顶楼');

/* 房屋结构表 */
CREATE TABLE cfg_structure
(
id SMALLINT NOT NULL,
name VARCHAR(50) NOT NULL,
PRIMARY KEY (id)
)

INSERT INTO cfg_structure (id, name) VALUES (1, '两室朝南');

/* 二手房表 */
CREATE TABLE tb_shh
(
id INTEGER NOT NULL,
name VARCHAR(50) NOT NULL,
province_id SMALLINT NOT NULL,
city_id SMALLINT NOT NULL,
borough_id SMALLINT NOT NULL,
address VARCHAR(100) NOT NULL,
price NUMERIC NOT NULL,
building_area NUMERIC NOT NULL,
usable_area NUMERIC NOT NULL,
bedroom_count SMALLINT NOT NULL,
drawing_room_count SMALLINT NOT NULL,
kitchen_count SMALLINT NOT NULL,
washroom_count SMALLINT NOT NULL,
balcony_count SMALLINT NOT NULL,
orientation_id SMALLINT NOT NULL,
total_floor SMALLINT NOT NULL,
floor SMALLINT NOT NULL,
property_id SMALLINT NOT NULL,
PRIMARY KEY (id)
)

CREATE SEQUENCE gen_shh;
ALTER SEQUENCE gen_shh RESTART WITH 1;

SET TERM ^ ;

CREATE TRIGGER trig_shh FOR tb_shh
ACTIVE BEFORE INSERT POSITION 0
AS
BEGIN
  IF (NEW.id IS NULL) THEN 
	NEW.id = GEN_ID(gen_shh, 1);
END^

SET TERM ; ^

