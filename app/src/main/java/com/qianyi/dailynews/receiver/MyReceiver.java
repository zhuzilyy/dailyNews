package com.qianyi.dailynews.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.qianyi.dailynews.ui.news.activity.NewsDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		Log.i("tag", "00"+bundle.getString(JPushInterface.EXTRA_ALERT));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
            Log.i("tag", "11[MyReceiver] 接收到推送下来的通知的ID: " +"ACTION_REGISTRATION_ID");
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.i("tag", "22[MyReceiver] 接收到推送下来的通知的ID: " +"ACTION_MESSAGE_RECEIVED");
			//监听收到的消息
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.i("tag", "33[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了");
            String json = bundle.getString(JPushInterface.EXTRA_ALERT);
            String body="";
            String title="";
            String newsId="";
            String createTime="";
            try {
                JSONObject jsonObject=new JSONObject(json);
                body=jsonObject.getString("body");
                title=jsonObject.getString("title");
                newsId=jsonObject.getString("newsId");
                createTime=jsonObject.getString("createTime");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //打开自定义的Activity*/
        	Intent i = new Intent(context, NewsDetailsActivity.class);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.putExtra("id", body+"title="+title+"newsId="+newsId+"createTime="+createTime);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根�?JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity�?打开�?��网页�?.
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

	}
	// 打印�?���?intent extra 数据
		private static String printBundle(Bundle bundle) {
			StringBuilder sb = new StringBuilder();
			for (String key : bundle.keySet()) {
				if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
					sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
				}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
					sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
				} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
					if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
						Log.i(TAG, "This message has no Extra data");
						continue;
					}

					try {
						JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
						Iterator<String> it =  json.keys();

						while (it.hasNext()) {
							String myKey = it.next().toString();
							sb.append("\nkey:" + key + ", value: [" +
									myKey + " - " +json.optString(myKey) + "]");
						}
					} catch (JSONException e) {
						Log.e(TAG, "Get message extra JSON error!");
					}

				} else {
					sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
				}
			}
			return sb.toString();
		}
	

}
