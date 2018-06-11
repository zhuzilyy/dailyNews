package com.qianyi.dailynews.ui.Mine.activity;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

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
    //第六题
    @BindView(R.id.cb_decoration)
    CheckBox cb_decoration;
    @BindView(R.id.cb_education)
    CheckBox cb_education;
    @BindView(R.id.cb_meiRong)
    CheckBox cb_meiRong;
    @BindView(R.id.cb_jiaJu)
    CheckBox cb_jiaJu;
    @BindView(R.id.cb_movie)
    CheckBox cb_movie;
    @BindView(R.id.cb_clock)
    CheckBox cb_clock;
    @BindView(R.id.cb_baby)
    CheckBox cb_baby;
    @BindView(R.id.cb_sport)
    CheckBox cb_sport;
    @BindView(R.id.cb_entertainment)
    CheckBox cb_entertainment;
    @BindView(R.id.cb_finical)
    CheckBox cb_finical;
    @BindView(R.id.cb_reading)
    CheckBox cb_reading;
    @BindView(R.id.cb_music)
    CheckBox cb_music;
    @BindView(R.id.cb_clothing)
    CheckBox cb_clothing;
    @BindView(R.id.cb_phone)
    CheckBox cb_phone;
    @BindView(R.id.cb_housing)
    CheckBox cb_housing;
    @BindView(R.id.cb_travling)
    CheckBox cb_travling;
    @BindView(R.id.cb_car)
    CheckBox cb_car;
    @BindView(R.id.cb_meiZhuang)
    CheckBox cb_meiZhuang;
    @BindView(R.id.cb_computer)
    CheckBox cb_computer;
    @BindView(R.id.cb_news)
    CheckBox cb_news;
    @BindView(R.id.cb_machine)
    CheckBox cb_machine;
    @BindView(R.id.cb_game)
    CheckBox cb_game;


    //第7题
    @BindView(R.id.cb_advertisement)
    CheckBox cb_advertisement;
    @BindView(R.id.cb_content)
    CheckBox cb_content;
    @BindView(R.id.cb_title)
    CheckBox cb_title;
    @BindView(R.id.cb_ui)
    CheckBox cb_ui;
    @BindView(R.id.cb_noInterest)
    CheckBox cb_noInterest;
    @BindView(R.id.cb_push)
    CheckBox cb_push;
    @BindView(R.id.cb_other)
    CheckBox cb_other;

    @BindView(R.id.rg_question1)
    RadioGroup rg_question1;
    @BindView(R.id.rg_question2)
    RadioGroup rg_question2;
    @BindView(R.id.rg_question3)
    RadioGroup rg_question3;
    @BindView(R.id.rg_question4)
    RadioGroup rg_question4;
    @BindView(R.id.rg_question5)
    RadioGroup rg_question5;
    @BindView(R.id.rg_question8)
    RadioGroup rg_question8;
    @BindView(R.id.et_advice)
    EditText et_advice;
    private String question1,question2,question3,question4,question5,question6,question7,question8,userId;
    private CustomLoadingDialog customLoadingDialog;
    private List<CheckBox> cbSixList,cbSevenList;
    private List<String> sixAnswerList,sevenAnswerList;
    @Override
    protected void initViews() {
        userId= (String) SPUtils.get(QuestionSurveyActivity.this,"user_id","");
        customLoadingDialog=new CustomLoadingDialog(this);
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
        String strCallName = "8.以下称呼哪个最符合您的形象 <font color='#FF0000'>*</font> 单选";
        tv_callName.setText(Html.fromHtml(strCallName));
        cbSixList=new ArrayList<>();
        cbSevenList=new ArrayList<>();
        sixAnswerList=new ArrayList<>();
        sevenAnswerList=new ArrayList<>();
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
        rg_question1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                question1 = radioButton.getText().toString();
            }
        });
        rg_question2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                question2 = radioButton.getText().toString();
            }
        });
        rg_question3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                question3 = radioButton.getText().toString();
            }
        });
        rg_question4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                question4 = radioButton.getText().toString();
            }
        });
        rg_question5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                question5 = radioButton.getText().toString();
            }
        });
        rg_question8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                question8 = radioButton.getText().toString();
            }
        });

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back,R.id.btn_confrim})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confrim:
                getListAnswer();
                if (TextUtils.isEmpty(question1)){
                    Toast.makeText(this, "请选择第1题的答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(question2)){
                    Toast.makeText(this, "请选择第2题的答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(question3)){
                    Toast.makeText(this, "请选择第3题的答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(question4)){
                    Toast.makeText(this, "请选择第4题的答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(question5)){
                    Toast.makeText(this, "请选择第5题的答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sixAnswerList.size()==0){
                    Toast.makeText(this, "请选择第6题的答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sevenAnswerList.size()==0){
                    Toast.makeText(this, "请选择第7题的答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(question8)){
                    Toast.makeText(this, "请选择第8题的答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                getSixAndSevenAnswer();
                commitQuestion();
                break;
        }
    }
    private void getSixAndSevenAnswer() {
        String[] sixAnswerArr=new String[sixAnswerList.size()];
        String[] sevenAnswerArr=new String[sevenAnswerList.size()];
        StringBuffer sixAnswerBuffer=new StringBuffer();
        StringBuffer sevenAnswerBuffer=new StringBuffer();
        for (int i = 0; i < sixAnswerList.size(); i++) {
            if (i==0) {
                sixAnswerArr[i]=sixAnswerList.get(i);
            }else{
                sixAnswerArr[i]="|"+sixAnswerList.get(i);
            }
            sixAnswerBuffer.append(sixAnswerArr[i]);
        }

        for (int i = 0; i < sevenAnswerList.size(); i++) {
            if (i==0) {
                sevenAnswerArr[i]=sevenAnswerList.get(i);
            }else{
                sevenAnswerArr[i]="|"+sevenAnswerList.get(i);
            }
            sevenAnswerBuffer.append(sevenAnswerArr[i]);
        }
        question6=sixAnswerBuffer.toString();
        question7=sevenAnswerBuffer.toString();
    }

    private void getListAnswer() {
        cbSixList.add(cb_decoration);
        cbSixList.add(cb_education);
        cbSixList.add(cb_meiRong);
        cbSixList.add(cb_jiaJu);
        cbSixList.add(cb_movie);
        cbSixList.add(cb_baby);
        cbSixList.add(cb_sport);
        cbSixList.add(cb_entertainment);
        cbSixList.add(cb_finical);
        cbSixList.add(cb_reading);
        cbSixList.add(cb_music);
        cbSixList.add(cb_clothing);
        cbSixList.add(cb_phone);
        cbSixList.add(cb_housing);
        cbSixList.add(cb_travling);
        cbSixList.add(cb_car);
        cbSixList.add(cb_meiZhuang);
        cbSixList.add(cb_computer);
        cbSixList.add(cb_news);
        cbSixList.add(cb_machine);
        cbSixList.add(cb_game);

        cbSevenList.add(cb_advertisement);
        cbSevenList.add(cb_content);
        cbSevenList.add(cb_title);
        cbSevenList.add(cb_ui);
        cbSevenList.add(cb_noInterest);
        cbSevenList.add(cb_push);
        cbSevenList.add(cb_other);
        for (int i = 0; i <cbSixList.size() ; i++) {
            if (cbSixList.get(i).isChecked()){
                sixAnswerList.add(cbSixList.get(i).getText().toString().trim());
            }
        }
        for (int i = 0; i <cbSevenList.size() ; i++) {
            if (cbSevenList.get(i).isChecked()){
                sevenAnswerList.add(cbSevenList.get(i).getText().toString().trim());
            }
        }

    }

    private void commitQuestion() {
        String advice = et_advice.getText().toString();
        customLoadingDialog.show();
        String answers=question1+"|"+question2+"|"+question3+"|"+question4+"|"+question5+"|"+question6+"|"+question7+"|"+question8+"|"+advice;
        ApiMine.greendHandQuestion(ApiConstant.GREEN_HAND_QUESTION, userId, "0", answers, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                Toast.makeText(QuestionSurveyActivity.this, "答题完成", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String return_code = jsonObject.getString("return_code");
                    if (return_code.equals("SUCCESS")){
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                Toast.makeText(QuestionSurveyActivity.this, "网络错误重新提交", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
