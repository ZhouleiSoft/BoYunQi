package com.app.zhoulei.boyunqi.view;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.zhoulei.boyunqi.R;


/**
 * Created by Zhoulei on 2016/6/25.
 */
public class FragmentArticle extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fragment_arctic,container,false);
    }
}
