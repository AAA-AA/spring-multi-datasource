package com.bingzhilanmo.base.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;

/**
 * 
 * @author bowen_wang
 * 多数据源手动注入容器---druid
 */
public class InjectDataSourceFactoryBean implements
		BeanDefinitionRegistryPostProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(InjectDataSourceFactoryBean.class);

	private static final String DB_CONFIG_FILE = "config/jdbc.properties";
	
	private static final List<String> DATA_SOURCE_BEAN_NAMES = new ArrayList<String>();
	
	private static final String FILTER_NAME = "statFilter";

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		logger.info("Begin to do postProcessBeanFactory method..................");
		List<Filter> filters = new ArrayList<Filter>();
		Filter filter = beanFactory.getBean(FILTER_NAME,StatFilter.class);
		filters.add(filter);
		for(String beanName : DATA_SOURCE_BEAN_NAMES){
			DruidDataSource ds = beanFactory.getBean(beanName,DruidDataSource.class);
			ds.setProxyFilters(filters);
		}
		logger.info("End to do postProcessBeanFactory method..................");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(
			BeanDefinitionRegistry registry) throws BeansException {
		logger.info("Begin to do postProcessBeanDefinitionRegistry method..................");
		try {
			registerDataSourceBean(registry);
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("End to do postProcessBeanDefinitionRegistry method..................");
	}

	/**
	 * 完成数据源的注入
	 * @param registry
	 * @throws Exception
	 */
	private void registerDataSourceBean(BeanDefinitionRegistry registry) throws Exception{
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(StatFilter.class);
		registry.registerBeanDefinition(FILTER_NAME, beanDefinition);//注册proxyFilter

		Resource resource = new ClassPathResource(DB_CONFIG_FILE);
		Properties p = new Properties();
		p.load(resource.getInputStream());
		String key = null;
		String keyPrefix = null;
		String keyUrl=null;
		String keyUsername=null;
		String keyPassword=null;

		String initialSize = p.getProperty("jdbc.pool.initialSize");
		String minIdle = p.getProperty("jdbc.pool.minIdle");
		String maxActive = p.getProperty("jdbc.pool.maxActive");
		String validationQuery = p.getProperty("jdbc.pool.validationQuery");
		String testOnBorrow = p.getProperty("jdbc.pool.testOnBorrow");
		String testWhileIdle = p.getProperty("jdbc.pool.testWhileIdle");
		String maxOpenPreparedStatements = p.getProperty("jdbc.pool.maxOpenPreparedStatements");
		String poolPreparedStatements = p.getProperty("jdbc.pool.poolPreparedStatements");
		String name = null;
		String url =null;
		String username = null;
		String password = null;
		Map<String,String> valuesMap = null;
		MutablePropertyValues  mpv = null;
		for(Object obj : p.keySet()){
			key = obj.toString();
			if(key.endsWith(".url")){
				keyPrefix = key.substring(0,key.indexOf(".url"));
				keyUrl=key;
				name = p.getProperty(keyPrefix+".name");
				if(StringUtils.isBlank(name)){
					name = "dataSource"+keyPrefix.replace("jdbc.db", "").trim();
				}
				keyUsername=keyPrefix+".username";
				keyPassword=keyPrefix+".password";
				url = p.getProperty(keyUrl);
				username = p.getProperty(keyUsername);
				password = p.getProperty(keyPassword);
				
				valuesMap = new LinkedHashMap<String, String>();
				valuesMap.put("name", name);
				valuesMap.put("url", url);
				valuesMap.put("username", username);
				valuesMap.put("password", password);
				valuesMap.put("initialSize", initialSize);
				valuesMap.put("minIdle", minIdle);
				valuesMap.put("maxActive", maxActive);
				valuesMap.put("validationQuery", validationQuery);
				valuesMap.put("testOnBorrow", testOnBorrow);
				valuesMap.put("testWhileIdle", testWhileIdle);
				valuesMap.put("maxOpenPreparedStatements", maxOpenPreparedStatements);
				valuesMap.put("poolPreparedStatements", poolPreparedStatements);
				valuesMap.put("filters", "stat,wall");
				
				mpv = new MutablePropertyValues(valuesMap);
				beanDefinition = new GenericBeanDefinition();
				beanDefinition.setBeanClass(DruidDataSource.class);
				beanDefinition.setInitMethodName("init");
				beanDefinition.setDestroyMethodName("close");
				beanDefinition.setPropertyValues(mpv);
				registry.registerBeanDefinition(name, beanDefinition);
				
				logger.info("***********register data source bean desc-->class:{}, name:{},properties:{}",new Object[]{DruidDataSource.class.getName(),name,JSON.toJSONString(valuesMap)});
				
				DATA_SOURCE_BEAN_NAMES.add(name);
			}
		}
		
		logger.info(">>>>>>>>>>init data source names:"+Arrays.toString(DATA_SOURCE_BEAN_NAMES.toArray(new String[0])));
	}

	

}
