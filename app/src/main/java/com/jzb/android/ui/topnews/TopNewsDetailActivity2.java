package com.jzb.android.ui.topnews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.eduu.bang.R;
import com.jzb.android.ui.recycler.Cheeses;
import com.jzb.android.ui.recycler.DividerItemDecoration;
import com.jzb.android.ui.recycler.SimpleStringAdapter;
import com.jzb.android.widget.NestedWebView;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class TopNewsDetailActivity2 extends AppCompatActivity {
    protected NestedWebView mWebView;
    protected RecyclerView  mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_news_detail2);
        initView();
        initData();
    }

    protected void initView() {
        mWebView = findViewById(R.id.webView);
        initRecyclerView();
    }

    protected void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new SimpleStringAdapter(this, Cheeses.sCheeseStrings) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ViewHolder vh = super.onCreateViewHolder(parent, viewType);
                vh.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int pos = vh.getAdapterPosition();
                        if (pos == RecyclerView.NO_POSITION) {
                            return;
                        }

                        if (pos + 1 < getItemCount()) {
                            swap(pos, pos + 1);
                        }
                    }
                });
                return vh;
            }
        });
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    private void initData() {
        //        mWebView.loadUrl("http://www.baidu.com");
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("https://api-dev.jzb.com/top/article/8fba852b3c4b60cf04c4e04244433d3703b01a53/?category_source=首页&uid=4131338&uname=叶问来了&bnsource=qq&bottom&uid=4131338&uname=%E5%8F%B6%E9%97%AE%E6%9D%A5%E4%BA%86");
    }
}
