#include <iostream>
#include <string>
using namespace std;

#define OTL_ORA11G
#define OTL_STREAM_READ_ITERATOR_ON
#define OTL_STL
#define OTL_ANSI_CPP

#include "otlv4.h"

int main()
{
	otl_connect::otl_initialize(); 
	otl_connect conn;
	
	
	try
	{
		conn.rlogon("anders/123@oracle9i");
		conn.auto_commit_off();
		//string sql = "insert into otl (id, name, age, salary) values (seq_otl.nextval, :f1<char[50]>, :f2<int>, :f3<float>)";

		string sql = "insert into otl (id, name, age) values (seq_otl.nextval, :f1<char[50]>, :f2<int>)";
		
		otl_stream i;
		//i.set_all_column_types(otl_all_date2str | otl_all_num2str);
		i.open(100, sql.c_str(), conn);		
		i.set_commit(0);

		string name = "zhuzhen";
		int age = 27;
		float salary = 5500;		
		
		for (int count = 0; count < 3; count++)
		{
			//i<<name<<age<<salary;
			//i<<name<<23<<5500.0f;
			i<<name<<23;
		}

		//i.flush();
		int rpc = i.get_rpc();
		i.close();
		cout<<rpc<<endl;
	}
	catch(otl_exception& ex)
	{ 
		cerr<<ex.msg<<endl; 		
		cerr<<ex.stm_text<<endl;		
		cerr<<ex.var_info<<endl;
		cerr<<ex.code<<endl;
		//conn.rollback();
	}
	
	//conn.rollback();
	conn.logoff(); 

	return 0;
}
