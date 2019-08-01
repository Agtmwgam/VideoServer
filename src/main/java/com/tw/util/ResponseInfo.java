package com.tw.util;


import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

//	public ResponseInfo(String sCode,String sResualt,T tData) {
//		super();
//		this.code=sCode;
//		this.message=sResualt;
//		this.data= tData;
//	}


	    public static <T> ResponseInfo<T> success(T data) {
	        return new ResponseInfo<>(CODE_SUCCESS, codeMap.get(CODE_SUCCESS), data);
	    }

	    public static <T> ResponseInfo<T> error(T data) {
	        return new ResponseInfo<>(CODE_ERROR, codeMap.get(CODE_ERROR), data);
	    }

	    @JsonIgnore
	    public boolean isSuccess() {
	        return CODE_SUCCESS.equals(code);
	    }
	}
