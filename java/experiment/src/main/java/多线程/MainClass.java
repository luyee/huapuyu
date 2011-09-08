package 多线程;

public class MainClass {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new Increase());
		Thread t2 = new Thread(new Increase());
		Thread t3 = new Thread(new Decrease());
		t1.start();
		t2.start();
		t3.start();
	}
}

class CountNum {
	private static ThreadLocal<CountNum> countNumLocals = new ThreadLocal<CountNum>();

	public static CountNum getCountNum() {
		if (CountNum.countNumLocals.get() == null) {
			System.out.println("here");
			CountNum.countNumLocals.set(new CountNum());
		}
		return CountNum.countNumLocals.get();
	}

	private int i = 0;

	public void increase() {
		++i;
		System.out.println(Thread.currentThread().getName() + " : " + i);
	}

	public void decrease() {
		--i;
		System.out.println(Thread.currentThread().getName() + " : " + i);
	}
}

class Increase implements Runnable {
	public Increase() {
	}

	@Override
	public void run() {
		CountNum countNum = CountNum.getCountNum();

		for (int i = 0; i < 10; i++) {
			countNum.increase();
		}
	}
}

class Decrease implements Runnable {
	public Decrease() {
	}

	@Override
	public void run() {
		CountNum countNum = CountNum.getCountNum();

		for (int i = 0; i < 10; i++) {
			countNum.decrease();
		}
	}
}
