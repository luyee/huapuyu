package com.anders.ethan.log.cat.client.common;

public interface Constants {
	public final static String TRANS_TYPE_MVC = "MVC";
	// TODO Anders 是否要换成自定义名称
	public final static String TRANS_TYPE_CONSUMER = "PigeonCall";
	public final static String TRANS_TYPE_PROVIDER = "PigeonService";
	public final static String TRANS_TYPE_SERVICE = "Service";
	public final static String TRANS_TYPE_JDBC = "JDBC";
	public final static String TRANS_TYPE_JEDIS = "Jedis";

	public final static String LOG_FAILED_TO_CREATE_TRANS = "[EX] failed to create cat transaction";
	
	public final static String LOG_DUBBO_CONSUMER_IN_MSG = "[IN] dubbo consumer : {}, remote address : {}";
	public final static String LOG_DUBBO_CONSUMER_EX_MSG = "[EX] dubbo consumer : {}, remote address : {}";
	public final static String LOG_DUBBO_CONSUMER_OUT_MSG = "[OUT] dubbo consumer : {}, remote address : {}";
	
	public final static String LOG_DUBBO_PROVIDER_IN_MSG = "[IN] dubbo provider : {}, remote address : {}";
	public final static String LOG_DUBBO_PROVIDER_EX_MSG = "[EX] dubbo provider : {}, remote address : {}";
	public final static String LOG_DUBBO_PROVIDER_OUT_MSG = "[OUT] dubbo provider : {}, remote address : {}";
	
	public final static String LOG_SERVICE_IN_MSG = "[IN] service : {}";
	public final static String LOG_SERVICE_EX_MSG = "[EX] service : {}";
	public final static String LOG_SERVICE_OUT_MSG = "[OUT] service : {}";
	
	public final static String LOG_JDBC_IN_MSG = "[IN] jdbc : {}, url : {}";
	public final static String LOG_JDBC_EX_MSG = "[EX] jdbc : {}, url : {}";
	public final static String LOG_JDBC_OUT_MSG = "[OUT] jdbc : {}, url : {}";
	
	public final static String LOG_JEDIS_IN_MSG = "[IN] jedis : {}";
	public final static String LOG_JEDIS_EX_MSG = "[EX] jedis : {}";
	public final static String LOG_JEDIS_OUT_MSG = "[OUT] jedis : {}";

}
