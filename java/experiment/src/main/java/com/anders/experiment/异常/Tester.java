package com.anders.experiment.异常;

public class Tester {

	public static void main(String[] args) {
		try {
			异常类 t = new 异常类();
			try {
				t.ioException();
			}
			catch (MyIOException e) {
				RuntimeException newEx = new MyRuntimeException("IO异常重新包装为运行时异常");
				// 如果没有下面这句，则e.getCause()得到的是null
				newEx.initCause(e);
				throw newEx;
			}

			t.runtimeException();

			t.error();
		}
		catch (Throwable e) {
			// 通过e.getCause()获得原始异常
			System.out.println(e.getCause());
			System.out.println("********************分割线********************");
			System.out.println(e);
		}
	}

}

class 异常类 {
	public void ioException() throws MyIOException {
		throw new MyIOException("我的IO异常");
	}

	public void runtimeException() {
		throw new MyRuntimeException("我的运行时异常");
	}

	public void error() {
		throw new MyError("我的错误");
	}
}
