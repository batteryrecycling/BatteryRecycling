package com.baicai.batteryrecycling.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baicai.batteryrecycling.R;
import com.baicai.batteryrecycling.activity.LoginActivity;
import com.baicai.batteryrecycling.activity.UserMssageActivity;
import com.baicai.batteryrecycling.base.BaseFragment;
import com.baicai.batteryrecycling.config.Constants;

/**
 * �а�����Fragment
 * 
 * @author Administrator
 * 
 */
public class YouAiFragment extends BaseFragment {

	private RelativeLayout rl_yidenglu;
	private ImageView iv_weidenglu;
	private RelativeLayout rl_ziliao;

	@Override
	public View initView() {
		View rootView = View.inflate(mContext, R.layout.fragment_youai, null);
		RelativeLayout include_youai = (RelativeLayout) rootView
				.findViewById(R.id.include_youai);
		TextView tv_title = (TextView) include_youai
				.findViewById(R.id.tv_title_center);
		tv_title.setText("�а�����");
		// δ��¼ͷ��
		rl_yidenglu = (RelativeLayout) rootView.findViewById(R.id.rl_yidenglu);
		// �ѵ�¼ͷ��
		iv_weidenglu = (ImageView) rootView.findViewById(R.id.iv_dengluzhuce);
		//�༭����
		rl_ziliao = (RelativeLayout) rootView.findViewById(R.id.rl_youai_ziliao);

		return rootView;
	}

	@Override
	protected void initData() {

		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences(Constants.SP_TOKEN, Activity.MODE_PRIVATE);
		String token = sharedPreferences.getString("token", "");
		Log.d("login", "token____"+token);
		if (token .equals("")) {
			// ���δ��½������rl_yidenglu�ͱ༭���ϡ�
			rl_ziliao.setVisibility(View.GONE);
			rl_yidenglu.setVisibility(View.GONE);
			iv_weidenglu.setVisibility(View.VISIBLE);
		} else {
			// �����½��������iv_weidenglu����������������
			rl_yidenglu.setVisibility(View.VISIBLE);
			iv_weidenglu.setVisibility(View.GONE);
		}

		super.initData();
	}

	@Override
	protected void initEvent() {
		iv_weidenglu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, LoginActivity.class);
				startActivity(intent);
			}
		});
		
		rl_ziliao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, UserMssageActivity.class);
				startActivity(intent);
			}
		});
		super.initEvent();
	}

}
