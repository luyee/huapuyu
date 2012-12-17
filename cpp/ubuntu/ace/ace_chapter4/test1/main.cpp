#include "ace/Thread.h"
#include "ace/Synch.h"
#include <iostream>

#include <stdio.h>

static int number = 0;
static int seed = 0;

static void* worker(void* arg)
{
	ACE_UNUSED_ARG(arg);
	ACE_DEBUG((LM_DEBUG, "Thread (%t) created to do some work"));
	::number++;
	ACE_DEBUG((LM_DEBUG, " and number is %d\n", ::number));
	ACE_OS::sleep(ACE_OS::rand() % 2);
	ACE_DEBUG((LM_DEBUG, "Thread (%t) done! The number is now: %d!\n", number));
	return 0;
}

int main(int argc, char* argv[])
{
	/*
	if (argc < 2)
	{
		ACE_DEBUG((LM_DEBUG, "Usage: %s <number of threads>\n", argv[0]));
		ACE_OS::exit(1);
	}
	*/
	
	ACE_OS::srand(::seed);
	int n_threads = ACE_OS::atoi("5");
	ACE_thread_t* threadID = new ACE_thread_t[n_threads + 1];
	ACE_hthread_t* threadHandles = new ACE_hthread_t[n_threads + 1];

	//启动线程
	if (ACE_Thread::spawn_n(threadID, n_threads, (ACE_THR_FUNC)worker, 0, THR_JOINABLE | THR_NEW_LWP, ACE_DEFAULT_THREAD_PRIORITY, 0, 0, threadHandles) == -1)
		ACE_DEBUG((LM_DEBUG, "Error in spawning thread!\n"));
	
	for (int i = 0; i < n_threads; i++)
		ACE_Thread::join(threadHandles[i]);

	if (ACE_Thread::spawn((ACE_THR_FUNC)worker, 0, THR_JOINABLE | THR_NEW_LWP, ACE_DEFAULT_THREAD_PRIORITY, 0, 0, 0) == -1)
		ACE_DEBUG((LM_DEBUG, "Error in spawning thread!\n"));

	
	//注意：使用join，主线程将等到所有的子线程全部结束才继续执行；否则主线程将直接执行，不等待子线程的结束
	std::cout<<"main thread"<<std::endl;

	getchar();

	return 0;
}