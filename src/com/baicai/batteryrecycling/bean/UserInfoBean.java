package com.baicai.batteryrecycling.bean;

import java.util.List;

/**
 * ÇëÇó×¢²áµÄBean
 * 
 * @author Administrator
 * 
 */
public class UserInfoBean {

	public  String success;
	public String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public class Results {
		public List<AddressList> addressList;
		public List<PriceSetting> priceSetting;
		public String __v;
		public String _id;
		public String address;
		public String area;
		public String city;
		public String createTime;
		public String name;
		public String phone;
		public String province;
		public String pwd;

		public class AddressList {

		}

		public class PriceSetting {

		}
	}

	@Override
	public String toString() {
		return "UserInfoBean [success=" + success + ", token=" + token + "]";
	}
	
	

}
