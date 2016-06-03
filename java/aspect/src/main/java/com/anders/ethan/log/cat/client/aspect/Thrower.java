package com.anders.ethan.log.cat.client.aspect;

public class Thrower {
	private static Throwable throwable;
	
	private Thrower() throws Throwable{
		throw throwable;
	}
	
	public static void __throw(Throwable t){
		throwable = t;
		try {
			Thrower.class.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}

