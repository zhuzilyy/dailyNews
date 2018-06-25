package com.qianyi.dailynews.ui.Mine.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.views.ClearEditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/1.
 */

public class ModifyPasswordActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    public ImageView back;
    @BindView(R.id.tv_title)
    public TextView title;
    @BindView(R.id.et_oldPwd)
    ClearEditText et_oldPwd;
    @BindView(R.id.et_newPwd)
    ClearEditText et_newPwd;
    @BindView(R.id.et_confirmPwd)
    ClearEditText et_confirmPwd;
    private CustomLoadingDialog customLoadingDialog;
    private String userId;
    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("修改密码");
        userId= (String) SPUtils.get(this,"user_id","");
        customLoadingDialog=new CustomLoadingDialog(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_modifypwd);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_confrim})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_confrim:
                String oldPwd = et_oldPwd.getText().toString().trim();
                String newPwd = et_newPwd.getText().toString().trim();
                String confirmPwd = et_confirmPwd.getText().toString().trim();
                if (TextUtils.isEmpty(oldPwd)){
                    Toast.makeText(this, "原密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(newPwd)){
                    Toast.makeText(this, "新密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmPwd)){
                    Toast.makeText(this, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (newPwd.length()<6){
                    Toast.makeText(this, "新密码不能小于6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPwd.equals(confirmPwd)){
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                modifyPwd(oldPwd,newPwd,confirmPwd);
                break;
        }
    }
    //修改密码
    private void modifyPwd(String oldPwd, String newPwd, String confirmPwd) {
        customLoadingDialog.show();
        ApiAccount.modifyPwd(ApiConstant.MODIFY_PWD, userId, oldPwd, newPwd, new RequestCallBack<String>() {
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
                            Toast.makeText(ModifyPasswordActivity.this, return_msg, Toast.LENGTH_SHORT).show();
                            if (return_msg.equals("成功")){
                                finish();
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
            }
        });
    }
}
