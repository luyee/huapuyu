#include <boost/regex.hpp>
#include <string>
#include <iostream>

using namespace std;
using namespace boost;

regex e("^\\w+\\d+");
string text1("abc123");
string text2("123abc");

int main()
{
	if (regex_match(text1, e))
	{
		cout<<"yes"<<endl;
	}
	else
	{
		cout<<"no"<<endl;
	}

	if (regex_match(text2, e))
	{
		cout<<"yes"<<endl;
	}
	else
	{
		cout<<"no"<<endl;
	}
	return 0;
}
