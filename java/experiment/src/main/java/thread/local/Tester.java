package thread.local;

public class Tester {
	public static void main(String[] args) {
		User user1 = new User("zhuzhen");
		NoneThreadLocal noneThreadLocal1 = new NoneThreadLocal(user1);
		NoneThreadLocal noneThreadLocal2 = new NoneThreadLocal(user1);
		noneThreadLocal1.start();
		noneThreadLocal2.start();

		User user2 = new User("zhuzhen");
		MyThreadLocal myThreadLocal1 = new MyThreadLocal(user2);
		MyThreadLocal myThreadLocal2 = new MyThreadLocal(user2);
		myThreadLocal1.start();
		myThreadLocal2.start();
	}
}

class User {
	private String name;

	public User(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

/**
 * 没有用ThreadLocal进行保护的多线程类
 * 
 * @author Anders Zhu
 */
class NoneThreadLocal extends Thread {
	private User user;

	public NoneThreadLocal(User user) {
		this.user = user;
	}

	@Override
	public void run() {
		if (user.getName().equals("zhuzhen")) {
			user.setName("guolili");
			System.out.println(user.getName());
		}
		super.run();
	}
}

class MyThreadLocal extends Thread {
	private static final ThreadLocal<User> session = new ThreadLocal<User>() {
		@Override
		protected User initialValue() {
			System.out.println(Thread.currentThread().getName());
			return new User("zhuzhen");
		}
	};

	private User user;

	public MyThreadLocal(User user) {
		this.user = user;
	}

	@Override
	public void run() {
		if (session.get() == null) {
			session.set(user);
		}

		if (session.get().getName().equals("zhuzhen")) {
			session.get().setName("guolili");
			System.out.println(session.get().getName());
		}
		super.run();
	}

}
