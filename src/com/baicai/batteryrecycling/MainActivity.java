package com.baicai.batteryrecycling;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baicai.batteryrecycling.fragment.HuiShouFragment;
import com.baicai.batteryrecycling.fragment.MallFragment;
import com.baicai.batteryrecycling.fragment.YouAiFragment;

public class MainActivity extends FragmentActivity {

	private RadioButton rb_huishou;
	private RadioButton rb_mall;
	private RadioButton rb_youai;
	private RadioGroup rg_menu;
	private FragmentManager supportFragmentManager;
	
	private HuiShouFragment huiShouFragment;
	private MallFragment mallFragment;
	private YouAiFragment youaiFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		initView();
		initData();
		initEvent();
	}

	// 初始化View
	private void initView() {
		rg_menu = (RadioGroup) findViewById(R.id.tab_menu);
		rb_huishou = (RadioButton) findViewById(R.id.rb_main_huishou);
		rb_mall = (RadioButton) findViewById(R.id.rb_main_mall);
		rb_youai = (RadioButton) findViewById(R.id.rb_main_youai);

		// 首次显示“回收广场”界面
		huiShouFragment = new HuiShouFragment();
		supportFragmentManager = getSupportFragmentManager();
		supportFragmentManager.beginTransaction()
				.replace(R.id.main_content, huiShouFragment).commit();
	}

	private void initEvent() {
		// RadioGroup点击事件
		rg_menu.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_main_huishou:
					if (huiShouFragment == null) {
						huiShouFragment = new HuiShouFragment();
					}
					supportFragmentManager.beginTransaction()
							.replace(R.id.main_content, huiShouFragment).commit();
					break;
				case R.id.rb_main_mall:
					if (mallFragment == null) {
						mallFragment = new MallFragment();
					}
					supportFragmentManager.beginTransaction()
							.replace(R.id.main_content, mallFragment).commit();
					break;
				case R.id.rb_main_youai:
					if (youaiFragment == null) {
						youaiFragment = new YouAiFragment();
					}
					supportFragmentManager.beginTransaction()
							.replace(R.id.main_content, youaiFragment).commit();
					break;

				default:
					break;
				}
			}
		});
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

}
