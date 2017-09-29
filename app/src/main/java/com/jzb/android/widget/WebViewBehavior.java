package com.jzb.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.eduu.bang.R;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class WebViewBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private int targetId;


    public WebViewBehavior(Context context, AttributeSet attrs) {
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
        //        return super.layoutDependsOn(parent, child, dependency);
        return dependency.getId() == targetId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, V child, View dependency) {
        //        return super.onDependentViewChanged(parent, child, dependency);
//        child.setY(dependency.getY() + dependency.getHeight());
//        child.offsetTopAndBottom((int) (dependency.getY() + dependency.getHeight()));
        ViewGroup.LayoutParams layoutParams = child.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = (int) (dependency.getY() + dependency.getHeight());
        }
        return true;
    }

//    @Override
//    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child
//            , @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
//        //这里返回true，才会接受到后续滑动事件。
//        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
//    }
//
//    @Override
//    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target
//            , int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        //进行滑动事件处理
//        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
//    }
//
//    @Override
//    public boolean onNestedFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target
//            , float velocityX, float velocityY, boolean consumed) {
//        //当进行快速滑动
//        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
//    }


    //    @Override
    //    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
    //        return true;//这里返回true，才会接受到后续滑动事件。
    //    }
    //
    //    @Override
    //    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    //        //进行滑动事件处理
    //    }
    //
    //    @Override

    //    public boolean onNestedFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY, boolean consumed) {
    //        //当进行快速滑动
    //        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed);
    //    }
}
