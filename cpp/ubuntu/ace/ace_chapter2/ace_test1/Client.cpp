#include <ace/SOCK_Connector.h>
#include <ace/SOCK_Stream.h>
#include <ace/OS.h>
#include <ace/Log_Msg.h>

#define SIZE_BUF 1024
#define NO_ITERATIONS 5

class Client
{
public:
	Client(int port, char* hostname) : remote_addr(port, hostname)
	{
		data_buf = "Hello from Client";
	}
	
	int connect_to_server()
	{
		ACE_DEBUG((LM_DEBUG, "(%P|%t) Starting connect to %s:%d\n", remote_addr.get_host_name(), remote_addr.get_port_number()));
		if (ace_sock_connector.connect(ace_sock_stream, remote_addr) == -1)
		{
			ACE_ERROR_RETURN((LM_ERROR, "(%P|%t) %p\n", "connection failed"), -1);
		}
		else
		{
			ACE_DEBUG((LM_DEBUG, "(%P|%t) connected to %s\n", remote_addr.get_host_name()));
			return 0;
		}
	}
	
	int send_to_server()
	{
		for(int i = 0; i < NO_ITERATIONS; i++)
		{
			if (ace_sock_stream.send_n(data_buf, ACE_OS::strlen(data_buf) + 1, 0) == -1)
			{
				ACE_ERROR_RETURN((LM_ERROR, "(%P|%t) %p\n", "send_n"), 0);
				break;
			}
		}
		
		if (ace_sock_stream.close() == -1)
		{
			ACE_ERROR_RETURN((LM_ERROR, "(%P|%t) %p\n", "close"), -1);
		}
		else
		{
			return 0;
		}
	}
private:
	ACE_SOCK_Stream ace_sock_stream;
	ACE_INET_Addr remote_addr;
	ACE_SOCK_Connector ace_sock_connector;
	char* data_buf;
};

int main(int argc, char* argv[])
{
/*
if(argc < 3)
{
ACE_DEBUG((LM_DEBUG, "Usage %s <hostname> <port_number>\n", argv[0]));
ACE_OS::exit(1);
}

  Client client(ACE_OS::atoi(argv[2]), argv[1]);
	*/
	
	Client client(ACE_OS::atoi("1122"), "127.0.0.1");
	client.connect_to_server();
	client.send_to_server();
	
	return 0;
}
