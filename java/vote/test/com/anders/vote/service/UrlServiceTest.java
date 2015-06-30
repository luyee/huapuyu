package com.anders.vote.service;

import java.util.HashSet;
import java.util.Set;

import mockit.Injectable;
import mockit.NonStrictExpectations;
import mockit.Tested;

import org.junit.Test;

import com.anders.vote.bo.Url;
import com.anders.vote.dao.UrlDao;
import com.anders.vote.service.impl.UrlServiceImpl;

public class UrlServiceTest {
	@Tested
	private UrlServiceImpl urlService;

	@Injectable
	private UrlDao urlDao;

	@Test
	public void testImportHintForPangu() {
		new NonStrictExpectations() {
			{
				urlDao.getAllFetchRoles();
				Set<Url> urls = new HashSet<Url>();
				Url url1 = new Url();
				url1.setName("test1");
				Url url2 = new Url();
				url2.setName("test2");
				urls.add(url1);
				urls.add(url2);
				result = urls;
			}
		};

		Set<Url> urls = urlDao.getAllFetchRoles();
		for (Url url : urls) {
			System.out.println(url.getName());
		}
	}
}
