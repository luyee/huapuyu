#include <stdio.h>

#include "ace/OS.h"
#include "ace/Task.h"

class TaskOne : public ACE_Task<ACE_MT_SYNCH>
{
public:
    //Implement the Service Initialization and Termination methods
    int open(void*)
    {
        ACE_DEBUG((LM_DEBUG,"(%t) Active Object opened \n"));
        //Activate the object with a thread in it.
        activate();
        return 0;
    }

    int close(u_long)
    {
        ACE_DEBUG((LM_DEBUG, "(%t) Active Object being closed down \n"));
        return 0;
    }

    int svc(void)
    {
        ACE_DEBUG((LM_DEBUG, "(%t) This is being done in a separate thread \n"));
        
		for (int i = 0; i < 10; i++)
			printf("%d\n", i);

        return 0;
    }
};
int main(int argc, char *argv[])
{
    //Create the task
    TaskOne* one = new TaskOne;
    //Start up the task
    one->open(0);
    //wait for all the tasks to exit
    ACE_Thread_Manager::instance()->wait();
    ACE_DEBUG((LM_DEBUG, "(%t) Main Task ends!\n"));

	return 0;
}