package com.anders.experiment.多线程.akka;

import akka.actor.UntypedActor;

public class Greeter extends UntypedActor {

	public static enum Msg {
		GREET, DONE;
	}

	@Override
	public void onReceive(Object msg) {
		if (msg == Msg.GREET) {
			// System.out.println(getSender());
			System.out.println(getSelf());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Cust cust = new Cust();
			cust.setName("zhuzhen");
			cust.setAge(33);
			getSender().tell(cust, getSelf());
			getContext().stop(getSelf());
		} else
			unhandled(msg);
	}

}
