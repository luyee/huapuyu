#include <ace/SOCK_Acceptor.h>
#include <ace/SOCK_Stream.h>
#include <ace/OS.h>
#include <ace/Log_Msg.h>

#define SIZE_DATA 18 
#define SIZE_BUF 1024 
#define NO_ITERATIONS 5

class Server
{
public:
	Server(int port) : server_addr(port), ace_sock_acceptor(server_addr)
	{
		data_buf = new char[SIZE_BUF];
	}
	
	int handle_connection()
	{
		for(int i = 0; i < NO_ITERATIONS; i++)
		{
			int byte_count = 0;
			if ((byte_count = ace_sock_stream.recv_n(data_buf, SIZE_DATA, 0)) == -1)
			{
				ACE_ERROR((LM_ERROR, "%p\n", "Error in recv"));
			}
			else
			{
				data_buf[byte_count] = 0;
				ACE_DEBUG((LM_DEBUG, "Server received %s \n", data_buf));
			}
		}
		
		if (ace_sock_stream.close() == -1)
			ACE_ERROR_RETURN((LM_ERROR, "%p\n", "close"), 1);
		else
			return 0;
	}
	
	int accept_connections()
	{
		if (ace_sock_acceptor.get_local_addr(server_addr) == -1)
		{
			ACE_ERROR_RETURN((LM_ERROR, "%p\n", "Error in get_local_addr"), 1);
		}
		else
		{
			ACE_DEBUG((LM_DEBUG, "Starting server at port %d\n", server_addr.get_port_number()));
		}
		
		while(1)
		{
			ACE_Time_Value timeout(ACE_DEFAULT_TIMEOUT);
			if (ace_sock_acceptor.accept(ace_sock_stream, &client_addr, &timeout) == -1)
			{
				ACE_ERROR((LM_ERROR, "%p\n", "accept"));
				continue;
			}
			else
			{
				ACE_DEBUG((LM_DEBUG, "Connection established with remote %s:%d\n", client_addr.get_host_name(), client_addr.get_port_number()));
				handle_connection();
			}
		}
	}
private:
	char* data_buf;
	ACE_INET_Addr server_addr;
	ACE_INET_Addr client_addr;
	ACE_SOCK_Acceptor ace_sock_acceptor;
	ACE_SOCK_Stream ace_sock_stream;
};

int main(int argc, char* argv[])
{
/*
if (argc < 2)
{
ACE_ERROR((LM_ERROR, "Usage %s <port_num>", argv[0]));
ACE_OS::exit(1);
}

  Server server(ACE_OS::atoi(argv[1]));
	*/
	
	Server server(ACE_OS::atoi("1122"));
	
	server.accept_connections();
	
	return 0;
}
