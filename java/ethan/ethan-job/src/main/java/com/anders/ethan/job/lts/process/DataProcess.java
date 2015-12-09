package com.anders.ethan.job.lts.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lts.core.commons.utils.Assert;

public abstract class DataProcess {

	protected int shardingCount = 1;
	protected Map<String, String> originalParams;
	protected List<Map<String, String>> newParams;

	public void process() {
		if (newParams == null || newParams.size() == 0) {
			newParams = new ArrayList<Map<String, String>>(shardingCount);
			for (int i = 0; i < shardingCount; i++) {
				newParams.add(new HashMap<String, String>());
			}
		}

		split();
	}

	public abstract void split();

	public Map<String, String> getSliceParams(int sliceNum) {
		if (sliceNum < 0 || sliceNum >= newParams.size()) {
			throw new ArrayIndexOutOfBoundsException();
		}

		return newParams.get(sliceNum);
	}

	public void setOriginalParams(Map<String, String> originalParams) {
		this.originalParams = originalParams;
	}

	public void setShardingCount(int shardingCount) {
		this.shardingCount = shardingCount;
	}

	public void setParam(int index, String key, String value) {
		if (index < 0 && index >= shardingCount) {
			throw new ArrayIndexOutOfBoundsException();
		}

		Assert.notNull(key);
		Assert.notNull(value);

		Map<String, String> map = newParams.get(index);
		if (map == null) {
			map = new HashMap<String, String>();
			newParams.set(index, map);
		}

		map.put(key, value);
	}

	public String getParam(int index, String key) {
		if (index < 0 && index >= shardingCount) {
			throw new ArrayIndexOutOfBoundsException();
		}

		Assert.notNull(key);

		Map<String, String> map = newParams.get(index);
		if (map == null) {
			map = new HashMap<String, String>();
			newParams.set(index, map);
			return null;
		}

		return map.get(key);
	}

	public void setParam(String key, String value) {
		Assert.notNull(key);
		Assert.notNull(value);

		for (int index = 0; index < newParams.size(); index++) {
			Map<String, String> map = newParams.get(index);
			if (map == null) {
				map = new HashMap<String, String>();
				newParams.set(index, map);
			}
			map.put(key, value);
		}

	}
}
