package com.anders.experiment.多线程.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class HelloWorld extends UntypedActor {

	private ActorRef greeter1;
	private ActorRef greeter2;

	public HelloWorld(String name) {
		System.out.println(name);
	}

	@Override
	public void preStart() {
		// create the greeter actor
		greeter1 = getContext().actorOf(Props.create(Greeter.class), "greeter1");// 创建greeter actor实例
		// System.out.println(greeter1);
		// System.out.println(getSelf());

		// final ActorRef greeter2 = getContext().actorOf(Props.create(Greeter.class), "greeter2");
		// tell it to perform the greeting
		greeter1.tell(Greeter.Msg.GREET, getSelf());// 通过tell方法给greeter actor 发送一条消息
		// greeter2.tell(Greeter.Msg.GREET, getSelf());

		greeter2 = getContext().actorOf(Props.create(Greeter.class), "greeter2");
		greeter2.tell(Greeter.Msg.GREET, getSelf());
	}

	@Override
	public void onReceive(Object msg) {
		if (msg instanceof Cust) {
			// when the greeter is done, stop this actor and with it the application
			Cust cust = (Cust) msg;
			System.out.println(cust.getName());
			getContext().stop(getSelf());

		} else
			unhandled(msg);
	}
}
