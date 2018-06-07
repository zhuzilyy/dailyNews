package com.qianyi.dailynews.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Administrator on 2018/6/6.
 */

public class WhiteBgBitmapUtil {
    public static Bitmap drawableBitmapOnWhiteBg(Context context, Bitmap bitmap){
        Bitmap newBitmap = Bitmap.createBitmap(120, 120, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(context.getResources().getColor(android.R.color.white));
        Paint paint=new Paint();
        canvas.drawBitmap(bitmap, 0, 0, paint); //将原图使用给定的画笔画到画布上
        return newBitmap;
    }
}
