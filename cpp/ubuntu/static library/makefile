objects = main.o

main : $(objects) libmylib.a
	g++ -o main $(objects) -L./ -lmylib 

main.o : main.cpp
	g++ -c main.cpp -I./

libmylib.a : mylib.o
	ar cr libmylib.a mylib.o

mylib.o : mylib.cpp mylib.h
	g++ -c mylib.cpp

.PHONY : clean
clean : 
	-rm main $(objects) libmylib.a mylib.o


