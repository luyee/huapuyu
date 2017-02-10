package com.anders.ssh.ms;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anders.ssh.facade.AccountFacade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-ms-test.xml" })
public class AccountServiceTest {
	@Resource(name = "accountFacade")
	private AccountFacade accountFacade;

	@Test
	public void testSave() {
		accountFacade.读写分离();
	}
}
