package com.qianyi.dailynews.ui.Mine.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.Mine.adapter.MyPageAdapter;
import com.qianyi.dailynews.ui.Mine.fragment.EasyMoney_RecordFragment;
import com.qianyi.dailynews.ui.Mine.fragment.HighRebate_RecordFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/5.
 */

public class TaskRecordActivity extends BaseActivity {
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.tab) public TabLayout tab;
    @BindView(R.id.viewpager) public ViewPager viewpager;
    public MyPageAdapter myPageAdapter;
    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("任务记录");

        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new HighRebate_RecordFragment());
        datas.add(new EasyMoney_RecordFragment());
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("高额返利");
        titles.add("轻松赚钱");
        myPageAdapter.setTitles(titles);

        viewpager.setAdapter(myPageAdapter);
        // 将ViewPager与TabLayout相关联
        tab.setupWithViewPager(viewpager);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_taskrecord);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
