package com.baicai.batteryrecycling.utils;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

/**
 * Õ¯¬Á«Î«Û”ÎÕº∆¨º”‘ÿ
 * @author Administrator
 *
 */
public class NetandImage {

	public static void netWithGet(String url,Context context){
		 RequestQueue requestQueue=Volley.newRequestQueue(context);
		 
		 JsonObjectRequest jr=new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		 
		 requestQueue.add(jr);
	}
}
