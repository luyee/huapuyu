package com.anders.ethan.sharding.lb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class RoundRobinLB implements LB<String> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RoundRobinLB.class);

	private static final int MIN_LB_FACTOR = 1;

	private List<String> targets;
	private int currentPos;

	private Map<String, Integer> currentTargets;
	private Map<String, Integer> failedTargets;

	public RoundRobinLB(Map<String, Integer> lbFactors) {
		// Assert.notNull(lbFactors);
		Assert.notEmpty(lbFactors);
		currentTargets = Collections.synchronizedMap(lbFactors);
		// Assert.notEmpty(lbFactors);
		failedTargets = Collections
				.synchronizedMap(new HashMap<String, Integer>(currentTargets
						.size()));
		reInitTargets(currentTargets);
	}

	private void reInitTargets(Map<String, Integer> lbFactors) {
		targets = initTargets(lbFactors);
		// TODO Anders 抛错还是日志提示
		// Assert.notEmpty(targets);
		if (CollectionUtils.isEmpty(targets)) {
			LOGGER.error("targets is empty");
		}
		currentPos = 0;
	}

	public List<String> initTargets(Map<String, Integer> lbFactors) {
		if (MapUtils.isEmpty(lbFactors)) {
			return null;
		}

		fixFactor(lbFactors);

		Collection<Integer> factors = lbFactors.values();
		int min = Collections.min(factors);
		if (min > MIN_LB_FACTOR && canModAll(min, factors)) {
			return buildBalanceTargets(lbFactors, min);
		}

		return buildBalanceTargets(lbFactors, MIN_LB_FACTOR);
	}

	protected synchronized List<String> getTargets() {
		if (targets == null) {
			targets = new ArrayList<String>();
		}
		return targets;
	}

	private void fixFactor(Map<String, Integer> lbFactors) {
		for (Map.Entry<String, Integer> entry : lbFactors.entrySet()) {
			// TODO Anders 如果为0则不纠正
			if (entry.getValue() < MIN_LB_FACTOR) {
				entry.setValue(MIN_LB_FACTOR);
			}
		}
	}

	private boolean canModAll(int baseFactor, Collection<Integer> factors) {
		for (Integer factor : factors) {
			if (factor % baseFactor != 0) {
				return false;
			}
		}
		return true;
	}

	private List<String> buildBalanceTargets(Map<String, Integer> lbFactors,
			int baseFactor) {
		List<String> targets = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : lbFactors.entrySet()) {
			int count = entry.getValue() / baseFactor;

			for (int i = 0; i < count; i++) {
				targets.add(entry.getKey());
			}
		}
		return targets;
	}

	@Override
	public synchronized String elect() {
		if (CollectionUtils.isEmpty(targets)) {
			return null;
		}
		if (currentPos >= targets.size()) {
			currentPos = 0;
		}
		return targets.get(currentPos++);
	}

	@Override
	public synchronized void removeTarget(String key) {
		if (currentTargets.containsKey(key)) {
			failedTargets.put(key, currentTargets.get(key));
			currentTargets.remove(key);
			reInitTargets(currentTargets);
		}
	}

	@Override
	public synchronized void recoverTarget(String key) {
		if (failedTargets.containsKey(key)) {
			currentTargets.put(key, failedTargets.get(key));
			failedTargets.remove(key);
			reInitTargets(currentTargets);
		}
	}
}