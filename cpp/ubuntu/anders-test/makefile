objects=test.o

#oraclelib=-L$(ORACLE_HOME)/lib -lclntsh
oraclelib=-L/usr/lib/oracle/xe/app/oracle/product/10.2.0/client/lib -lclntsh

#oracleinclude=-I$(ORACLE_HOME)/rdbms/public -I$(ORACLE_HOME)/rdbms/demo
oracleinclude=-I/usr/lib/oracle/xe/app/oracle/product/10.2.0/client/rdbms/public -I/usr/lib/oracle/xe/app/oracle/product/10.2.0/client/rdbms/demo

test : $(objects)
	g++ -o test $(objects) $(oraclelib)

test.o : test.cpp otlv4.h
	g++ -c $(oracleinclude) test.cpp
#make具有自动推导能力，因此上面的操作完全可以用下面的操作代替
#test.o : otlv4.h

.PHONY : clean
clean : 
	-rm test $(objects)
