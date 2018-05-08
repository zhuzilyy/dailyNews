package com.qianyi.dailynews.ui.video.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Nathen on 2016/11/6.
 */

public class JCVideoPlayerStandardShowTextureViewAfterAutoComplete extends JCVideoPlayerStandard {
    public JCVideoPlayerStandardShowTextureViewAfterAutoComplete(Context context) {
        super(context);
    }

    public JCVideoPlayerStandardShowTextureViewAfterAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        thumbImageView.setVisibility(View.GONE);
    }

    @Override
    public void onClickUiToggle() {
        super.onClickUiToggle();
        if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
            thumbImageView.setVisibility(View.GONE);
        }
    }
}
