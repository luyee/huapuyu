package com.anders.ssh.metrics;

import java.util.concurrent.TimeUnit;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Histogram;
import com.yammer.metrics.reporting.ConsoleReporter;

public class HistogramsTest {
	private static Histogram histo = Metrics.newHistogram(HistogramsTest.class, "histo-sizes");

	public static void main(String[] args) throws InterruptedException {
		ConsoleReporter.enable(1, TimeUnit.SECONDS);

		int i = 0;

		while (true) {
			histo.update(i++);
			Thread.sleep(1000);
		}
	}
}
