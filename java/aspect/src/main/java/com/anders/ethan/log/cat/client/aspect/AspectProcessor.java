package com.anders.ethan.log.cat.client.aspect;

public interface AspectProcessor {
	void doBefore(Object signature, Object instance, Object[] args);

	void doAfter(Object signature, Object instance, Object[] args);

	void doException(Throwable throwable);

	void doSucceed(Object signature, Object instance, Object[] args);

	void doFailed(Object signature, Object instance, Object[] args,
			Throwable throwable);
}
