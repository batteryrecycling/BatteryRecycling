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
 * �ڶ���ע��
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
		// �û���

		et_name = (EditText) findViewById(R.id.et_username);

		// ���ڵ���

		et_area = (EditText) findViewById(R.id.et_userarea);

		// ��ϸ��ַ

		et_address = (EditText) findViewById(R.id.et_useraddress);

		// ���
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
					Toast.makeText(getApplicationContext(), "�ǳƲ���Ϊ��", 0).show();
				} else if (area.isEmpty()) {
					Toast.makeText(getApplicationContext(), "��������Ϊ��", 0).show();
				} else if (address.isEmpty()) {
					Toast.makeText(getApplicationContext(), "��ַ����Ϊ��", 0).show();
				} else {
					// ���ע��
					completeResigter(name, area, address);
				}

			}
		});
	}

	/**
	 * ���ע��
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
		userInfo.put("province", "�㶫");
		userInfo.put("city", "����");
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
							// ��ת��ע��ɹ�����
							Intent intent = new Intent(getApplicationContext(),
									RegisterDoneActivity.class);
							startActivity(intent);
						} else if (response.optString("result").equals(
								"���ֻ���ע��")) {
							Toast.makeText(getApplicationContext(), "���ֻ��ѱ�ע��",
									0).show();
						} else {
							Toast.makeText(getApplicationContext(), "ע��ʧ�ܣ�������",
									0).show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("register", error.toString());
						Toast.makeText(getApplicationContext(), "ע��ʧ�ܣ�������", 0)
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
