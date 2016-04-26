package com.baicai.batteryrecycling.activity;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baicai.batteryrecycling.MainActivity;
import com.baicai.batteryrecycling.R;
import com.baicai.batteryrecycling.bean.ResultsUserInfoBean;
import com.baicai.batteryrecycling.bean.UserInfoBean;
import com.baicai.batteryrecycling.config.Constants;
import com.baicai.batteryrecycling.fragment.YouAiFragment;
import com.baicai.batteryrecycling.utils.VolleryToUTF8;
import com.google.gson.Gson;

public class LoginActivity extends Activity {

	private TextView tv_cancel;// 取消按钮
	private TextView tv_regiter_fast;
	private TextView tv_find_password;
	private Button btn_login;
	private EditText et_phone;
	private EditText et_pwd;
	private String phone;
	private String pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		initView();
		initData();
		initEvent();
		super.onCreate(savedInstanceState);
	}

	private void initView() {
		// TODO Auto-generated method stub
		RelativeLayout include = (RelativeLayout) findViewById(R.id.include_login);
		TextView tv_title = (TextView) include
				.findViewById(R.id.tv_title_center);
		tv_cancel = (TextView) include.findViewById(R.id.tv_title_cancel);
		tv_title.setText("登陆");
		tv_cancel.setVisibility(View.VISIBLE);
		tv_cancel.setText("取消");

		// 快速注册
		tv_regiter_fast = (TextView) findViewById(R.id.tv_register_fast);

		// 找回密码
		tv_find_password = (TextView) findViewById(R.id.tv_register_findpassword);

		// 登陆
		btn_login = (Button) findViewById(R.id.btn_login);

		et_phone = (EditText) findViewById(R.id.et_loginPhone);
		et_pwd = (EditText) findViewById(R.id.et_login_pwd);
	}

	private void initData() {

	}

	private void initEvent() {
		// 取消
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// 登陆
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				phone = et_phone.getText().toString().trim();
				pwd = et_pwd.getText().toString().trim();
				Log.d("login", phone + pwd);
				if (phone.isEmpty() || pwd.isEmpty()) {
					Toast.makeText(getApplicationContext(), "用户名或密码不能为空", 0)
							.show();
				} else {
					Login();

				}

			}

		});

		// 快速注册
		tv_regiter_fast.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 登陆
	 */
	private void Login() {
		// TODO Auto-generated method stub
		RequestQueue requestQueue = Volley
				.newRequestQueue(getApplicationContext());

		Map<String, String> userInfo = new HashMap<String, String>();
		userInfo.put("phone", phone);
		userInfo.put("pwd", pwd);

		String url = Constants.BASEURL + "/User/login";
		JsonObjectRequest request = new JsonObjectRequest(Method.POST, url,
				new JSONObject(userInfo), new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {
							Log.d("login", VolleryToUTF8.toUTF8(response));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						if (response.optString("success").equals("true")) {
							// 解析数据，并保存token
							Gson gson = new Gson();
							UserInfoBean userInfo = gson.fromJson(
									response.toString(), UserInfoBean.class);
							
							ResultsUserInfoBean results = gson.fromJson(
									response.optString("results"), ResultsUserInfoBean.class);
							Log.d("login", "id(((((("+results._id);

							SharedPreferences sp = getSharedPreferences(
									Constants.SP_TOKEN, Activity.MODE_PRIVATE);
							SharedPreferences.Editor editor = sp.edit();
							editor.putString("token", userInfo.token);
							editor.commit();

							// 跳转到“有爱的我”
							Intent intent = new Intent(getApplicationContext(),
									MainActivity.class);
							startActivity(intent);

							finish();
						} else {
							Toast.makeText(getApplicationContext(),
									"账号或者密码不正确", 0).show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});

		requestQueue.add(request);
	}
}
