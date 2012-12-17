objects=sender.o receiver.o

acelib=-L/usr/lib -lACE

aceinc=-I/usr/include

sender : sender.o
	g++ -o sender sender.o $(acelib)

receiver : receiver.o
	g++ -o receiver receiver.o $(acelib)

sender.o : sender.cpp
	g++ -c sender.cpp $(aceinc) 

receiver.o : receiver.cpp
	g++ -c receiver.cpp $(aceinc) 

.PHONY : clean
clean : 
	-rm sender receiver $(objects)
