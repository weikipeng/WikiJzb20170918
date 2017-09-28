package com.jzb.android.ui.topnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.eduu.bang.R;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class TopNewsDetailActivity extends AppCompatActivity {
    protected WebView      mWebView;
    protected RecyclerView mRecyclerView;
    /**
     * 下拉刷新视图
     */
    protected View         mRefreshView;
    /**
     * 上拉加载更多视图
     */
    protected View         mLoadMoreView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news_detail);
        initView();
    }

    protected void initView() {
        mWebView = findViewById(R.id.webView);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshView = findViewById(R.id.refreshView);
        mLoadMoreView = findViewById(R.id.loadMoreView);

    }
}