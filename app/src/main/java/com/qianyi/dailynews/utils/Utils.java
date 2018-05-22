package com.qianyi.dailynews.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.application.MyApplication;
import com.qianyi.dailynews.ui.account.entity.LoginBean;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.Context.MODE_PRIVATE;

/**
 * ============================================================
 * <p>
 * 版 权 ： 沈阳夜鱼科技有限公司
 * <p>
 * 作 者 ： Ywp
 * <p>
 * 版 本 ： 2.0
 * <p>
 * 创建日期 ：2017.08.07 16:40
 * <p>
 * 描 述 ：
 *      Utils初始化相关
 * 修订历史 ：
 * <p>
 * ============================================================
 **/
public class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static String getVersionName() {
        try {
            return MyApplication
                    .getApplication()
                    .getPackageManager()
                    .getPackageInfo(MyApplication.getApplication().getPackageName(), 0)
                    .versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            return "undefined version name";
        }
    }
    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 获取当前手机IP地址
     * @param context
     * @return
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 检查是否有网络
     * @return
     */
    public static boolean hasInternet() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getApplication()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnected();
    }

    /**
     * 判断网络是不是wifi
     */
    public static boolean isWifi(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info.getType()==ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
    /**
     * 复制到剪切板
     */
    public static void copy(String content, Context context)
    {
    // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }



    /***
     * 登录成功后，将user对象存储在share中
     * @param user
     * @param context
     */
    public static void saveUser(LoginBean.LoginData user, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("User",MODE_PRIVATE);

        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(user);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encodeBase64(baos.toByteArray()));
            Log.i("xxx","save的字符串=="+oAuth_Base64);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("uu", oAuth_Base64);
            Logger.i("uu##@@@@@=="+oAuth_Base64);

            editor.commit();
            Logger.i("user存储成功");
            Log.i("xxx","user存储成功");
        } catch (IOException e) {
            Logger.i("user存储失败");
            Logger.i("存储失败==="+e);
            Log.i("xxx","user存储失败"+e);
        }

    }

    /****
     * 从share中读取User
     * @return
     */

    public static LoginBean.LoginData readUser(Context context) {
        LoginBean.LoginData user = null;
        Log.i("xxx","context@=="+context);
        SharedPreferences preferences =context.getSharedPreferences("User",MODE_PRIVATE);

        String productBase64 = preferences.getString("uu", "");
        Log.i("xxx","productBase64=="+productBase64);

        //读取字节
        byte[] base64 =Base64.decodeBase64(productBase64.getBytes());

        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                user = (LoginBean.LoginData) bis.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("xxx","有读取势必=="+e);
        }
        return user;
    }
    /****
     * 清空sharedPreference中保存的用户信息
     * @param context
     * @return
     */
    public static void  clearSharedUser(Context context){
        SharedPreferences preferences =context.getSharedPreferences("User",MODE_PRIVATE);
        preferences.edit().clear().commit();
        Log.i("clear","789");
    }
    //保存微信的accessToken
  /*  public static boolean saveAccessInfotoLocation(WXAccessTokenInfo tokenInfo, Context context){
        SharedPreferences preferences = context.getSharedPreferences("tokenInfo",MODE_PRIVATE);
        // 创建字节输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            // 创建对象输出流，并封装字节流
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            // 将对象写入字节流
            oos.writeObject(tokenInfo);
            // 将字节流编码成base64的字符窜
            String oAuth_Base64 = new String(Base64.encodeBase64(baos
                    .toByteArray()));
            Log.i("xxx","save的字符串=="+oAuth_Base64);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Info", oAuth_Base64);

            editor.commit();
            Log.i("xxx","tokenInfo存储成功");
            return true;
        } catch (IOException e) {

            Log.i("xxx","tokenInfo存储失败"+e);
            return false;
        }

    }*/

    /****
     * 从share中读取User
     * @return
     */

   /* public static WXAccessTokenInfo readAccessInfotoLocation(Context context) {
        WXAccessTokenInfo tokenInfo = null;
        Log.i("xxx","context@=="+context);
        SharedPreferences preferences =context.getSharedPreferences("tokenInfo",MODE_PRIVATE);

        String productBase64 = preferences.getString("Info", "");
        Log.i("xxx","productBase64=="+productBase64);

        //读取字节
        byte[] base64 = org.apache.commons.codec.binary.Base64.decodeBase64(productBase64.getBytes());

        //封装到字节流
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try {
            //再次封装
            ObjectInputStream bis = new ObjectInputStream(bais);
            try {
                //读取对象
                tokenInfo = (WXAccessTokenInfo) bis.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (StreamCorruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("xxx","有读取势必=="+e);
        }
        return tokenInfo;
    }*/

}
