package com.qianyi.dailynews.ui.news.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.fragment.NewsFragment;
import com.qianyi.dailynews.ui.Mine.activity.AccountDetailsActivity;
import com.qianyi.dailynews.ui.news.bean.NewsBean;
import com.qianyi.dailynews.ui.news.bean.NewsContentBean;
import com.qianyi.dailynews.ui.news.bean.NewsTitleBean;
import com.qianyi.dailynews.utils.SPUtils;
import com.qianyi.dailynews.views.PullToRefreshView;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Response;

public class PageFragment extends LazyloadFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener {

    public PullToRefreshView mPullToRefreshView;
    public ListView listview;
    private NewsAdapter newsAdapter;
    private List<NewsTitleBean.NewsTitleData.NewsTypeRes> newsTypeRes;

    private int page = 1;


    public PageFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public PageFragment(List<NewsTitleBean.NewsTitleData.NewsTypeRes> newsTypeRes) {
        this.newsTypeRes = newsTypeRes;

    }

    @Override
    public int setContentView() {
        return R.layout.fragment_page;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void init() {
        listview = rootView.findViewById(R.id.listview);
        mPullToRefreshView = rootView.findViewById(R.id.pulltorefreshView);
        newsAdapter = new NewsAdapter(getActivity());
        listview.setAdapter(newsAdapter);

        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);

    }

    @Override
    public void lazyLoad() {
        firstData(NewsFragment.CurrentNewsTitle);
        Toast.makeText(mActivity, "" + NewsFragment.CurrentNewsTitle, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        moreData();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        firstData(NewsFragment.CurrentNewsTitle);
    }

    private void firstData(final int position) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String userid = (String) SPUtils.get(getActivity(), "user_id", "");
                        if (TextUtils.isEmpty(userid)) {
                            return;
                        }
                        Log.i("tag", "url" + ApiConstant.NEWS_CONTENTS);
                        Log.i("tag", userid + "==userid==");
                        Log.i("tag", newsTypeRes.get(position).getCatId() + "==getCatId==");
                        Log.i("tag", page + "==page==");
                        Log.i("tag", userid + "==userid==");
                        ApiNews.GetNewsContent(ApiConstant.NEWS_CONTENTS, userid, "news_house", page, 10, 1, 10, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(Call call, Response response, String s) {
                                Log.i("ttt", "s" + s);
                                Gson gson = new Gson();
                                NewsContentBean contentBean = gson.fromJson(s, NewsContentBean.class);
                                if (contentBean != null) {
                                    String code = contentBean.getCode();
                                    if ("0000".equals(code)) {
                                        NewsContentBean.NewsContentData contentData = contentBean.getData();
                                        if (contentData != null) {
                                            List<NewsContentBean.NewsContentData.NewsByType> newsByTypes = contentData.getNewsByType();
                                            if (newsByTypes.size() > 0) {
                                                List<NewsContentBean.NewsContentData.NewsByType.AdavertContent> adavertContents = newsByTypes.get(0).getAdvertArray();
                                                List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos = newsByTypes.get(0).getNewsInfoArray();

                                                if (adavertContents.size() > 0 || newsContentInfos.size() > 0) {
                                                    List<NewsBean> newsBeans = dowithNews(adavertContents, newsContentInfos);
                                                    Log.i("sss",newsBeans.size()+"");
                                                }


                                            }


                                        }
                                    }
                                }


                            }

                            @Override
                            public void onEror(Call call, int statusCode, Exception e) {
                                Log.i("ttt", "e" + e.getMessage());
                            }
                        });
                        mPullToRefreshView.onHeaderRefreshComplete();
                    }
                });
            }
        }, 2000);
        //请求成功
       /* if (list.size() < Integer.parseInt(Constants.PAGE_SIZE_STR)) {
            mPullToRefreshView.onFooterRefreshComplete(true);
        }else{
            mPullToRefreshView.onFooterRefreshComplete(false);
        }*/

    }

    /**
     * 将新闻和广告按11排列
     *
     * @param adavertContents
     * @param newsContentInfos
     * @return
     */
    private List<NewsBean> dowithNews(List<NewsContentBean.NewsContentData.NewsByType.AdavertContent> adavertContents, List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        for (int i = 0; i < (adavertContents.size() + newsContentInfos.size()); i++) {
            if (newsContentInfos.size() > 0) {
                NewsBean newsBean = new NewsBean();
                NewsContentBean.NewsContentData.NewsByType.NewsContentInfo news = newsContentInfos.get(i);
                newsBean.setId(news.getId());
                newsBean.setPublishDate(news.getPublishDate());
                newsBean.setPosterScreenName(news.getPosterScreenName());
                newsBean.setUrl(news.getUrl());
                newsBean.setTitle(news.getTitle());
                newsBean.setPosterId(news.getPosterId());
                newsBean.setViewCount(news.getViewCount());
                newsBean.setContent(news.getContent());
                newsBean.setImgsUrl(news.getImgsUrl());
                newsBean.setIfRead(news.getIfRead());
                newsBean.setNewsType(news.getNewsTyps());
                newsBeanList.add(newsBean);
                continue;
            } else if (adavertContents.size() > 0) {
                NewsBean newsBean = new NewsBean();
                NewsContentBean.NewsContentData.NewsByType.AdavertContent ad = adavertContents.get(i);
                newsBean.setId(ad.getId());
                newsBean.setTitle(ad.getTitle());
                newsBean.setUrl(ad.getUrl());
                newsBean.setReadNum(ad.getReadNum());
                newsBean.setImgs(ad.getImgs());
                newsBean.setAdType(ad.getAdType());
                newsBeanList.add(newsBean);
                continue;
            }
        }
        return newsBeanList;
    }
    private void moreData() {
        mPullToRefreshView.setEnablePullTorefresh(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshView.onHeaderRefreshComplete();
                        mPullToRefreshView.onFooterRefreshComplete(false);
                    }
                });
            }
        }, 2000);
    }
}
