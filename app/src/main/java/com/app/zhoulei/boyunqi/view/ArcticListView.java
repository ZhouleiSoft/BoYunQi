package com.app.zhoulei.boyunqi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.zhoulei.boyunqi.R;

/**
 * Created by Zhoulei on 2016/6/25.
 */
public class ArcticListView extends ListView implements AbsListView.OnScrollListener {

    private View header;
    private int headerHeight; //header的高度

    private int firstItemVisible;//第一个可见item的位置
    private int scrollState;

    private boolean isRemarkDown = false;//判断 当前item是在最顶端下拉的
    private int startY; //按下时的Y值

    private int state;//当前操作状态
    final int NONE = 0;//正常状态
    final int PULL = 1;//提示下拉状态
    final int RELESE = 2;//提示松开释放刷新状态
    final int REFLASHING = 3;//正在刷新状态

    public ArcticListView(Context context) {
        super(context);
        InitView(context);
    }
    public ArcticListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context);
    }
    public ArcticListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView(context);
    }

    /**
     * 初始化 listView 控件
     * @param context
     */
    private void InitView(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.arctic_list_header,null);
        measureView(header);
        headerHeight=header.getMeasuredHeight();
        Log.i("MyListView", "headerHeight: "+headerHeight);
        topPadding(-headerHeight);
        this.addHeaderView(header);
        this.setOnScrollListener(this);
    }

    /**
     * 通知父布局 自己占用的高宽
     * @param view
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null){
            p = new  ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0,0,p.width);
        int height;
        int temHeight = p.height;
        if(temHeight > 0){
            height = MeasureSpec.makeMeasureSpec(temHeight,MeasureSpec.EXACTLY);
        }else{
            height = MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED);
            view.measure(width,height);
        }
    }

    /**
     * 隐藏listView 头部
     * @param topPadding
     */
    private void topPadding(int topPadding){
        header.setPadding(header.getPaddingLeft(),topPadding,header.getPaddingRight(),
                header.getPaddingBottom());
        header.invalidate();

    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        this.scrollState = i ;
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        this.firstItemVisible = i;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (firstItemVisible == 0){//item在最顶端
                    isRemarkDown = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                onMove(ev);
                break;
            case MotionEvent.ACTION_UP:
                if(state == RELESE){
                    state = REFLASHING;
                    reflashViewByState();
                    //ToDo(加载最新数据)

                }else if( state == PULL){
                    state = NONE;
                    isRemarkDown = false;
                    reflashViewByState();
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 判断移动过程中的操做
     * @param ev
     */
    private void onMove(MotionEvent ev){
        if (!isRemarkDown){
            return;
        }
        //获取当前移动的距离
        int tempY = (int)ev.getY();
        int space = tempY - startY;
        int topPadding = space - headerHeight;
        switch (state){
            case NONE:
                if (space > 0){
                    state = PULL;
                    reflashViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if (space > headerHeight+20 && scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    state = RELESE;
                    reflashViewByState();
                }
                break;
            case RELESE:
                topPadding(topPadding);
                if (space < headerHeight+30){
                    state = PULL;
                    reflashViewByState();
                }else if(space <=0) {
                    state = NONE;
                    isRemarkDown = false;
                    reflashViewByState();
                }
                break;
        }
    }

    /**
     * 根据状态改变 控件显示
     */
    private void reflashViewByState(){
        TextView tip = (TextView) header.findViewById(R.id.tip);
        ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
        ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);

        //箭头旋转动画
        RotateAnimation animOne = new RotateAnimation(0,180,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        animOne.setDuration(500);
        animOne.setFillAfter(true);
        RotateAnimation animTwo = new RotateAnimation(180,0, RotateAnimation.RELATIVE_TO_SELF,0.5f);
        animTwo.setDuration(500);
        animTwo.setFillAfter(true);
        switch(state){
            case NONE:
                arrow.clearAnimation();
                topPadding(-headerHeight);
                break;
            case PULL:
                tip.setText("下拉可以刷新");
                arrow.setVisibility(View.VISIBLE);
                arrow.setAnimation(animOne);
                progress.setVisibility(View.GONE);
                break;
            case RELESE:
                tip.setText("松开可以刷新");
                arrow.setVisibility(View.VISIBLE);
                arrow.setAnimation(animTwo);
                progress.setVisibility(View.GONE);
                break;
            case REFLASHING:
                arrow.clearAnimation();
                topPadding(40);
                tip.setText("正在刷新......");
                arrow.setVisibility(View.GONE);
                progress.setVisibility(View.VISIBLE);
                break;
        }
    }
}
