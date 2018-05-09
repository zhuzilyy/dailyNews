package com.qianyi.dailynews.ui.mine.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.mine.adapter.MyPageAdapter;
import com.qianyi.dailynews.ui.mine.fragment.EasyMoneyFragment;
import com.qianyi.dailynews.ui.mine.fragment.HighRebateFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/1.
 */

public class MakeMoneyCenterActivity extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    @BindView(R.id.tv_right) public TextView tv_right;
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
        title.setText("赚钱中心");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("任务记录");

        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new HighRebateFragment());
        datas.add(new EasyMoneyFragment());
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
        setContentView(R.layout.activity_makemoneycenter);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.tv_right})
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_right:
                Intent intent_taskrecord = new Intent(MakeMoneyCenterActivity.this,TaskRecordActivity.class);
                startActivity(intent_taskrecord);
            break;

            default:
            break;


        }
    }
}
