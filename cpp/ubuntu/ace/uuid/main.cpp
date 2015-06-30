#include <ace/UUID.h>
#include <ace/Log_Msg.h>
#include <iostream>
#include <map>
#include <string>
using namespace std;
#include <stdio.h>

int main()
{
	ACE_Utils::UUID str_uuid;
	ACE_Utils::UUID_GENERATOR::instance()->generate_UUID(str_uuid);

	cout<<str_uuid.to_string()<<endl;

	getchar();

	/*
	map<string, string> m;
	m.insert(pair<string, string>("zhuzhen", "test"));
	iterator<string, string> it = m.begin();
	while (it != m.end())
	{
		cout<<it.second<<endl;

	}
	*/	

	return 0;
}
