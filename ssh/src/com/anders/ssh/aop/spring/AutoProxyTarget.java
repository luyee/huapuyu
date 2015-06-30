package com.anders.ssh.aop.spring;

public class AutoProxyTarget implements IProxyTarget {
	public void ShowMessage() {
		System.out.println(this.getClass().getName() + " : ShowMessage");
	}

	@Override
	public void ShowName() {
		System.out.println(this.getClass().getName() + " : ShowName");
	}

	@Override
	public void printMessage() {
		System.out.println(this.getClass().getName() + " : printMessage");

	}
}
