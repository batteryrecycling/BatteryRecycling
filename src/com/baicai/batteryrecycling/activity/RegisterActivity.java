package com.baicai.batteryrecycling.activity;

import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baicai.batteryrecycling.R;
import com.baicai.batteryrecycling.config.Constants;
import com.baicai.batteryrecycling.utils.VolleryToUTF8;

/**
 * 第一步注册
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends Activity {

	protected static final int TIMER = 0;
	protected static final int TIMER_DONE = 1;
	private TextView tv_cancel;// 取消按钮
	private Button btn_next;
	private EditText et_phoneNomber;
	private EditText et_msg;
	private Button btn_msg;
	private Button btn_reGet;
	private String phoneNomber;
	private RequestQueue requestQueue;
	private EditText et_password;
	private String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		initView();
		initData();
		initEvent();
		super.onCreate(savedInstanceState);
	}

	private void initView() {
		// TODO Auto-generated method stub
		RelativeLayout include = (RelativeLayout) findViewById(R.id.include_register);
		tv_cancel = (TextView) include.findViewById(R.id.tv_title_cancel);
		tv_cancel.setVisibility(View.VISIBLE);
		tv_cancel.setText("取消");

		// 下一步
		btn_next = (Button) findViewById(R.id.btn_register_next);
		// 手机号码输入框

		et_phoneNomber = (EditText) findViewById(R.id.et_register_phonenomber);

		// 验证码输入框

		et_msg = (EditText) findViewById(R.id.et_register_msg);

		// 获取短信验证码按钮

		btn_msg = (Button) findViewById(R.id.btn_register_getMsg);

		// 重新获取按钮

		btn_reGet = (Button) findViewById(R.id.btn_register_msgAgian);
		btn_reGet.setEnabled(false);

		// 密码
		et_password = (EditText) findViewById(R.id.et_password);
	}

	private void initData() {
		// Volley请求队列
		requestQueue = Volley.newRequestQueue(getApplicationContext());

	}

	private void initEvent() {

		// 获取验证码
		btn_msg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				phoneNomber = et_phoneNomber.getText().toString().trim();
				Log.d("register", phoneNomber.toString());
				if (phoneNomber.isEmpty()) {
					Toast.makeText(getApplicationContext(), "手机号不能为空", 0)
							.show();
				} else if (!isMobileNum(phoneNomber)) {
					Toast.makeText(getApplicationContext(), "请输入正确的手机号码", 0)
							.show();
				} else {
					// 发送手机号获取验证码
					getCheckCode();
					btn_msg.setEnabled(false);
					startCount();
				}
			}
		});

		// 重新获取验证码
		btn_reGet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getCheckCode();
				btn_reGet.setEnabled(false);
				startCount();
			}
		});

		// 取消
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		// 下一步
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String checkCode = et_msg.getText().toString().trim();
				password = et_password.getText().toString().trim();
				if (et_phoneNomber.getText().toString().trim().isEmpty()) {
					Toast.makeText(getApplicationContext(), "请输入手机号", 0).show();
				} else if (checkCode.isEmpty()) {

					Toast.makeText(getApplicationContext(), "验证码不能为空", 0)
							.show();
				} else if (password.isEmpty()) {
					Toast.makeText(getApplicationContext(), "密码不能为空", 0).show();
				} else if (password.length() < 6) {
					Toast.makeText(getApplicationContext(), "密码长度必须大于6位", 0)
							.show();
				} else {
					// 校验验证码
					checkCodeIsTrue(checkCode);
				}

			}
		});
	}

	/**
	 * 判断验证码是否正确
	 */
	protected void checkCodeIsTrue(String checkCode) {
		// TODO Auto-generated method stub
		String url = Constants.BASEURL + "/sms/check?phone=" + phoneNomber
				+ "&checkCode=" + checkCode;
		JsonObjectRequest request = new JsonObjectRequest(url, null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {
							String string = VolleryToUTF8.toUTF8(response);
							Log.d("register", "判断验证码" + string);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (response.optString("result").equals("true")) {
							// 验证成功，跳转到下一步
							Intent intent = new Intent(getApplicationContext(),
									RegisterSecondActivity.class);
							intent.putExtra("phone", phoneNomber);
							intent.putExtra("password", password);
							startActivity(intent);
						} else {
							Toast.makeText(getApplicationContext(), "验证码不正确", 0)
									.show();
						}

					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("register", error.getMessage(), error);
						Toast.makeText(getApplicationContext(), "验证码不正确", 0)
								.show();
					}
				});

		requestQueue.add(request);
	}

	/**
	 * 发送手机号进行获取验证码
	 */
	protected void getCheckCode() {
		// TODO Auto-generated method stub
		String url = Constants.BASEURL + "/sms/get?phone=" + phoneNomber;
		JsonObjectRequest request = new JsonObjectRequest(url, null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						Log.d("register", response.toString());
						try {
							String checkCode = response.getString("result");
							Log.d("register", "result_____" + checkCode);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("register", error.getMessage(), error);
					}
				});

		requestQueue.add(request);
	}

	// 倒计时
	int count = 120;
	private TimerTask timerTask;
	private Timer timer;

	// 开始倒计时
	public void startCount() {
		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				if (count > 0) {
					Message message = new Message();
					message.what = TIMER;
					RegisterHandler.sendMessage(message);
				} else {
					Message message = new Message();
					message.what = TIMER_DONE;
					RegisterHandler.sendMessage(message);
				}

				count--;

			}
		};
		timer.schedule(timerTask, 0, 1000);
	}

	// 停止倒计时
	public void stopCount() {
		timerTask.cancel();
	}

	Handler RegisterHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case TIMER:
				btn_reGet.setText(count + "秒后重新获取");
				break;
			case TIMER_DONE:
				stopCount();
				count = 20;
				btn_reGet.setEnabled(true);
				btn_reGet.setText("重新获取");
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 判断是否是手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNum(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		System.out.println(m.matches() + "---");
		return m.matches();

	}

}
