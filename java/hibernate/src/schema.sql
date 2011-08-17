
    alter table cfg_area 
        drop 
        foreign key FKF4FBA7888B1871B0;

    alter table tb_house 
        drop 
        foreign key FKFA352E0F487CA5D8;

    alter table tb_house 
        drop 
        foreign key FKFA352E0F5F49AC4C;

    alter table tb_house 
        drop 
        foreign key FKFA352E0F9BB0F96A;

    alter table tb_house 
        drop 
        foreign key FKFA352E0FD8DB9B07;

    alter table tb_house 
        drop 
        foreign key FKFA352E0FAC481FD4;

    alter table tb_house 
        drop 
        foreign key FKFA352E0F963CC7F2;

    alter table tb_house 
        drop 
        foreign key FKFA352E0F1E48583C;

    alter table tb_house 
        drop 
        foreign key FKFA352E0F5C50DF89;

    alter table tb_house 
        drop 
        foreign key FKFA352E0F3E829D4F;

    alter table tb_privilege_resource_relation 
        drop 
        foreign key FKB26D06AE9034844A;

    alter table tb_privilege_resource_relation 
        drop 
        foreign key FKB26D06AEB8FA73A7;

    alter table tb_privilege_resource_relation 
        drop 
        foreign key FKB26D06AE5DEDDCD;

    alter table tb_rent_house 
        drop 
        foreign key FKD1110D0B6FD599F1;

    alter table tb_rent_house 
        drop 
        foreign key FKD1110D0B60CC67DD;

    alter table tb_rent_house 
        drop 
        foreign key FKD1110D0B5160587B;

    alter table tb_rent_house 
        drop 
        foreign key FKD1110D0B1EC10037;

    alter table tb_role_privilege_relation 
        drop 
        foreign key FKAE52EB62B8FA73A7;

    alter table tb_role_privilege_relation 
        drop 
        foreign key FKAE52EB6261CB064D;

    alter table tb_second_hand_house 
        drop 
        foreign key FKAB9F946AAA1168D6;

    alter table tb_second_hand_house 
        drop 
        foreign key FKAB9F946ADEC1F761;

    alter table tb_second_hand_house 
        drop 
        foreign key FKAB9F946AD89EBCF0;

    alter table tb_second_hand_house 
        drop 
        foreign key FKAB9F946AB0AC03D5;

    alter table tb_second_hand_house 
        drop 
        foreign key FKAB9F946A4106B7E6;

    alter table tb_user_role_relation 
        drop 
        foreign key FK3C3623A261CB064D;

    alter table tb_user_role_relation 
        drop 
        foreign key FK3C3623A26F5CA2D;

    drop table if exists cfg_area;

    drop table if exists cfg_data;

    drop table if exists t_person;

    drop table if exists tb_house;

    drop table if exists tb_privilege;

    drop table if exists tb_privilege_resource_relation;

    drop table if exists tb_rent_house;

    drop table if exists tb_resource;

    drop table if exists tb_role;

    drop table if exists tb_role_privilege_relation;

    drop table if exists tb_second_hand_house;

    drop table if exists tb_user;

    drop table if exists tb_user1;

    drop table if exists tb_user_role_relation;

    create table cfg_area (
        id integer not null,
        name varchar(50) not null,
        type tinyint not null,
        enable bit not null,
        parent_id integer,
        primary key (id)
    );

    create table cfg_data (
        id integer not null,
        name varchar(50) not null,
        type tinyint not null,
        enable bit not null,
        primary key (id)
    );

    create table t_person (
        ID bigint not null auto_increment comment '标识',
        MZ varchar(255) comment '名字',
        NL integer comment '年龄',
        primary key (ID)
    ) comment='"人"';

    create table tb_house (
        id bigint not null auto_increment,
        name varchar(50) not null,
        province_id integer not null,
        city_id integer not null,
        district_id integer not null,
        address varchar(100) not null,
        bedroom_count tinyint not null,
        living_room_count tinyint not null,
        kitchen_count tinyint not null,
        washroom_count tinyint not null,
        balcony_count tinyint not null,
        prop_mgt_type_id integer,
        direction_id integer,
        decoration_id integer,
        total_floor tinyint,
        floor tinyint,
        construct_year_id integer,
        transport varchar(500),
        environment varchar(500),
        remark varchar(500),
        rent_house_id bigint,
        second_hand_house_id bigint,
        primary key (id)
    );

    create table tb_privilege (
        id integer not null auto_increment,
        name varchar(50) not null,
        enable bit not null,
        primary key (id)
    );

    create table tb_privilege_resource_relation (
        privilege_id integer not null,
        resource_id integer not null,
        primary key (privilege_id, resource_id)
    );

    create table tb_rent_house (
        id bigint not null auto_increment,
        price decimal(7,2),
        area decimal(7,2),
        type tinyint,
        share_type_id integer,
        roommate_gender_id integer,
        payment_id integer,
        check_in_id integer,
        primary key (id)
    );

    create table tb_resource (
        id integer not null auto_increment,
        name varchar(50) not null,
        enable bit not null,
        primary key (id)
    );

    create table tb_role (
        id integer not null auto_increment,
        name varchar(50) not null,
        enable bit not null,
        primary key (id)
    );

    create table tb_role_privilege_relation (
        privilege_id integer not null,
        role_id integer not null,
        primary key (role_id, privilege_id)
    );

    create table tb_second_hand_house (
        id bigint not null auto_increment,
        price decimal(7,2),
        building_area decimal(7,2),
        usable_area decimal(7,2),
        propety_id integer,
        prop_type_id integer,
        prop_struct_id integer,
        construct_type_id integer,
        visit_time_id integer,
        primary key (id)
    );

    create table tb_user (
        id integer not null auto_increment,
        name varchar(50) not null,
        pwd varchar(50) not null,
        enable bit not null,
        primary key (id)
    );

    create table tb_user1 (
        id integer not null auto_increment,
        name varchar(50) not null,
        pwd varchar(50) not null,
        enable bit comment '启用符',
        primary key (id)
    ) comment='用户表';

    create table tb_user_role_relation (
        role_id integer not null,
        user_id integer not null,
        primary key (user_id, role_id)
    );

    alter table cfg_area 
        add index FKF4FBA7888B1871B0 (parent_id), 
        add constraint FKF4FBA7888B1871B0 
        foreign key (parent_id) 
        references cfg_area (id);

    alter table tb_house 
        add index FKFA352E0F487CA5D8 (direction_id), 
        add constraint FKFA352E0F487CA5D8 
        foreign key (direction_id) 
        references cfg_data (id);

    alter table tb_house 
        add index FKFA352E0F5F49AC4C (district_id), 
        add constraint FKFA352E0F5F49AC4C 
        foreign key (district_id) 
        references cfg_area (id);

    alter table tb_house 
        add index FKFA352E0F9BB0F96A (province_id), 
        add constraint FKFA352E0F9BB0F96A 
        foreign key (province_id) 
        references cfg_area (id);

    alter table tb_house 
        add index FKFA352E0FD8DB9B07 (decoration_id), 
        add constraint FKFA352E0FD8DB9B07 
        foreign key (decoration_id) 
        references cfg_data (id);

    alter table tb_house 
        add index FKFA352E0FAC481FD4 (rent_house_id), 
        add constraint FKFA352E0FAC481FD4 
        foreign key (rent_house_id) 
        references tb_rent_house (id);

    alter table tb_house 
        add index FKFA352E0F963CC7F2 (construct_year_id), 
        add constraint FKFA352E0F963CC7F2 
        foreign key (construct_year_id) 
        references cfg_data (id);

    alter table tb_house 
        add index FKFA352E0F1E48583C (prop_mgt_type_id), 
        add constraint FKFA352E0F1E48583C 
        foreign key (prop_mgt_type_id) 
        references cfg_data (id);

    alter table tb_house 
        add index FKFA352E0F5C50DF89 (second_hand_house_id), 
        add constraint FKFA352E0F5C50DF89 
        foreign key (second_hand_house_id) 
        references tb_second_hand_house (id);

    alter table tb_house 
        add index FKFA352E0F3E829D4F (city_id), 
        add constraint FKFA352E0F3E829D4F 
        foreign key (city_id) 
        references cfg_area (id);

    alter table tb_privilege_resource_relation 
        add index FKB26D06AE9034844A (privilege_id), 
        add constraint FKB26D06AE9034844A 
        foreign key (privilege_id) 
        references tb_resource (id);

    alter table tb_privilege_resource_relation 
        add index FKB26D06AEB8FA73A7 (privilege_id), 
        add constraint FKB26D06AEB8FA73A7 
        foreign key (privilege_id) 
        references tb_privilege (id);

    alter table tb_privilege_resource_relation 
        add index FKB26D06AE5DEDDCD (resource_id), 
        add constraint FKB26D06AE5DEDDCD 
        foreign key (resource_id) 
        references tb_resource (id);

    alter table tb_rent_house 
        add index FKD1110D0B6FD599F1 (payment_id), 
        add constraint FKD1110D0B6FD599F1 
        foreign key (payment_id) 
        references cfg_data (id);

    alter table tb_rent_house 
        add index FKD1110D0B60CC67DD (share_type_id), 
        add constraint FKD1110D0B60CC67DD 
        foreign key (share_type_id) 
        references cfg_data (id);

    alter table tb_rent_house 
        add index FKD1110D0B5160587B (check_in_id), 
        add constraint FKD1110D0B5160587B 
        foreign key (check_in_id) 
        references cfg_data (id);

    alter table tb_rent_house 
        add index FKD1110D0B1EC10037 (roommate_gender_id), 
        add constraint FKD1110D0B1EC10037 
        foreign key (roommate_gender_id) 
        references cfg_data (id);

    alter table tb_role_privilege_relation 
        add index FKAE52EB62B8FA73A7 (privilege_id), 
        add constraint FKAE52EB62B8FA73A7 
        foreign key (privilege_id) 
        references tb_privilege (id);

    alter table tb_role_privilege_relation 
        add index FKAE52EB6261CB064D (role_id), 
        add constraint FKAE52EB6261CB064D 
        foreign key (role_id) 
        references tb_role (id);

    alter table tb_second_hand_house 
        add index FKAB9F946AAA1168D6 (visit_time_id), 
        add constraint FKAB9F946AAA1168D6 
        foreign key (visit_time_id) 
        references cfg_data (id);

    alter table tb_second_hand_house 
        add index FKAB9F946ADEC1F761 (prop_type_id), 
        add constraint FKAB9F946ADEC1F761 
        foreign key (prop_type_id) 
        references cfg_data (id);

    alter table tb_second_hand_house 
        add index FKAB9F946AD89EBCF0 (propety_id), 
        add constraint FKAB9F946AD89EBCF0 
        foreign key (propety_id) 
        references cfg_data (id);

    alter table tb_second_hand_house 
        add index FKAB9F946AB0AC03D5 (construct_type_id), 
        add constraint FKAB9F946AB0AC03D5 
        foreign key (construct_type_id) 
        references cfg_data (id);

    alter table tb_second_hand_house 
        add index FKAB9F946A4106B7E6 (prop_struct_id), 
        add constraint FKAB9F946A4106B7E6 
        foreign key (prop_struct_id) 
        references cfg_data (id);

    alter table tb_user_role_relation 
        add index FK3C3623A261CB064D (role_id), 
        add constraint FK3C3623A261CB064D 
        foreign key (role_id) 
        references tb_role (id);

    alter table tb_user_role_relation 
        add index FK3C3623A26F5CA2D (user_id), 
        add constraint FK3C3623A26F5CA2D 
        foreign key (user_id) 
        references tb_user (id);
