objects = main.o

main : $(objects) libmylib.so
	g++ -o main $(objects) -L./ -lmylib 

main.o : main.cpp
	g++ -c main.cpp -I./

libmylib.so : mylib.o
	g++ -shared -fPCI -o libmylib.so mylib.o

mylib.o : mylib.cpp mylib.h
	g++ -c mylib.cpp

.PHONY : clean
clean : 
	-rm main $(objects) libmylib.so mylib.o


