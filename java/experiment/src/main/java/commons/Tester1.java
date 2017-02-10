package commons;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.time.StopWatch;

public class Tester1 {
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		stopWatch.stop();
		System.out.println(stopWatch.getTime());
	}
}