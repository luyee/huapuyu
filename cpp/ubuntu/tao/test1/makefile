objects=server.o

acelib=-L/usr/lib -lACE -lTAO -lTAO_AnyTypeCode -lTAO_PortableServer

aceinc=-I/usr/include

server : server.o echoS.o echoC.o
	g++ -o server server.o echoS.o echoC.o $(acelib)

client : client.o echoS.o echoC.o
	g++ -o client client.o echoS.o echoC.o $(acelib)

server.o : server.cpp echoS.h
	g++ -c $(aceinc) server.cpp

client.o : client.cpp echoC.h
	g++ -c $(aceinc) client.cpp

echoS.o : echoS.cpp echoS.h
	g++ -c $(aceinc) echoS.cpp

echoC.o : echoC.cpp echoC.h
	g++ -c $(aceinc) echoC.cpp


.PHONY : clean
clean : 
	-rm server $(objects)
