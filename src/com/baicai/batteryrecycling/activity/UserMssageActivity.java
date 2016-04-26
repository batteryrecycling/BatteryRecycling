package com.baicai.batteryrecycling.activity;

import com.baicai.batteryrecycling.R;
import com.baicai.batteryrecycling.bean.ResultsUserInfoBean;
import com.baicai.batteryrecycling.bean.UserInfoBean;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 个人资料
 * 
 * @author Administrator
 * 
 */
public class UserMssageActivity extends Activity implements OnClickListener {

	private TextView tv_name;
	private TextView tv_ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_usermessage);

		initView();
		initData();
		initEvent();

		super.onCreate(savedInstanceState);
	}

	private void initView() {
		tv_name = (TextView) findViewById(R.id.tv_ziliao_name);
		tv_ID = (TextView) findViewById(R.id.tv_ziliao_id);
	}

	private void initData() {
		// TODO Auto-generated method stub
	}

	private void initEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.rl_ziliao_name:

			break;

		case R.id.rl_ziliao_address:

			break;
		case R.id.rl_ziliao_changePwd:

			break;
		case R.id.btn_ziliao_exit:

			break;

		default:
			break;
		}
	}
}
