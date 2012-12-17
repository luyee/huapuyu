flex++ y.l
bison -d y.ypp
g++ lex.yy.cc y.tab.cpp main.cpp -o a.out
rm a
link a.out a
./a
