package com.anders.dp.单例;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class 马林懒汉 {
	private volatile static 马林懒汉 singleton = null;
	private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private 马林懒汉() {
	}

	public static final 马林懒汉 getInstance() {
		readWriteLock.readLock().lock();
		try {
			if (singleton != null) {
				return singleton;
			}
		}
		finally {
			readWriteLock.readLock().unlock();
		}

		readWriteLock.writeLock().lock();
		try {
			if (singleton == null) {
				singleton = new 马林懒汉();
			}

			return singleton;
		}
		finally {
			readWriteLock.writeLock().unlock();
		}

	}
}
