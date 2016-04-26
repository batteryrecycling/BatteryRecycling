package com.baicai.batteryrecycling.base;

import com.baicai.batteryrecycling.MainActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * fragment����
 * @author �����
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
	
	//����fragmentʱ���ø÷�����ʾView
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//��ȡ������
		mContext=(MainActivity) getActivity();
		rootView = initView();
		return rootView;
	}
	
	/**
	 * �������ʵ�֣���ʾ������ԵĽ���
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
	 * ���า�Ǹ÷�����ʼ������
	 */
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * ���า�Ǹ÷�����ʼ���¼�
	 */
	protected void initEvent() {
		// TODO Auto-generated method stub
		
	}
}
