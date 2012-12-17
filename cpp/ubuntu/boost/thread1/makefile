objects=main.o

boostlib=-L/usr/lib -lboost_thread

boostinc=-I/usr/include

main : $(objects)
	g++ -o main $(objects) $(boostlib)

main.o : main.cpp
	g++ -c main.cpp $(boostinc) 

.PHONY : clean
clean : 
	-rm main $(objects)
