package com.qianyi.dailynews.ui.news.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.qianyi.dailynews.R;
import com.qianyi.dailynews.adapter.NewsAdapter;
import com.qianyi.dailynews.api.ApiAccount;
import com.qianyi.dailynews.api.ApiConstant;
import com.qianyi.dailynews.api.ApiNews;
import com.qianyi.dailynews.application.MyApplication;
import com.qianyi.dailynews.callback.RequestCallBack;
import com.qianyi.dailynews.dialog.CustomLoadingDialog;
import com.qianyi.dailynews.fragment.NewsFragment;
import com.qianyi.dailynews.ui.Mine.activity.AccountDetailsActivity;
import com.qianyi.dailynews.ui.news.activity.NewsDetailsActivity;
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

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class PageFragment extends LazyloadFragment implements PullToRefreshView.OnHeaderRefreshListener, PullToRefreshView.OnFooterRefreshListener{

    public PullToRefreshView mPullToRefreshView;
    public ListView listview;
    public ImageView iv_newPeople_del;
    public RelativeLayout re_newPeople;
    //private List<NewsTitleBean.NewsTitleData.NewsTypeRes> newsTypeRes;
    public ImageView iv_newPeople_details;
    private NewsAdapter newsAdapter;
    private int page = 1;
    private List<NewsBean> bigList = new ArrayList<>();
    private CustomLoadingDialog customLoadingDialog;


    public PageFragment() {
        super();
    }

    @Override
    public void onStart() {
          super.onStart();
//        firstData(NewsFragment.CurrentNewsTitle);
        //  Toast.makeText(mActivity, "****************************PageFragment********onStart************************", Toast.LENGTH_SHORT).show();
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
        iv_newPeople_del = rootView.findViewById(R.id.iv_newPeople_del);
        iv_newPeople_details = rootView.findViewById(R.id.iv_newPeople_details);
        re_newPeople=rootView.findViewById(R.id.re_newPeople);

        newsAdapter = new NewsAdapter(getActivity(), bigList, "1");
        listview.setAdapter(newsAdapter);


        customLoadingDialog = new CustomLoadingDialog(getActivity());

        mPullToRefreshView.setmOnHeaderRefreshListener(this);
        mPullToRefreshView.setmOnFooterRefreshListener(this);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                intent.putExtra("title", bigList.get(i).getTitle());
                intent.putExtra("url", bigList.get(i).getUrl());
                intent.putExtra("des", bigList.get(i).getContent());
                intent.putExtra("id", bigList.get(i).getId());
                intent.putExtra("redMoney", bigList.get(i).getRedMoney());
                intent.putExtra("ifread", bigList.get(i).getIfRead());
                intent.putExtra("isRed",bigList.get(i).getRedpackage());

                getActivity().startActivity(intent);
            }
        });
        /***
         * 删除某条新闻
         */
//        newsAdapter.setDeleteNewsListener(new NewsAdapter.DeleteNewsListener() {
//            @Override
//            public void deleteNews(final String id, View v) {
//                final Dialog dialog = new Dialog(getActivity());
//                View vv = LayoutInflater.from(getActivity()).inflate(R.layout.delete_pop_window, null);
//                dialog.setContentView(vv);
//                vv.findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleteNewsOne(id);
//                        dialog.dismiss();
//                    }
//                });
//                vv.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleteNewsOne(id);
//                        dialog.dismiss();
//                    }
//                });
//                vv.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleteNewsOne(id);
//                        dialog.dismiss();
//                    }
//                });
//                vv.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        deleteNewsOne(id);
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.show();
//            }
//        });

    }

    @Override
    public void onResume() {
        super.onResume();
        firstData(NewsFragment.CurrentNewsTitle);
    }


    /***
     * 删除该条新闻
     * @param id
     */
    private void deleteNewsOne(String id) {

        for (int i = 0; i < bigList.size(); i++) {
            if (bigList.get(i).getId().equals(id)) {
                bigList.remove(i);
                newsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void lazyLoad() {
        firstData(NewsFragment.CurrentNewsTitle);
    }

    @Override
    public void
    onFooterRefresh(PullToRefreshView view) {
        moreData(NewsFragment.CurrentNewsTitle);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        firstData(NewsFragment.CurrentNewsTitle);
    }

    private void firstData(final int position) {
        mPullToRefreshView.setEnablePullTorefresh(true);
        //  customLoadingDialog.show();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String userid = (String) SPUtils.get(MyApplication.getApplication(), "user_id", "");
//                        Log.i("tag", "url" + ApiConstant.NEWS_CONTENTS);
//                        Log.i("tag", userid + "==userid==");
//                        Log.i("tag", MyApplication.newsTypeRes.get(position).getCatId() + "==getCatId==");
//                        Log.i("tag", page + "==page==");
//                        Log.i("tag", userid + "==userid==");
//                        Logger.i("url" + ApiConstant.NEWS_CONTENTS);
//                        Logger.i(userid + "==userid==");
//                        Logger.i(MyApplication.newsTypeRes.get(position).getCatId() + "==getCatId==");
//                        Logger.i(page + "==page==");
//                        Logger.i(userid + "==userid==");

                        ApiNews.GetNewsContent(ApiConstant.NEWS_CONTENTS, userid, MyApplication.newsTypeRes.get(NewsFragment.CurrentNewsTitle).getCatId(), page, 12, page, 3, new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(Call call, Response response, String s) {
                                customLoadingDialog.dismiss();
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
                                                List<NewsContentBean.NewsContentData.AdavertContent> adavertContents = contentData.getAdvertArray();
                                                List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos = newsByTypes.get(0).getNewsInfoArray();
                                                if (adavertContents.size() > 0 || newsContentInfos.size() > 0) {
                                                    List<NewsBean> newsBeans = dowithNews(adavertContents, newsContentInfos);
                                                    bigList.clear();
                                                    bigList.addAll(newsBeans);
                                                    // newsAdapter.notifyDataSetChanged();
                                                    newsAdapter = new NewsAdapter(getActivity(), bigList, "1");
                                                    listview.setAdapter(newsAdapter);
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onEror(Call call, int statusCode, Exception e) {
                                customLoadingDialog.dismiss();
                                Log.i("ttt", "e" + e.getMessage());
                            }
                        });
                        mPullToRefreshView.onHeaderRefreshComplete();
                    }
                });
            }
        }, 250);
        //请求成功
       /* if (list.size() < Integer.parseInt(Constants.PAGE_SIZE_STR)) {
            mPullToRefreshView.onFooterRefreshComplete(true);
        }else{
            mPullToRefreshView.onFooterRefreshComplete(false);
        }*/

    }

    private void moreData(final int position) {
        page++;
        mPullToRefreshView.setEnablePullTorefresh(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                String userid = (String) SPUtils.get(getActivity(), "user_id", "");

                                Log.i("tag", "url" + ApiConstant.NEWS_CONTENTS);
                                Log.i("tag", userid + "==userid==");
                                Log.i("tag", MyApplication.newsTypeRes.get(position).getCatId() + "==getCatId==");
                                Log.i("tag", page + "==page==");
                                Log.i("tag", userid + "==userid==");
                                ApiNews.GetNewsContent(ApiConstant.NEWS_CONTENTS, userid, MyApplication.newsTypeRes.get(NewsFragment.CurrentNewsTitle).getCatId(), page, 12, page, 3, new RequestCallBack<String>() {
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
                                                        List<NewsContentBean.NewsContentData.AdavertContent> adavertContents = contentData.getAdvertArray();
                                                        List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos = newsByTypes.get(0).getNewsInfoArray();

                                                        if (adavertContents.size() > 0 || newsContentInfos.size() > 0) {
                                                            List<NewsBean> newsBeans = dowithNews(adavertContents, newsContentInfos);
                                                            bigList.addAll(newsBeans);
                                                            newsAdapter.notifyDataSetChanged();
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


                        mPullToRefreshView.onHeaderRefreshComplete();
                        mPullToRefreshView.onFooterRefreshComplete(false);
                    }
                });
            }
        }, 250);
    }




    /*private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.delete_pop_window, null);
        // 设置按钮的点击事件
        Button button = contentView.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "button is pressed",
                        Toast.LENGTH_SHORT).show();
            }
        });

        final PopupWindow popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);

        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.selectmenu_bg_downward));

        // 设置好参数之后再show
        popupWindow.showAsDropDown(view,10,10,Gravity.RIGHT);



    }*/


    /**
     * 将新闻和广告按11排列
     *
     * @param adavertContents
     * @param newsContentInfos
     * @return
     */
    private List<NewsBean> dowithNews(List<NewsContentBean.NewsContentData.AdavertContent> adavertContents, List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        List<NewsBean> newsBeansAd = DoWithNewsForAD(adavertContents);
        List<NewsBean> newsBeansNews = DoWithNewsForNEWS(newsContentInfos);
        newsBeanList = dowithNews2(newsBeansAd, newsBeansNews);
        return newsBeanList;
    }

    /***
     *
     * @param newsBeansAd
     * @param newsBeansNews
     * @return
     */
    private List<NewsBean> dowithNews2(List<NewsBean> newsBeansAd, List<NewsBean> newsBeansNews) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        int size = newsBeansAd.size() + newsBeansNews.size();

        for (int i = 0; i < size; i++) {
            if (((i + 1) % 5) == 0) {
                if (newsBeansAd.size() > 0) {
                    newsBeanList.add(newsBeansAd.get(0));
                    newsBeansAd.remove(0);
                }
            } else {
                if (newsBeansNews.size() > 0) {
                    newsBeanList.add(newsBeansNews.get(0));
                    newsBeansNews.remove(0);
                } else {
                    return newsBeanList;
                }
            }
        }
        return newsBeanList;
    }

    /**
     * 处理新闻
     *
     * @param newsContentInfos
     * @return
     */
    private List<NewsBean> DoWithNewsForNEWS(List<NewsContentBean.NewsContentData.NewsByType.NewsContentInfo> newsContentInfos) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        for (int i = 0; i < newsContentInfos.size(); i++) {
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
            newsBean.setRedpackage(news.getRedpackage());
            newsBean.setRedMoney(news.getRedMoney());
            newsBeanList.add(newsBean);
        }
        return newsBeanList;
    }

    /**
     * 处理广告
     *
     * @param adavertContents
     * @return
     */
    private List<NewsBean> DoWithNewsForAD(List<NewsContentBean.NewsContentData.AdavertContent> adavertContents) {
        List<NewsBean> newsBeanList = new ArrayList<>();
        for (int i = 0; i < adavertContents.size(); i++) {
            NewsBean newsBean = new NewsBean();
            NewsContentBean.NewsContentData.AdavertContent ad = adavertContents.get(i);
            newsBean.setId(ad.getId());
            newsBean.setTitle(ad.getTitle());
            newsBean.setUrl(ad.getUrl());
            newsBean.setReadNum(ad.getReadNum());
            newsBean.setImgs(ad.getImgs());
            newsBean.setAdType(ad.getAdType());
            newsBeanList.add(newsBean);
        }
        return newsBeanList;
    }



}
