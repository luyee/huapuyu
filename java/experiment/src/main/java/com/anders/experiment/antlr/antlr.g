grammar StreamSQL;
options {
  language=C;
  ASTLabelType=pANTLR3_BASE_TREE;
  output=AST;
}
tokens {
    TOK_CREATE_SCHEMA;
    TOK_CREATE_STREAM;
    TOK_CREATE_WINDOW;
    TOK_SELECT;
    TOK_SCHEMA_LIST;
    TOK_NAME_TYPE;
    TOK_SELEXPR;
    TOK_SELITEM;
    TOK_SELLIST;
}
statement
    : selectStatement EOF
    | createStatement EOF
    ;
selectStatement
    : KW_SELECT selectList
       KW_FROM instreamName=Identifier LSQUARE windowName=Identifier RSQUARE KW_INTO outstreamName=Identifier
       -> ^(KW_SELECT selectList $instreamName $windowName $outstreamName)
    ;
selectList
    : selectColumn (COMMA selectColumn)*
       -> ^(TOK_SELLIST selectColumn+)
    ;
selectColumn
    : selectItem
    | selectExpression  
    ;
selectItem
    : Identifier KW_AS Identifier
       -> ^(TOK_SELITEM Identifier Identifier)
    ;
selectExpression
    : functionName=Identifier LPAREN itemName=Identifier RPAREN KW_AS asName=Identifier
       -> ^(TOK_SELEXPR $functionName $itemName $asName)
    ;
createStatement
    : KW_CREATE KW_SCHEMA Identifier schemaList     
       -> ^(TOK_CREATE_SCHEMA Identifier schemaList)
    | KW_CREATE streamType KW_STREAM streamName=Identifier schemaName=Identifier
       -> ^(TOK_CREATE_STREAM streamType $streamName $schemaName)
    | KW_CREATE KW_WINDOW
       windowName=Identifier LPAREN KW_SIZE Number KW_ADVANCE Number KW_ON onWhat=Identifier RPAREN
       -> ^(TOK_CREATE_WINDOW $windowName Number Number $onWhat)
    ;
schemaList
    : LPAREN columnNameType (COMMA columnNameType)* RPAREN
       -> ^(TOK_SCHEMA_LIST columnNameType+)
    ;
streamType
    : (KW_INPUT | KW_OUTPUT)
    ;
columnNameType
    : coluName=Identifier dataType 
       -> ^(TOK_NAME_TYPE $coluName dataType)
    ;
dataType
    : KW_INT
    | KW_DOUBLE
    ;
// Keywords
KW_FROM : 'FROM';
KW_AS : 'AS';
KW_SELECT : 'SELECT';
KW_ON : 'ON';
KW_CREATE: 'CREATE';
KW_INT: 'INT';
KW_DOUBLE: 'DOUBLE';
KW_INTO: 'INTO';
KW_SCHEMA: 'SCHEMA';
KW_INPUT: 'INPUT';  
KW_OUTPUT: 'OUTPUT';
KW_STREAM: 'STREAM';
KW_WINDOW: 'WINDOW';
KW_SIZE: 'SIZE'; 
KW_ADVANCE: 'ADVANCE';
 
// Operators
// NOTE: if you add a new function/operator, add it to sysFuncNames so that describe function _FUNC_ will work.
 
DOT : '.'; // generated as a part of Number rule
COMMA : ',' ;
SEMICOLON : ';' ;
LPAREN : '(' ;
RPAREN : ')' ;
LSQUARE : '[' ;
RSQUARE : ']' ;
MINUS : '-';
PLUS : '+';
 
// LITERALS
fragment
Letter
    : 'a'..'z' | 'A'..'Z'
    ;
fragment
Digit
    : '0'..'9'
    ;
fragment
Exponent
    : 'e' ( PLUS|MINUS )? (Digit)+
    ;
fragment
Number
    : (Digit)+ ( DOT (Digit)* (Exponent)? | Exponent)?
    ;
Identifier
    : (Letter | Digit) (Letter | Digit | '_')*
    ;
WS  :  (' '|'\r'|'\t'|'\n') {$channel=HIDDEN;}
    ;