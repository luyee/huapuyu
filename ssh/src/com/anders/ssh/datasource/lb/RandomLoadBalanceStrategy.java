package com.anders.ssh.datasource.lb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLoadBalanceStrategy implements LoadBalanceStrategy<String> {

	private List<String> targets;
	private List<String> failedTargets;
	private final Random random = new Random();

	public RandomLoadBalanceStrategy(List<String> lbTargets) {
		targets = Collections.synchronizedList(lbTargets);
		if (targets == null) {
			throw new RuntimeException("target datasources can not be empty");
		}
		failedTargets = Collections.synchronizedList(new ArrayList<String>(targets.size()));
	}

	public synchronized String elect() {
		if (targets == null) {
			return null;
		}
		return targets.get(random.nextInt(targets.size()));
	}

	public synchronized void removeTarget(String t) {
		if (targets.contains(t)) {
			targets.remove(t);
			failedTargets.add(t);
		}
	}

	public synchronized void recoverTarget(String t) {
		if (failedTargets.contains(t)) {
			failedTargets.remove(t);
			targets.add(t);
		}
	}

}
