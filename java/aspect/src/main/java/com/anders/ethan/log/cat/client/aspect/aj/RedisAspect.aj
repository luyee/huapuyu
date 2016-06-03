package com.anders.ethan.log.cat.client.aspect.aj;

import com.anders.ethan.log.cat.client.aspect.AspectProcessor;
import com.anders.ethan.log.cat.client.aspect.JedisProcessor;
import com.anders.ethan.log.cat.client.aspect.Thrower;

public aspect RedisAspect {

    pointcut invoke() : call(* redis.clients.jedis.BinaryClient.*(..,*));

    Object around() : invoke() {
        AspectProcessor processor = new JedisProcessor();

        try {
			processor.doBefore(thisJoinPoint.getSignature(), thisJoinPoint.getTarget(), thisJoinPoint.getArgs());
        } catch (Throwable e) {
        	processor.doException(e);
        	return proceed();
        }
        
        Object result = null;
        try {
			result = proceed();
			processor.doSucceed(thisJoinPoint.getSignature(), thisJoinPoint.getTarget(), thisJoinPoint.getArgs());
			return result;
        } catch (Throwable e) {
        	processor.doFailed(thisJoinPoint.getSignature(), thisJoinPoint.getTarget(), thisJoinPoint.getArgs(), e);
        	Thrower.__throw(e);
        } finally {
        	processor.doAfter(thisJoinPoint.getSignature(), thisJoinPoint.getTarget(), thisJoinPoint.getArgs());
        }
        
        return result;
    }
}
