package com.anders.experiment.jvm;

//VM参数：-Xss20m
public class Stack {

	public int stackLength = 1;

	public void overflow() {
		stackLength++;
		overflow();
	}

	public static void main(String[] args) {
		Stack stack = new Stack();
		try {
			stack.overflow();
		}
		catch (Throwable e) {
			System.out.println(stack.stackLength);
//			System.out.println(e.getLocalizedMessage());
//			System.out.println(e.getMessage());
//			for (StackTraceElement stackTraceElement : e.getStackTrace()) {
//				System.out.println(stackTraceElement.getMethodName());
//			}
			
			while (e.getCause() != null) {
				e = e.getCause();
				System.out.println(e.getMessage());
			}
//			e.printStackTrace();
		}
	}

}
