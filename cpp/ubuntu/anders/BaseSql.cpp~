#include "BaseSql.h"

otl_stream& operator<<(otl_stream& o, TB_RECORD model)
{
	o<<model.getCall_pk();
	o<<model.getUi_id();
	o<<model.getOp_id();
	o<<model.getOrg_id();
	o<<model.getChannel_id();
	o<<model.getClient_ip();
	o<<model.getCall_sequence();
	o<<model.getWeb_partition_id();
	o<<model.getWeb_server_ip();
	o<<model.getCall_id();
	o<<model.getParent_call_id();
	o<<model.getMonitor_data_type();
	o<<model.getStatement();
	o<<model.getStart_time();
	o<<model.getFinish_time();
	o<<model.getTotal_time();
 	return o;
}

otl_stream& operator>>(otl_stream& o, TB_RECORD model)
{
 	/*
	o>>model.getCall_pk();
	o>>model.getUi_id();
	o>>model.getOp_id();
	o>>model.getOrg_id();
	o>>model.getChannel_id();
	o>>model.getClient_ip();
	o>>model.getCall_sequence();
	o>>model.getWeb_partition_id();
	o>>model.getWeb_server_ip();
	o>>model.getCall_id();
	o>>model.getParent_call_id();
	o>>model.getMonitor_data_type();
	o>>model.getStatement();
	o>>model.getStart_time();
	o>>model.getFinish_time();
	o>>model.getTotal_time();
*/
 	return o;
}

int BaseSql::executeUpdate(string sql)
{
	int returnValue = -1;

	otl_connect::otl_initialize(); 
	otl_connect conn;
	conn.auto_commit_off();
	
	try
	{
		conn.rlogon(ORACLE_CONN_STR);
		returnValue = conn.direct_exec(sql.c_str());
		conn.commit();
		cout<<returnValue<<endl;
	}
	catch(otl_exception& ex)
	{ 
		cerr<<ex.msg<<endl; 
		cerr<<ex.stm_text<<endl;
		cerr<<ex.var_info<<endl;
		conn.rollback();
	}
	
	conn.logoff(); 
	
	return returnValue;
}

int BaseSql::executeUpdate(string sql, TB_RECORD model)
{
	int returnValue = -1;
	
	otl_connect::otl_initialize(); 
	otl_connect conn;
	try
	{
		conn.rlogon(ORACLE_CONN_STR);
		otl_stream i;
		i.open(100, sql.c_str(), conn);
		i.set_commit(0);		i<<model;		i.flush();
		returnValue = i.get_rpc();
		cout<<returnValue<<endl;
		//i.close();
		conn.commit();
		i.close();
	}
	catch(otl_exception& ex)
	{ 
		cerr<<ex.msg<<endl; 
		cerr<<ex.stm_text<<endl;
		cerr<<ex.var_info<<endl;
	}
	
	conn.logoff(); 
	
	return returnValue;
}

void BaseSql::executeQuery(string sql)
{
	otl_connect::otl_initialize(); 
	otl_connect conn;
	
	try
	{
		conn.rlogon(ORACLE_CONN_STR);
		otl_stream i(100, sql.c_str(), conn);
		
		int temp;
		char name[50];
		
		otl_stream_read_iterator<otl_stream, otl_exception,	otl_lob_stream> rs;
		rs.attach(i);
		while(rs.next_row())
		{ 
			rs.get(1, temp);
			rs.get(2, name);

			char id[50];
			//itoa(temp, id, 10);
			sprintf(id, "%d", temp);

			cout<<"id="<<id<<", name="<<name<<endl;
		}
		rs.detach();

		i.close();
	}
	catch(otl_exception& ex)
	{ 
		cerr<<ex.msg<<endl; 
		cerr<<ex.stm_text<<endl;
		cerr<<ex.var_info<<endl;
	}

	conn.logoff(); 
}

void BaseSql::executeQuery(string sql, map<int, string> params)
{
	otl_connect::otl_initialize(); 
	otl_connect conn;
	
	try
	{
		conn.rlogon(ORACLE_CONN_STR);
		otl_stream i;
		i.set_all_column_types(otl_all_date2str|otl_all_num2str);
		i.open(100, sql.c_str(), conn);
		
		char id[50];
		char name[50];
		
		map<int, string>::iterator it;
		for (it = params.begin(); it != params.end(); it++)
		{
			//if (it->first == PARAMS_TYPE_INT)
			//	i<<atoi(it->second.c_str());
			//else if (it->first == PARAMS_TYPE_FLOAT)
			//	i<<atof(it->second.c_str());
			//else
				i<<it->second.c_str();
		}
		
		while (!i.eof())
		{ 
			i>>id>>name;
			cout<<"id="<<id<<", name="<<name<<endl;
		}

		i.close();
		
	}
	catch(otl_exception& ex)
	{ 
		cerr<<ex.msg<<endl; 
		cerr<<ex.stm_text<<endl;
		cerr<<ex.var_info<<endl;
	}

	conn.logoff(); 
}
