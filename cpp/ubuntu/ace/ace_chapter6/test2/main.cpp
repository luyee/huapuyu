#include "ace/Reactor.h"
#include "ace/SOCK_Acceptor.h"

#define PORT_NO 1122

class My_Input_Handler : public ACE_Event_Handler
{
public:
    My_Input_Handler()
    {
        ACE_DEBUG((LM_DEBUG, "Constructor\n"));
    }

    int handle_input(ACE_HANDLE)
    {
        peer_i().recv_n(data, 18);
        ACE_DEBUG((LM_DEBUG, "%s\n", data));
        return 0;
    }

    ACE_HANDLE get_handle() const
    {
        return this->peer_.get_handle();
    }

    ACE_SOCK_Stream& peer_i()
    {
        return this->peer_;
    }
private:
    ACE_SOCK_Stream peer_;
    char data[12];
};

class My_Accept_Handler: public ACE_Event_Handler
{
public:
    My_Accept_Handler(ACE_Addr &addr)
    {
        this->open(addr);
    }

    int open(ACE_Addr &addr)
    {
        peer_acceptor.open(addr);
        return 0;
    }

    int handle_input(ACE_HANDLE handle)
    {
        ACE_INET_Addr remote_addr("127.0.0.1", PORT_NO);
		My_Input_Handler* eh = new My_Input_Handler();
        //if (this->peer_acceptor.accept(eh->peer_i(), &remote_addr, 0, 1) == -1)
		//remote_addr为0，就是默认本机地址
		if (this->peer_acceptor.accept(eh->peer_i(), 0, 0, 1) == -1)
		{
			ACE_DEBUG((LM_ERROR, "Error in connection\n"));
		}
        ACE_DEBUG((LM_DEBUG, "Connection established\n"));
        ACE_Reactor::instance()->register_handler(eh, ACE_Event_Handler::READ_MASK);
        //从内部分派表中拆除
		return -1;
    }

    ACE_HANDLE get_handle(void) const
    {
        return this->peer_acceptor.get_handle();
    }
private:
    ACE_SOCK_Acceptor peer_acceptor;
};

int main(int argc, char* argv[])
{
    ACE_INET_Addr addr(PORT_NO);
    My_Accept_Handler* eh = new My_Accept_Handler(addr);
    ACE_Reactor::instance()->register_handler(eh, ACE_Event_Handler::ACCEPT_MASK);

    while (1)
    {
        ACE_Reactor::instance()->handle_events();
    }

    return 0;
}