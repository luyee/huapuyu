package com.vip.datasource.interceptor;

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
import org.springframework.util.Assert;

import com.vip.datasource.DynamicDataSource;
import com.vip.datasource.DynamicDataSourceKey;
import com.vip.datasource.ShardDataSource;
import com.vip.mybatis.annotation.Shard;
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
	 * key : dynamic datasource id in spring, value : DynamicDataSource bean in spring
	 */
	private Map<String, DynamicDataSource> dynamicDataSourceMap = new HashMap<String, DynamicDataSource>();
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

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Shard shardAnnotation = invocation.getThis().getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes()).getAnnotation(Shard.class);

		// no shard annotation, use default datasource
		if (shardAnnotation == null) {
			return invocation.proceed();
		}

		Object[] args = invocation.getArguments();
		String shardFieldValue = null;

		for (Object o : args) {
			// TODO Anders 需要添加更多的逻辑判断，有性能问题，需要优化
			if (o.getClass().getName().equals(shardAnnotation.classType().getName())) {
				shardFieldValue = BeanUtils.getProperty(o, shardAnnotation.fieldName());
				break;
			}
		}

		Assert.notNull(shardFieldValue);

		ShardParameter shardParameter = new ShardParameter();
		shardParameter.setName(shardAnnotation.name());
		shardParameter.setValue(shardFieldValue);

		ShardStrategy shardStrategy = shardStrategyMap.get(shardAnnotation.name());
		shardStrategy.setShardParameter(shardParameter);

		StrategyHolder.setShardStrategy(shardStrategy);

		ShardDataSource shardDataSource = (ShardDataSource) applicationContext.getBean("shardDataSource");
		shardDataSource.setDataSource(dynamicDataSourceMap.get(shardStrategy.getTargetDynamicDataSource()));

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

	public void setShardDataSources(Map<String, DynamicDataSource> shardDataSources) {
		this.dynamicDataSourceMap = shardDataSources;
	}

	public void setShardDataSourceKeys(Map<String, DynamicDataSourceKey> shardDataSourceKeys) {
		this.dynamicDataSourceKeyMap = shardDataSourceKeys;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
