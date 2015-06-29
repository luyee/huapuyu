main : main.o
	g++ -o main main.o -L$(ORACLE_HOME)/lib -lclntsh

main.o : main.cpp otlv4.h
	g++ -c main.cpp -I$(ORACLE_HOME)/rdbms/public -I$(ORACLE_HOME)/rdbms/demo
