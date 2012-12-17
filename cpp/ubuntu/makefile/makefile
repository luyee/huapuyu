objects = main.o

edit : $(objects)
	g++ -o edit $(objects)


main.o : main.cpp main.h
	g++ -c main.cpp
#make具有自动推导能力，因此上面的操作完全可以用下面的操作代替
#main.o : main.h

.PHONY : clean
clean : 
	-rm edit $(objects)

