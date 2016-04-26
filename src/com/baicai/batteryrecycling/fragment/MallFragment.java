package com.baicai.batteryrecycling.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baicai.batteryrecycling.R;
import com.baicai.batteryrecycling.activity.MessageActivity;
import com.baicai.batteryrecycling.base.BaseFragment;
import com.baicai.batteryrecycling.config.Constants;
import com.baicai.batteryrecycling.utils.VolleryToUTF8;
import com.baicai.batteryrecycling.view.TimeDownView;

/**
 * 环保商城的Fragment
 * 
 * @author Administrator
 * 
 */
public class MallFragment extends BaseFragment {

	private RequestQueue requestQueue;
	private ViewPager vp_mall;
	private List<Integer> ivList;
	private LinearLayout ll_point;
	private View v_point;
	private TimeDownView v_daojishi;
	private ImageView iv_xiaoxi;
	private ImageView iv_gouwuche;

	@Override
	public View initView() {
		View rootView = View.inflate(mContext, R.layout.fragment_mall, null);

		View title = rootView.findViewById(R.id.include_mall);
		TextView tv_center = (TextView) title
				.findViewById(R.id.tv_title_center);
		iv_xiaoxi = (ImageView) title.findViewById(R.id.iv_xiaoxi);
		iv_gouwuche = (ImageView) title
				.findViewById(R.id.iv_gouwuche);
		EditText et_search = (EditText) title.findViewById(R.id.ed_include);

		et_search.setVisibility(View.VISIBLE);
		tv_center.setVisibility(View.GONE);
		iv_gouwuche.setVisibility(View.VISIBLE);
		iv_xiaoxi.setVisibility(View.VISIBLE);

		vp_mall = (ViewPager) rootView.findViewById(R.id.vp_mall);
		ll_point = (LinearLayout) rootView.findViewById(R.id.ll_pointgroup);
		v_daojishi = (TimeDownView) rootView.findViewById(R.id.v_daojishi);

		return rootView;
	}

	@Override
	protected void initData() {

		requestQueue = Volley.newRequestQueue(mContext);
		getMallData();

		int[] time = { 01, 01, 01 };
		v_daojishi.setTimes(time);
		if (!v_daojishi.isRun()) {
			v_daojishi.run();
		}

		ivList = new ArrayList<Integer>();
		ivList.add(R.drawable.bg_mall);
		ivList.add(R.drawable.bg_mall);
		ivList.add(R.drawable.bg_mall);

		// 设置点
		setPoint();
		// 默认第一个点被选中
		setDescAndPointSelect(0);

		vp_mall.setAdapter(new MallAdapter());
		super.initData();
	}

	@Override
	protected void initEvent() {

		vp_mall.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				setDescAndPointSelect(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		
		//跳转到消息通知
		iv_xiaoxi.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(mContext, MessageActivity.class);
				startActivity(intent);
			}
		});

		super.initEvent();
	}

	/**
	 * 设置点
	 */
	private void setPoint() {
		// TODO Auto-generated method stub

		ll_point.removeAllViews();

		for (int i = 0; i < ivList.size(); i++) {
			v_point = new View(mContext);

			// 设置点的参数
			v_point.setBackgroundResource(R.drawable.v_lunbo_point_selector);
			LayoutParams lp = new LayoutParams(10, 10);
			if (i != 0) {
				lp.leftMargin = 20;
			}
			v_point.setEnabled(false);
			v_point.setLayoutParams(lp);
			ll_point.addView(v_point);
		}
	}

	/**
	 * 显示点的描述和图片的选中的点的背景切换
	 * 
	 * @param position
	 */
	protected void setDescAndPointSelect(int position) {
		// 切换点
		for (int i = 0; i < ivList.size(); i++) {
			// 判断位置是否是图片切换的位置
			ll_point.getChildAt(i).setEnabled(i == position);
		}

	}

	class MallAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ivList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv = new ImageView(mContext);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setImageResource(ivList.get(position));
			container.addView(iv);
			return iv;
		}

	}

	/**
	 * 获取商城数据
	 */
	private void getMallData() {
		// TODO Auto-generated method stub
		String url = Constants.BASEURL
				+ "/Goods?token=673b46e080bc36323bc377a0b81ce116";
		Log.d("mall", "url+++++" + url);

		JsonObjectRequest request = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {
							Log.d("mall", VolleryToUTF8.toUTF8(response));

						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
