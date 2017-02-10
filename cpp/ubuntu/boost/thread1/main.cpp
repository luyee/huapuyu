#include <boost/thread/thread.hpp>
#include <boost/thread/mutex.hpp>
#include <iostream>
using namespace std;
using namespace boost;

#include <stdio.h>

mutex io_mutex;

void count() 
{
	for (int i = 0; i < 10; ++i)
	{
		mutex::scoped_lock lock(io_mutex);
		cout<<i<<endl;
	}
}

int main()
{
	thread thrd1(&count);
	thread thrd2(&count);

	thrd1.join();
	thrd2.join();

	cout<<"is ending"<<endl;

	getchar();

	return 0;
}
