package com.anders.vote.service;

import java.util.Map;
import java.util.Set;

import com.anders.vote.bo.Url;

public interface UrlService extends GenericService<Long, Url> {

	Map<String, Set<String>> getUrlWithRoleNames();
}
