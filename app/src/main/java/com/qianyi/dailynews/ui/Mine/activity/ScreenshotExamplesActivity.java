package com.qianyi.dailynews.ui.Mine.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.ui.Mine.bean.ExamScreenBean;
import com.qianyi.dailynews.utils.loader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class ScreenshotExamplesActivity extends BaseActivity {
    @BindView(R.id.banner) public Banner banner;
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    private List<String> imageUrl =new ArrayList<>();
    private CustomLoadingDialog customLoadingDialog;
    private String  id;


    @Override
    protected void initViews() {
        customLoadingDialog=new CustomLoadingDialog(this);
        customLoadingDialog.show();
        id =getIntent().getStringExtra("id");
        if(!TextUtils.isEmpty(id)){
            ApiMine.getMakeMoneyExample(ApiConstant.getMakeMoneyExample, id, new RequestCallBack<String>() {
                @Override
                public void onSuccess(Call call, Response response, String s) {
                    Log.i("ss",s);
                    customLoadingDialog.dismiss();
                    Gson gson = new Gson();
                    ExamScreenBean screenBean = gson.fromJson(s,ExamScreenBean.class);
                    String code = screenBean.getCode();
                    if("0000".equals(code)){
                        List<ExamScreenBean.ExamScreenData> strings = screenBean.getData();
                        if(strings.size()>0){
                            for (int i = 0; i <strings.size() ; i++) {
                                imageUrl.add(strings.get(i).getImg());
                            }
                        setData2(imageUrl);
                        }
                    }
                }
                @Override
                public void onEror(Call call, int statusCode, Exception e) {
                    customLoadingDialog.dismiss();
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("截图案例");
    }

    private void setData2(List<String> imgurls) {
        //设置banner样式(显示圆形指示器)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置指示器位置（指示器居右）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imgurls);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
//        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);

        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_screenshot_example);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
}
