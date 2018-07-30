package com.qianyi.dailynews.views;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

public class ShareCountDownTimer extends CountDownTimer {
	private TextView tv_hour,tv_minute,tv_second;
	public ShareCountDownTimer(long millisInFuture, long countDownInterval, TextView tv_hour,TextView tv_minute,TextView tv_second) {
		super(millisInFuture, countDownInterval);
		this.tv_hour=tv_hour;
		this.tv_minute=tv_minute;
		this.tv_second=tv_second;
		addCode(millisInFuture);
	}
	@Override
	public void onTick(long time) {
		/*button.setEnabled(false);
		button.setText(time / 1000 + "");
		button.setTextSize(14);
		Spannable spannable = new SpannableString(button.getText().toString());
		ForegroundColorSpan textColor = new ForegroundColorSpan(Color.parseColor("#ff0000"));
		if (time / 1000 >= 10) {
			spannable.setSpan(textColor, 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		} else {
			spannable.setSpan(textColor, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

		}
		button.setText(spannable);*/
	}
	private void addCode(long time) {
		if(time<=0){
			return;
		}
		time--;
		String formatLongToTimeStr = formatLongToTimeStr(time);
		String[] split = formatLongToTimeStr.split("：");
		for (int i = 0; i < split.length; i++) {
			if(i==0){
				tv_hour.setText(split[0]+"小时");
			}
			if(i==1){
				tv_minute.setText(split[1]+"分钟");
			}
			if(i==2){
				tv_second.setText(split[2]+"秒");
			}
		}
	}

	public  String formatLongToTimeStr(Long l) {
		int hour = 0;
		int minute = 0;
		int second = 0;
		second = l.intValue() ;
		if (second > 60) {
			minute = second / 60;         //取整
			second = second % 60;         //取余
		}

		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		}
		String strtime = hour+"："+minute+"："+second;
		return strtime;

	}
	@Override
	public void onFinish() {
	/*	button.setText("获取验证码");
		button.setEnabled(true);
		button.setTextSize(14);*/
	}

}
