#include <boost/lexical_cast.hpp>
#include <iostream>
using namespace std;
using namespace boost;

int main()
{
	int a = lexical_cast<int>("123");
	double b = lexical_cast<double>("123.12");
	cout<<a<<endl;
	cout<<b<<endl;
	return 0;
}
