package com.qianyi.dailynews.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.base.BaseFragment;
import com.qianyi.dailynews.ui.Mine.activity.AccountDetailsActivity;
import com.qianyi.dailynews.ui.Mine.activity.MakeMoneyCenterActivity;
import com.qianyi.dailynews.ui.Mine.activity.SettingsActivity;
import com.qianyi.dailynews.ui.Mine.activity.WriteInvitationActivity;
import com.qianyi.dailynews.ui.WebviewActivity;
import com.qianyi.dailynews.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/30.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View newsView;
    //填写邀请码
    @BindView(R.id.re_WriteCode)
    public RelativeLayout re_WriteCode;
    //赚钱中心
    @BindView(R.id.re_MoneyCenter)
    public RelativeLayout re_MoneyCenter;
    //任务中心
    @BindView(R.id.re_MissionCentre)
    public RelativeLayout re_MissionCentre;
    //账户明细
    @BindView(R.id.re_AccountDetails)
    public RelativeLayout re_AccountDetails;
    //消息中心
    @BindView(R.id.re_MessageCentre)
    public RelativeLayout re_MessageCentre;
    //帮助与反馈
    @BindView(R.id.re_HelpBack)
    public RelativeLayout re_HelpBack;
    //设置
    @BindView(R.id.re_Settings)
    public RelativeLayout re_Settings;
    //提现
    @BindView(R.id.ll_tixian)
    public LinearLayout ll_tixian;
    @BindView(R.id.tv_copy)
    public TextView tv_copy;
    @BindView(R.id.tv_InvitationCode)
    public TextView tv_InvitationCode;

    @Override
    protected View getResLayout(LayoutInflater inflater, ViewGroup container) {
        newsView = inflater.inflate(R.layout.fragment_mine, null);
        return newsView;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.re_WriteCode, R.id.re_MoneyCenter, R.id.re_MissionCentre, R.id.re_AccountDetails,
            R.id.re_MessageCentre, R.id.re_HelpBack, R.id.re_Settings, R.id.ll_tixian, R.id.tv_copy,
    })
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re_WriteCode:
                //填写邀请码
                Intent intent_writecode = new Intent(getActivity(), WriteInvitationActivity.class);
                startActivity(intent_writecode);
                break;
            case R.id.re_MoneyCenter:
                //赚钱中心
                Intent intent_makemoney = new Intent(getActivity(), MakeMoneyCenterActivity.class);
                startActivity(intent_makemoney);
                break;
            case R.id.re_MissionCentre:
                //任务中心

                break;
            case R.id.re_AccountDetails:
                //账户明细
                Intent intent_accountdetails = new Intent(getActivity(), AccountDetailsActivity.class);
                startActivity(intent_accountdetails);
                break;
            case R.id.re_MessageCentre:
                //消息中心
                Toast.makeText(mActivity, "消息中心", Toast.LENGTH_SHORT).show();
                break;
            case R.id.re_HelpBack:
                //帮助与反馈
                Intent intent_helpback = new Intent(getActivity(), WebviewActivity.class);
                intent_helpback.putExtra("title","帮助与反馈");
                intent_helpback.putExtra("url","http://www.baidu.com");
                startActivity(intent_helpback);
                break;
            case R.id.re_Settings:
                //设置
                Intent intent_settings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent_settings);
                break;
            case R.id.ll_tixian:
                //提现
                Toast.makeText(mActivity, "提现", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_copy:
                String code = tv_InvitationCode.getText().toString().trim();
                try {
                    Utils.copy(code, getActivity());
                    Toast.makeText(mActivity, "已复制", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Logger.i(e.getMessage());
                }
                break;
            default:
                break;
        }
    }
}
