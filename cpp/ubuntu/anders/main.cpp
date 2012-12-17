#include <stdlib.h>
#include <stdio.h>

#include <iostream>
#include <string>
#include <map>
#include <vector>
#include <fstream>
using namespace std;

#include "log4cxx/logger.h"
#include "log4cxx/basicconfigurator.h"
#include "log4cxx/propertyconfigurator.h"
#include "log4cxx/helpers/exception.h"
using namespace log4cxx;
using namespace log4cxx::helpers;

#include "ace/Log_Msg.h"

#include "BaseSql.h"
#include "config.h"

//extern char* getParseValue(const char*);
//extern map<string, string> getParseValue(const char*);
extern vector<string> getParseValue(const char*);

int main(int argc, char** argv)
{
		LoggerPtr logger(Logger::getRootLogger());   

		try
		{
			ifstream isFileExist; 
			isFileExist.open("log4cxx.properties");
			if (isFileExist)
				PropertyConfigurator::configure("log4cxx.properties");
			else
				BasicConfigurator::configure();
			//LOG4CXX_INFO(logger, "hello1");
			//logger->info("hello2");
		}
		catch (Exception& e)
		{
			//cerr<<e.what()<<endl;
			ACE_ERROR((LM_ERROR, e.what()));
		} 

		ifstream ifs(FILE_PATH);
    string s;
    while (getline(ifs, s))
    {
				cout<<endl;				
				cout<<"**************************************************"<<endl;        
				cout<<s<<endl;
				cout<<"**************************************************"<<endl;        
        s = "@" + s + "@";

				/*        
				map<string, string> m = getParseValue(s.c_str());
        map<string, string>::iterator it;
        for (it = m.begin(); it != m.end(); it++)
        {
            cout<<it->first<<":"<<it->second<<endl;							
        }
				*/

				vector<string> v = getParseValue(s.c_str());

				if (v.size() == 0)
						continue;

				vector<string>::iterator it;
				for (it = v.begin(); it != v.end(); it++)
        {
            cout<<*it<<endl;	
											
        }
				TB_RECORD model;
				model.setCall_pk(v[0]);
				model.setUi_id(v[1]);
				model.setOp_id(v[2]);
				model.setOrg_id(v[3]);
				model.setChannel_id(v[4]);
				model.setClient_ip(v[5]);
				model.setCall_sequence(v[6]);
				model.setWeb_partition_id(v[7]);
				model.setWeb_server_ip(v[8]);
				model.setCall_id(v[9]);
				model.setParent_call_id(v[10]);
				model.setMonitor_data_type(v[11]);
				model.setStatement(v[12]);
				model.setStart_time(v[13]);
				model.setFinish_time(v[14]);
				model.setTotal_time(55);

				string sql;
				sql.append(" INSERT INTO TB_RECORD ");
				sql.append(" ( ");
				sql.append("  ID, ");
				sql.append("  CALL_PK, ");
				sql.append("  UI_ID, ");
				sql.append("  OP_ID, ");
				sql.append("  ORG_ID, ");
				sql.append("  CHANNEL_ID, ");
				sql.append("  CLIENT_IP, ");
				sql.append("  CALL_SEQUENCE, ");
				sql.append("  WEB_PARTITION_ID, ");
				sql.append("  WEB_SERVER_IP, ");
				sql.append("  CALL_ID, ");
				sql.append("  PARENT_CALL_ID, ");
				sql.append("  MONITOR_DATA_TYPE, ");
				sql.append("  STATEMENT, ");
				sql.append("  START_TIME, ");
				sql.append("  FINISH_TIME, "); 
				sql.append("  TOTAL_TIME, ");
				sql.append("  INSERT_TIME ");
				sql.append(" ) ");
				sql.append(" VALUES ");
				sql.append(" ( ");
				sql.append("  TB_RECORD_SEQ.NEXTVAL, ");
				sql.append("  :CALL_PK<char[100]>, ");
				sql.append("  :UI_ID<char[100]>, ");
				sql.append("  :OP_ID<char[100]>, ");
				sql.append("  :ORG_ID<char[100]>, ");
				sql.append("  :CHANNEL_ID<char[100]>, ");
				sql.append("  :CLIENT_IP<char[15]>, ");
				sql.append("  :CALL_SEQUENCE<char[100]>, ");
				sql.append("  :WEB_PARTITION_ID<char[100]>, ");
				sql.append("  :WEB_SERVER_IP<char[15]>, ");
				sql.append("  :CALL_ID<char[100]>, ");
				sql.append("  :PARENT_CALL_ID<char[100]>, ");
				sql.append("  :MONITOR_DATA_TYPE<char[100]>, ");
				sql.append("  :STATEMENT<char[500]>, ");
				sql.append("  :START_TIME<char[50]>, ");
				sql.append("  :FINISH_TIME<char[50]>, ");
				sql.append("  :TOTAL_TIME<int>, ");
				sql.append("  SYSDATE ");
				sql.append(" ) ");

				//cout<<sql<<endl;

				BaseSql* bs = new BaseSql();
				bs->executeUpdate(sql, model);

        //cout<<m.size()<<endl;
    }

		LOG4CXX_INFO(logger, "finish");
		ACE_ERROR((LM_ERROR, "finish"));

    return (EXIT_SUCCESS);
}


