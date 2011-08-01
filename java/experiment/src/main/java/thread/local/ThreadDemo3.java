package thread.local;

import java.util.Random;

public class ThreadDemo3 extends Thread {

	private ThreadLocal<Student> stuLocal = new ThreadLocal<Student>() {
		@Override
		protected Student initialValue() {
			System.out.println(Thread.currentThread().getId());
			return super.initialValue();
		}
	};

	public ThreadDemo3(Student stu) {
		System.out.println(Thread.currentThread().getId());
		stuLocal.set(stu);
	}

	public static void main(String[] args) {
		Student stu = new Student();
		ThreadDemo3 td31 = new ThreadDemo3(stu);
		ThreadDemo3 td32 = new ThreadDemo3(stu);
		ThreadDemo3 td33 = new ThreadDemo3(stu);
		td31.start();
		td32.start();
		td33.start();
	}

	@Override
	public void run() {
		accessStudent();
	}

	public void accessStudent() {

		String currentThreadName = Thread.currentThread().getName();
		System.out.println(currentThreadName + " is running!");
		Random random = new Random();
		int age = random.nextInt(100);
		System.out.println("thread " + currentThreadName + " set age to:" + age);
		Student student = stuLocal.get();
		student.setAge(age);
		System.out.println("thread " + currentThreadName + " first  read age is:" + student.getAge());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("thread " + currentThreadName + " second read age is:" + student.getAge());

	}
}
