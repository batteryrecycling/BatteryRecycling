package com.baicai.batteryrecycling.utils;

import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

/**
 * 
 * @author Administrator
 * 
 */
public class VolleryToUTF8 {
	public static String toUTF8(JSONObject response)
			throws UnsupportedEncodingException {
		String s = new String(response.toString().getBytes("ISO-8859-1"),
				"utf-8");
		return s;
	}
}
