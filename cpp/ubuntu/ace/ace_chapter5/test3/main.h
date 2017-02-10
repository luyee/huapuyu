#include "ace/OS.h"
#include "ace/Task.h"
#include "ace/Message_Block.h"
#include "ace/Future.h"
#include "ace/Activation_Queue.h"
#include "ace/Method_Object.h"



//The worker thread with which the client will interact
class Logger: public ACE_Task<ACE_MT_SYNCH>
{
public:
    //Initialization and termination methods
    Logger();
    virtual ~Logger(void);
    virtual int open (void *);
    virtual int close (u_long flags = 0);
    //The entry point for all threads created in the Logger
    virtual int svc (void);
    ///////////////////////////////////////////////////////
    //Methods which can be invoked by client asynchronously.
    ///////////////////////////////////////////////////////
    //Log message
    ACE_Future<u_long> logMsg(const char* msg);
    //Return the name of the Task
    ACE_Future<const char*> name (void);
    ///////////////////////////////////////////////////////
    //Actual implementation methods for the Logger
    ///////////////////////////////////////////////////////
    u_long logMsg_i(const char *msg);
    const char * name_i();
private:
    char *name_;
    ACE_Activation_Queue activation_queue_;
};



//Method Object which implements the logMsg() method of the active
//Logger active object class
class logMsg_MO: public ACE_Method_Object
{
public:
    //Constructor which is passed a reference to the active object, the
    //parameters for the method, and a reference to the future which
    //contains the result.
    logMsg_MO(Logger * logger, const char * msg, ACE_Future<u_long> &future_result);
    virtual ~logMsg_MO();
    //The call() method will be called by the Logger Active Object
    //class, once this method object is dequeued from the activation
    //queue. This is implemented so that it does two things. First it
    //must execute the actual implementation method (which is specified
    //in the Logger class. Second, it must set the result it obtains from
    //that call in the future object that it has returned to the client.
    //Note that the method object always keeps a reference to the same
    //future object that it returned to the client so that it can set the
    //result value in it.
    virtual int call (void);
private:
    Logger * logger_;
    const char* msg_;
    ACE_Future<u_long> future_result_;
};



//Method Object which implements the name() method of the active Logger
//active object class
class name_MO: public ACE_Method_Object
{
public:
    //Constructor which is passed a reference to the active object, the
    //parameters for the method, and a reference to the future which
    //contains the result.
    name_MO(Logger * logger, ACE_Future<const char*> &future_result);
    virtual ~name_MO();
    //The call() method will be called by the Logger Active Object
    //class, once this method object is dequeued from the activation
    //queue. This is implemented so that it does two things. First it
    //must execute the actual implementation method (which is specified
    //in the Logger class. Second, it must set the result it obtains from
    //that call in the future object that it has returned to the client.
    //Note that the method object always keeps a reference to the same
    //future object that it returned to the client so that it can set the
    //result value in it.
    virtual int call (void);
private:
    Logger * logger_;
    ACE_Future<const char*> future_result_;
};