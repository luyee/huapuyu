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

import com.baidu.rigel.unique.bo.NoncoreWord;
import com.baidu.rigel.unique.dao.NoncoreWordDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/conf/applicationContext.xml", "classpath:/applicationContext-test.xml" })
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class NoncoreWordServiceTest {
	@Autowired
	private NoncoreWordService noncoreWordService;
	@Autowired
	private NoncoreWordDao noncoreWordDao;

	@Before
	public void setUp() throws Exception {
		NoncoreWord noncoreWord = new NoncoreWord();
		noncoreWord.setId(1234L);
		noncoreWord.setCreatorId(1234L);
		noncoreWord.setCreateTime(new Date());
		noncoreWord.setUpdateId(1234L);
		noncoreWord.setUpdateTime(new Date());
		noncoreWord.setDelFlag((short) 0);
		noncoreWord.setWord("keyword");
		noncoreWordDao.save(noncoreWord);
	}

	@After
	public void tearDown() throws Exception {
		noncoreWordDao.delete(1234L);
	}

	@Test
	public void testPageList() {
		List<NoncoreWord> list = noncoreWordService.pageList(1234L, "keyword", 0, 10);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("keyword", list.get(0).getWord());
		list = noncoreWordService.pageList(12345L, "keyword", 0, 10);
		Assert.assertEquals(0, list.size());
	}

	@Test
	public void testPageCount() {
		Assert.assertEquals(1, noncoreWordService.pageCount(1234L, "keyword").longValue());
		Assert.assertEquals(0, noncoreWordService.pageCount(12345L, "keyword").longValue());
	}

	@Test
	public void testSelectDisCreatorId() {
		List<Long> list = noncoreWordService.selectDisCreatorId();
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(1234, list.get(0).longValue());
	}

	@Test
	public void testSelectNoncoreWordByWord() {
		NoncoreWord noncoreWord = noncoreWordService.selectNoncoreWordByWord("keyword");
		Assert.assertEquals(1234, noncoreWord.getId().longValue());
		Assert.assertNull(noncoreWordService.selectNoncoreWordByWord("keyword123"));
	}
}
