package com.baicai.batteryrecycling.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class ScrollDisabledListView extends ListView {
	private int mPosition;  
	   
    public ScrollDisabledListView(Context context) {  
        super(context);  
    }  
   
    public ScrollDisabledListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
   
    public ScrollDisabledListView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
   
    @Override  
    public boolean dispatchTouchEvent(MotionEvent ev) {  
        final int actionMasked = ev.getActionMasked() & MotionEvent.ACTION_MASK;  
   
        if (actionMasked == MotionEvent.ACTION_DOWN) {  
            // ��¼��ָ����ʱ��λ��  
            mPosition = pointToPosition((int) ev.getX(), (int) ev.getY());  
            return super.dispatchTouchEvent(ev);  
        }  
   
        if (actionMasked == MotionEvent.ACTION_MOVE) {  
            // ��ؼ��ĵط�������MOVE �¼�  
            // ListView onTouch��ȡ����MOVE�¼����Բ��ᷢ����������  
            return true;  
        }  
   
        // ��ָ̧��ʱ  
        if (actionMasked == MotionEvent.ACTION_UP  
                || actionMasked == MotionEvent.ACTION_CANCEL) {  
            // ��ָ������̧����ͬһ����ͼ�ڣ��������ؼ���������һ������¼�  
            if (pointToPosition((int) ev.getX(), (int) ev.getY()) == mPosition) {  
                super.dispatchTouchEvent(ev);  
            } else {  
                // �����ָ�Ѿ��Ƴ�����ʱ��Item��˵���ǹ�����Ϊ������Item pressed״̬  
                setPressed(false);  
                invalidate();  
                return true;  
            }  
        }  
   
        return super.dispatchTouchEvent(ev);  
    }  
}
