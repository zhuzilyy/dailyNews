package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.qianyi.dailynews.ui.account.activity.LoginActivity;
import com.qianyi.dailynews.utils.ListActivity;
import com.qianyi.dailynews.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/6/6.
 */

public class GuideQuestionActivity extends BaseActivity{
    @BindView(R.id.tv_title)
    TextView tv_title;
    /*//第一题
    @BindView(R.id.rb_first1)
    RadioButton rb_first1;
    @BindView(R.id.rb_first2)
    RadioButton rb_first2;
    @BindView(R.id.rb_first3)
    RadioButton rb_first3;

    //第2题
    @BindView(R.id.rb_second1)
    RadioButton rb_second1;
    @BindView(R.id.rb_second2)
    RadioButton rb_second2;
    @BindView(R.id.rb_second3)
    RadioButton rb_second3;


    //第3题
    @BindView(R.id.rb_third1)
    RadioButton rb_third1;
    @BindView(R.id.rb_third2)
    RadioButton rb_third2;
    @BindView(R.id.rb_third3)
    RadioButton rb_third3;

    //第4题
    @BindView(R.id.rb_forth1)
    RadioButton rb_forth1;
    @BindView(R.id.rb_forth2)
    RadioButton rb_forth2;
    @BindView(R.id.rb_forth3)
    RadioButton rb_forth3;

    //第5题
    @BindView(R.id.rb_fifth1)
    RadioButton rb_fifth1;
    @BindView(R.id.rb_fifth2)
    RadioButton rb_fifth2;
    @BindView(R.id.rb_fifth3)
    RadioButton rb_fifth3;*/

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

    private String question1,question2,question3,question4,question5,userId;
    private CustomLoadingDialog customLoadingDialog;
    @Override
    protected void initViews() {
        tv_title.setText("新手答题");
        userId= (String)SPUtils.get(GuideQuestionActivity.this,"user_id","");
        customLoadingDialog=new CustomLoadingDialog(this);
        ListActivity.list.add(this);
    }
    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_guide_question);
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
                commitQuestion();
                break;
        }
    }

    private void commitQuestion() {
        customLoadingDialog.show();
        String answers=question1+"|"+question2+"|"+question3+"|"+question4+"|"+question5;
        ApiMine.greendHandQuestion(ApiConstant.GREEN_HAND_QUESTION, userId, "1", answers, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                Toast.makeText(GuideQuestionActivity.this, "答题完成", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(s);
                    String return_code = jsonObject.getString("return_code");
                    if (return_code.equals("SUCCESS")){
                        Intent intent=new Intent();
                        intent.setAction("com.action.update.mission");
                        sendBroadcast(intent);
                        ListActivity.close();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                Toast.makeText(GuideQuestionActivity.this, "网络错误重新提交", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
