package com.baicai.batteryrecycling.bean;

import java.util.List;

/**
 * µÁ≥ÿ¿‡–Õ
 * 
 * @author Administrator
 * 
 */
public class BatteryTypeBean {
	public List<Result> result;
	public String success;
	public String total;

	public class Result {

		public int __v;
		public String _id;
		public String createTime;
		public int index;
		public double maxPrice;
		public String name;
		public String number;
		public String picUrl;
		public double price;

		@Override
		public String toString() {
			return "Result [__v=" + __v + ", _id=" + _id + ", createTime="
					+ createTime + ", index=" + index + ", maxPrice="
					+ maxPrice + ", name=" + name + ", number=" + number
					+ ", picUrl=" + picUrl + ", price=" + price + "]";
		}

	}

	@Override
	public String toString() {
		return "BatteryTypeBean [result=" + result + ", success=" + success
				+ ", total=" + total + "]";
	}

}
