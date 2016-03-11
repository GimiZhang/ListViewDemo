package com.dfhe.listviewdemo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements AbsListView.OnScrollListener {

    public ArrayList<String> data= new ArrayList<>();
    ListView lv;
    int lastVisibleItem;
    int mTouchSlop;//获取系统认为的滑动的最小距离
    float mFirstY;
    float mCurrentY;
    int direction;
    boolean mShow;
    private Toolbar toolbar;
    Animator mAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化toolBar
        toolbar = (Toolbar) findViewById(R.id.tool);
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        initLayout();
    }

    /**
     * 初始化数据
     */
    private void initLayout() {

        //模拟数据
        for (int i = 0; i < 50; i++) {
            data.add("测试数据"+i);
        }

        //获取系统认为的最低滑动距离
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();

        //对滑动时间进行判断
        View.OnTouchListener myTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        if(mCurrentY - mFirstY  > mTouchSlop){
                            direction = 0;//down
                        }else if (mFirstY - mCurrentY > mTouchSlop){
                            direction = 1;//up
                        }

                        if(direction == 1){
                            if(mShow){
                                toolbarAnimal(1);//隐藏toolBar
                                mShow = !mShow;
                            }
                        }else if(direction == 0){
                            if(!mShow){
                                toolbarAnimal(0);//显示toolBar
                                mShow = !mShow;
                            }
                        }


                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        };
        lv = (ListView) findViewById(R.id.lv);
        View header = new View(this);
        header.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int)getResources().getDimension(R.dimen.abc_action_bar_default_height)));
        lv.addHeaderView(header);

        lv.setAdapter(new MyAdapter());
        lv.setOnTouchListener(myTouchListener);
        lv.setOnScrollListener(this);
    }

    /**
     * 隐藏toolbar动画
     * @param flag
     */
    private void toolbarAnimal(int flag) {
        if(mAnimator != null && mAnimator.isRunning()){
            mAnimator.cancel();
        }

        if(flag == 0){
            mAnimator = ObjectAnimator.ofFloat(toolbar,"translationY",toolbar.getTranslationY(),0);
        }else{
            mAnimator = ObjectAnimator.ofFloat(toolbar,"translationY",toolbar.getTranslationY(),-toolbar.getHeight());
        }

        mAnimator.start();
    }

    /**
     * 这里可以无视，是测试滑动状态
     * @param view
     * @param scrollState
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState){
            case NumberPicker.OnScrollListener.SCROLL_STATE_IDLE:
                Log.e("SCROLL_STATE_IDLE","停止");
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                Log.e("SCROLL_STATE_TOUCH_SCROLL","滚动");
                break;
            case NumberPicker.OnScrollListener.SCROLL_STATE_FLING:
                Log.e("SCROLL_STATE_FLING","惯性滑动");
                break;
        }
    }

    /**
     * 这里是判断上滑或者listView滑动的位置
     * @param view
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0){
            Log.e("滑到底部了", "滑到底部了");
        }

        if(firstVisibleItem > lastVisibleItem){
            Log.e("正在上滑","正在上滑");
        }else if(firstVisibleItem<lastVisibleItem){
            Log.e("正在下滑","正在下滑");
        }

        if(firstVisibleItem == 0){
            Log.e("滑动到第一项了","滑动到第一项了");
        }

        lastVisibleItem = firstVisibleItem;
    }


    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = View.inflate(MainActivity.this,R.layout.activity_item,null);
            TextView tv  = (TextView) convertView.findViewById(R.id.tv_content);
            String tvData = data.get(position);
            tv.setText(tvData);
            return convertView;
        }
    }
}
