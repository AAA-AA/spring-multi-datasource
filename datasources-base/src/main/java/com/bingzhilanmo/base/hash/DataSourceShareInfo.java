package com.bingzhilanmo.base.hash;

/**
 * 数据源 切分信息
 * @author wangbo
 *
 */
public class DataSourceShareInfo extends ShardInfo<String> {

	/**
	 * 注入到spring dynamicDataSource 中TargetDataSources的值
	 */
	private String name;
	/**
	 * 目标数据源的链接信息
	 */
	private String connectionInfo;
	
	private int weight;
	
	
	public DataSourceShareInfo(String name,String connectionInfo,int weight){
		this.name=name;
		this.connectionInfo=connectionInfo;
		this.weight=weight;
	}

	@Override
	protected String createResource() {
		return name;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getConnectionInfo() {
		return connectionInfo;
	}

	public void setConnectionInfo(String connectionInfo) {
		this.connectionInfo = connectionInfo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "DataSourceShareInfo [name=" + name + ", connectionInfo="
				+ connectionInfo + ", weight=" + weight + "]";
	}

}
