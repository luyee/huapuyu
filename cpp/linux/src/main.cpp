#include <ace/Configuration.h>  
#include <ace/Configuration_Import_Export.h>
#include <ace/Log_Msg.h>
#include <iostream>
#include <map>
#include <string>
using namespace std;
#include <stdio.h>

int main()
{
	ACE_Configuration_Heap config;
	if (-1 == config.open())
		ACE_ERROR_RETURN((LM_ERROR, ACE_TEXT("%p\n"), ACE_TEXT("config")), -1);
	//ACE_Registry_ImpExp impExp(config);  
	ACE_Ini_ImpExp impExp(config);    
	 
    if (-1 == impExp.import_config(ACE_TEXT("config.ini")))
		ACE_ERROR_RETURN((LM_ERROR, ACE_TEXT("%p\n"), ACE_TEXT("config.ini")), -1);    	 
	
	ACE_Configuration_Section_Key rootKey = config.root_section();  
	ACE_Configuration_Section_Key valueKey;
	
	config.open_section(rootKey, ACE_TEXT("oracle"), 0, valueKey);

	ACE_TString user, pwd; 
	config.get_string_value(valueKey, ACE_TEXT("user"), user);
	config.get_string_value(valueKey, ACE_TEXT("pwd"), pwd);
	//u_int pwd;
	//config.get_integer_value(valueKey, ACE_TEXT("pwd"), pwd);
	//config.get_binary_value(valueKey, ACE_TEXT("user"), user);
	
	cout<<user<<"; "<<pwd<<endl;
	
	getchar();

	return 0;
}
