package com.anders.experiment.jvm.栈;

/**
 * VM参数：-Xss100m，注意：-Xoss设置本地方法栈的参数其实没有用
 * 
 * @author Anders
 * 
 */
public class OutOfMemoryError {

	private void neverStop() {
		while (true) {
			;
		}
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					neverStop();
				}
			});
			thread.start();
		}
	}

	// Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
	// at java.lang.Thread.start0(Native Method)
	// at java.lang.Thread.start(Thread.java:640)
	// at com.anders.experiment.jvm.栈.OutOfMemoryError.stackLeakByThread(OutOfMemoryError.java:26)
	// at com.anders.experiment.jvm.栈.OutOfMemoryError.main(OutOfMemoryError.java:32)
	public static void main(String[] args) {
		OutOfMemoryError oom = new OutOfMemoryError();
		oom.stackLeakByThread();
	}

}
