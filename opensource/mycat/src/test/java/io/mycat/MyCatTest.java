package io.mycat;

import org.junit.Test;

import io.mycat.server.MycatServer;

public class MyCatTest {
	@Test
	public void test1() {
		try {
		
			MycatServer server = MycatServer.getInstance();
			server.startup();

			System.out.println("ok");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
