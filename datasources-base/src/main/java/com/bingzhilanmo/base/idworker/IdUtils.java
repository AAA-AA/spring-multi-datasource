package com.bingzhilanmo.base.idworker;

import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 获取唯一ID 的工具类
 * @author bowen_wang
 *
 */
public class IdUtils {

	private static IdWorker idWorker;

	static {
		Resource resource = new ClassPathResource(
				"config/dataSourceShareInfo.properties");
		Properties p = new Properties();
		try {
			p.load(resource.getInputStream());
			Long workerId = Long.valueOf(p.getProperty("worker.id"));
			idWorker = new IdWorker(workerId);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Long getId(){
		return idWorker.nextId();
	}
	
}
