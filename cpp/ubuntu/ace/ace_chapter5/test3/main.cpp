#include "main.h"

#include <iostream>
using namespace std;

//Constructor for the Logger
Logger::Logger()
{
    this->name_= new char[sizeof("Worker")];
	ACE_OS::strcpy(name_,"Worker");
}
//Destructor
Logger::~Logger(void)
{
    delete this->name_;
}
//The open method where the active object is activated
int Logger::open (void *)
{

    ACE_DEBUG ((LM_DEBUG, "(%t) Logger %s open\n", this->name_));
    return this->activate (THR_NEW_LWP);
}
//Called then the Logger task is destroyed.
int Logger::close (u_long flags = 0)
{
    ACE_DEBUG((LM_DEBUG, "Closing Logger \n"));
    return 0;
}
//The svc() method is the starting point for the thread created in the
//Logger active object. The thread created will run in an infinite loop
//waiting for method objects to be enqueued on the private activation
//queue. Once a method object is inserted onto the activation queue the
//thread wakes up, dequeues the method object and then invokes the
//call() method on the method object it just dequeued. If there are no
//method objects on the activation queue, the task blocks and falls
//asleep.
int Logger::svc (void)
{
    while (1)
    {
        // Dequeue the next method object (we use an auto pointer in
        // case an exception is thrown in the <call>).
		auto_ptr<ACE_Method_Object> mo(this->activation_queue_.dequeue ());
        ACE_DEBUG ((LM_DEBUG, "(%t) calling method object\n"));
        // Call it.
        if (mo->call () == -1)
            break;
        // Destructor automatically deletes it.
    }
    return 0;
}
//////////////////////////////////////////////////////////////
//Methods which are invoked by client and execute asynchronously.
//////////////////////////////////////////////////////////////
//Log this message
ACE_Future<u_long> Logger::logMsg(const char* msg)
{
    ACE_Future<u_long> resultant_future;
    //Create and enqueue method object onto the activation queue
    this->activation_queue_.enqueue
    (new logMsg_MO(this,msg,resultant_future));
    return resultant_future;
}
//Return the name of the Task
ACE_Future<const char*> Logger::name (void)
{
    ACE_Future<const char*> resultant_future;
    //Create and enqueue onto the activation queue
    this->activation_queue_.enqueue
    (new name_MO(this, resultant_future));
    return resultant_future;
}
///////////////////////////////////////////////////////
//Actual implementation methods for the Logger
///////////////////////////////////////////////////////
u_long Logger::logMsg_i(const char *msg)
{
    ACE_DEBUG((LM_DEBUG,"Logged: %s\n",msg));
    //Go to sleep for a while to simulate slow I/O device
    ACE_OS::sleep(2);
    return 10;
}
const char * Logger::name_i()
{
    //Go to sleep for a while to simulate slow I/O device
    ACE_OS::sleep(2);
    return name_;
}



//Implementation for the logMsg_MO method object.
//Constructor
logMsg_MO::logMsg_MO(Logger * logger, const char * msg, ACE_Future<u_long>& future_result)
        :logger_(logger), msg_(msg), future_result_(future_result)
{
    ACE_DEBUG((LM_DEBUG, "(%t) logMsg invoked \n"));
}
//Destructor
logMsg_MO::~logMsg_MO()
{
    ACE_DEBUG ((LM_DEBUG, "(%t) logMsg object deleted.\n"));
}
//Invoke the logMsg() method
int logMsg_MO::call (void)
{
    return this->future_result_.set (
               this->logger_->logMsg_i (this->msg_));
}

//Implementation for the name_MO method object.
//Constructor
name_MO::name_MO(Logger * logger, ACE_Future<const char*> &future_result):
        logger_(logger), future_result_(future_result)
{
    ACE_DEBUG((LM_DEBUG, "(%t) name() invoked \n"));
}
//Destructor
name_MO::~name_MO()
{
    ACE_DEBUG ((LM_DEBUG, "(%t) name object deleted.\n"));
}
//Invoke the name() method
int name_MO::call (void)
{
    return this->future_result_.set (this->logger_->name_i ());
}


//Client or application code.
int main (int, char *[])
{
    //Create a new instance of the Logger task
    Logger *logger = new Logger;
    //The Futures or IOUs for the calls that are made to the logger.
    ACE_Future<u_long> logresult;
    ACE_Future<const char *> name;
    //Activate the logger
    logger->open(0);
    //Log a few messages on the logger
    for (size_t i = 0; i < n_loops; i++)
    {
        char *msg= new char[50];
        ACE_DEBUG ((LM_DEBUG, "Issuing a non-blocking logging call\n"));
        ACE_OS::sprintf(msg, "This is iteration %d", i);
        logresult= logger->logMsg(msg);
        //Don¡¯t use the log result here as it isn't that important...
    }
    ACE_DEBUG((LM_DEBUG,"(%t)Invoked all the log calls and can now continue with other work \n"));
    //Do some work over here...
    // ...
    // ...
    //Find out the name of the logging task
    name = logger->name ();
    //Check to "see" if the result of the name() call is available
    if (name.ready())
        ACE_DEBUG((LM_DEBUG,"Name is ready! \n"));
    else
        ACE_DEBUG((LM_DEBUG,"Blocking till I get the result of that call \n"));

    //obtain the underlying result from the future object.
    const char* task_name;
    name.get(task_name);
    ACE_DEBUG ((LM_DEBUG,"(%t)==> The name of the task is: %s\n\n\n", task_name));
    //Wait for all threads to exit.
    ACE_Thread_Manager::instance()->wait();

    return 0;
}