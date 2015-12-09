package com.anders.ethan.job.lts.process.impl;

import com.anders.ethan.job.lts.process.DataProcess;

public class SplitProcessImpl extends DataProcess {

	@Override
	public void split() {
		for (String key : originalParams.keySet()) {
			String value = originalParams.get(key);
			if (key.equals("orderId")) {
				String[] values = value.split(",");

				for (int i = 0; i < values.length; i++) {
					int index = i % shardingCount;
					String v = getParam(index, key);
					if (v != null) {
						setParam(index, key, v + "," + values[i]);
					} else {
						setParam(index, key, values[i]);
					}
				}

			} else {
				setParam(key, value);
			}
		}
	}
}
