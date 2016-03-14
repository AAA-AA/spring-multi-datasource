package com.bingzhilanmo.base.result;

import java.io.Serializable;

/**
 * 
 * @author bowen_wang
 * json返回格式的包装类
 */
public class ApiResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static Integer CODE_SUCCESS=1,CODE_ERRO=0;
	
	public ApiResult(Integer code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 操作成功 返回数据
	 */
	public ApiResult(Object data) {
		this.code = CODE_SUCCESS;
		this.message = "操作成功";
		this.data=data;
	}

	/**
	 * 操作成功 不返回数据
	 */
	public ApiResult() {
		this.code = CODE_SUCCESS;
		this.message = "操作成功";
	}
	
	/**
	 * 操作失败 返回失败原因
	 */
	public ApiResult(String message) {
		this.code = CODE_ERRO;
		this.message = message;
	}
	
	private Integer code;
	private String message;
	private Object data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
