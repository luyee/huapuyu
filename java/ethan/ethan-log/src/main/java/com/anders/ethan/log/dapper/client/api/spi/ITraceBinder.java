package com.anders.ethan.log.dapper.client.api.spi;

/**
 * @author chris.xue trace实现绑定接口
 */
public interface ITraceBinder {
	public ITraceFactory getTraceFactory();

	public String getRequiredVersion();

	public String getTraceGenStr();
}
