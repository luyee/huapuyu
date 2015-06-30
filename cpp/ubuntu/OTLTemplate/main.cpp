#include <iostream>
#include "BaseSql.h"

using namespace std;

int main(int argc, char *argv[])
{
	Tb_test1 model;
	model.setName("zhuzhen");
	model.setAge(27);
	model.setBirthday("1983-08-11");

	BaseSql dao;
	dao.executeUpdate("insert into tb_test1 (id, name, age) values (seq_test1.nextval, 'zhuzhen', 27)");

	dao.executeUpdate("insert into tb_test1 (id, name, age) values (seq_test1.nextval, :f1<char[50]>, :f2<int>)", model);

	dao.executeQuery("select * from tb_test1");

	dao.executeQuery("select * from tb_test1", 0);
	return 0;
}
