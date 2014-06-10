package com.vip.datasource.interceptor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.vip.datasource.DynamicDataSourceKey;
import com.vip.datasource.util.Utils;
import com.vip.mybatis.annotation.ShardParam;
import com.vip.mybatis.strategy.ShardStrategy;
import com.vip.mybatis.util.ShardParameter;
import com.vip.mybatis.util.StrategyHolder;

/**
 * shard dynamic datasource interceptor
 * 
 * @author Anders
 * 
 */
public class ShardDataSourceInterceptor implements MethodInterceptor, InitializingBean, ApplicationContextAware {

	/**
	 * key : shard strategy name, value : ShardStrategy instance
	 */
	private Map<String, ShardStrategy> shardStrategyMap;
	/**
	 * key : shard strategy name, value : ShardStrategy class
	 */
	private Map<String, Class<?>> shardStrategyClassMap = new HashMap<String, Class<?>>();
	/**
	 * key : dynamic datasource id in spring, value : DynamicDataSourceKey bean in spring
	 */
	private Map<String, DynamicDataSourceKey> dynamicDataSourceKeyMap = new HashMap<String, DynamicDataSourceKey>();

	private ApplicationContext applicationContext;

	@SuppressWarnings("unchecked")
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object[] args = invocation.getArguments();

		// TODO Anders 无参数方法，设置默认值

		int i = 0;

		ShardStrategy shardStrategy = null;

		StrategyHolder.removeShardStrategy();

		for (Object o : args) {
			Annotation[] annotations = invocation.getThis().getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes()).getParameterAnnotations()[i];
			for (Annotation annotation : annotations) {
				if (annotation instanceof ShardParam) {

					String name = ((ShardParam) annotation).name();
					String field = ((ShardParam) annotation).field();

					ShardParameter shardParameter = new ShardParameter();
					shardParameter.setName(((ShardParam) annotation).name());

					if (Utils.isBasicType(o)) {
						shardParameter.setValue(String.valueOf(o));
					}
					else if (Utils.isMapType(o)) {
						shardParameter.setValue(String.valueOf(((Map<String, Object>) o).get(field)));
					}
					else {
						shardParameter.setValue(BeanUtils.getProperty(o, field));
					}

					shardStrategy = shardStrategyMap.get(name);
					shardStrategy.setShardParameter(shardParameter);

					// default according to first argument
					if (i++ == 0) {
						StrategyHolder.setShardStrategy(shardStrategy);
					}

					StrategyHolder.addShardStrategies(name, shardStrategy);
				}
			}
		}

		DynamicDataSourceInterceptor dynamicDataSourceInterceptor = (DynamicDataSourceInterceptor) applicationContext.getBean("dynamicDataSourceInterceptor");
		dynamicDataSourceInterceptor.setDataSourceKey(dynamicDataSourceKeyMap.get(shardStrategy.getTargetDynamicDataSource()));

		return invocation.proceed();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (MapUtils.isNotEmpty(shardStrategyClassMap)) {
			shardStrategyMap = new HashMap<String, ShardStrategy>();
			for (Map.Entry<String, Class<?>> entry : shardStrategyClassMap.entrySet()) {
				Class<?> clazz = entry.getValue();
				if (!ShardStrategy.class.isAssignableFrom(clazz)) {
					throw new IllegalArgumentException("class " + clazz.getName() + " is illegal, subclass of ShardStrategy is required.");
				}
				try {
					shardStrategyMap.put(entry.getKey(), (ShardStrategy) (entry.getValue().newInstance()));
				}
				catch (Exception e) {
					throw new RuntimeException("new instance for class " + clazz.getName() + " failed, error : " + e.getMessage());
				}
			}

			shardStrategyClassMap = null;
		}
	}

	public void setShardStrategies(Map<String, Class<?>> shardStrategies) {
		this.shardStrategyClassMap = shardStrategies;
	}

	public void setShardDataSourceKeys(Map<String, DynamicDataSourceKey> shardDataSourceKeys) {
		this.dynamicDataSourceKeyMap = shardDataSourceKeys;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
