#include "ace/SOCK_Dgram_Mcast.h"
#include "ace/OS.h"
#include "ace/Log_Msg.h"

#define DEFAULT_MULTICAST_ADDR "225.0.0.1"
#define TIMEOUT 5

class Receiver_Multicast
{
public:
	Receiver_Multicast(u_short multicast_port) : mcast_addr(multicast_port, DEFAULT_MULTICAST_ADDR), remote_addr()
	{
		if (mcast_dgram.subscribe(mcast_addr) == -1)
		{
			ACE_DEBUG((LM_DEBUG, "Error in subscribing to Multicast address!\n"));
			exit(-1);
		}
	}
	  
	~Receiver_Multicast()
	{
		if (mcast_dgram.unsubscribe() == -1)
			ACE_DEBUG((LM_ERROR, "Error in unsubscribing from multicast group!\n"));
	}

	int recv_multicast()
	{
		if (mcast_dgram.recv(&mcast_info, sizeof(mcast_info), remote_addr) == -1)
		{
			return -1;
		}
		else
		{
			ACE_DEBUG((LM_DEBUG, "(%P|%t) Received multicast from %s:%d!\n", remote_addr.get_host_name(), remote_addr.get_port_number()));
			ACE_DEBUG((LM_DEBUG, "Successfully received %d!\n", mcast_info));
			return 0;
		}
	}
private:
	ACE_INET_Addr mcast_addr;
	ACE_INET_Addr remote_addr;
	ACE_SOCK_Dgram_Mcast mcast_dgram;
	int mcast_info;
};

int main(int argc, char* argv[])
{
	Receiver_Multicast m(4321);

	while (m.recv_multicast() != -1)
	{
		ACE_DEBUG((LM_DEBUG, "Multicaster successful!\n"));
	}
	
	ACE_DEBUG((LM_ERROR, "Multicaster failed!\n"));
	getchar();
	return -1;
}