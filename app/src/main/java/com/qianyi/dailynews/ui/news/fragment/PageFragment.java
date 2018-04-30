package com.qianyi.dailynews.ui.news.fragment;

import android.os.Handler;
import android.widget.Toast;


import com.qianyi.dailynews.R;

import java.util.ArrayList;

public class PageFragment extends LazyloadFragment {

    private ArrayList<String> datas = new ArrayList<>();
    private Handler handler = new Handler();

    @Override
    public int setContentView() {
        return R.layout.fragment_page;
    }


    @Override
    public void init() {


    }

    @Override
    public void lazyLoad() {
        Toast.makeText(getActivity(), "改变fragment", Toast.LENGTH_SHORT).show();

    }


}
