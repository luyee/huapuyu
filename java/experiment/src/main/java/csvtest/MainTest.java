package csvtest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTest {

	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(4);
		pool.execute(new Gift());
		// pool.execute(new GiftDb());
		pool.shutdown();
	}
}
