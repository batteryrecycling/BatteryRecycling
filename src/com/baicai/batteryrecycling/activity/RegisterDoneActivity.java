package com.baicai.batteryrecycling.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baicai.batteryrecycling.R;

/**
 * 完成注册
 * 
 * @author Administrator
 * 
 */
public class RegisterDoneActivity extends Activity {

	private TextView tv_done;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register_done);

		initView();
		initData();
		initEvent();
		super.onCreate(savedInstanceState);
	}

	private void initView() {
		// TODO Auto-generated method stub
		RelativeLayout include = (RelativeLayout) findViewById(R.id.include_register_done);
		tv_done = (TextView) include.findViewById(R.id.tv_register_done);
		tv_done.setVisibility(View.VISIBLE);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	private void initEvent() {
		tv_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 跳转到登陆界面
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
}
