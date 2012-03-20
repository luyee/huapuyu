select * from user_segments;
select * from user_tablespaces;
select * from user_extents;
select * from user_free_space;
select * from dba_data_files;
select * from dba_free_space;

select distinct segment_type from dba_segments;--查看所有segment类型


select * from user_db_links
