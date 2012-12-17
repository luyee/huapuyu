objects=main.o

acelib=-L/usr/lib -lACE

aceinc=-I/usr/include

main : $(objects)
	g++ -o main $(objects) $(acelib)

main.o : main.cpp
	g++ -c $(aceinc) main.cpp

.PHONY : clean
clean : 
	-rm main $(objects)
