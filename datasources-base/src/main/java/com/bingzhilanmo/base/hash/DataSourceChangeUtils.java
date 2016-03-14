package com.bingzhilanmo.base.hash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;



/**
 * 数据库切分工具类，我们采用的是 一致性hash，
 * @author wangbo
 */
public class DataSourceChangeUtils {
	
	private static List<DataSourceShareInfo> shardsSource=new ArrayList<DataSourceShareInfo>();
	private static Sharded<String,DataSourceShareInfo> sharded;
	//默认权重 --- 这里涉及的主要是可能存在的 一个数据库要承载更多的任务，目前默认都为1 及所有数据库都保持一致
	private static Integer DEFAULT_WEIGHT=1;

	private static final Logger logger = LoggerFactory.getLogger(DataSourceChangeUtils.class);
	

	private static final String SHARE_INFO_FILE = "config/dataSourceShareInfo.properties";
	
	static{
		Resource resource = new ClassPathResource(SHARE_INFO_FILE);
		Properties p = new Properties();
		try {
			p.load(resource.getInputStream());
			//数据源信息
			Integer shareNum=Integer.valueOf(p.getProperty("share.num"));
			for (int i = 1; i <=shareNum; i++) {
				String name=p.getProperty("db.name"+i);
				String connectionInfo=p.getProperty("db.connectino.info"+i);
				DataSourceShareInfo dataSourceShareInfo=new DataSourceShareInfo(name, connectionInfo,DEFAULT_WEIGHT);
				shardsSource.add(dataSourceShareInfo);
			}
			//LoggerUtil.shareLog("datasource info :>>>>>>>>>>>>>>>"+shardsSource.toString());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		logger.info(">>>>>>======= init " +shardsSource.size()+" datasources !");
		sharded=new Sharded<String,DataSourceShareInfo>(shardsSource);
	}
	public static String getDataSourceInfo(String changeInfo){
		return sharded.getShard(changeInfo);
	}

	
	public static void main(String[] args) {
		System.out.println(getDataSourceInfo("1326550118811648"));
	}
	
}
