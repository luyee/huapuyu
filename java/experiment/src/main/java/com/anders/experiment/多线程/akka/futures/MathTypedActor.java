package com.anders.experiment.多线程.akka.futures;

import scala.concurrent.Future;

public interface MathTypedActor {
	public Future<Integer> square(int value);
}
