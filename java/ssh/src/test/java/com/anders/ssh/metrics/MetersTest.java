package com.anders.ssh.metrics;

import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Meter;
import com.yammer.metrics.reporting.ConsoleReporter;

public class MetersTest {
	private static Meter meter = Metrics.newMeter(MetersTest.class, "requests", "requests", TimeUnit.SECONDS);

	public static void main(String[] args) throws InterruptedException {
		ConsoleReporter.enable(1, TimeUnit.SECONDS);

		while (true) {
			meter.mark();
			meter.mark();
			Thread.sleep(1000);
		}
	}
}
