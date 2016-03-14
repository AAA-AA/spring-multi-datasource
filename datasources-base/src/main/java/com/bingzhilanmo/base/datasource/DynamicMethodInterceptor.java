package com.bingzhilanmo.base.datasource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.bingzhilanmo.base.hash.DataSourceChangeUtils;


/** 
 * 动态数据源切换拦截器
 * @author bowen_wang
 * @date 2015年12月1日 下午2:12:16 
 *  
 */
public class DynamicMethodInterceptor implements MethodInterceptor{
	
	@Autowired
	@Qualifier("defaultDataSource")
	private String defaultDataSource;
	
	Logger logger=LoggerFactory.getLogger(DynamicMethodInterceptor.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable{
		Object target = invocation.getThis();
		if(target.getClass().isAnnotationPresent(DataSource.class)){
			DataSource dataSource = target.getClass().getAnnotation(DataSource.class);
			boolean fixed=dataSource.fixed();
			String key =null;
			if(fixed){
				key= dataSource.value();
			}else{
				//获取参数
			    Object[] params=invocation.getArguments();
			    logger.info(" this class have >>>>>>"+params.length+" params...");
			    //默认取第一个参数 作为KEY--判断参数是否合法
			    Integer paramPostion=0;
			    try {
			     paramPostion=Integer.valueOf(dataSource.paramPosition());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" share DB erro ,DataSource param paramPosition【"+dataSource.paramPosition()+"】 is erro !!!");
				}
			    //判断参数位置是否越界
			    Integer paramSize=params.length;
			    if(paramPostion>paramSize-1){
			    	try {
			    		//越界不没有 可切换的 数据库
			    		key=null;	
					} catch (Exception e) {
						logger.error(" share DB erro ,paramSize <<"+paramSize+">>,but DataSource param paramPosition【"+dataSource.paramPosition()+"】  IndexOutOfBoundsException !!!");
					}
			    }
			    String targetDB=dataSource.value();
			    try {
				 if(targetDB!=null){
					    logger.info("share DB ::>>>> table type is >>>>>>"+targetDB+" and share info value is >>>>>"+params[paramPostion].toString());
				    	key =DataSourceChangeUtils.getDataSourceInfo(params[paramPostion].toString());
				   }
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(" share DB erro ,this share table not fand  <<"+targetDB+">>!!!");
				}
			}
			logger.info("target datasource is ========================"+key);
			if(StringUtils.isBlank(key)){
				key=defaultDataSource;
			}
			//提前清理一次
			DataSourceContextHolder.clearDataSourceType();
			DataSourceContextHolder.setDataSourceType(key);
			Object returnInfo = invocation.proceed();
			//可能存在线程池，最后一定要清理
			DataSourceContextHolder.clearDataSourceType();
			return returnInfo;
		}
		return invocation.proceed();
	}
}
