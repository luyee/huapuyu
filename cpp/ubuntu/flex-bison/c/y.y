%{
#include <stdio.h>
typedef char* string;
#define YYSTYPE string //定义参数类型为char*，如$2，$4等。
void yyerror(char** inputValue, char* msg); //注意：当采用%parse-param {char* inputValue}传递参数后，yyerror也必须增加这个参数
//int yyerror(char** inputValue, char* msg);
/*
Monitor Info: CALLPK=($10.9.213.141_1245081595008$),UIId=($null$),OpId=($null$),OrgId=($null$),ChannelId=($null$),ClientIP=($10.9.213.141$),CallSequence=($10.9.213.141_1245081595008$),WebPartitionId=($query:standard$),WebServerIP=($10.10.105.91$),CALLID=($2$),PARENTCALLID=($1$),MONITORDATATYPE=($METHOD$),STATEMENT=($com.asiainfo.boss30.common.IAppMonitor4BOMS_AIProxy.testHlrMgnt()$),STARTTIME=($2009-06-15 23:59:55:008$),FINISHTIME=($2009-06-15 23:59:55:021$),TOTALTIME=($13ms$), Monitor End
*/
%}

%parse-param { char** outputValue }

//%lex-param { char** value }

%token LPAREN RPAREN COLON DOLLAR COMMA EQUAL STRINGS ROWBEGIN ROWEND

%%

row : ROWBEGIN string ROWEND;

string : value COMMA string | value;

value : STRINGS EQUAL LPAREN DOLLAR STRINGS DOLLAR RPAREN 
				{ printf("%s=%s\n", $1, $5); *outputValue = $5; }
       	| 
				STRINGS EQUAL LPAREN DOLLAR DOLLAR RPAREN 
				{ printf("%s=%s\n", $1, ""); *outputValue = ""; /*return 89;如果此处返回int值，则该值可以通过yyparse()获得*/ }

%%

void yyerror(char** outputValue, char* msg)
{
	printf("Error : %s!\n", msg);
}

char* getParseValue(const char* value)
{
	yy_scan_string(value);
	char* temp;
	int t = yyparse(&temp);
	return temp;
}
