package com.app.zhoulei.boyunqi.view;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.app.zhoulei.boyunqi.R;


/**
 * Created by Zhoulei on 2016/6/25.
 */
public class FragmentArticle extends Fragment {

    private ArcticListView listView;
    private ArrayAdapter<String> adapterArray;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String [] arr_data = {"文章标题一","文章标题22","文章标题33","文章标题44","文章标题55","文章标题66","文章标题77"};
        view = inflater.inflate(R.layout.layout_fragment_arctic,container,false);
        listView = (ArcticListView) view.findViewById(R.id.arctic_list);
        adapterArray = new ArrayAdapter <String>(getActivity(),android.R.layout.simple_list_item_1,arr_data);
        listView.setAdapter(adapterArray);
        adapterArray.notifyDataSetChanged();
        return view;
    }
}
