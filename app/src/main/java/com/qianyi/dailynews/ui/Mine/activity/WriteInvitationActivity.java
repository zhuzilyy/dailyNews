package com.qianyi.dailynews.ui.Mine.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiInvite;
import com.qianyi.dailynews.base.BaseActivity;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/1.
 */

public class WriteInvitationActivity extends BaseActivity {
    @BindView(R.id.back) public ImageView back;
    @BindView(R.id.ed_invatation) public EditText ed_invatation;
    @BindView(R.id.btn_submit) public Button btn_submit;
    @Override
    protected void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void getResLayout() {
        //设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_write_invitation_code);

    }

    @Override
    protected void initListener() {
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeOfFriend = ed_invatation.getText().toString().trim();
                if (!TextUtils.isEmpty(codeOfFriend)) {
                    writeInvatiCode(codeOfFriend);
                }
            }
        });
    }

    @Override
    protected void setStatusBarColor() {

    }

    /****
     * 填写好友邀请码
     * @param codeOfFriend
     */
    private void writeInvatiCode(String codeOfFriend) {

        String userid = (String) SPUtils.get(WriteInvitationActivity.this, "user_id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }

        ApiInvite.writeCode(ApiConstant.WRITE_CODE, userid, codeOfFriend, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, String s) {
                Log.i("sss", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String code = jsonObject.getString("code");
                    if ("0000".equals(code)) {
                        //更新用户信息
                        updateUserInfo();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onEror(Call call, int statusCode, Exception e) {
                Log.i("sss", e.getMessage());
            }
        });
    }
    /***
     * 更新用户信息
     */
    private void updateUserInfo() {
        String userid = (String) SPUtils.get(WriteInvitationActivity.this, "user_id", "");
        if (TextUtils.isEmpty(userid)) {
            return;
        }

        ApiAccount.getUserInfo(ApiConstant.GET_USER_INFO, userid, new RequestCallBack<String>() {
            @Override
            public void onSuccess(Call call, Response response, final String s) {
                Log.i("sss", s);
                WriteInvitationActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject jsonObject = new JSONObject(s);
                            String code = jsonObject.getString("code");
                            if("0000".equals(code)){
                                String return_msg = jsonObject.getString("return_msg");
                                JSONObject data = jsonObject.getJSONObject("data");
                                String user_id = data.getString("user_id");
                                String phone = data.getString("phone");
                                String head_portrait = data.getString("head_portrait");
                                String gold = data.getString("gold");
                                String my_invite_code = data.getString("my_invite_code");
                                String balance = data.getString("balance");
                                String earnings = data.getString("earnings");
                                String invite_code = data.getString("invite_code");


                                SPUtils.put(WriteInvitationActivity.this, "user_id", user_id);
                                SPUtils.put(WriteInvitationActivity.this, "phone", phone);
                                SPUtils.put(WriteInvitationActivity.this, "head_portrait", head_portrait);
                                SPUtils.put(WriteInvitationActivity.this, "gold", gold);
                                SPUtils.put(WriteInvitationActivity.this, "my_invite_code", my_invite_code);
                                SPUtils.put(WriteInvitationActivity.this, "balance", balance);
                                SPUtils.put(WriteInvitationActivity.this, "earnings", earnings);
                                SPUtils.put(WriteInvitationActivity.this, "invite_code", invite_code);

                                Intent intent = new Intent();
                                setResult(200,intent);
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
                Log.i("sss", e.getMessage());
            }
        });
    }
}
