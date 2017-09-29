package com.jzb.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.eduu.bang.R;
import com.jzb.android.support.design.widget.BaseBehavior;
import com.weiauto.develop.tool.DevLogTool;

/**
 * Created by wikipeng on 2017/9/29.
 */
public class RecyclerViewBehavior<V extends View> extends BaseBehavior<V> {
    private int targetId;

    public RecyclerViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Follow);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int attr = a.getIndex(i);
            if (a.getIndex(i) == R.styleable.Follow_target) {
                targetId = a.getResourceId(attr, -1);
            }
        }
        a.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, V child, View dependency) {
        return dependency.getId() == targetId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            int topMargin = (int) (dependency.getY() + dependency.getHeight());
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = topMargin;

            DevLogTool.getInstance(getContext()).saveLog("-----onDependentViewChanged "
                    + "\ntopMargin:     " + topMargin
            );
        }
        return true;
    }
}
