#include <ace/Log_Msg.h>
#include <ace/Configuration.h>
#include <ace/Configuration_Import_Export.h>
#include <iostream>
#include <map>
#include <string>
using namespace std;

#include <stdio.h>

int main()
{
	ACE_Configuration_Heap config;
	
	if (config.open() == -1)
		ACE_ERROR_RETURN((LM_ERROR, ACE_TEXT("%P\n"), ACE_TEXT("config")), -1);
	
	//ACE_Registry_ImpExp impExp(config);
	ACE_Ini_ImpExp impExp(config);
	
	if (impExp.import_config(ACE_TEXT("config.ini")) == -1)
		ACE_ERROR_RETURN((LM_ERROR, ACE_TEXT("%P\n"), ACE_TEXT("config")), -1);
	
	ACE_Configuration_Section_Key section;
	
	config.open_section(config.root_section(), ACE_TEXT("oracle"), 0, section);
	
	ACE_TString user, pwd, db;
	config.get_string_value(section, ACE_TEXT("user"), user);
	config.get_string_value(section, ACE_TEXT("pwd"), pwd);
	config.get_string_value(section, ACE_TEXT("database"), db);

	cout<<user<<pwd<<db<<endl;

	getchar();

    return 0;
}
