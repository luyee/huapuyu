package com.anders.ethan.sharding.id;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

public class IdGenerator {

	private AtomicInteger seq = new AtomicInteger(0);

	private String machineId;

	public String genId() {
		Assert.hasText(machineId);

		seq.compareAndSet(999, 0);

		return System.currentTimeMillis()
				+ StringUtils.leftPad(machineId, 3, "0")
				+ StringUtils.leftPad(String.valueOf(seq.incrementAndGet()), 3,
						"0");
	}

	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		Assert.hasText(machineId);
		if (machineId.length() > 3) {
			this.machineId = machineId.substring(0, 3);
		} else {
			this.machineId = machineId;
		}
	}
}
