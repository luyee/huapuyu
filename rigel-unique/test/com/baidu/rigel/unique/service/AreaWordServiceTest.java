package com.baidu.rigel.unique.service;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.baidu.rigel.unique.bo.AreaWord;
import com.baidu.rigel.unique.dao.AreaWordDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class AreaWordServiceTest {
	@Autowired
	private AreaWordService areaWordService;
	@Autowired
	private AreaWordDao areaWordDao;

	@Before
	public void setUp() throws Exception {
		AreaWord areaWord = new AreaWord();
		areaWord.setAddeduser(1234L);
		areaWord.setAddedtime(new Date());
		areaWord.setcId(1234L);
		areaWord.setcWord("keyword");
		areaWord.setPosId(1234L);
		areaWordDao.save(areaWord);
	}

	@After
	public void tearDown() throws Exception {
		areaWordDao.delete(1234L);
	}

	@Test
	public void testSelectAllOrderByCWord() {
		List<AreaWord> list = areaWordService.selectAllOrderByCWord();
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("keyword", list.get(0).getcWord());
	}

}
