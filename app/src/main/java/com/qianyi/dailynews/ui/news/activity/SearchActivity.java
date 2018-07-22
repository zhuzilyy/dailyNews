package com.qianyi.dailynews.ui.news.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.activity.SettingsActivity;
import com.qianyi.dailynews.ui.Mine.adapter.HotWordAdapter;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.ui.news.bean.HotWordBean;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.utils.Utils;
import com.qianyi.dailynews.views.ClearEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/1.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.no_data_rl)
    RelativeLayout no_data_rl;
    @BindView(R.id.no_internet_rl)
    RelativeLayout no_internet_rl;
    @BindView(R.id.gv_hotWord)
    GridView gv_hotWord;
    private CustomLoadingDialog customLoadingDialog;
    private HotWordAdapter hotWordAdapter;
    @BindView(R.id.newsSearch_etc) public ClearEditText newsSearch_etc;
    @BindView(R.id.back) public TextView back;
    private List<String> data;
    private String tag;
    @Override
    protected void initViews() {
        customLoadingDialog=new CustomLoadingDialog(this);
        Intent intent=getIntent();
        if (intent!=null){
            tag=intent.getStringExtra("tag");
        }
    }
    @Override
    protected void initData() {
        if (!Utils.hasInternet()){
            gv_hotWord.setVisibility(View.GONE);
            no_data_rl.setVisibility(View.GONE);
            no_internet_rl.setVisibility(View.VISIBLE);
        }else{
            customLoadingDialog.show();
            getData(ApiConstant.HOT_WORD);
        }
    }

    private void getData(String url) {
        ApiNews.hotWord(url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                Gson gson=new Gson();
                HotWordBean hotWordBean = gson.fromJson(s, HotWordBean.class);
                String code = hotWordBean.getCode();
                if (code.equals("SUCCESS")){
                    data = hotWordBean.getData();
                    hotWordAdapter=new HotWordAdapter(data, SearchActivity.this);
                    gv_hotWord.setAdapter(hotWordAdapter);
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                no_data_rl.setVisibility(View.GONE);
                no_internet_rl.setVisibility(View.VISIBLE);
            }
        });
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
                    String hotWord = newsSearch_etc.getText().toString();
                    Uri uri = null;
                    final Intent intent=new Intent(SearchActivity.this,SearchWebviewActivity.class);
                    intent.putExtra("hotWord",hotWord);
                    startActivity(intent);
                    if (tag.equals("mission")){
                        mission();
                    }
                   /* try {
                        uri = Uri.parse("http://www.baidu.com/s?&ie=utf-8&oe=UTF-8&wd=" + URLEncoder.encode(st,"UTF-8"));
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                    final Intent it = new Intent(Intent.ACTION_VIEW, uri);*/
                   /* Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            if (tag.equals("mission")){
                                mission();
                            }
                            startActivity(intent);
                        }
                    };
                    timer.schedule(task, 0);*/
                }

                return false;
            }
        });

        gv_hotWord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String hotWord = data.get(i);
                Intent intent=new Intent(SearchActivity.this,SearchWebviewActivity.class);
                intent.putExtra("hotWord",hotWord);
                startActivity(intent);
              /*  Uri uri = null;
                try {
                    uri = Uri.parse("https://www.baidu.com/s?wd=" + URLEncoder.encode(hotWord,"UTF-8"));
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
                final Intent it = new Intent(Intent.ACTION_VIEW, uri);
                it.setAction("android.intent.action.VIEW");
                Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        startActivity(it);
                    }
                };
                timer.schedule(task, 0);*/
                if (tag.equals("mission")){
                    mission();
                }

            }
        });
    }
    //
    private void mission() {
        String user_id= (String) SPUtils.get(SearchActivity.this,"user_id","");
        ApiNews.missionHotWord(ApiConstant.MISSSION_READ, user_id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if (code.equals(ApiConstant.SUCCESS)){
                        Intent intent=new Intent();
                        intent.setAction("com.action.search.mission");
                        sendBroadcast(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {

            }
        });
    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.ll_change,R.id.reload,R.id.ll_search})
    public void click(View view){
        switch (view.getId()){
            case R.id.ll_change:
                data.clear();
                getData(ApiConstant.CHANGE_HOT_WORD);
                break;
            case R.id.reload:
                getData(ApiConstant.HOT_WORD);
                break;
            case R.id.ll_search:
                InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
        }
    }
}
