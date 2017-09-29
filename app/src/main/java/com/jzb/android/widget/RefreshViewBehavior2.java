package com.jzb.android.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.eduu.bang.BangApplication;
import com.jzb.android.support.design.widget.HeaderBehavior;
import com.weiauto.develop.tool.DevLogTool;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class RefreshViewBehavior2<V extends View> extends HeaderBehavior<V> {
    private float mInterceptTouchDownX = -1;
    private float mInterceptTouchDownY = -1;


    public RefreshViewBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        boolean   isIntercept = false;
        final int x           = (int) ev.getX();
        final int y           = (int) ev.getY();

        int     childCount         = parent.getChildCount();
        View    touchChildView     = null;
        boolean pointInChildBounds = false;
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                touchChildView = parent.getChildAt(i);
                pointInChildBounds = parent.isPointInChildBounds(touchChildView, x, y);
                if (pointInChildBounds) {
                    break;
                }
            }
        }

        if (!pointInChildBounds) {
            touchChildView = null;
        }

        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                " onInterceptTouchEvent coordinatorLayout:" + parent
                        + "\nchild:" + child
                        + "\ntouch child:" + touchChildView
                        + "\nchild.getScrollY():" + child.getScrollY()
                        + "\nev:" + ev.toString()
        );

        if (touchChildView != null) {
            int scrollY = touchChildView.getScrollY();
            //说明滑动到了最顶部
            if (scrollY == 0) {
//                isIntercept = true;
            }
        }

        ///////////////////////////////////////////////////////////////////////////
        // 如果拦截
        ///////////////////////////////////////////////////////////////////////////
        if (isIntercept) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mInterceptTouchDownX = ev.getRawX();
                    mInterceptTouchDownY = ev.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //                    if (!mIsLoadingMore && (mCurrentRefreshStatus != RefreshStatus.REFRESHING)) {
                    if (mInterceptTouchDownX == -1) {
                        mInterceptTouchDownX = (int) ev.getRawX();
                    }
                    if (mInterceptTouchDownY == -1) {
                        mInterceptTouchDownY = (int) ev.getRawY();
                    }

                    int interceptTouchMoveDistanceX = (int) (Math.abs(ev.getRawX() - mInterceptTouchDownX));
                    int interceptTouchMoveDistanceY = (int) (ev.getRawY() - mInterceptTouchDownY);
                    // 可以没有上拉加载更多，但是必须有下拉刷新，否则就不拦截事件
                    if (interceptTouchMoveDistanceX < Math.abs(interceptTouchMoveDistanceY)) {
                        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                                " 拦截触摸事件======>"
                        );
                        isIntercept = true;
                    } else {
                        isIntercept = false;
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    // 重置
                    mInterceptTouchDownX = -1;
                    mInterceptTouchDownY = -1;
                    break;
            }
        }

        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                "------onInterceptTouchEvent isIntercept------>" + isIntercept
        );
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                " onTouchEvent------ coordinatorLayout:" + parent
                        + "\nchild:" + child
                        + "\nev:" + ev.toString()
        );

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //                mWholeHeaderDownY = (int) ev.getY();
                //                if (mCustomHeaderView != null) {
                //                    mWholeHeaderViewDownPaddingTop = mWholeHeaderView.getPaddingTop();
                //                }
                //
                //                if (mCustomHeaderView == null || !mIsCustomHeaderViewScrollable) {
                //                    mRefreshDownY = (int) ev.getY();
                //                }
                //
                //                if (isWholeHeaderViewCompleteInvisible()) {
                //                    mRefreshDownY = (int) ev.getY();
                //                    //                        /*FOpenLog.e("jzbFocus debug JZBRefreshLayout ===> onTouchEvent  " + JzbWebView.getEventName(event) + "处理<<<<<<<<<<<<<<<<<<<");*/
                //                    return true;
                //                }
                break;
            case MotionEvent.ACTION_MOVE:
                //                if (handleActionMove(ev)) {
                //                    //                        /*FOpenLog.e("jzbFocus debug JZBRefreshLayout ===> onTouchEvent  " + JzbWebView.getEventName(event) + "处理<<<<<<<<<<<<<<<<<<<");*/
                //                    return true;
                //                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //                if (handleActionUpOrCancel(ev)) {
                //                    //                        /*FOpenLog.e("jzbFocus debug JZBRefreshLayout ===> onTouchEvent  " + JzbWebView.getEventName(event) + "处理<<<<<<<<<<<<<<<<<<<");*/
                //                    return true;
                //                }
                break;
            default:
                break;
        }

        //        /*FOpenLog.e("jzbFocus debug JZBRefreshLayout ===> onTouchEvent  " + JzbWebView.getEventName(event) + "============不消耗========================== ");*/
        return false;
    }


}
