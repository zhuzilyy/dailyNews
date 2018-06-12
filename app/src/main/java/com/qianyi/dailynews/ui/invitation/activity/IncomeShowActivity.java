package com.qianyi.dailynews.ui.invitation.activity;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/12.
 */

public class IncomeShowActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.btn_share) public Button btn_share;
    @BindView(R.id.ll_shai) public LinearLayout ll_shai;
    @BindView(R.id.iv_back) public ImageView back;
    @BindView(R.id.tv_title) public TextView title;
    public LinearLayout ll_friendCircle;//分享到盆友圈
    public LinearLayout ll_QQ;//分享到QQ
    public LinearLayout ll_wechat;//分享到微信
    public LinearLayout ll_weibo;//分享到微博

    private PopupWindow pw_share;
    private View view_share, view_onekeyshoutu;
    @Override
    protected void initViews() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("晒收入");

        view_share = LayoutInflater.from(IncomeShowActivity.this).inflate(R.layout.pw_share, null);
        ll_friendCircle=view_share.findViewById(R.id.ll_friendCircle);
        ll_QQ=view_share.findViewById(R.id.ll_QQ);
        ll_wechat=view_share.findViewById(R.id.ll_wechat);
        ll_weibo=view_share.findViewById(R.id.ll_weibo);

        ll_friendCircle.setOnClickListener(this);
        ll_QQ.setOnClickListener(this);
        ll_wechat.setOnClickListener(this);
        ll_weibo.setOnClickListener(this);

    }

    @Override
    protected void initData(){

    }

    @Override
    protected void getResLayout() {
        setContentView(R.layout.activity_income_show);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void setStatusBarColor() {

    }
    @OnClick({R.id.btn_share})
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_share:
                shwoSharePw();
                break;
            case R.id.ll_wechat:
                //微信分享
                Toast.makeText(this, "微信分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_friendCircle:
                //朋友圈分享
                Toast.makeText(this, "朋友圈分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_QQ:
                //QQ分享
                Toast.makeText(this, "QQ分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_weibo:
                //微博分享
                Toast.makeText(this, "微博分享", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    private void shwoSharePw() {
        pw_share = new PopupWindow(IncomeShowActivity.this);
        pw_share.setContentView(view_share);
        pw_share.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        pw_share.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        pw_share.setTouchable(true);
        pw_share.setFocusable(true);
        pw_share.setBackgroundDrawable(new BitmapDrawable());
        pw_share.setAnimationStyle(R.style.AnimBottom);
        pw_share.showAtLocation(ll_shai, Gravity.BOTTOM, 0, 0);
        // 设置pw弹出时候的背景颜色的变化
        backgroundAlpha(0.5f);
        pw_share.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
