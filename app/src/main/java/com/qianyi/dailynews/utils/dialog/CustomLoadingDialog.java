package com.qianyi.dailynews.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.qianyi.dailynews.R;


/**
 * ============================================================
 * <p>
 * 版 权 ： 沈阳夜鱼科技有限公司
 * <p>
 * 作 者 ： Ywp
 * <p>
 * 版 本 ： 2.0
 * <p>
 * 创建日期 ：2017.07.11 15:17
 * <p>
 * 描 述 ：
 *      公共加载框
 *
 * 修订历史 ：
 * <p>
 * ============================================================
 **/
public class CustomLoadingDialog extends Dialog {

    private ImageView mImageView;
    private AnimationDrawable mAnimation;

    public CustomLoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
        /** 设置触摸外面的取消为false */
        setCanceledOnTouchOutside(false);
        setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_custom_loading_dialog);
        mImageView = (ImageView) findViewById(R.id.loadingIv);
        mAnimation = (AnimationDrawable) mImageView.getBackground();
        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
    }


}
