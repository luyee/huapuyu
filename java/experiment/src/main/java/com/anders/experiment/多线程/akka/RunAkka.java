package com.anders.experiment.多线程.akka;

import java.sql.SQLException;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

public class RunAkka {
	public static void main(String[] args) throws SQLException {
		ActorSystem actorSystem = ActorSystem.create("Main");
		try {
			ActorRef app = actorSystem.actorOf(Props.create(HelloWorld.class, "hello world"), "app");
			actorSystem.actorOf(Props.create(Terminator.class, app), "app-terminator");
		} catch (Exception e) {
			actorSystem.shutdown();
			throw new SQLException(e);
		}
	}
}

class Terminator extends UntypedActor {

	public Terminator(ActorRef app) {
		getContext().watch(app);
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		if (arg0 instanceof Terminated) {
			getContext().system().shutdown();
		} else {
			unhandled(arg0);
		}
	}

}