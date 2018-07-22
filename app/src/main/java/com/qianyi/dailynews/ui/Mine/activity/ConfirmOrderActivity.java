package com.qianyi.dailynews.ui.Mine.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiMine;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.ListActivity;
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
 * Created by Administrator on 2018/6/27.
 */
public class ConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.et1)
    TextView textView1;
    @BindView(R.id.et2)
    TextView textView2;
    @BindView(R.id.et3)
    TextView textView3;
    @BindView(R.id.et4)
    TextView textView4;
    @BindView(R.id.et5)
    TextView textView5;
    @BindView(R.id.et6)
    TextView textView6;
    @BindView(R.id.et_confirmCode)
    EditText et_confirmCode;
    @BindView(R.id.tv_paste)
    TextView tv_paste;
    private String userId,withdrawalMoney,subMoney;
    private CustomLoadingDialog customLoadingDialog;
    private Intent intent;
    private List<TextView> textViewList;
    @Override
    protected void initViews() {
        ListActivity.list.add(this);
        ListActivity.list2.add(this);
        tv_title.setText("确认订单");
        userId= (String) SPUtils.get(this,"user_id","");
        customLoadingDialog=new CustomLoadingDialog(this);
        intent=getIntent();
        if (intent!=null){
            withdrawalMoney=intent.getStringExtra("withdrawalMoney");
            subMoney = withdrawalMoney.substring(0, withdrawalMoney.length()-1);
        }
        textViewList=new ArrayList<>();
        textViewList.add(textView1);
        textViewList.add(textView2);
        textViewList.add(textView3);
        textViewList.add(textView4);
        textViewList.add(textView5);
        textViewList.add(textView6);

        et_confirmCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv_paste.setVisibility(View.VISIBLE);
                return false;
            }
        });
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_confrim_order);
    }
    @Override
    protected void initListener() {
        et_confirmCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String code = charSequence.toString();
                for (int j = 0; j <textViewList.size() ; j++) {
                     textViewList.get(j).setText("");
                }
                for (int j = 0; j <code.length() ; j++) {
                    char c = code.charAt(j);
                    textViewList.get(j).setText(c+"");
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.iv_back,R.id.btn_confrim,R.id.tv_paste})
    public void click(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_confrim:
                String first = textView1.getText().toString().trim();
                String second =textView2.getText().toString().trim();
                String third = textView3.getText().toString().trim();
                String fourth =textView4.getText().toString().trim();
                String fifth = textView5.getText().toString().trim();
                String sixth = textView6.getText().toString().trim();
                if (TextUtils.isEmpty(first)){
                    Toast.makeText(this, "请输入完整验证码第1位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(second)){
                    Toast.makeText(this, "请输入完整验证码第2位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(third)){
                    Toast.makeText(this, "请输入完整验证码第3位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fourth)){
                    Toast.makeText(this, "请输入完整验证码第4位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fifth)){
                    Toast.makeText(this, "请输入完整验证码第5位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sixth)){
                    Toast.makeText(this, "请输入完整验证码第6位", Toast.LENGTH_SHORT).show();
                    return;
                }
                doWithDrawal(first,second,third,fourth,fifth,sixth);
                break;
            case R.id.tv_paste:
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData data = cm.getPrimaryClip();
                ClipData.Item item = data.getItemAt(0);
                String content = item.getText().toString();
                if(TextUtils.isEmpty(content)){
                    Toast.makeText(this, "粘贴板内容位空", Toast.LENGTH_SHORT).show();
                    return;
                }
                tv_paste.setVisibility(View.GONE);
                for (int j = 0; j <textViewList.size() ; j++) {
                    textViewList.get(j).setText("");
                }
                for (int j = 0; j <content.length() ; j++) {
                    char c = content.charAt(j);
                    textViewList.get(j).setText(c+"");
                }
                et_confirmCode.setText(content);
                break;
        }
    }
    //提现
    private void doWithDrawal(String first, String second, String third, String fourth, String fifth, String sixth) {
        String confrimCode=first+second+third+fourth+fifth+sixth;
        customLoadingDialog.show();
        ApiMine.doWithdrawal(ApiConstant.DO_WITHDRAWAL, userId, subMoney, confrimCode, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                customLoadingDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(s);
                            String return_code = jsonObject.getString("return_code");
                            String return_msg = jsonObject.getString("return_msg");
                            Toast.makeText(ConfirmOrderActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                            if (return_code.equals(ApiConstant.SUCCESS)){
                                ListActivity.close();
                                ListActivity.close2();
                                Intent intent=new Intent();
                                intent.setAction("com.action.withdrawal.success");
                                sendBroadcast(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                customLoadingDialog.dismiss();
                Toast.makeText(ConfirmOrderActivity.this, "网络错误提现失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
