%{

#include <stdio.h>
#include <iostream>
#include <string>
#include <FlexLexer.h>
using namespace std;

//int strdup(char*); //yylval = atoi(yytext); 

//char* yylval;
//<4628:111228> 00:00:00.025 [10001] MUSERINFOAPP_IUSERINFO.TEST_USERINFO() entered
%}

CHAR [A-Za-z]
NUMBER [0-9]
DELIM [ \n\t]
WORDS {CHAR}+
NUMBERS {NUMBER}+
WHITESPACE {DELIM}+

LPAREN [(]
RPAREN [)]
LBRACE [{]
RBRACE [}]
LBRACKET [<]
RBRACKET [>]
SEMICOLON [;]
COMMA [,]
DOT [.]
COLON [:]


%%

{LBRACKET} ;
{NUMBERS} { cout<<"zhuzhen"; } 
{COLON} ;
{RBRACKET} ;
{DOT} ;
{WHITESPACE} ;

%%

int yyFlexLexer::yywrap()
{
    return 1;
}

