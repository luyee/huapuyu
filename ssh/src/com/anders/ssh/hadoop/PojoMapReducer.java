package com.anders.ssh.hadoop;

import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.hadoop.annotation.Mapper;
import org.springframework.hadoop.annotation.Reducer;

public class PojoMapReducer {

	@Mapper
	public void map(String value, Map<String, Integer> writer) {
		StringTokenizer itr = new StringTokenizer(value);
		while (itr.hasMoreTokens()) {
			writer.put(itr.nextToken(), 1);
		}
	}

	@Reducer
	public int reduce(Iterable<Integer> values) {
		int sum = 0;
		for (Integer val : values) {
			sum += val;
		}
		return sum;
	}

}