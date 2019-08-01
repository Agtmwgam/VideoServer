package com.tw.test.utilTest;

import com.tw.util.ResponseInfo;

public class ResponseTest {
	public static void main(String[] args) {
		ResponseInfo<String> responseInfo=new ResponseInfo<>();
		
		//属性测试
//		System.out.println(responseInfo.getCode());
		System.out.println(ResponseInfo.CODE_ERROR);
		System.out.println(ResponseInfo.KEY_MESSAGE);
		System.out.println(ResponseInfo.codeMap.get(ResponseInfo.CODE_SUCCESS));
		
		//方法测试
		ResponseInfo<String> responseInfo2=ResponseInfo.success("nihao");
		ResponseInfo<String> responseInfo3=ResponseInfo.error("chenc");
		System.out.println("成功------------>"+responseInfo2.isSuccess()+"--------"+responseInfo2);
		System.out.println("失败------------>"+responseInfo3.isSuccess()+"--------"+responseInfo3);
		
	}
}
