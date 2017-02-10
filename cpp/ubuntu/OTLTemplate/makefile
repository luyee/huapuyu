objects=main.o BaseSql.o DataSet.o Tb_test1.o

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
	g++ -Wall -o main $(objects) $(oraclelib)

main.o : main.cpp BaseSql.h
	g++ -c main.cpp $(oracleinc) 

BaseSql.o : BaseSql.cpp BaseSql.h otlv4.h DataSet.h Tb_test1.h config.h
	g++ -c BaseSql.cpp $(oracleinc) 

DataSet.o : DataSet.cpp DataSet.h
	g++ -c DataSet.cpp

Tb_test1.o : Tb_test1.cpp Tb_test1.h
	g++ -c Tb_test1.cpp

#make具有自动推导能力，因此上面的操作完全可以用下面的操作代替
#test.o : otlv4.h

.PHONY : clean
clean : 
	-rm main $(objects)
