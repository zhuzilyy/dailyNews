package com.qianyi.dailynews.views;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

public class MyCountDownTimer extends CountDownTimer {
	private Button button;
	public MyCountDownTimer(long millisInFuture, long countDownInterval, Button button) {
		super(millisInFuture, countDownInterval);
		this.button=button;
	}
	@Override
	public void onTick(long time) {
		button.setEnabled(false);
		button.setText(time/1000+"");
		button.setTextSize(14);
		Spannable spannable=new SpannableString(button.getText().toString());
		ForegroundColorSpan textColor=new ForegroundColorSpan(Color.parseColor("#ff0000"));
		if (time/1000>=10) {
			spannable.setSpan(textColor, 0, 2,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		}else{
			spannable.setSpan(textColor, 0, 1,Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

		}
		button.setText(spannable);
	}
	@Override
	public void onFinish() {
		button.setText("获取验证码");
		button.setEnabled(true);
		button.setTextSize(14);
	}

}
