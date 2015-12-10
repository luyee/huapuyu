package com.anders.ethan.sharding.loadbalance;

import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.Assert;

public class RandomLoadBalance implements LoadBalance<String> {

	private List<String> targets;
	// private List<String> failedTargets;
	private final Random random = new Random();

	public RandomLoadBalance(List<String> targets) {
		Assert.notEmpty(targets);
		this.targets = targets;
		// this.targets = Collections.synchronizedList(targets);
		// failedTargets = Collections.synchronizedList(new ArrayList<String>(
		// targets.size()));
	}

	@Override
	public synchronized String elect() {
		if (CollectionUtils.isEmpty(targets)) {
			return null;
		}
		return targets.get(random.nextInt(targets.size()));
	}

	// @Override
	// public synchronized void removeTarget(String target) {
	// if (targets.contains(target)) {
	// targets.remove(target);
	// failedTargets.add(target);
	// }
	// }

	// @Override
	// public synchronized void recoverTarget(String target) {
	// if (failedTargets.contains(target)) {
	// targets.add(target);
	// failedTargets.remove(target);
	// }
	// }
}
