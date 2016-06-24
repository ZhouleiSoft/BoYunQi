package com.app.zhoulei.boyunqi;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        //获得当前窗体对象  加载窗体设置
        Window window=MainActivity.this.getWindow();
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_main);
    }
}
