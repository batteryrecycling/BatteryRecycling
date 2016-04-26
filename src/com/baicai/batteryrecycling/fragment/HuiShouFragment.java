package com.baicai.batteryrecycling.fragment;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baicai.batteryrecycling.R;
import com.baicai.batteryrecycling.activity.MessageActivity;
import com.baicai.batteryrecycling.base.BaseFragment;
import com.baicai.batteryrecycling.bean.BatteryTypeBean;
import com.baicai.batteryrecycling.bean.BatteryTypeBean.Result;
import com.baicai.batteryrecycling.config.Constants;
import com.baicai.batteryrecycling.utils.BitmapCache;
import com.baicai.batteryrecycling.utils.VolleryToUTF8;
import com.baicai.batteryrecycling.view.ScrollDisabledListView;
import com.google.gson.Gson;

/**
 * ���չ㳡��Fragment
 * 
 * @author Administrator
 * 
 */
public class HuiShouFragment extends BaseFragment {

	private RequestQueue requestQueue;
	// private TextView tv_type1;
	// private TextView tv_type2;
	// private TextView tv_type3;
	// private TextView tv_price1;
	// private TextView tv_price2;
	// private TextView tv_price3;
	private BatteryTypeBean batteryTypeBean;
	private ImageView iv_type1;
	private ImageView iv_type2;
	private ImageView iv_type3;
	private ImageView iv_xiaoxi;
	private ImageView iv_gouwuche;
	private Button btn_fabu;
	private ScrollDisabledListView lv_shouye;
	private List<Result> resultList;
	private List<Result> show2Item = new ArrayList<Result>();
	private RelativeLayout rl_more;
	private int showItem=2;
	private BatteryTypeAdapter adapter;
	private boolean flag=false;//����Ƿ������鿴����

	@Override
	public View initView() {
		View rootView = View.inflate(mContext, R.layout.fragment_huishou, null);

		View title = rootView.findViewById(R.id.include_huishou);
		TextView tv_center = (TextView) title
				.findViewById(R.id.tv_title_center);
		iv_xiaoxi = (ImageView) title.findViewById(R.id.iv_xiaoxi);
		iv_gouwuche = (ImageView) title.findViewById(R.id.iv_gouwuche);

		tv_center.setText("���չ㳡");
		iv_gouwuche.setVisibility(View.VISIBLE);
		iv_xiaoxi.setVisibility(View.VISIBLE);

		btn_fabu = (Button) rootView.findViewById(R.id.btn_fabu);

		lv_shouye = (ScrollDisabledListView) rootView
				.findViewById(R.id.lv_shouye);
		
		rl_more = (RelativeLayout) rootView.findViewById(R.id.rl_loadmore);

		return rootView;
	}
	
	@Override
	public void onStart() {
		if(flag){
			rl_more.setVisibility(View.GONE);
		}
		super.onStart();
	}

	@Override
	protected void initData() {

		requestQueue = Volley.newRequestQueue(mContext);

		// ��ȡtoken
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences(Constants.SP_TOKEN, Activity.MODE_PRIVATE);
		String token = sharedPreferences.getString("token", "");

		if (token.equals("")) {
			// ������½����
			rl_more.setVisibility(View.GONE);
			Toast.makeText(mContext, "δ��¼���¼����", 0).show();
		} else {
			// ��ȡ�����������
			getBatteryType(token);
		}

		super.initData();
	}
	
	/**
	 * ��̬����ListView�ĸ߶�
	 * @param listView
	 */
	public void fixListViewHeight(ListView listView) {   
        // ���û��������������������ListViewû��������ء�  
        ListAdapter listAdapter = listView.getAdapter();  
        int totalHeight = 0;   
        if (listAdapter == null) {   
            return;   
        }   
        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {     
            View listViewItem = listAdapter.getView(index , null, listView);  
            // ��������View �Ŀ��   
            listViewItem.measure(0, 0);    
            // ������������ĸ߶Ⱥ�
            totalHeight += listViewItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        // listView.getDividerHeight()��ȡ�����ָ����ĸ߶�   
        // params.height����ListView��ȫ��ʾ��Ҫ�ĸ߶�    
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        listView.setLayoutParams(params);   
    }   

	//listview������
	class BatteryTypeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			
			return showItem;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return resultList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(mContext, R.layout.item_showmore,
						null);
				holder = new ViewHolder();
				holder.iv_shouye_pic = (ImageView) convertView
						.findViewById(R.id.iv_huishou_battery1);
				holder.et_shuliang = (EditText) convertView
						.findViewById(R.id.et_shouye_shuliang);
				holder.tv_type = (TextView) convertView
						.findViewById(R.id.tv_batterytye1);
				holder.tv_price = (TextView) convertView
						.findViewById(R.id.tv_huishou_price1);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			
			Log.d("huishou", "pic_url(((((("+resultList.get(position).picUrl);
			ImageLoader imageLoader=new ImageLoader(requestQueue, new BitmapCache());
			ImageListener listener = ImageLoader.getImageListener(holder.iv_shouye_pic,  
			        R.drawable.ic_launcher, R.drawable.ic_launcher);  
			imageLoader.get(resultList.get(position).picUrl, listener,100,100);
			
			holder.tv_type.setText(resultList.get(position).name);
			holder.tv_price.setText(resultList.get(position).maxPrice+"");

			return convertView;
		}

	}

	class ViewHolder {
		ImageView iv_shouye_pic;
		TextView tv_type;
		TextView tv_price;
		EditText et_shuliang;
	}

	@Override
	protected void initEvent() {
		// TODO Auto-generated method stub

		iv_xiaoxi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext, MessageActivity.class);
				startActivity(intent);
			}
		});
		
		
		//�鿴����
		rl_more.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Integer.parseInt(batteryTypeBean.total)>2){
					showItem=Integer.parseInt(batteryTypeBean.total);
					adapter.notifyDataSetChanged();
					fixListViewHeight(lv_shouye);
					rl_more.setVisibility(View.GONE);
					flag=true;
				}
				
				Toast.makeText(mContext, "û�и�����", 0).show();
				flag=true;
			}
		});

		super.initEvent();
	}

	/**
	 * ��ȡ��̨�ĵ����������
	 */
	private void getBatteryType(String token) {
		// TODO Auto-generated method stub
		String url = Constants.BASEURL + "/BatteryType?token=" + token;
		Log.d("huishou", "url+++++" + url);

		JsonObjectRequest request = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {

					

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						try {
							Log.d("huishou", VolleryToUTF8.toUTF8(response));

							if (response.optString("success").equals("true")) {
								Gson gson = new Gson();
								batteryTypeBean = gson.fromJson(VolleryToUTF8
										.toUTF8(response).toString(),
										BatteryTypeBean.class);

								Log.d("huishou", "batteryTypeBean~~~~"
										+ batteryTypeBean.toString());

								resultList = batteryTypeBean.result;
								show2Item.clear();
								for (int i = 0; i < 2; i++) {
									show2Item.add(resultList.get(i));
								}

								Log.d("huishou",
										"show2Item_____++" + show2Item.size());
								Log.d("huishou", "List<Result>"
										+ batteryTypeBean.result.get(0));

								adapter = new BatteryTypeAdapter();
								lv_shouye.setAdapter(adapter);
								 fixListViewHeight(lv_shouye);   
							}
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
