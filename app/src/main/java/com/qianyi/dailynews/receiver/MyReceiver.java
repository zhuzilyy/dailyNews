package com.qianyi.dailynews.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.qianyi.dailynews.MainActivity;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.ui.news.activity.NewsDetailsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";
	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		Log.i("tag", "00"+bundle.getString(JPushInterface.EXTRA_EXTRA));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.i("tag", "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...
            Log.i("tag", "11[MyReceiver] 接收到推送下来的通知的ID: " +"ACTION_REGISTRATION_ID");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.i("tag", "22[MyReceiver] 接收到推送下来的通知的ID: " +"ACTION_MESSAGE_RECEIVED");
			//监听收到的消息
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.i("tag", "33[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);
			String jsonNotification = bundle.getString(JPushInterface.EXTRA_EXTRA);
			String bodyNotification="";
			String titleNotification="";
			String newsIdNotification="";
			String createTimeNotification="";
			String typeNotification="";
			String urlNotification="";
			JSONObject jsonObjectNotification=null;
			JSONObject extraNotification=null;
			try {
				if (TextUtils.isEmpty(jsonNotification)){
					return;
				}
				jsonObjectNotification=new JSONObject(jsonNotification);
				extraNotification=jsonObjectNotification.getJSONObject("extra");
				typeNotification=extraNotification.getString("type");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (typeNotification.equals("1")){
				try {
					bodyNotification=extraNotification.getString("body");
					titleNotification=extraNotification.getString("title");
					newsIdNotification=extraNotification.getString("newsId");
					createTimeNotification=extraNotification.getString("createTime");
					urlNotification=extraNotification.getString("url");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			//showCustomerNotification(context,bodyNotification,titleNotification,newsIdNotification,urlNotification);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了");
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String body="";
            String title="";
            String newsId="";
            String createTime="";
            String type="";
            String url="";
			JSONObject jsonObject=null;
			JSONObject extra=null;
            try {
				jsonObject=new JSONObject(json);
				extra=jsonObject.getJSONObject("extra");
				type=extra.getString("type");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //打开自定义的Activity*/
			if (type.equals("1")){
				try {
					body=extra.getString("body");
					title=extra.getString("title");
					newsId=extra.getString("newsId");
					createTime=extra.getString("createTime");
					url=extra.getString("url");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				//显示通知栏
				Intent i = new Intent(context, NewsDetailsActivity.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra("id", newsId);
				i.putExtra("url", url);
				i.putExtra("des", title);
				i.putExtra("redMoney", "0");
				i.putExtra("isRed", "1");
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);
			}else{
				Intent i = new Intent(context, MainActivity.class);
				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
				context.startActivity(i);
			}
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

	private void showCustomerNotification(Context context,String body,String title,String newsId,String url) {
		Intent[] intents = new Intent[2];
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		String message = body;
		//String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
		// 使用notification
		// 使用广播或者通知进行内容的显示
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);
		builder.setContentText(message).setSmallIcon(R.mipmap.logo).setContentTitle(title);
		builder.setDefaults(Notification.DEFAULT_SOUND);
		builder.setAutoCancel(true);
		//manager.notify(1,builder.build());

		//显示通知栏
		Intent intentNewsDetail = new Intent(context, NewsDetailsActivity.class);
		//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intentNewsDetail.putExtra("id", newsId);
		intentNewsDetail.putExtra("url", url);
		intentNewsDetail.putExtra("des", title);
		intentNewsDetail.putExtra("redMoney", "0");
		intentNewsDetail.putExtra("isRed", "1");
		//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
		//context.startActivity(i);
		/*intents[0] = Intent.makeRestartActivityTask(new ComponentName(context, MainActivity.class));
		intents[1] = Intent.makeRestartActivityTask(new ComponentName(context, NewsDetailsActivity.class));
		intents[1].putExtra("id", newsId);
		intents[1].putExtra("url", url);
		intents[1].putExtra("des", title);
		intents[1].putExtra("redMoney", "0");
		intents[1].putExtra("isRed", "1");*/
		int notifyId = (int) System.currentTimeMillis();
		PendingIntent resultPendingIntent = PendingIntent.getActivity( context, 0, intentNewsDetail, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(resultPendingIntent);
		manager.notify(notifyId,builder.build());



		/*CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
				context, R.layout.customer_notitfication_layout,
				R.id.icon, R.id.title, R.id.text);
		// 指定定制的 Notification Layout
		builder.statusBarDrawable = R.drawable.notification_icon;
		// 指定最顶层状态栏小图标
		builder.layoutIconDrawable = R.drawable.notification_icon;
		builder.
		// 指定下拉状态栏时显示的通知图标
		JPushInterface.setPushNotificationBuilder(1, builder1);*/
	}

	// 打印�?���?intent extra 数据
		@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
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
