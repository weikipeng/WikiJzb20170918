package com.jzb.android.ui.topnews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.eduu.bang.BangApplication;
import com.weiauto.develop.tool.DevLogTool;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class RefreshViewBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    protected int     maxHeight;
    private   boolean mSkipNestedPreScroll;

    public RefreshViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        maxHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40
                , context.getResources().getDisplayMetrics());
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child
            , @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //这里返回true，才会接受到后续滑动事件。
        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                "onStartNestedScroll coordinatorLayout:" + coordinatorLayout
                        + "\nchild:" + child
                        + "\ndirectTargetChild:" + directTargetChild
                        + "\ntarget:" + target
                        + "\naxes:" + axes
                        + "\ntype:" + type
        );

        if (ViewCompat.SCROLL_AXIS_VERTICAL == axes) {
            return true;
        }

        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target
            , int dx, int dy, @NonNull int[] consumed, int type) {
        //进行滑动事件处理
        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                "------ onNestedPreScroll coordinatorLayout:" + coordinatorLayout
                        + "\nchild:" + child
                        + "\ntarget:" + target
                        + "\ndx:" + dx
                        + "\ndy:" + dy
                        + "\nconsumed:" + consumed
                        + "\ntype:" + type
                        + "\nchild.getTop():" + child.getTop()
        );

        if (dy > 0) {
            // We're scrolling up 向上滑动
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
            int                    height       = child.getHeight();
            if (height > 0) {
                layoutParams.height = height - dy;
                if (layoutParams.height <= 0) {
                    layoutParams.height = 0;
                }
                coordinatorLayout.updateViewLayout(child, layoutParams);
                if (height > dy) {
                    consumed[1] = dy;
                } else {
                    consumed[1] = height;
                }
            }
        }

        //        if (dy != 0 && !mSkipNestedPreScroll) {
        //            int min, max;
        //            if (dy < 0) {
        //                // We're scrolling down
        //                min = -child.getTotalScrollRange();
        //                max = min + child.getDownNestedPreScrollRange();
        //            } else {
        //                // We're scrolling up
        //                min = -child.getUpNestedPreScrollRange();
        //                max = 0;
        //            }
        //            consumed[1] = scroll(coordinatorLayout, child, dy, min, max);
        //        }
        //        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target
            , int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //        onNestedScroll coordinatorLayout:android.support.design.widget.CoordinatorLayout{157999cc V.E..... ........ 0,0-1080,1845}
        //        child:android.widget.LinearLayout{3e52c540 V.E..... ........ 0,0-1080,120 #7f080076 app:id/refreshView}
        //        target:com.jzb.android.ui.topnews.NestedWebView{10d7ac79 VFEDHVC. .F...... 0,0-1080,1845 #7f0800be app:id/webView}
        //        dxConsumed:0
        //        dyConsumed:0
        //        dxUnconsumed:0
        //        dyUnconsumed:-47
        //        type:0
        //进行滑动事件处理
        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                "onNestedScroll coordinatorLayout:" + coordinatorLayout
                        + "\nchild:" + child
                        + "\ntarget:" + target
                        + "\ndxConsumed:" + dxConsumed
                        + "\ndyConsumed:" + dyConsumed
                        + "\ndxUnconsumed:" + dxUnconsumed
                        + "\ndyUnconsumed:" + dyUnconsumed
                        + "\ntype:" + type
                        + "\nchild.getTop():" + child.getTop()
                        + "\nchild.getHeight():" + child.getHeight()
        );

        int height = child.getHeight();

        //说明是下拉刷新
        if (dyUnconsumed < 0) {
            DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                    "onNestedScroll >>>>>>>>>>>>>>>>>>> show refreshView"
            );
            if (height < maxHeight) {

                int minus = Math.min(-dyUnconsumed, maxHeight);

                ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
                layoutParams.height = height + minus;
                if (layoutParams.height > maxHeight) {
                    layoutParams.height = maxHeight;
                }
//                coordinatorLayout.updateViewLayout(child, layoutParams);
                child.requestLayout();
            }
        } else {
            ViewGroup.LayoutParams layoutParams = child.getLayoutParams();

            int minus = Math.min(dyUnconsumed, height);

            if (height > 0) {
                layoutParams.height = height - minus;
                if (layoutParams.height <= 0) {
                    layoutParams.height = 0;
                }
//                coordinatorLayout.updateViewLayout(child, layoutParams);
                child.requestLayout();
            }
        }

        //        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target
            , float velocityX, float velocityY, boolean consumed) {
        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                "<<<<<<<<< onNestedFling coordinatorLayout:" + coordinatorLayout
                        + "\nchild:" + child
                        + "\ntarget:" + target
                        + "\nvelocityX:" + velocityX
                        + "\nvelocityY:" + velocityY
                        + "\nconsumed:" + consumed
                        + "\nchild.getTop():" + child.getTop()
                        + "\nchild.getHeight():" + child.getHeight()
        );
        //当进行快速滑动
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                "onLayoutChild -----child:" + child
                        + "\nchild.getTop():" + child.getTop()
                        + "\nchild.getHeight():" + child.getHeight()
                        + "\nlayoutDirection:" + layoutDirection
                        + "\nlayoutParams Height:" + layoutParams.height
        );
        parent.dispatchDependentViewsChanged(child);
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, V child, int parentWidthMeasureSpec, int widthUsed
            , int parentHeightMeasureSpec, int heightUsed) {
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }
}
