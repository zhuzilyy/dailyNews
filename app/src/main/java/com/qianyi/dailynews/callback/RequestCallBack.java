package com.qianyi.dailynews.callback;




import com.qianyi.dailynews.base.BaseCallBack;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by NEUNB on 2018/3/5.
 */

public abstract class RequestCallBack<T> extends BaseCallBack<T> {
    @Override
    public void OnRequestBefore(Request request) {

    }
    @Override
    public void onFailure(Call call, IOException e) {

    }
    @Override
    public void onResponse(Response response) {

    }
    @Override
    public void inProgress(int progress, long total, int id) {

    }
}
