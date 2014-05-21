package com.vipshop.mybatis.spring;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import com.vipshop.mybatis.strategy.ShardStrategy;

public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean, ApplicationListener<ApplicationEvent>, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(SqlSessionFactoryBean.class);

	private Resource configLocation;

	private Resource[] mapperLocations;

	private DataSource dataSource;

	private TransactionFactory transactionFactory;

	private Properties configurationProperties;

	private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();

	private SqlSessionFactory sqlSessionFactory;

	private String environment = SqlSessionFactoryBean.class.getSimpleName();

	private boolean failFast;

	private Interceptor[] plugins;

	private TypeHandler<?>[] typeHandlers;

	private String typeHandlersPackage;

	private Class<?>[] typeAliases;

	private String typeAliasesPackage;

	private Class<?> typeAliasesSuperType;

	private DatabaseIdProvider databaseIdProvider;

	private ObjectFactory objectFactory;

	private ObjectWrapperFactory objectWrapperFactory;

	// 新增属性
	private ApplicationContext applicationContext;
	// private DataSource defaultDataSource;
	// private SqlSessionFactory defaultSqlSessionFactory;
	private Map<String, DataSource> shardDataSources;
	private Map<String, SqlSessionFactory> shardSqlSessionFactory;
	private List<DataSource> shardDataSourceList;
	private Map<String, ShardStrategy> shardStrategyMap = new HashMap<String, ShardStrategy>();
	private Map<String, Class<?>> shardStrategyConfig = new HashMap<String, Class<?>>();

	// private SqlConverter sqlConverter = new DefaultSqlConverter();

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public void setObjectWrapperFactory(ObjectWrapperFactory objectWrapperFactory) {
		this.objectWrapperFactory = objectWrapperFactory;
	}

	public DatabaseIdProvider getDatabaseIdProvider() {
		return databaseIdProvider;
	}

	public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
		this.databaseIdProvider = databaseIdProvider;
	}

	public void setPlugins(Interceptor[] plugins) {
		this.plugins = plugins;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType) {
		this.typeAliasesSuperType = typeAliasesSuperType;
	}

	public void setTypeHandlersPackage(String typeHandlersPackage) {
		this.typeHandlersPackage = typeHandlersPackage;
	}

	public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
		this.typeHandlers = typeHandlers;
	}

	public void setTypeAliases(Class<?>[] typeAliases) {
		this.typeAliases = typeAliases;
	}

	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public void setConfigurationProperties(Properties sqlSessionFactoryProperties) {
		this.configurationProperties = sqlSessionFactoryProperties;
	}

	public void setDataSource(DataSource dataSource) {
		if (dataSource instanceof TransactionAwareDataSourceProxy) {
			this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
		}
		else {
			this.dataSource = dataSource;
		}
	}

	public void setSqlSessionFactoryBuilder(SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
		this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
	}

	public void setTransactionFactory(TransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public void afterPropertiesSet() throws Exception {
		notNull(dataSource, "Property 'dataSource' is required");
		notNull(sqlSessionFactoryBuilder, "Property 'sqlSessionFactoryBuilder' is required");
		if (CollectionUtils.isNotEmpty(shardDataSourceList) && MapUtils.isEmpty(shardStrategyConfig)) {
			throw new IllegalArgumentException("Property 'shardStrategy' is required");
		}

		// 没有shardDataSourceList也可以，提供最原始的功能
		// if (CollectionUtils.isEmpty(shardDataSourceList)) {
		// throw new
		// IllegalArgumentException("Property 'shardDataSourceList' is required");
		// }

		if (CollectionUtils.isNotEmpty(shardDataSourceList)) {
			shardDataSources = new LinkedHashMap<String, DataSource>();
			// 从Spring容器中获取所有的DataSource
			// TODO Anders : 此处代码是否要升级？
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
		// if (shardDataSourceList.get(0) instanceof
		// TransactionAwareDataSourceProxy) {
		// this.defaultDataSource = ((TransactionAwareDataSourceProxy)
		// shardDataSourceList.get(0)).getTargetDataSource();
		// }
		// else {
		// defaultDataSource = shardDataSources.get(0);
		// }
		// }

		this.sqlSessionFactory = buildSqlSessionFactory(dataSource);

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

	protected SqlSessionFactory buildSqlSessionFactory(DataSource dataSource) throws IOException {

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
			this.transactionFactory = new SpringManagedTransactionFactory(dataSource);
		}

		Environment environment = new Environment(this.environment, this.transactionFactory, this.dataSource);
		configuration.setEnvironment(environment);

		if (this.databaseIdProvider != null) {
			// TODO Anders Zhu
			// try {
			// configuration.setDatabaseId(this.databaseIdProvider.getDatabaseId(this.dataSource));
			// }
			// catch (SQLException e) {
			// throw new NestedIOException("Failed getting a databaseId", e);
			// }
		}

		if (!isEmpty(this.mapperLocations)) {
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

	public SqlSessionFactory getObject() throws Exception {
		if (this.sqlSessionFactory == null) {
			afterPropertiesSet();
		}

		return this.sqlSessionFactory;
	}

	public Class<? extends SqlSessionFactory> getObjectType() {
		return this.sqlSessionFactory == null ? SqlSessionFactory.class : this.sqlSessionFactory.getClass();
	}

	public boolean isSingleton() {
		return true;
	}

	public void onApplicationEvent(ApplicationEvent event) {
		if (failFast && event instanceof ContextRefreshedEvent) {
			this.sqlSessionFactory.getConfiguration().getMappedStatementNames();
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public Map<String, DataSource> getShardDataSources() {
		return shardDataSources;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public List<DataSource> getShardDataSourceList() {
		return shardDataSourceList;
	}

	public void setShardDataSourceList(List<DataSource> shardDataSourceList) {
		this.shardDataSourceList = shardDataSourceList;
	}

	public Map<String, Class<?>> getShardStrategyConfig() {
		return shardStrategyConfig;
	}

	public void setShardStrategyConfig(Map<String, Class<?>> shardStrategyConfig) {
		this.shardStrategyConfig = shardStrategyConfig;
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public Map<String, SqlSessionFactory> getShardSqlSessionFactory() {
		return shardSqlSessionFactory;
	}

	public void setShardSqlSessionFactory(Map<String, SqlSessionFactory> shardSqlSessionFactory) {
		this.shardSqlSessionFactory = shardSqlSessionFactory;
	}

	public Map<String, ShardStrategy> getShardStrategyMap() {
		return shardStrategyMap;
	}

	public void setShardStrategyMap(Map<String, ShardStrategy> shardStrategyMap) {
		this.shardStrategyMap = shardStrategyMap;
	}

	public void setShardStrategy(Map<String, Class<?>> shardStrategyMap) {
		this.shardStrategyConfig = shardStrategyMap;
	}
}
