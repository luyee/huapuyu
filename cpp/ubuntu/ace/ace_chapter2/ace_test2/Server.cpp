#include <ace/OS.h>
#include <ace/SOCK_Dgram.h>
#include <ace/INET_Addr.h>
#include <ace/Log_Msg.h>

#include <iostream>
using namespace std;

#define DATA_BUFFER_SIZE 1024
#define SIZE_DATA 10

class Server
{
public:
	Server(u_short local_port) : local_addr(local_port), ace_sock_dgram(local_addr)
	{
		data_buf = new char[DATA_BUFFER_SIZE];
	}
	
	int accept_data()
	{
		int byte_count = 0;
		while ((byte_count = ace_sock_dgram.recv(data_buf, SIZE_DATA, remote_addr)) != -1)
		{
			data_buf[byte_count] = 0;
			ACE_DEBUG((LM_DEBUG, "Data received from client %s:%d was: %s!\n", remote_addr.get_host_name(), remote_addr.get_port_number(), data_buf));
			ACE_OS::sleep(1);
			if (send_data() == -1) 
				break;
		}
		return -1;
	}
	
	int send_data()
	{
		ACE_DEBUG((LM_DEBUG, "Preparing to send reply to client %s:%d!\n", remote_addr.get_host_name(), remote_addr.get_port_number()));
		ACE_OS::sprintf(data_buf, "server...");
		return ace_sock_dgram.send(data_buf, ACE_OS::strlen(data_buf) + 1, remote_addr);
	}
private:
	char* data_buf;
	ACE_INET_Addr remote_addr;
	ACE_INET_Addr local_addr;
	ACE_SOCK_Dgram ace_sock_dgram;
};

int main(int argc, char* argv[])
{
	/*
	if (argc < 2)
	{
		ACE_DEBUG((LM_DEBUG, "Usage %s <Port Number>", argv[0]));
		ACE_OS::exit(1);
	}
	Server server(ACE_OS::atoi(argv[1]));
	*/
	
	Server server(ACE_OS::atoi("1234"));
	server.accept_data();
	
	return 0;
}
