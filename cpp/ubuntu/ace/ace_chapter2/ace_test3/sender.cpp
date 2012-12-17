#include "ace/SOCK_Dgram_Mcast.h"
#include "ace/OS.h"
#include "ace/Log_Msg.h"

#define DEFAULT_MULTICAST_ADDR "127.0.0.1"
#define TIMEOUT 5

class Sender_Multicast
{
public:
	Sender_Multicast(u_short local_port, u_short multicast_port) : local_addr(local_port), ace_sock_dgram(local_addr), multicast_addr(multicast_port, DEFAULT_MULTICAST_ADDR)
	{
	}
	
	int send_to_multicast_group()
	{
		mcast_info = htons(10000);
		int returnValue = -1;
		if((returnValue = ace_sock_dgram.send(&mcast_info, sizeof(mcast_info), multicast_addr)) == -1)
		{
			return returnValue;
		}
		else
		{
			ACE_DEBUG((LM_DEBUG, "%s; Sent multicast to group. Number sent is %d.\n", __FILE__, mcast_info));
			return returnValue;
		}
	}
private:
	ACE_INET_Addr multicast_addr;
	ACE_INET_Addr local_addr;
	ACE_SOCK_Dgram ace_sock_dgram;
	int mcast_info;
};

int main(int argc, char* argv[])
{
	Sender_Multicast m(1234, 4321);
	int returnValue = -1;
	if ((returnValue = m.send_to_multicast_group()) == -1)
	{
		ACE_DEBUG((LM_ERROR, "Send to multicast group failed!\n"));
	}
	else
	{
		ACE_DEBUG((LM_DEBUG, "Send to multicast group successful!\n"));
	}

	getchar();
	return returnValue;
}