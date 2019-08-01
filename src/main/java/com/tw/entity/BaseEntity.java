package com.tw.entity;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import lombok.Data;

@Data
public class BaseEntity {
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	@Value("${userName}")
	private String systemName;
	public BaseEntity(){
		if(!StringUtils.isEmpty(systemName)){
			this.createdBy="SYSTEM";
			this.updatedBy="SYSTEM";
		}else{
			this.createdBy=systemName;
			this.updatedBy=systemName;
		}
		Date date=new Date(System.currentTimeMillis());
		this.createdDate=date;
		this.updatedDate=date;
	}
	
}
