package com.tw.util;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author liutianwen
 * @Description: 返回信息实体类
 * @date 2019年8月3日
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public  class ResponseInfo<T> {
	
	public static final String KEY_CODE = "code";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_DATA = "data";
	public static final String CODE_SUCCESS = "0000";
	public static final String CODE_ERROR = "9999";

	public static final HashMap<String, String> codeMap=new HashMap<String, String>();
	static {
//	    	codeMap=new HashMap<String, String>();
		codeMap.put(CODE_SUCCESS, "成功");
		codeMap.put(CODE_ERROR, "失败");
	}

	private String code;
	private String message;
	private T data;

	private Integer pageNo;
	private Integer pageSize;
	private Integer total;

	public ResponseInfo(String code,String message,T data, Integer pageNo, Integer pageSize, Integer total) {
		super();
		this.code=code;
		this.message=message;
		this.data= data;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.total = total;
	}

	public ResponseInfo(String code,String message,T data) {
		super();
		this.code=code;
		this.message=message;
		this.data= data;
	}


	public static <T> ResponseInfo<T> success(T data) {
		return new ResponseInfo<>(CODE_SUCCESS, codeMap.get(CODE_SUCCESS), data);
	}

	public static <T> ResponseInfo<T> error(T data) {
		return new ResponseInfo<>(CODE_ERROR, codeMap.get(CODE_ERROR), data);
	}

	public static <T> ResponseInfo<T> response(String code, String message, T data) {
		return new ResponseInfo<>(code, message, data);
	}

	public static <T> ResponseInfo<T> returnPage(T data, Integer pageNo, Integer pageSize, Integer total) {
		return new ResponseInfo<>(CODE_SUCCESS, codeMap.get(CODE_SUCCESS), data, pageNo, pageSize, total);
	}



	@JsonIgnore
	public boolean isSuccess() {
		return CODE_SUCCESS.equals(code);
	}
}
