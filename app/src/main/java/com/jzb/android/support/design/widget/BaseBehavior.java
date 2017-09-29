package com.jzb.android.support.design.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by wikipeng on 2017/9/29.
 */
public class BaseBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public static final int   INVALID_POINTER                 = -1;
    public static final float DRAG_RATE                       = .5f;
    public static final int   DRAG_MAX_DISTANCE               = 120;
    public static final int   MAX_OFFSET_ANIMATION_DURATION   = 700;
    public static final float DECELERATE_INTERPOLATION_FACTOR = 2f;
    public static final int   RESTORE_ANIMATION_DURATION      = 2350;

    protected Context mContext;

    /**
     * 滑动的临界值，当滑动距离超过这个值时才认为手势为滑动
     */
    protected int mTouchSlop;

    /**
     * 当前活动的按下点ID
     */
    protected int mActivePointerId;


    public BaseBehavior() {
        super();
    }

    public BaseBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(context);
    }

    protected void init(Context context) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    protected Context getContext() {
        return mContext;
    }

    public View getTargetView(CoordinatorLayout parent, MotionEvent ev) {

        View      targetView = null;
        final int evX        = (int) ev.getX();
        final int evY        = (int) ev.getY();

        int     childCount         = parent.getChildCount();
        boolean pointInChildBounds = false;
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                targetView = parent.getChildAt(i);
                pointInChildBounds = parent.isPointInChildBounds(targetView, evX, evY);
                if (pointInChildBounds) {
                    break;
                }
            }
        }

        if (!pointInChildBounds) {
            targetView = null;
        }

        return targetView;
    }

    protected void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = MotionEventCompat.getActionIndex(ev);
        final int pointerId    = MotionEventCompat.getPointerId(ev, pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
        }
    }
}
