package com.qianyi.dailynews.ui.news.views;

import android.widget.ListView;

/**
 * Created by Administrator on 2018/5/31.
 */
import android.content.Context;
import android.util.AttributeSet;


public class ListViewForScorollView extends ListView {
    public ListViewForScorollView(Context context) {
        super(context);
    }

    public ListViewForScorollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScorollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int gaodu = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);//显示所有条目
        int gaodu2 = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);//指定listview的高度
        super.onMeasure(widthMeasureSpec, gaodu2);
    }

}