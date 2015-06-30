#include <string>
using namespace std;

class Tb_test1
{
public:
	//id
	void setId(int id)
	{
		this->id = id;
	}
	int getId()
	{
		return this->id;
	}
	
	//name
	void setName(string name)
	{
		this->name = name;
	}
	string getName()
	{
		return this->name;
	}
	
	//age
	void setAge(int age)
	{
		this->age = age;
	}
	int getAge()
	{
		return this->age;
	}
	
	//birthday
	void setBirthday(string birthday)
	{
		this->birthday = birthday;
	}
	string getBirthday()
	{
		return this->birthday;
	}
private:
	int id;
	string name;
	int age;
	string birthday;
};
