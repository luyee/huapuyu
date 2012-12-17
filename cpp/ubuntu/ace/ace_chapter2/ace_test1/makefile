objects=Server.o Client.o

acelib=-L/usr/lib -lACE

aceinc=-I/usr/include

Server : Server.o
	g++ -o Server Server.o $(acelib)

Client : Client.o
	g++ -o Client Client.o $(acelib)

Server.o : Server.cpp
	g++ -c $(aceinc) Server.cpp

Client.o : Client.cpp
	g++ -c $(aceinc) Client.cpp

.PHONY : clean
clean : 
	-rm Server Client $(objects)
