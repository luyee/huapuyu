objects=main.o BaseSql.o DataSet.o tb_record.o flex.o bison.o

oraclelib=-L$(ORACLE_HOME)/lib -lclntsh
#oraclelib=-L/usr/lib/oracle/xe/app/oracle/product/10.2.0/client/lib -lclntsh

oracleinc=-I$(ORACLE_HOME)/rdbms/public -I$(ORACLE_HOME)/rdbms/demo
#oracleinclude=-I/usr/lib/oracle/xe/app/oracle/product/10.2.0/client/rdbms/public -I/usr/lib/oracle/xe/app/oracle/product/10.2.0/client/rdbms/demo

acelib=-L$(ACE_ROOT)/lib -lACE
#acelib=-L/home/anders/software/ACE_wrappers/lib -lACE

aceinc=-I$(ACE_ROOT)
#aceinc=-I/home/anders/software/ACE_wrappers

loglib=-L/usr/lib -llog4cxx

loginc=-I/usr/include

main : $(objects)
	g++ -o main $(objects) $(oraclelib) $(acelib) $(loglib)

main.o : main.cpp BaseSql.h config.h bison.cpp bison.hpp flex.cpp 
	g++ -c $(oracleinc) $(aceinc) $(loginc) main.cpp

BaseSql.o : BaseSql.cpp BaseSql.h otlv4.h DataSet.h tb_record.h config.h
	g++ -c $(oracleinc) BaseSql.cpp

DataSet.o : DataSet.cpp DataSet.h
	g++ -c DataSet.cpp

tb_record.o : tb_record.cpp tb_record.h
	g++ -c tb_record.cpp

flex.o : flex.cpp bison.hpp
	g++ -c flex.cpp

bison.o : bison.cpp bison.hpp
	g++ -c bison.cpp

flex.cpp : flex.l
	flex -oflex.cpp flex.l

bison.cpp bison.hpp : bison.y
	bison -d -o bison.cpp bison.y

#make具有自动推导能力，因此上面的操作完全可以用下面的操作代替
#test.o : otlv4.h

.PHONY : clean
clean : 
	-rm main $(objects) flex.cpp bison.cpp bison.hpp
