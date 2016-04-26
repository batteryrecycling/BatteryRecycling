package com.baicai.batteryrecycling.activity;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baicai.batteryrecycling.R;
import com.baicai.batteryrecycling.config.Constants;
import com.lion.swipelistview.BaseSwipeAdapter;
import com.lion.swipelistview.SwipeListView;

/**
 * 消息通知
 * 
 * @author Administrator
 * 
 */
public class MessageActivity extends Activity {

	private SwipeListView lv_msg;
	private RequestQueue requestQueue;
	private ArrayList<LeftData> data;
	private ListViewAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message);

		initView();
		initData();
		initEvent();
		super.onCreate(savedInstanceState);
	}

	private void initView() {
		View title = findViewById(R.id.include_xiaoxi_title);
		TextView tv_title = (TextView) title.findViewById(R.id.tv_title_center);
		tv_title.setText("消息通知");

		lv_msg = (SwipeListView) findViewById(R.id.lv_message);
	}

	private void initData() {

		requestQueue = Volley.newRequestQueue(getApplicationContext());

		// 获取消息数据
		// getMsgData();
		data = new ArrayList<LeftData>();
		for (int i = 0; i < 30; i++) {
			String name = Integer.toString(i) + "订单详情";
			LeftData leftData = new LeftData(name, "12:00");
			data.add(leftData);
		}

		mAdapter = new ListViewAdapter(this);
		lv_msg.setAdapter(mAdapter);
		lv_msg.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		lv_msg.setMultiChoiceModeListener(new MultiChoiceModeListener() {

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				Log.e("MultiChoiceModeListener", "onActionItemClicked");
				return false;
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				if (lv_msg.onCreateActionMode()) {
					Log.e("MultiChoiceModeListener",
							"onCreateActionMode--before");
					return true;
				} else {
					Log.e("MultiChoiceModeListener",
							"onCreateActionMode--after");
					return false;
				}
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				Log.e("MultiChoiceModeListener", "onDestroyActionMode");
				lv_msg.onDestroyActionMode();
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				Log.e("MultiChoiceModeListener", "onPrepareActionMode");
				return false;
			}

			@Override
			public void onItemCheckedStateChanged(ActionMode mode,
					int position, long id, boolean checked) {
				Log.e("MultiChoiceModeListener", "onItemCheckedStateChanged");
			}

		});

		// 条目点击事件
		lv_msg.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getApplicationContext(),
						Integer.toString(position), Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void getMsgData() {
		String url = Constants.BASEURL + "";
		// TODO Auto-generated method stub
		JsonObjectRequest request = new JsonObjectRequest(url, null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void initEvent() {

	}

	class LeftData {
		String name;
		String desc;

		public LeftData(String name, String desc) {
			this.name = name;
			this.desc = desc;
		}
	}

	private class ViewHolder {
		TextView mDingdanDesc;
		TextView mDingdanTime;
		Button mDelete;
	}

	class ListViewAdapter extends BaseSwipeAdapter {

		public ListViewAdapter(Context context) {
			super(context, lv_msg);
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = super.getView(position, convertView, parent);

				holder = new ViewHolder();
				holder.mDingdanDesc = (TextView) convertView
						.findViewById(R.id.item_dingdan);
				holder.mDingdanTime = (TextView) convertView
						.findViewById(R.id.item_time);

				holder.mDelete = (Button) convertView.findViewById(R.id.delete);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.mDelete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Toast.makeText(getApplicationContext(), "DELETE",
							Toast.LENGTH_SHORT).show();
					int position = lv_msg.getPositionForView(v);
					lv_msg.hiddenRightView();
					data.remove(position);
					mAdapter.notifyDataSetChanged();
				}
			});

			holder.mDingdanDesc.setText(((LeftData) getItem(position)).name);
			holder.mDingdanTime.setText(((LeftData) getItem(position)).desc);

			return convertView;
		}
	}

	@Override
	public void onBackPressed() {
		if (!lv_msg.hiddenRightView()) {
			super.onBackPressed();
		}
	}

}
