objects=test.o

loglib=-L/usr/lib -llog4cxx

loginc=-I/usr/include

test : $(objects)
	g++ -o test $(objects) $(loglib)

test.o : test.cpp
	g++ -c $(loginc) test.cpp
#make具有自动推导能力，因此上面的操作完全可以用下面的操作代替
#test.o : otlv4.h

.PHONY : clean
clean : 
	-rm test $(objects)
