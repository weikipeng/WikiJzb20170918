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
        mRefreshView = findViewById(R.id.refreshView);
        //        mLoadMoreView = findViewById(R.id.loadMoreView);
        initRecyclerView();
    }

    protected void initRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        //        mRecyclerView.setLayoutManager(new MyLayoutManager(this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
//        mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
        mRecyclerView.setAdapter(new SimpleStringAdapter(this, Cheeses.sCheeseStrings) {
            @Override
            public SimpleStringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    //    /**
    //     * A basic ListView-style LayoutManager.
    //     */
    //    class MyLayoutManager extends RecyclerView.LayoutManager {
    //        private static final String TAG = "MyLayoutManager";
    //        private       int mFirstPosition;
    //        private final int mScrollDistance;
    //
    //        public MyLayoutManager(Context c) {
    //            final DisplayMetrics dm = c.getResources().getDisplayMetrics();
    //            mScrollDistance = (int) (SCROLL_DISTANCE * dm.density + 0.5f);
    //        }
    //
    //        @Override
    //        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
    //            //            DevLogTool.getInstance(BangApplication.getInstance()).saveLog("Printing stack trace:");
    //            //            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    //            //            for (int i = 1; i < elements.length; i++) {
    //            //                StackTraceElement s = elements[i];
    //            //                DevLogTool.getInstance(BangApplication.getInstance()).saveLog("\tat " + s.getClassName() + "." + s.getMethodName()
    //            //                        + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
    //            //            }
    //
    //            final int  parentBottom = getHeight() - getPaddingBottom();
    //            final View oldTopView   = getChildCount() > 0 ? getChildAt(0) : null;
    //            int        oldTop       = getPaddingTop();
    //
    //            DevLogTool.getInstance(BangApplication.getInstance()).saveLog("getChildCount():" + getChildCount()
    //                    + "      getHeight():" + getHeight()
    //                    + "     getPaddingBottom():" + getPaddingBottom()
    //                    + "  oldTopView:" + oldTopView
    //                    + "  oldTop:" + oldTop
    //            );
    //
    //            if (oldTopView != null) {
    //                oldTop = oldTopView.getTop();
    //                DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                        " oldTopView!=null oldTop=" + oldTop
    //                );
    //            }
    //
    //            detachAndScrapAttachedViews(recycler);
    //
    //            int       top   = oldTop;
    //            int       bottom;
    //            final int left  = getPaddingLeft();
    //            final int right = getWidth() - getPaddingRight();
    //            final int count = state.getItemCount();
    //
    //            DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                    " start >>>>>>top:" + top
    //                            + " bottom:" + 0
    //                            + " left:" + left
    //                            + " right:" + right
    //                            + " count:" + count
    //                            + " mFirstPosition:" + mFirstPosition
    //            );
    //
    //            for (int i = 0; mFirstPosition + i < count && top < parentBottom; i++, top = bottom) {
    //                View v = recycler.getViewForPosition(mFirstPosition + i);
    //                addView(v, i);
    //                measureChildWithMargins(v, 0, 0);
    //                bottom = top + getDecoratedMeasuredHeight(v);
    //
    //                DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                        " addView top:" + top
    //                                + " bottom:" + bottom
    //                                + " left:" + left
    //                                + " right:" + right
    //                                + " count:" + count
    //                                + " mFirstPosition:" + mFirstPosition
    //                                + " i:" + i
    //                );
    //                layoutDecorated(v, left, top, right, bottom);
    //            }
    //        }
    //
    //        @Override
    //        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
    //            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
    //                    ViewGroup.LayoutParams.WRAP_CONTENT);
    //        }
    //
    //        @Override
    //        public boolean canScrollVertically() {
    //            return true;
    //        }
    //
    //        @Override
    //        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler,
    //                                      RecyclerView.State state) {
    //            DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                    " scrollVerticallyBy start >>>>>>dy:" + dy
    //            );
    //
    //            if (getChildCount() == 0) {
    //                return 0;
    //            }
    //            int       scrolled = 0;
    //            final int left     = getPaddingLeft();
    //            final int right    = getWidth() - getPaddingRight();
    //            if (dy < 0) {
    //                while (scrolled > dy) {
    //                    final View topView    = getChildAt(0);
    //                    final int  hangingTop = Math.max(-getDecoratedTop(topView), 0);
    //                    final int  scrollBy   = Math.min(scrolled - dy, hangingTop);
    //
    //                    DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                            " scrollVerticallyBy topView:" + topView
    //                                    + " -getDecoratedTop(topView):" + (-getDecoratedTop(topView))
    //                                    + " hangingTop:" + hangingTop
    //                                    + " scrolled - dy:" + (scrolled - dy)
    //                                    + " scrollBy:" + scrollBy
    //                                    + " scrolled------:" + scrolled
    //                    );
    //
    //                    scrolled -= scrollBy;
    //                    offsetChildrenVertical(scrollBy);
    //                    DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                            " offsetChildrenVertical  scrollBy:" + scrollBy
    //                                    + " scrolled:" + scrolled
    //                                    + " dy:" + dy
    //                                    + " mFirstPosition:" + mFirstPosition
    //                    );
    //
    //                    if (mFirstPosition > 0 && scrolled > dy) {
    //                        mFirstPosition--;
    //                        View v = recycler.getViewForPosition(mFirstPosition);
    //                        addView(v, 0);
    //                        measureChildWithMargins(v, 0, 0);
    //                        final int bottom = getDecoratedTop(topView);
    //                        final int top    = bottom - getDecoratedMeasuredHeight(v);
    //                        layoutDecorated(v, left, top, right, bottom);
    //                        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                                " offsetChildrenVertical  mFirstPosition--:"
    //                                        + " top:" + top
    //                                        + " bottom:" + bottom
    //                        );
    //                    } else {
    //                        break;
    //                    }
    //                }
    //            } else if (dy > 0) {
    //                final int parentHeight = getHeight();
    //                DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                        " dy > 0 parentHeight:" + parentHeight
    //                );
    //                while (scrolled < dy) {
    //                    final View bottomView = getChildAt(getChildCount() - 1);
    //                    final int hangingBottom =
    //                            Math.max(getDecoratedBottom(bottomView) - parentHeight, 0);
    //                    final int scrollBy = -Math.min(dy - scrolled, hangingBottom);
    //
    //                    DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                            " 向上滑动 getDecoratedBottom(bottomView):" + getDecoratedBottom(bottomView)
    //                                    + " hangingBottom：" + hangingBottom
    //                                    + " dy：" + dy
    //                                    + " scrolled：" + scrolled
    //                                    + " dy - scrolled：" + (dy - scrolled)
    //                                    + " scrollBy：" + scrollBy
    //                    );
    //
    //
    //                    scrolled -= scrollBy;
    //
    //                    offsetChildrenVertical(scrollBy);
    //
    //                    DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                            " 向上滑动 offsetChildrenVertical(scrollBy):" + scrollBy
    //                                    + " scrolled -= scrollBy：" + scrolled
    //                    );
    //
    //                    if (scrolled < dy && state.getItemCount() > mFirstPosition + getChildCount()) {
    //                        View      v   = recycler.getViewForPosition(mFirstPosition + getChildCount());
    //                        final int top = getDecoratedBottom(getChildAt(getChildCount() - 1));
    //                        addView(v);
    //                        measureChildWithMargins(v, 0, 0);
    //                        final int bottom = top + getDecoratedMeasuredHeight(v);
    //                        layoutDecorated(v, left, top, right, bottom);
    //                        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                                " 向上滑动 addView :" + scrollBy
    //                                        + " mFirstPosition：" + mFirstPosition
    //                                        + " getChildCount()：" + getChildCount()
    //                                        + " top：" + top
    //                                        + " bottom：" + bottom
    //                        );
    //                    } else {
    //                        break;
    //                    }
    //                }
    //            }
    //            recycleViewsOutOfBounds(recycler);
    //            return scrolled;
    //        }
    //
    //        @Override
    //        public View onFocusSearchFailed(View focused, int direction,
    //                                        RecyclerView.Recycler recycler, RecyclerView.State state) {
    //            DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                    "onFocusSearchFailed======"
    //            );
    //
    //            final int oldCount = getChildCount();
    //            if (oldCount == 0) {
    //                return null;
    //            }
    //            final int left           = getPaddingLeft();
    //            final int right          = getWidth() - getPaddingRight();
    //            View      toFocus        = null;
    //            int       newViewsHeight = 0;
    //            if (direction == View.FOCUS_UP || direction == View.FOCUS_BACKWARD) {
    //                while (mFirstPosition > 0 && newViewsHeight < mScrollDistance) {
    //                    mFirstPosition--;
    //                    View      v      = recycler.getViewForPosition(mFirstPosition);
    //                    final int bottom = getDecoratedTop(getChildAt(0));
    //                    addView(v, 0);
    //                    measureChildWithMargins(v, 0, 0);
    //                    final int top = bottom - getDecoratedMeasuredHeight(v);
    //                    layoutDecorated(v, left, top, right, bottom);
    //                    if (v.isFocusable()) {
    //                        toFocus = v;
    //                        break;
    //                    }
    //                }
    //            }
    //            if (direction == View.FOCUS_DOWN || direction == View.FOCUS_FORWARD) {
    //                while (mFirstPosition + getChildCount() < state.getItemCount() &&
    //                        newViewsHeight < mScrollDistance) {
    //                    View      v   = recycler.getViewForPosition(mFirstPosition + getChildCount());
    //                    final int top = getDecoratedBottom(getChildAt(getChildCount() - 1));
    //                    addView(v);
    //                    measureChildWithMargins(v, 0, 0);
    //                    final int bottom = top + getDecoratedMeasuredHeight(v);
    //                    layoutDecorated(v, left, top, right, bottom);
    //                    if (v.isFocusable()) {
    //                        toFocus = v;
    //                        break;
    //                    }
    //                }
    //            }
    //            return toFocus;
    //        }
    //
    //        public void recycleViewsOutOfBounds(RecyclerView.Recycler recycler) {
    //            DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                    "recycleViewsOutOfBounds=============================="
    //            );
    //            final int childCount   = getChildCount();
    //            final int parentWidth  = getWidth();
    //            final int parentHeight = getHeight();
    //            boolean   foundFirst   = false;
    //            int       first        = 0;
    //            int       last         = 0;
    //            for (int i = 0; i < childCount; i++) {
    //                final View v = getChildAt(i);
    //                if (v.hasFocus() || (getDecoratedRight(v) >= 0 &&
    //                        getDecoratedLeft(v) <= parentWidth &&
    //                        getDecoratedBottom(v) >= 0 &&
    //                        getDecoratedTop(v) <= parentHeight)) {
    //                    if (!foundFirst) {
    //                        first = i;
    //                        foundFirst = true;
    //                    }
    //                    last = i;
    //                }
    //            }
    //            DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                    "recycleViewsOutOfBounds==============================first:" + first
    //                            + " last:" + last
    //                            + " childCount:" + childCount
    //            );
    //
    //            for (int i = childCount - 1; i > last; i--) {
    //                removeAndRecycleViewAt(i, recycler);
    //            }
    //            for (int i = first - 1; i >= 0; i--) {
    //                removeAndRecycleViewAt(i, recycler);
    //            }
    //            if (getChildCount() == 0) {
    //                mFirstPosition = 0;
    //                DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                        "recycleViewsOutOfBounds==============================mFirstPosition = 0 getChildCount() == 0"
    //                );
    //            } else {
    //                mFirstPosition += first;
    //                DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
    //                        "recycleViewsOutOfBounds==============================mFirstPosition ：" + mFirstPosition
    //                                + " first:" + first
    //                );
    //            }
    //        }
    //    }

}
