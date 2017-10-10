package com.jzb.android.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.eduu.bang.BangApplication;
import com.jzb.android.support.design.widget.BaseBehavior;
import com.weiauto.develop.tool.DevLogTool;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class WebViewBehavior<V extends View> extends BaseBehavior<V> {

    public WebViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = (int) (dependency.getY() + dependency.getHeight());
        }
        return false;
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
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target
            , int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
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
}
