package com.dfhe.listviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ListView;

/**
 * 项目名称：ListViewDemo
 * 类描述：
 * 创建人：Administrator
 * 创建时间：2016/3/10 11:40
 * 修改人：Administrator
 * 修改时间：2016/3/10 11:40
 * 修改备注：
 *
 * @param
 */
public class MyListView extends ListView {
    Context mContxt;
    int mMaxOverDistance = 30;
    public MyListView(Context context) {
        this(context, null);
    }

    public MyListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContxt = context;
        getmMaxOverDistance();
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverDistance, isTouchEvent);
    }

    //实用屏幕的density来计算具体的值，是为了让不同分辨率的弹性距离一致
    public void getmMaxOverDistance(){
        DisplayMetrics metrics = mContxt.getResources().getDisplayMetrics();
        float density = metrics.density;
        mMaxOverDistance = (int)(density*mMaxOverDistance);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);
    }
}
