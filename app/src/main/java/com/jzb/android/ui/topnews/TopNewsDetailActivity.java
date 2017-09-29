package com.jzb.android.ui.topnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;

import com.eduu.bang.R;
import com.jzb.android.widget.NestedWebView;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class TopNewsDetailActivity extends AppCompatActivity {
    protected NestedWebView mWebView;
    protected RecyclerView  mRecyclerView;
    /**
     * 下拉刷新视图
     */
    protected View          mRefreshView;
    /**
     * 上拉加载更多视图
     */
    protected View          mLoadMoreView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news_detail);
        initView();
        initData();
    }

    protected void initView() {
        mWebView = findViewById(R.id.webView);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshView = findViewById(R.id.refreshView);
//        mLoadMoreView = findViewById(R.id.loadMoreView);
    }

    private void initData() {
//        mWebView.loadUrl("http://www.baidu.com");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://api-dev.jzb.com/top/article/8fba852b3c4b60cf04c4e04244433d3703b01a53/?category_source=首页&uid=4131338&uname=叶问来了&bnsource=qq&bottom&uid=4131338&uname=%E5%8F%B6%E9%97%AE%E6%9D%A5%E4%BA%86");
    }

}
