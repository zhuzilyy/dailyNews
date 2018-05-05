package com.qianyi.dailynews.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/5.
 */

public class TestAdapter extends BaseAdapter {
    private Context context;
    public TestAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 60;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView=new TextView(context);
        textView.setText("哈哈哈哈哈");
        return textView;
    }
}
