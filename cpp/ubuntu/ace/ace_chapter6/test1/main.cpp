#include <signal.h>
#include "ace/Reactor.h"
#include "ace/Event_Handler.h"

class MyEventHandler: public ACE_Event_Handler
{
    int handle_signal(int signum, siginfo_t*, ucontext_t*)
    {
        switch (signum)
        {
        case SIGILL:
            ACE_DEBUG((LM_DEBUG, "You pressed SIGILL \n"));
            break;
        case SIGINT:
            ACE_DEBUG((LM_DEBUG, "You pressed SIGINT \n"));
            break;
        }
        //注意：如果返回0事件处理器将保持在反应器上的登记；
        //如果返回<0反应器将自动回调此事件处理器的handle_close()方法，并将它从自己的内部分派表中拆除
        return -1;
    }

    virtual int handle_close(ACE_HANDLE handle, ACE_Reactor_Mask mask)
    {
        ACE_DEBUG((LM_DEBUG, "Handle close \n"));
        return 0;
    }
};

//本程序在DOS窗体中执行，执行CTRL+C查看运行结果
int main(int argc, char* argv[])
{
    MyEventHandler* eh = new MyEventHandler;

    ACE_Reactor::instance()->register_handler(SIGILL, eh);
    ACE_Reactor::instance()->register_handler(SIGINT, eh);

    while (1)
    {
        ACE_Reactor::instance()->handle_events();
    }

	return 0;
}