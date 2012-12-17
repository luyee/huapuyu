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
	conn.auto_commit_off();
	
	try
	{
		conn.rlogon("anders/123@oracle9i");
		string sql = "insert into otl (id, name, age, salary) values (seq_otl.nextval, 'zhuzhen', 27, 5500)";
		int i = conn.direct_exec(sql.c_str());
		conn.commit();
		cout<<i<<endl;
	}
	catch(otl_exception& ex)
	{ 
		cerr<<ex.msg<<endl; 		
		cerr<<ex.stm_text<<endl;		
		cerr<<ex.var_info<<endl;
		cerr<<ex.code<<endl;
		cerr<<ex.sqlstate<<endl;
		conn.rollback();
	}
	
	conn.logoff(); 

	return 0;
}
