package com.baicai.batteryrecycling.view;

import com.baicai.batteryrecycling.R;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TimeDownView extends RelativeLayout implements Runnable {

	private TextView timedown_day, timedown_hour, timedown_min,
			timedown_second;
	private Paint mPaint; // 画笔,包含了画几何图形、文本等的样式和颜色信息
	private int[] times;
	private long mday, mhour, mmin, msecond;// 天，小时，分钟，�?
	private boolean run = false; // 是否启动�?

	public TimeDownView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public TimeDownView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TimeDownView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		View.inflate(context, R.layout.timedown_layout, TimeDownView.this);
//		timedown_day = (TextView) this.findViewById(R.id.timedown_day);
		timedown_hour = (TextView) this.findViewById(R.id.timedown_hour);
		timedown_min = (TextView) this.findViewById(R.id.timedown_min);
		timedown_second = (TextView) this.findViewById(R.id.timedown_second);

		mPaint = new Paint();
	}

	public int[] getTimes() {
		return times;
	}

	public void setTimes(int[] times) {
		this.times = times;
//		mday = times[0];
		mhour = times[0];
		mmin = times[1];
		msecond = times[2];
	}

	/**
	 * 倒计时计�?
	 */
	private void ComputeTime() {
		msecond--;
		if (msecond < 0) {
			mmin--;
			msecond = 59;
			if (mmin < 0) {
				mmin = 59;
				mhour--;
				if (mhour < 0) {
					// 倒计时结�?
					mhour = 59;
					mday--;
				}
			}
		}
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	@Override
	public void run() {
		// 标示已经启动
		run = true;
		ComputeTime();
//		String strTime = "还剩" + mday + "�? + mhour + "小时" + mmin + "分钟"
//				+ msecond + "�?;
//		this.setText(strTime);
//		timedown_day.setText(mday+""); 
		timedown_hour.setText(mhour+"");
		timedown_min.setText(mmin+"");
		timedown_second.setText(msecond+"");
		postDelayed(this, 1000);
	}
}
