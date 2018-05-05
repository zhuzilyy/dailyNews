package com.qianyi.dailynews.ui.news.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.views.ClearEditText;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/1.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.newsSearch_etc) public ClearEditText newsSearch_etc;
    @BindView(R.id.back) public TextView back;
    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_home_search);
    }
    @Override
    protected void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        newsSearch_etc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(EditorInfo.IME_ACTION_SEARCH == actionId){
                    Intent intent = new Intent(SearchActivity.this, WebviewActivity.class);
                    intent.putExtra("title","搜索");
                    intent.putExtra("url","http://www.baidu.com");
                    startActivity(intent);
                }
                return false;
            }
        });
    }

    @Override
    protected void setStatusBarColor() {

    }
}
