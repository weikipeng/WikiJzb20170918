package com.jzb.android.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jzb.android.support.design.widget.ScrollingViewBehavior;
import com.weiauto.develop.tool.DevLogTool;

/**
 * Created by wikipeng on 2017/9/29.
 */
public class RecyclerViewBehavior extends ScrollingViewBehavior {

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            int topMargin = (int) (dependency.getY() + dependency.getHeight());
            if (((ViewGroup.MarginLayoutParams) layoutParams).topMargin != topMargin) {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = topMargin;
                //                child.requestLayout();
            }

            DevLogTool.getInstance(getContext()).saveLog("-----onDependentViewChanged "
                    + "\ndependency:     " + dependency
                    + "\ntopMargin:     " + topMargin
            );
        }
        //        parent.updateViewLayout(child,layoutParams);
        return false;
    }
}
