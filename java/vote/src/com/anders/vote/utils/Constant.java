package com.anders.vote.utils;

import org.apache.shiro.crypto.hash.Sha256Hash;

public interface Constant {
	public static final int DEFAULT_HASH_ITERATIONS = 1024;
	public static final String DEFAULT_ALGORITHM_NAME = Sha256Hash.ALGORITHM_NAME;
}
