package com.anders.experiment.多线程.akka.futures;

import java.util.concurrent.atomic.AtomicReference;

import scala.Function0;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import akka.actor.TypedActor.TypedActor;

public class MathTypedActorImpl extends TypedActor implements MathTypedActor {

	public MathTypedActorImpl(AtomicReference proxyVar, Function0 createInstance, Seq interfaces) {
		super(proxyVar, createInstance, interfaces);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Future<Integer> square(int value) {
		// return future(value * value);
		return null;
	}

}
