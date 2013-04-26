package jvm;

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
			throw new RuntimeException(e);
		}
	}

}
