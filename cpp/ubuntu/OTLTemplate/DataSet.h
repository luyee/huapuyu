#include <iostream>
#include <string>
#include <map>
using namespace std;

typedef map<string, string> ROW;
typedef map<int, ROW> DATA_SET;

class DataSet
{
public:
	void SetValue(int rowNum, string fieldName, string fieldValue);
	string GetValue(int rowNum, string fieldName);
	int GetSize();
private:
	DATA_SET ds;
};
