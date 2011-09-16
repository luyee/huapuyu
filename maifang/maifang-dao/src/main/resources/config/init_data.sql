-- Area --
insert into cfg_area (cfg_area.id , cfg_area.title , cfg_area.type , cfg_area.enable)
values (2,'上海',0,1);

insert into cfg_area (cfg_area.id , cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2001,'宝山',2,1,2);
insert into cfg_area (cfg_area.id , cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2002, '长宁',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2003, '崇明',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2004, '奉贤',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2005, '虹口',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2006, '黄浦',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2007, '嘉定',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2008, '金山',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (2009, '静安',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20010, '卢湾',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20011, '闵行',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20012, '浦东',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20013, '普陀',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20014, '青浦',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20015, '松江',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20016, '徐汇',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20017, '杨浦',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20018, '闸北',2,1,2);
insert into cfg_area (cfg_area.id ,  cfg_area.title , cfg_area.type , cfg_area.enable, cfg_area.parent_id)
values (20019, '上海周边',2,1,2);


-- Data --
insert into cfg_data (cfg_data.id , cfg_data.enable , cfg_data.title , cfg_data.type)
VALUES (1101 , 1 , '整租' , 11);
insert into cfg_data (cfg_data.id , cfg_data.enable , cfg_data.title , cfg_data.type)
VALUES (1301 , 1 , '面议' , 13);
insert into cfg_data (cfg_data.id , cfg_data.enable , cfg_data.title , cfg_data.type)
VALUES (1302 , 1 , '押一付三' , 13);
insert into cfg_data (cfg_data.id , cfg_data.enable , cfg_data.title , cfg_data.type)
VALUES (1303 , 1 , '押一付一' , 13);
insert into cfg_data (cfg_data.id , cfg_data.enable , cfg_data.title , cfg_data.type)
VALUES (1304 , 1 , '押二付一' , 13);
insert into cfg_data (cfg_data.id , cfg_data.enable , cfg_data.title , cfg_data.type)
VALUES (1305 , 1 , '半年付' , 13);
insert into cfg_data (cfg_data.id , cfg_data.enable , cfg_data.title , cfg_data.type)
VALUES (1306 , 1 , '年付' , 13);

