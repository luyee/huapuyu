#include "BaseSql.h"

otl_stream& operator<<(otl_stream& o, Tb_test1 model)
{
	//o<<model.getId();
	o<<model.getName();
	o<<model.getAge();
	//o<<model.getBirthday();

 	return o;
}

otl_stream& operator>>(otl_stream& o, Tb_test1& model)
{
	int id;
	string name;
	int age;
	string birthday;	

	o>>id;
	o>>name;
	o>>age;
	o>>birthday;

	model.setId(id);
	model.setName(name);
	model.setAge(age);
	model.setBirthday(birthday);

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

int BaseSql::executeUpdate(string sql, Tb_test1 model)
{
	int returnValue = -1;
	
	otl_connect::otl_initialize(); 
	otl_connect conn;
	otl_stream i;
	try
	{
		conn.rlogon(ORACLE_CONN_STR);
		i.open(100, sql.c_str(), conn);
		i.set_commit(0);
		i<<model;
		i.flush();
		returnValue = i.get_rpc();
		cout<<returnValue<<endl;
		conn.commit();
	}
	catch(otl_exception& ex)
	{ 
		cerr<<ex.msg<<endl; 
		cerr<<ex.stm_text<<endl;
		cerr<<ex.var_info<<endl;
		conn.rollback();
	}
	
	i.close();
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
		//otl_stream i(100, sql.c_str(), conn);
		otl_stream i;
		i.set_all_column_types(otl_all_date2str);
		i.open(100, sql.c_str(), conn);		

		int id;
		string name;
		int age;
		string birthday;
		
		otl_stream_read_iterator<otl_stream, otl_exception, otl_lob_stream> rs;
		rs.attach(i);
		while(rs.next_row())
		{ 
			rs.get(1, id);
			rs.get(2, name);
			rs.get(3, age);
			rs.get(4, birthday);

			//char id[50];
			//itoa(temp, id, 10);
			//sprintf(id, "%d", temp);

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

//void BaseSql::executeQuery(string sql, map<int, string> params)
void BaseSql::executeQuery(string sql, int iyyy)
{
	otl_connect::otl_initialize(); 
	otl_connect conn;
	
	try
	{
		conn.rlogon(ORACLE_CONN_STR);
		otl_stream i;
		//i.set_all_column_types(otl_all_date2str|otl_all_num2str);
		i.set_all_column_types(otl_all_date2str);
		i.open(100, sql.c_str(), conn);
		
		char id[50];
		char name[50];
		
		//map<int, string>::iterator it;
		//for (it = params.begin(); it != params.end(); it++)
		//{
			//if (it->first == PARAMS_TYPE_INT)
			//	i<<atoi(it->second.c_str());
			//else if (it->first == PARAMS_TYPE_FLOAT)
			//	i<<atof(it->second.c_str());
			//else
				//i<<it->second.c_str();
		//}
		
		Tb_test1 model;
		while (!i.eof())
		{ 
			i>>model;
			cout<<"id="<<model.getId()<<", name="<<model.getName()<<endl;
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
