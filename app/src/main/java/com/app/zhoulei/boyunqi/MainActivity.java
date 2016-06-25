package com.app.zhoulei.boyunqi;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.app.zhoulei.boyunqi.view.FragmentArticle;
import com.app.zhoulei.boyunqi.view.FragmentGamble;
import com.app.zhoulei.boyunqi.view.FragmentMore;

public class MainActivity extends Activity {

    private FragmentMore fragmentMore;
    private FragmentArticle fragmentArctic;
    private FragmentGamble fragmentGamble;

    private TextView textArctic;
    private TextView textGamble;
    private TextView textMore;

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
        initView();
    }

    private void initView(){
        textArctic = (TextView) findViewById(R.id.text_arctic);
        textArctic.setOnClickListener(itemClick);
        textGamble = (TextView) findViewById(R.id.text_gamble);
        textGamble.setOnClickListener(itemClick);
        textMore= (TextView) findViewById(R.id.text_more);
        textMore.setOnClickListener(itemClick);

    }

    private View.OnClickListener itemClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this, "sdfa:"+view.getId(), Toast.LENGTH_SHORT).show();
            //开启一个fragment事务
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (view.getId()){
                case R.id.text_arctic:
                    if(fragmentArctic == null){
                        fragmentArctic = new FragmentArticle();
                    }
                    transaction.replace(R.id.layout_list,fragmentArctic);
                    textArctic.setBackgroundColor(0XFFE4BA3F);
                    textGamble.setBackgroundColor(0xffffffff);
                    textMore.setBackgroundColor(0xffffffff);
                    break;
                case R.id.text_gamble:
                    if(fragmentGamble == null){
                        fragmentGamble = new FragmentGamble();
                    }
                    transaction.replace(R.id.layout_list,fragmentGamble);
                    textArctic.setBackgroundColor(0xffffffff);
                    textGamble.setBackgroundColor(0XFFE4BA3F);
                    textMore.setBackgroundColor(0xffffffff);
                    break;
                case R.id.text_more:
                    if(fragmentMore == null){
                        fragmentMore = new FragmentMore();
                    }
                    transaction.replace(R.id.layout_list,fragmentMore);
                    textArctic.setBackgroundColor(0xffffffff);
                    textGamble.setBackgroundColor(0xffffffff);
                    textMore.setBackgroundColor(0XFFE4BA3F);
                    break;
                default:
                    break;
            }
            transaction.commit();
        }
    };
}
