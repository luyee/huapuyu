#include <ace/OS.h>
#include <ace/SOCK_Dgram.h>
#include <ace/INET_Addr.h>
#include <ace/Log_Msg.h>

#include <iostream>
using namespace std;

#define send_data_bufFER_SIZE 1024
#define SIZE_DATA 10

class Client
{
public:
	Client(u_short remote_port, char* remote_host, u_short local_port) : remote_addr(remote_port, remote_host), local_addr(local_port), ace_sock_dgram(local_addr)
	{
		send_data_buf = new char[SIZE_DATA];
		accept_data_buf = new char[SIZE_DATA];
	}
	
	int accept_data()
	{
		int returnValue = -1;
		if ((returnValue = ace_sock_dgram.recv(accept_data_buf, SIZE_DATA, remote_addr)) != -1)
		{
			accept_data_buf[returnValue] = 0;
			ACE_DEBUG((LM_DEBUG, "Data received from server[%s:%d] was: %s!\n", remote_addr.get_host_name(), remote_addr.get_port_number(), accept_data_buf));
		}
		return returnValue;
	}

	int send_data()
	{
		ACE_DEBUG((LM_DEBUG, "Preparing to send data to server[%s:%d]!\n", remote_addr.get_host_name(), remote_addr.get_port_number()));
		ACE_OS::sprintf(send_data_buf, "Client...");
		int returnValue = -1;
		while ((returnValue = ace_sock_dgram.send(send_data_buf, ACE_OS::strlen(send_data_buf) + 1, remote_addr)) != -1)
		{
			ACE_OS::sleep(1);
			if(accept_data() == -1)
				break;
		}
		return returnValue;
	}
private:
	char* send_data_buf;
	char* accept_data_buf;
	ACE_INET_Addr remote_addr;
	ACE_INET_Addr local_addr;
	ACE_SOCK_Dgram ace_sock_dgram;
};
int main(int argc, char* argv[])
{
	/*
	if (argc < 3)
	{
		ACE_OS::printf("Usage: %s <hostname> <port_number> \n", argv[0]);
		ACE_OS::exit(1);
	}
	Client client(argv[1], ACE_OS::atoi(argv[2]));
	*/
	Client client(ACE_OS::atoi("1234"), "127.0.0.1", ACE_OS::atoi("4321"));
	client.send_data();

	getchar();

	return 0;
}
