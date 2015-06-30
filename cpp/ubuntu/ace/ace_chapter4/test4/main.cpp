#include "ace/Token.h"
#include "ace/Thread.h"
#include "ace/Synch.h"

struct Args
{
public:
    Args(int iterations) : token_("myToken"), iterations_(iterations) {}
    ACE_Token token_;
    int iterations_;
};

static void* worker(void* arguments)
{
    Args* arg = (Args*) arguments;
    for (int i = 0; i < arg->iterations_; i++)
    {
        //This is our critical section
        arg->token_.acquire();
		ACE_DEBUG((LM_DEBUG,"(%t) Trying to get a hold of this iteration\n"));
        ACE_DEBUG((LM_DEBUG,"(%t) This is iteration number %d\n",i));
        //work
        ACE_OS::sleep(2);
        arg->token_.release();
    }
    return 0;
}
int main(int argc, char* argv[])
{
    Args arg(atoi("3"));

    int n_threads = ACE_OS::atoi("2");

    ACE_thread_t* threadID = new ACE_thread_t[n_threads + 1];
    ACE_hthread_t* threadHandles = new ACE_hthread_t[n_threads + 1];
    if (ACE_Thread::spawn_n(threadID, //id¡¯s for each of the threads
		n_threads, //number of threads to spawn
		(ACE_THR_FUNC)worker, //entry point for new thread
		&arg, //args to worker
		THR_JOINABLE | THR_NEW_LWP, //flags
		ACE_DEFAULT_THREAD_PRIORITY,
		0, 0, threadHandles)==-1)
        ACE_DEBUG((LM_DEBUG,"Error in spawning thread\n"));

    for (int i = 0; i < n_threads; i++)
        ACE_Thread::join(threadHandles[i]);
	
    return 0;
}
