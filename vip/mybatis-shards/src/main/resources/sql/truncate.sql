USE db1_master;
truncate table user0;
truncate table user1;

USE db1_slave1;
truncate table user0;
truncate table user1;

USE db1_slave2;
truncate table user0;
truncate table user1;

USE db2_master;
truncate table user0;
truncate table user1;

USE db2_slave1;
truncate table user0;
truncate table user1;

USE db2_slave2;
truncate table user0;
truncate table user1;

USE db3_master;
truncate table user0;
truncate table user1;

USE db3_slave1;
truncate table user0;
truncate table user1;

USE db3_slave2;
truncate table user0;
truncate table user1;
