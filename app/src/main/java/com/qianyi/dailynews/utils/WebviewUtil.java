package com.qianyi.dailynews.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewUtil {
	public static void setWebview(final WebView webView, WebSettings mWebSettings){
		 mWebSettings = webView.getSettings();
	        mWebSettings.setJavaScriptEnabled(true);
	        mWebSettings.setBuiltInZoomControls(true);
	        mWebSettings.setLightTouchEnabled(true);
	        mWebSettings.setSupportZoom(false);
	        webView.setHapticFeedbackEnabled(false);

	        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

	        webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,
					String url) {
				//view.loadUrl(url);
				return false;
			}
				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);

				}
			});
	        webView.setOnKeyListener(new OnKeyListener() {
				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (keyCode==KeyEvent.KEYCODE_BACK) {
						if(webView.canGoBack()){
							webView.goBack();
				            return true;
						}
					}
					return false;
				}
			});
	}

}
