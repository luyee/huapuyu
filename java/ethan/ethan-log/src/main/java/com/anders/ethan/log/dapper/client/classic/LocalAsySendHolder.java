/**
 *
 */
package com.anders.ethan.log.dapper.client.classic;

/**
 * @author mandy
 */
public class LocalAsySendHolder {
	private final static LocalAsySendImpl instance;

	static {
		instance = new LocalAsySendImpl();
		instance.start();
	}

	private LocalAsySendHolder() {
	}

	public static LocalAsySendImpl getInstance() {
		return instance;
	}

	public static void main(String[] args) throws Exception {
	}

}
