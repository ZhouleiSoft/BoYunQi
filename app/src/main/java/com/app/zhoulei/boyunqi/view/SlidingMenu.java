package com.app.zhoulei.boyunqi.view;


        import android.content.Context;
        import android.util.AttributeSet;
        import android.util.DisplayMetrics;
        import android.util.TypedValue;
        import android.view.MotionEvent;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.HorizontalScrollView;
        import android.widget.LinearLayout;

/**
 * Created by Zhoulei on 2016/6/22.
 */
public class SlidingMenu extends HorizontalScrollView {

    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private int mScreenWidth;

    private int mMenuWidth;

    private int mMenuRightPadding = 90 ;

    private int beforScrollX = 0;//隐藏的宽度
    private int downX=0;//按下时手指X坐标的值
    private boolean boo=false;

    private boolean once = false;

    /**
     * 未使用自定义属性时调用
     * @param context
     * @param attrs
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        //int50 转化为像素值
        mMenuRightPadding= (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,70,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 设置子view的宽和高 再 设置自己的宽和高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!once){
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);

            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding ;
            mContent.getLayoutParams().width = mScreenWidth;

            once = true;
        }



        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 进行布局排版(将menu隐藏)
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed){
            this.scrollTo(mMenuWidth,0);
        }
    }

    /**
     * 用户触摸事件
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();

        //根据按下时x的移动距离  和  抬起时X移动距离
        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX=(int) ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                if( !boo ){
                    if((ev.getX()-downX)>150){
                        this.smoothScrollTo(0,0);
                        boo=true;
                    }else{
                        this.smoothScrollTo(mMenuWidth,0);
                    }
                }else{
                    if((downX-ev.getX())>150){
                        this.smoothScrollTo(mMenuWidth,0);
                        boo=false;
                    }else{
                        this.smoothScrollTo(0,0);
                    }
                }
                return  true;
        }
        return super.onTouchEvent(ev);
    }
}

