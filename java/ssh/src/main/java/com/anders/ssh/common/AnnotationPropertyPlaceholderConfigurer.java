package com.anders.ssh.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.anders.ssh.annotation.Config;

public class AnnotationPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private final static Log log = LogFactory.getLog(AnnotationPropertyPlaceholderConfigurer.class);

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties) throws BeansException {
		// {load.init.data=false}
		this.properties = properties;

		super.processProperties(beanFactory, properties);

		// beanFactory.getBeanDefinitionNames() =
		// 获得所有被spring管理的bean的id，如：dataSource,sessionFactory,txManager
		for (String name : beanFactory.getBeanDefinitionNames()) {
			// clazz：获得PropertyPlaceholderConfigurer对象，class
			// org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
			Class<?> clazz = beanFactory.getType(name);
			// mpv：PropertyValues: length=1; bean property 'locations'
			MutablePropertyValues mpv = beanFactory.getBeanDefinition(name).getPropertyValues();

			// if (log.isDebugEnabled())
			// {
			// log.debug("Configuring properties for bean=" + name + "[" + clazz + "]");
			// }

			if (clazz != null) {
				// 从set或get去配置
				// property：org.springframework.beans.GenericTypeAwarePropertyDescriptor@cb4756ac
				for (PropertyDescriptor property : BeanUtils.getPropertyDescriptors(clazz)) {
					// public void
					// org.springframework.beans.factory.config.PropertyPlaceholderConfigurer.setBeanName(java.lang.String)
					Method setter = property.getWriteMethod();
					Method getter = property.getReadMethod();
					Config annotation = null;
					// isAnnotationPresent：判断setter方法是不是自定义config注解方法
					if (setter != null && setter.isAnnotationPresent(Config.class)) {
						annotation = setter.getAnnotation(Config.class);
					}
					else if (setter != null && getter != null && getter.isAnnotationPresent(Config.class)) {
						annotation = getter.getAnnotation(Config.class);
					}

					if (annotation != null) {
						// annotation：@com.anders.ssh.common.Config(name=load.init.data)
						String value = resolvePlaceholder(annotation.name(), properties, SYSTEM_PROPERTIES_MODE_FALLBACK);

						if (value != null) {
							if (log.isDebugEnabled()) {
								log.debug("setting property=[" + clazz.getName() + "." + property.getName() + "] value=[" + annotation.name() + "=" + value + "]");
							}
							// 设置字段值为配置数据
							mpv.addPropertyValue(property.getName(), value);
						}
						else {
							if (log.isDebugEnabled()) {
								log.debug("don't setting property=[" + clazz.getName() + "." + property.getName() + "] due to value absent");
							}
						}

					}
				}

				// 从字段去配置
				for (Field field : clazz.getDeclaredFields()) {
					// if (log.isDebugEnabled())
					// {
					// log.debug("examining field=[" + clazz.getName() + "." + field.getName() +
					// "]");
					// }
					if (field.isAnnotationPresent(Config.class)) {
						Config annotation = field.getAnnotation(Config.class);
						PropertyDescriptor property = BeanUtils.getPropertyDescriptor(clazz, field.getName());

						if (property.getWriteMethod() == null) {
							throw new RuntimeException("setter for property=[" + clazz.getName() + "." + field.getName() + "] not available.");
						}

						Object value = resolvePlaceholder(annotation.name(), properties, SYSTEM_PROPERTIES_MODE_FALLBACK);

						if (value != null) {
							if (log.isDebugEnabled()) {
								log.debug("setting property=[" + clazz.getName() + "." + field.getName() + "] value=[" + annotation.name() + "=" + value + "]");
							}
							mpv.addPropertyValue(property.getName(), value);
						}
						else {
							if (log.isDebugEnabled()) {
								log.debug("don't setting property=[" + clazz.getName() + "." + field.getName() + "] due to value absent");
							}
						}

					}
				}
			}
		}
	}

}
