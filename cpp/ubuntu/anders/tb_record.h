#include <string>
using namespace std;

class TB_RECORD
{
public:
	//call_pk
	void setCall_pk(string call_pk)
	{
		this->call_pk = call_pk;
	}
	string getCall_pk()
	{
		return this->call_pk;
	}

	//ui_id
	void setUi_id(string ui_id)
	{
		this->ui_id = ui_id;
	}
	string getUi_id()
	{
		return this->ui_id;
	}

	//op_id
	void setOp_id(string op_id)
	{
		this->op_id = op_id;
	}
	string getOp_id()
	{
		return this->op_id;
	}

	//org_id
	void setOrg_id(string org_id)
	{
		this->org_id = org_id;
	}
	string getOrg_id()
	{
		return this->org_id;
	}

	//channel_id
	void setChannel_id(string channel_id)
	{
		this->channel_id = channel_id;
	}
	string getChannel_id()
	{
		return this->channel_id;
	}

	//client_ip
	void setClient_ip(string client_ip)
	{
		this->client_ip = client_ip;
	}
	string getClient_ip()
	{
		return this->client_ip;
	}

	//call_sequence
	void setCall_sequence(string call_sequence)
	{
		this->call_sequence = call_sequence;
	}
	string getCall_sequence()
	{
		return this->call_sequence;
	}

	//web_partition_id
	void setWeb_partition_id(string web_partition_id)
	{
		this->web_partition_id = web_partition_id;
	}
	string getWeb_partition_id()
	{
		return this->web_partition_id;
	}

	//web_server_ip
	void setWeb_server_ip(string web_server_ip)
	{
		this->web_server_ip = web_server_ip;
	}
	string getWeb_server_ip()
	{
		return this->web_server_ip;
	}

	//call_id
	void setCall_id(string call_id)
	{
		this->call_id = call_id;
	}
	string getCall_id()
	{
		return this->call_id;
	}

	//parent_call_id
	void setParent_call_id(string parent_call_id)
	{
		this->parent_call_id = parent_call_id;
	}
	string getParent_call_id()
	{
		return this->parent_call_id;
	}

	//monitor_data_type
	void setMonitor_data_type(string monitor_data_type)
	{
		this->monitor_data_type = monitor_data_type;
	}
	string getMonitor_data_type()
	{
		return this->monitor_data_type;
	}

	//statement
	void setStatement(string statement)
	{
		this->statement = statement;
	}
	string getStatement()
	{
		return this->statement;
	}

	//start_time
	void setStart_time(string start_time)
	{
		this->start_time = start_time;
	}
	string getStart_time()
	{
		return this->start_time;
	}

	//finish_time
	void setFinish_time(string finish_time)
	{
		this->finish_time = finish_time;
	}
	string getFinish_time()
	{
		return this->finish_time;
	}

	//total_time
	void setTotal_time(int total_time)
	{
		this->total_time = total_time;
	}
	int getTotal_time()
	{
		return this->total_time;
	}
private:
	string call_pk;
	string ui_id;
	string op_id;
	string org_id;
	string channel_id;
	string client_ip;
	string call_sequence;
	string web_partition_id;
	string web_server_ip;
	string call_id;
	string parent_call_id;
	string monitor_data_type;
	string statement;
	string start_time;
	string finish_time;
	int total_time;
};
