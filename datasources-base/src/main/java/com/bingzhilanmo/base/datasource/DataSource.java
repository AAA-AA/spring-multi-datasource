package com.bingzhilanmo.base.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 需要切换---数据源注解
 * @author bowen_wang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataSource {
	/**
	* fixed == true 表示数据源是不需要动态分配的 直接使用value作为KEY 切分
	* flase 则需要动态计算
	*/
	boolean fixed() default true;
	//fixed == true 时，作为数据源的切换key
	//false时作为数据源切换的TYPE
	String value();
	/**
	 * 默认取第一个参数作为数据库切换的KEY
	 * @return
	 */
	String paramPosition() default "0";
}
