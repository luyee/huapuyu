package com.vip.datasource.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
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
import com.vip.mybatis.util.ShardParam;
import com.vip.mybatis.util.StrategyHolder;

/**
 * annotation dynamic datasource interceptor
 * 
 * @author Anders
 * 
 */
public class ShardDataSourceInterceptor implements MethodInterceptor, InitializingBean, ApplicationContextAware {

	private DynamicDataSourceKey dataSourceKey;

	private Map<String, DynamicDataSource> name2DynamicDataSourceMap = new HashMap<String, DynamicDataSource>();

	private Map<String, ShardStrategy> shardStrategyMap;
	private Map<String, Class<?>> shardStrategyConfig = new HashMap<String, Class<?>>();

	private ApplicationContext applicationContext;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Shard shardAnnotation = invocation.getThis().getClass().getMethod(invocation.getMethod().getName(), invocation.getMethod().getParameterTypes()).getAnnotation(Shard.class);

		// no shard annotation, use default datasource
		if (shardAnnotation == null) {
			return invocation.proceed();
		}

		Object[] args = invocation.getArguments();
		String fieldValue = null;

		// for (Object o : args) {
		// if (o.getClass().getName().equals(shardAnnotation.fieldType().getName())) {
		// fieldValue = BeanUtils.getProperty(o, shardAnnotation.fieldName());
		// break;
		// }
		// }

		fieldValue = "123";

		Assert.notNull(fieldValue);

		ShardParam shardParam = new ShardParam();
		shardParam.setName(shardAnnotation.name());
		shardParam.setShardValue(fieldValue);

		ShardStrategy shardStrategy = shardStrategyMap.get(shardAnnotation.name());
		shardStrategy.setShardParam(shardParam);

		StrategyHolder.setShardStrategy(shardStrategy);

		ShardDataSource shardDataSource = (ShardDataSource) applicationContext.getBean("shardDataSource");
		shardDataSource.setDataSource(name2DynamicDataSourceMap.get(shardStrategy.getTargetDynamicDataSource()));

		return invocation.proceed();
	}

	public void setDataSourceKey(DynamicDataSourceKey dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}

	public Map<String, ShardStrategy> getShardStrategyMap() {
		return shardStrategyMap;
	}

	public void setShardStrategies(Map<String, Class<?>> shardStrategies) {
		this.shardStrategyConfig = shardStrategies;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (MapUtils.isNotEmpty(shardStrategyConfig)) {
			shardStrategyMap = new HashMap<String, ShardStrategy>();
			for (Map.Entry<String, Class<?>> entry : shardStrategyConfig.entrySet()) {
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

			shardStrategyConfig = null;
		}
	}

	public void setShardDataSources(Map<String, DynamicDataSource> shardDataSources) {
		this.name2DynamicDataSourceMap = shardDataSources;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
