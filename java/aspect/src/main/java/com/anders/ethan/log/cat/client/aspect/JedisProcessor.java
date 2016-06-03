package com.anders.ethan.log.cat.client.aspect;

import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Client;
import redis.clients.jedis.Protocol.Command;
import redis.clients.util.SafeEncoder;

public class JedisProcessor implements AspectProcessor {

	@Override
	public void doBefore(Object signature, Object instance, Object[] args) {
		System.out.println("***********************doBefore begin***********************");
		System.out.println(signature);
		if (instance instanceof Client) {
			BinaryClient binaryClient = (BinaryClient) instance;
			System.out.println(binaryClient.getHost());
			System.out.println(binaryClient.getPort());
			for (Object object : args) {
            	try {
            		if (object instanceof byte[]) {
            			byte[] arg= (byte[])object;
            			String string =  SafeEncoder.encode(arg);
            			System.out.println(string);
					} else if (object instanceof Command) {
						Command command = (Command) object;
						System.out.println(command);
					} else if (object instanceof String) {
						System.out.println(object);
					}
					else if (object instanceof String[]) {
						String[] strs = (String[]) object;
						for (String str:strs) {
						System.out.println(str);
						}
					}
				} catch (Exception e) {
				}
            	
			}
		}
		System.out.println("***********************doBefore end***********************");
	}

	@Override
	public void doAfter(Object signature, Object instance, Object[] args) {
		System.out.println("***********************doAfter begin***********************");
		System.out.println(signature);
		if (instance instanceof Client) {
			BinaryClient binaryClient = (BinaryClient) instance;
			System.out.println(binaryClient.getHost());
			System.out.println(binaryClient.getPort());
			for (Object object : args) {
            	try {
            		if (object instanceof byte[]) {
            			byte[] arg= (byte[])object;
            			String string =  SafeEncoder.encode(arg);
            			System.out.println(string);
					} else if (object instanceof Command) {
						Command command = (Command) object;
						System.out.println(command);
					} else if (object instanceof String) {
						System.out.println(object);
					}
					else if (object instanceof String[]) {
						String[] strs = (String[]) object;
						for (String str:strs) {
						System.out.println(str);
						}
					}
				} catch (Exception e) {
				}
            	
			}
		}
		System.out.println("***********************doAfter end***********************");
	}

	@Override
	public void doException(Throwable throwable) {
		System.out.println("doException");
	}

	@Override
	public void doSucceed(Object signature, Object instance, Object[] args) {
		System.out.println("***********************doSucceed begin***********************");
		System.out.println(signature);
		if (instance instanceof Client) {
			BinaryClient binaryClient = (BinaryClient) instance;
			System.out.println(binaryClient.getHost());
			System.out.println(binaryClient.getPort());
			for (Object object : args) {
            	try {
            		if (object instanceof byte[]) {
            			byte[] arg= (byte[])object;
            			String string =  SafeEncoder.encode(arg);
            			System.out.println(string);
					} else if (object instanceof Command) {
						Command command = (Command) object;
						System.out.println(command);
					} else if (object instanceof String) {
						System.out.println(object);
					}
					else if (object instanceof String[]) {
						String[] strs = (String[]) object;
						for (String str:strs) {
						System.out.println(str);
						}
					}
				} catch (Exception e) {
				}
            	
			}
		}
		System.out.println("***********************doSucceed end***********************");
	}

	@Override
	public void doFailed(Object signature, Object instance, Object[] args,
			Throwable throwable) {
		System.out.println("***********************doFailed begin***********************");
		System.out.println(signature);
		if (instance instanceof Client) {
			BinaryClient binaryClient = (BinaryClient) instance;
			System.out.println(binaryClient.getHost());
			System.out.println(binaryClient.getPort());
			for (Object object : args) {
            	try {
            		if (object instanceof byte[]) {
            			byte[] arg= (byte[])object;
            			String string =  SafeEncoder.encode(arg);
            			System.out.println(string);
					} else if (object instanceof Command) {
						Command command = (Command) object;
						System.out.println(command);
					} else if (object instanceof String) {
						System.out.println(object);
					}
					else if (object instanceof String[]) {
						String[] strs = (String[]) object;
						for (String str:strs) {
						System.out.println(str);
						}
					}
				} catch (Exception e) {
				}
            	
			}
		}
		System.out.println("***********************doFailed end***********************");
	}

}
