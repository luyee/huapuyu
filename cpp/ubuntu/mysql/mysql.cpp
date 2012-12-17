#include <stdlib.h>
#include <iostream>
#include "mysql.h"
using namespace std;

int test_mysql()
{
	MYSQL mysql;
	MYSQL_RES* result;
	MYSQL_ROW row;

	//initialize MYSQL structure
	mysql_init(&mysql);

	//connect to database
	mysql_real_connect(&mysql, "localhost", "root", "123", "db_test", 3306, NULL, 0);

	//execute query
	mysql_query(&mysql, "select * from tb_test1");

	//get result set
	result = mysql_store_result(&mysql);

	//process result set
	while ((row=mysql_fetch_row(result)))
	{
		fprintf(stdout, "%s - %s\n", row[0], row[1]);
	}

	//clean up
	mysql_free_result(result);
	mysql_close(&mysql);
}

int main()
{
	test_mysql();
	return EXIT_SUCCESS;	
}
