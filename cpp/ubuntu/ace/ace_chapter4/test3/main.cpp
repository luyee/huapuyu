#include "ace/Synch.h"
#include "ace/Thread.h"

/*
常见的互斥体有两种： 
非递归互斥体（nonrecursive mutex）——如果当前拥有互斥体的线程在没有首先释放它的情况下，试图再次获得它，就会导致死锁或失败； 
递归互斥体（recursive mutex）——拥有互斥体的线程可以多次获得它而不会产生自死锁，只要这个线程最终以相同次数释放这个互斥体即可。 
*/

struct Args
{
public:
    Args(ACE_Lock* lock, int iterations) : mutex_(lock), iterations_(iterations) {}
    ACE_Lock* mutex_;
    int iterations_;
};

static void* worker(void* arguments)
{
    Args* arg = (Args*)arguments;
    for (int i = 0; i < arg->iterations_; i++)
    {
        //This is our critical section
        arg->mutex_->acquire();
        ACE_DEBUG((LM_DEBUG, "(%t) Trying to get a hold of this iteration\n"));
        ACE_DEBUG((LM_DEBUG, "(%t) This is iteration number %d\n", i));
        ACE_OS::sleep(2);
        //simulate critical work
        arg->mutex_->release();
    }
    return 0;
}
int main(int argc, char* argv[])
{
    /*
    if (argc < 4)
    {
        ACE_OS::printf("Usage: %s <number_of_threads><number_of_iterations> <lock_type>\n", argv[0]);
        ACE_OS::exit(1);
    }
    */
    //Polymorphic lock that will be used by the application
    ACE_Lock* lock = new ACE_Lock_Adapter<ACE_Recursive_Thread_Mutex>;
    //ACE_Lock* lock = new ACE_Lock_Adapter<ACE_Thread_Mutex>;
    //Decide which lock you want to use at run time,
    //recursive or non-recursive.
    //if (ACE_OS::strcmp(argv[3],"Recursive"))
    //    lock = new ACE_Lock_Adapter<ACE_Recursive_Thread_Mutex>;
    //else
    //    lock = new ACE_Lock_Adapter<ACE_Thread_Mutex>
    //Setup the arguments
    Args arg(lock, ACE_OS::atoi("3"));

    //Setup the arguments
    int n_threads = ACE_OS::atoi("2");
    //determine the number of threads to be spawned.
    ACE_thread_t* threadID = new ACE_thread_t[n_threads + 1];
    ACE_hthread_t* threadHandles = new ACE_hthread_t[n_threads + 1];
    if (ACE_Thread::spawn_n(threadID, //id’s for each of the threads
                            n_threads, //number of threads to spawn
                            (ACE_THR_FUNC)worker, //entry point for new thread
                            &arg, //args to worker
                            THR_JOINABLE | THR_NEW_LWP, //flags
                            ACE_DEFAULT_THREAD_PRIORITY,
                            0, 0, threadHandles)==-1)
        ACE_DEBUG((LM_DEBUG,"Error in spawning thread\n"));
    //spawn n_threads
    for (int i = 0; i < n_threads; i++)
        ACE_Thread::join(threadHandles[i]);

    return 0;
}