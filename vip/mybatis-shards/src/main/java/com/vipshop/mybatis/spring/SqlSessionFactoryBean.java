package com.vipshop.mybatis.spring;

import static org.springframework.util.Assert.notNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import com.vipshop.mybatis.converter.DefaultSqlConverter;
import com.vipshop.mybatis.converter.SqlConverter;
import com.vipshop.mybatis.strategy.ShardStrategy;

/**
 * 
 * @author Anders
 * 
 */
public class SqlSessionFactoryBean implements ApplicationContextAware, MultiDataSourceSupport {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ApplicationContext applicationContext;

	private DataSource defaultDataSource;
	private SqlSessionFactory defaultSqlSessionFactory;
	private Map<String, DataSource> shardDataSources;
	private Map<String, SqlSessionFactory> shardSqlSessionFactory;
	private List<DataSource> shardDataSourceList;

	private Map<String, ShardStrategy> shardStrategyMap = new HashMap<String, ShardStrategy>();
	private Map<String, Class<?>> shardStrategyConfig = new HashMap<String, Class<?>>();
	private SqlConverter sqlConverter = new DefaultSqlConverter();

	private Resource[] mapperLocations;
	private Interceptor[] plugins;

	// 新添加的属性
	private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

	public DataSource getDefaultDataSource() {
		return defaultDataSource;
	}

	public void setDefaultDataSource(DataSource defaultDataSource) {
		if (defaultDataSource instanceof TransactionAwareDataSourceProxy) {
			this.defaultDataSource = ((TransactionAwareDataSourceProxy) defaultDataSource).getTargetDataSource();
		}
		else {
			this.defaultDataSource = defaultDataSource;
		}
	}

	public void setShardDataSourceList(List<DataSource> shardDataSourceList) {
		this.shardDataSourceList = shardDataSourceList;
	}

	public Map<String, DataSource> getShardDataSources() {
		return shardDataSources;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public void setShardStrategy(Map<String, Class<?>> shardStrategyMap) {
		this.shardStrategyConfig = shardStrategyMap;
	}

	public SqlSessionFactory getDefaultSqlSessionFactory() {
		return defaultSqlSessionFactory;
	}

	public Map<String, SqlSessionFactory> getShardSqlSessionFactory() {
		return shardSqlSessionFactory;
	}

	public Map<String, ShardStrategy> getShardStrategyMap() {
		return shardStrategyMap;
	}

	public void afterPropertiesSet() throws Exception {
		notNull(defaultDataSource, "Property 'defaultDataSource' is required");
		notNull(sqlSessionFactoryBuilder, "Property 'sqlSessionFactoryBuilder' is required");
		if (CollectionUtils.isNotEmpty(shardDataSourceList) && MapUtils.isEmpty(shardStrategyConfig)) {
			throw new IllegalArgumentException("Property 'shardStrategy' is required");
		}

		// 没有shardDataSourceList也可以，提供最原始的功能
		// if (CollectionUtils.isEmpty(shardDataSourceList)) {
		// throw new IllegalArgumentException("Property 'shardDataSourceList' is required");
		// }

		if (CollectionUtils.isNotEmpty(shardDataSourceList)) {
			shardDataSources = new LinkedHashMap<String, DataSource>();
			// 从Spring容器中获取所有的DataSource
			// TODO Anders : 此处代码是否要升级
			Map<String, DataSource> dataSourceMap = applicationContext.getBeansOfType(DataSource.class);
			for (Entry<String, DataSource> entry : dataSourceMap.entrySet()) {
				for (int i = 0; i < shardDataSourceList.size(); i++) {
					DataSource ds = shardDataSourceList.get(i);

					if (entry.getValue() == ds) {
						DataSource dataSource = entry.getValue();
						if (dataSource instanceof TransactionAwareDataSourceProxy) {
							dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
						}
						shardDataSources.put(entry.getKey(), dataSource);
					}
				}
			}
		}

		// DataSource不能为空
		// if (defaultDataSource == null) {
		// if (shardDataSourceList.get(0) instanceof TransactionAwareDataSourceProxy) {
		// this.defaultDataSource = ((TransactionAwareDataSourceProxy) shardDataSourceList.get(0)).getTargetDataSource();
		// }
		// else {
		// defaultDataSource = shardDataSources.get(0);
		// }
		// }

		this.defaultSqlSessionFactory = buildSqlSessionFactory(getDefaultDataSource());

		if (MapUtils.isNotEmpty(shardDataSources)) {
			shardSqlSessionFactory = new LinkedHashMap<String, SqlSessionFactory>(shardDataSources.size());
			for (Entry<String, DataSource> entry : shardDataSources.entrySet()) {
				shardSqlSessionFactory.put(entry.getKey(), buildSqlSessionFactory(entry.getValue()));
			}
		}

		if (MapUtils.isNotEmpty(shardStrategyConfig)) {
			shardStrategyMap = new HashMap<String, ShardStrategy>();
			for (Map.Entry<String, Class<?>> entry : shardStrategyConfig.entrySet()) {
				Class<?> clazz = entry.getValue();
				if (!ShardStrategy.class.isAssignableFrom(clazz)) {
					throw new IllegalArgumentException("Class " + clazz.getName() + " is illegal, subClass of ShardStrategy is required.");
				}

				try {
					shardStrategyMap.put(entry.getKey(), (ShardStrategy) (entry.getValue().newInstance()));
				}
				catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
	}

	private SqlSessionFactory buildSqlSessionFactory(DataSource dataSource) throws IOException {
		Configuration configuration;

		XMLConfigBuilder xmlConfigBuilder = null;
		if (this.configLocation != null) {
			xmlConfigBuilder = new XMLConfigBuilder(this.configLocation.getInputStream(), null, this.configurationProperties);
			configuration = xmlConfigBuilder.getConfiguration();
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Property 'configLocation' not specified, using default MyBatis Configuration");
			}
			configuration = new Configuration();
			configuration.setVariables(this.configurationProperties);
		}

		if (this.objectFactory != null) {
			configuration.setObjectFactory(this.objectFactory);
		}

		if (this.objectWrapperFactory != null) {
			configuration.setObjectWrapperFactory(this.objectWrapperFactory);
		}

		if (hasLength(this.typeAliasesPackage)) {
			String[] typeAliasPackageArray = tokenizeToStringArray(this.typeAliasesPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeAliasPackageArray) {
				configuration.getTypeAliasRegistry().registerAliases(packageToScan, typeAliasesSuperType == null ? Object.class : typeAliasesSuperType);
				if (logger.isDebugEnabled()) {
					logger.debug("Scanned package: '" + packageToScan + "' for aliases");
				}
			}
		}

		if (!isEmpty(this.typeAliases)) {
			for (Class<?> typeAlias : this.typeAliases) {
				configuration.getTypeAliasRegistry().registerAlias(typeAlias);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered type alias: '" + typeAlias + "'");
				}
			}
		}

		if (!isEmpty(this.plugins)) {
			for (Interceptor plugin : this.plugins) {
				configuration.addInterceptor(plugin);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered plugin: '" + plugin + "'");
				}
			}
		}

		if (hasLength(this.typeHandlersPackage)) {
			String[] typeHandlersPackageArray = tokenizeToStringArray(this.typeHandlersPackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeHandlersPackageArray) {
				configuration.getTypeHandlerRegistry().register(packageToScan);
				if (logger.isDebugEnabled()) {
					logger.debug("Scanned package: '" + packageToScan + "' for type handlers");
				}
			}
		}

		if (!isEmpty(this.typeHandlers)) {
			for (TypeHandler<?> typeHandler : this.typeHandlers) {
				configuration.getTypeHandlerRegistry().register(typeHandler);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered type handler: '" + typeHandler + "'");
				}
			}
		}

		if (xmlConfigBuilder != null) {
			try {
				xmlConfigBuilder.parse();

				if (logger.isDebugEnabled()) {
					logger.debug("Parsed configuration file: '" + this.configLocation + "'");
				}
			}
			catch (Exception ex) {
				throw new NestedIOException("Failed to parse config resource: " + this.configLocation, ex);
			}
			finally {
				ErrorContext.instance().reset();
			}
		}

		if (this.transactionFactory == null) {
			this.transactionFactory = new SpringManagedTransactionFactory();
		}

		Environment environment = new Environment(this.environment, this.transactionFactory, dataSource);
		configuration.setEnvironment(environment);

		if (this.databaseIdProvider != null) {
			try {
				configuration.setDatabaseId(this.databaseIdProvider.getDatabaseId(dataSource));
			}
			catch (SQLException e) {
				throw new NestedIOException("Failed getting a databaseId", e);
			}
		}

		if (ArrayUtils.isNotEmpty(this.mapperLocations)) {
			for (Resource mapperLocation : this.mapperLocations) {
				if (mapperLocation == null) {
					continue;
				}

				try {
					XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(), configuration, mapperLocation.toString(), configuration.getSqlFragments());
					xmlMapperBuilder.parse();
				}
				catch (Exception e) {
					throw new NestedIOException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
				}
				finally {
					ErrorContext.instance().reset();
				}

				if (logger.isDebugEnabled()) {
					logger.debug("Parsed mapper file: '" + mapperLocation + "'");
				}
			}
		}
		else {
			if (logger.isDebugEnabled()) {
				logger.debug("Property 'mapperLocations' was not specified or no matching resources found");
			}
		}

		return this.sqlSessionFactoryBuilder.build(configuration);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void setPlugins(Interceptor[] plugins) {
		this.plugins = plugins;
	}

}
