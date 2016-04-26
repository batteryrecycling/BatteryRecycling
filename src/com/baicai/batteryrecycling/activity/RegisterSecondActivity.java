package com.baicai.batteryrecycling.activity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baicai.batteryrecycling.R;
import com.baicai.batteryrecycling.config.Constants;

/**
 * 第二步注册
 * 
 * @author Administrator
 * 
 */
public class RegisterSecondActivity extends Activity {

	private Button btn_done;
	private EditText et_name;
	private EditText et_area;
	private EditText et_address;
	private String phone;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_second);

		initView();
		initData();
		initEvent();
		super.onCreate(savedInstanceState);
	}

	private void initView() {
		// 用户名

		et_name = (EditText) findViewById(R.id.et_username);

		// 所在地区

		et_area = (EditText) findViewById(R.id.et_userarea);

		// 详细地址

		et_address = (EditText) findViewById(R.id.et_useraddress);

		// 完成
		btn_done = (Button) findViewById(R.id.btn_register_done);

	}

	private void initData() {
		phone = getIntent().getStringExtra("phone");
		password = getIntent().getStringExtra("password");
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		btn_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = et_name.getText().toString().trim();
				String area = et_area.getText().toString().trim();
				String address = et_address.getText().toString().trim();

				if (name.isEmpty()) {
					Toast.makeText(getApplicationContext(), "昵称不能为空", 0).show();
				} else if (area.isEmpty()) {
					Toast.makeText(getApplicationContext(), "地区不能为空", 0).show();
				} else if (address.isEmpty()) {
					Toast.makeText(getApplicationContext(), "地址不能为空", 0).show();
				} else {
					// 完成注册
					completeResigter(name, area, address);
				}

			}
		});
	}

	/**
	 * 完成注册
	 * 
	 * @param address
	 * @param area
	 * @param name
	 */
	protected void completeResigter(String name, String area, String address) {

		RequestQueue requestQueue = Volley
				.newRequestQueue(getApplicationContext());

		Map<String, String> userInfo = new HashMap<String, String>();
		userInfo.put("phone", phone);
		userInfo.put("pwd", password);
		userInfo.put("name", name);
		userInfo.put("province", "广东");
		userInfo.put("city", "深圳");
		userInfo.put("area", area);
		userInfo.put("address", address);

		String url = Constants.BASEURL + "/User/add";
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				new JSONObject(userInfo), new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {
							String s = new String(response.toString().getBytes(
									"ISO-8859-1"), "utf-8");
							Log.d("register", s);

							if (response.optString("").equals("")) {

							}
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (response.optString("success").equals("true")) {
							// 跳转到注册成功界面
							Intent intent = new Intent(getApplicationContext(),
									RegisterDoneActivity.class);
							startActivity(intent);
						} else if (response.optString("result").equals(
								"该手机已注册")) {
							Toast.makeText(getApplicationContext(), "该手机已被注册",
									0).show();
						} else {
							Toast.makeText(getApplicationContext(), "注册失败，请重试",
									0).show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("register", error.toString());
						Toast.makeText(getApplicationContext(), "注册失败，请重试", 0)
								.show();
					}
				});

		requestQueue.add(request);
	}

	@Override
	protected void onDestroy() {

		finish();
		super.onDestroy();
	}
}
