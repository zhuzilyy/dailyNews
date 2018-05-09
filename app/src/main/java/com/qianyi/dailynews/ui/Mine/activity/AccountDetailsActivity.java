package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.Mine.adapter.MyPageAdapter;
import com.qianyi.dailynews.ui.Mine.fragment.GoldCoinFragment;
import com.qianyi.dailynews.ui.Mine.fragment.WithdrawalsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/5/1.
 */

public class AccountDetailsActivity extends BaseActivity implements View.OnClickListener {
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
        tv_right.setText("提现状态");
        title.setText("账户明细");
        tv_right.setVisibility(View.VISIBLE);

        myPageAdapter = new MyPageAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new GoldCoinFragment());
        datas.add(new WithdrawalsFragment());
        myPageAdapter.setData(datas);

        ArrayList<String> titles = new ArrayList<String>();
        titles.add("金币");
        titles.add("提现");
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
        setContentView(R.layout.activity_account_details);

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
                Intent intent_presentStatue = new Intent(AccountDetailsActivity.this,PresentStatusActivity.class);
                startActivity(intent_presentStatue);

            break;

            default:
            break;


        }
    }
}
