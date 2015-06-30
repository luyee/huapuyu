package com.anders.experiment.jvm.栈;

/**
 * VM参数：-Xss128k，注意：-Xoss设置本地方法栈的参数其实没有用
 * 
 * @author Anders
 * 
 */
public class StackOverflowError {

	private int stackLength = 1;

	public void stackLeak() {
		stackLength++;
		stackLeak();
	}

	// stack length : 3157
	// Exception in thread "main" java.lang.StackOverflowError
	// at com.anders.experiment.jvm.栈.Tester.stackLeak(Tester.java:15)
	// at com.anders.experiment.jvm.栈.Tester.stackLeak(Tester.java:15)
	// at com.anders.experiment.jvm.栈.Tester.stackLeak(Tester.java:15)
	public static void main(String[] args) throws Throwable {
		StackOverflowError t = new StackOverflowError();
		try {
			t.stackLeak();
		}
		catch (Throwable e) {
			System.out.println("stack length : " + t.stackLength);
			throw e;
		}
	}
}
