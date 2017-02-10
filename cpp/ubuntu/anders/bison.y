%{
#include <stdio.h>
#include <iostream>
#include <string>
#include <map>
#include <vector>
using namespace std;
//定义参数类型为char*，如$2，$4等
//typedef char* string;
typedef char* YYSTYPE;
//#define YYSTYPE char* 

//注意：当采用%parse-param {char** outputValue}传递参数后，yyerror也必须增加这个参数
void yyerror(vector<string>& outputValue, const char* msg);
//void yyerror(map<string, string>& outputValue, const char* msg);
//void yyerror(char** outputValue, const char* msg); 
//int yyerror(char** outputValue, const char* msg);
extern int yylex(void);
extern struct yy_buffer_state* yy_scan_string(const char* value);
/*
Monitor Info: CALLPK=($10.9.213.141_1245081595008$),UIId=($null$),OpId=($null$),OrgId=($null$),ChannelId=($null$),ClientIP=($10.9.213.141$),CallSequence=($10.9.213.141_1245081595008$),WebPartitionId=($query:standard$),WebServerIP=($10.10.105.91$),CALLID=($2$),PARENTCALLID=($1$),MONITORDATATYPE=($METHOD$),STATEMENT=($com.asiainfo.boss30.common.IAppMonitor4BOMS_AIProxy.testHlrMgnt()$),STARTTIME=($2009-06-15 23:59:55:008$),FINISHTIME=($2009-06-15 23:59:55:021$),TOTALTIME=($13ms$), Monitor End
*/
%}

//%parse-param { char** outputValue }
//%parse-param { map<string, string>& outputValue }
%parse-param { vector<string>& outputValue }

//%lex-param { char** value }

%token LPAREN RPAREN COLON DOLLAR COMMA EQUAL STRINGS ROWBEGIN ROWEND

%%

row : ROWBEGIN msg ROWEND;

msg : value COMMA msg | value;

value : STRINGS EQUAL LPAREN DOLLAR STRINGS DOLLAR RPAREN 
				{ printf("%s=%s\n", $1, $5); outputValue.push_back($5); /*outputValue.insert(pair<string, string>($1, $5));*/ }
       	|
				STRINGS EQUAL LPAREN DOLLAR DOLLAR RPAREN 
				{ printf("%s=%s\n", $1, ""); outputValue.push_back(""); /*outputValue.insert(pair<string, string>($1, ""));*/ /*return 89;如果此处返回int值，则该值可以通过yyparse()获得*/ }

%%

/*
void yyerror(char** outputValue, const char* msg)
{
	printf("Error : %s!\n", msg);
}
*/

/*
int yyerror(char** outputValue, const char* msg)
{
	printf("Error : %s!\n", msg);
	return -1;
}
*/

/*
void yyerror(map<string, string>& outputValue, const char* msg)
{
	printf("Error : %s!\n", msg);
}
*/

void yyerror(vector<string>& outputValue, const char* msg)
{
	printf("Error : %s!\n", msg);
}

/*
char* getParseValue(const char* value)
{	
	yy_scan_string(value);
	char* temp;
	int t = yyparse(&temp);
	return temp;
}
*/

/*
map<string, string> getParseValue(const char* value)
{	
	yy_scan_string(value);
	map<string, string> m;
	yyparse(m);
	return m;
}
*/

vector<string> getParseValue(const char* value)
{	
	yy_scan_string(value);
	vector<string> v;
	yyparse(v);
	return v;
}
