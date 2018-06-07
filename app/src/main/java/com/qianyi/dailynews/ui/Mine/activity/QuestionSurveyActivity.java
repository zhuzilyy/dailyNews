package com.qianyi.dailynews.ui.Mine.activity;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/6.
 */

public class QuestionSurveyActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_selectSex)
    TextView tv_selectSex;
    @BindView(R.id.tv_selectAge)
    TextView tv_selectAge;
    @BindView(R.id.tv_career)
    TextView tv_career;
    @BindView(R.id.tv_earning)
    TextView tv_earning;
    @BindView(R.id.tv_education)
    TextView tv_education;
    @BindView(R.id.tv_hobby)
    TextView tv_hobby;
    @BindView(R.id.tv_satisfy)
    TextView tv_satisfy;
    @BindView(R.id.tv_callName)
    TextView tv_callName;
    @Override
    protected void initViews() {
        tv_title.setText("问卷调查");
        String strSex = "1.您的性别? <font color='#FF0000'>*</font> 单选";
        tv_selectSex.setText(Html.fromHtml(strSex));
        String strAge = "2.您的年龄? <font color='#FF0000'>*</font> 单选";
        tv_selectAge.setText(Html.fromHtml(strAge));
        String strCareer = "3.您目前从事的职业是? <font color='#FF0000'>*</font> 单选";
        tv_career.setText(Html.fromHtml(strCareer));
        String strEarning = "4.个人月收入? <font color='#FF0000'>*</font> 单选";
        tv_earning.setText(Html.fromHtml(strEarning));
        String strEducation = "5.受教育程度? <font color='#FF0000'>*</font> 单选";
        tv_education.setText(Html.fromHtml(strEducation));
        String strhobby = "6.个人兴趣爱好 <font color='#FF0000'>*</font> 多选题";
        tv_hobby.setText(Html.fromHtml(strhobby));
        String strSatisfy = "7.您对每日速报不满意的地方是 <font color='#FF0000'>*</font> 多选题";
        tv_satisfy.setText(Html.fromHtml(strSatisfy));
        String strCallName = "8.以下称呼哪个最符合您的形象 <font color='#FF0000'>*</font> 多选题";
        tv_callName.setText(Html.fromHtml(strCallName));
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_question_survey);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
