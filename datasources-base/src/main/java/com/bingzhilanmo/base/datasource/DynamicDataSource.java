package com.bingzhilanmo.base.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/** 
 * 动态数据源
 * @author bowen_wang
 * @date 2015年12月1日 下午2:12:58 
 *  
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
	final static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
	@Override
	protected Object determineCurrentLookupKey(){
		return DataSourceContextHolder.getDataSourceType();
	}
}
