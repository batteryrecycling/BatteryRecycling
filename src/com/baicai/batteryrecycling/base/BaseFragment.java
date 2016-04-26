package com.baicai.batteryrecycling.base;

import com.baicai.batteryrecycling.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * fragment基类
 * @author 吴嘉明
 *
 */
public abstract class BaseFragment extends Fragment {
	protected MainActivity mContext;
	protected View rootView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	//调用fragment时调用该方法显示View
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//获取上下文
		mContext=(MainActivity) getActivity();
		rootView = initView();
		return rootView;
	}
	
	/**
	 * 子类必须实现，显示子类各自的界面
	 */
	public abstract View initView();

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		initData();
		
		initEvent();
		
		super.onStart();
	}

	/**
	 * 子类覆盖该方法初始化数据
	 */
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 子类覆盖该方法初始化事件
	 */
	protected void initEvent() {
		// TODO Auto-generated method stub
		
	}
}
