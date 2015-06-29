package com.anders.metric;

import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

public class MetricTest {
	static final MetricRegistry metric = new MetricRegistry();

	public static void main(String args[]) {
		startReport();
		Meter requests = metric.meter("requests");
		requests.mark();
		wait5Seconds();
	}

	static void startReport() {
		ConsoleReporter reporter = ConsoleReporter.forRegistry(metric).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(1, TimeUnit.SECONDS);
	}

	static void wait5Seconds() {
		try {
			Thread.sleep(5 * 1000);
		} catch (InterruptedException e) {
		}
	}
}
