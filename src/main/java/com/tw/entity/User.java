package com.tw.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * 暂时不用
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class User extends BaseEntity{
	
	private static int count = 0;  
	private static String idModel;//静态化ID模板,count默认3位，不足3位用0补充，超过3位则用count本身
	private String id;
	private String giftNo;
	private String gift;
	private String name;
	private String birth; 
	private String sex;
	private String address;
	private String telephone;
	private String email;
	
	public User(){
		super();
//		idModel=creatId();
//		id=idModel;
	}
	
	public User(String id, String giftNo, String gift, String name, String birth, String sex, String address,
			String telephone, String email) {
		super();
		this.id = id;
		this.giftNo = giftNo;
		this.gift = gift;
		this.name = name;
		this.birth = birth;
		this.sex = sex;
		this.address = address;
		this.telephone = telephone;
		this.email = email;
	}
	
	public String creatId() {
		count++;
		String index = null;
		Date date = new Date();
		if (count < 10) {
			index = "00" + count;
		} else if (9 < count & count < 100) {
			index = "0" + count;
		} else {
			index = "" + count;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd" + index + "Hmmss");// 设置日期格式
		try {
			idModel = "TW" + df.format(date);
		} catch (Exception e) {
			System.out.println("格式转换错误!" + e.getMessage());
		}


//		System.out.println("生成ID:"+idModel);
		return idModel;
	}


	public static int getCount() {
		return count;
	}

	public static void setCount(int count) {
		User.count = count;
	}

	public static String getIdModel() {
		return idModel;
	}

	public static void setIdModel(String idModel) {
		User.idModel = idModel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGiftNo() {
		return giftNo;
	}

	public void setGiftNo(String giftNo) {
		this.giftNo = giftNo;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
