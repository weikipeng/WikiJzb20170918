package com.jzb.android.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;

import com.eduu.bang.BangApplication;
import com.jzb.android.support.design.widget.HeaderBehavior;
import com.weiauto.develop.tool.DevLogTool;

/**
 * Created by wikipeng on 2017/9/28.
 */
public class RefreshViewBehavior2<V extends View> extends HeaderBehavior<V> {
    /**
     * 当前正在刷新
     */
    private boolean mRefreshing;
    /**
     * 当前是否在拖动
     */
    private boolean mIsBeingDragged;


    /**
     * 刚开始按下的点Y坐标值
     */
    private float mInitialMotionY;

    private View mTarget;
    private View mRefreshView;
    private int  mCurrentOffsetTop;

    private float mCurrentDragPercent;
    /**
     * 总共可以拖动的距离
     */
    private int   mTotalDragDistance;

    private boolean mNotify;

    public RefreshViewBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDecelerateInterpolator = new DecelerateInterpolator(DECELERATE_INTERPOLATION_FACTOR);

        float density = context.getResources().getDisplayMetrics().density;
        mTotalDragDistance = Math.round((float) DRAG_MAX_DISTANCE * density);
        DevLogTool.getInstance(context).saveLog("手机设备密度density:" + density
                + "\n可以拖动的距离：" + mTotalDragDistance);
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        mRefreshView = child;


        mTarget = getTargetView(parent, ev);

        boolean isIntercept = false;

        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                " onInterceptTouchEvent coordinatorLayout:" + parent
                        + "\nchild:" + child
                        + "\ntouch child:" + mTarget
                        + "\nchild.getScrollY():" + child.getScrollY()
                        + "\nev:" + ev.toString()
        );

        boolean isEnabled        = child.isEnabled();
        boolean canChildScrollUp = false;

        if (mTarget != null) {
            canChildScrollUp = mTarget.canScrollVertically(-1);
        }

        if (!isEnabled || canChildScrollUp || mRefreshing) {
            isIntercept = false;
        } else {
            isIntercept = true;
        }

        final int action = ev.getAction();

        ///////////////////////////////////////////////////////////////////////////
        // 如果拦截
        ///////////////////////////////////////////////////////////////////////////
        if (isIntercept) {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    setTargetOffsetTop(0);
                    mActivePointerId = ev.getPointerId(0);
                    mIsBeingDragged = false;
                    final float initialMotionY = getMotionEventY(ev, mActivePointerId);
                    DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                            "-----onInterceptTouchEvent MotionEvent.ACTION_DOWN"
                                    + "\n当前活动的按下点ID mActivePointerId :" + mActivePointerId
                                    + "\ninitialMotionY:" + initialMotionY
                    );

                    if (initialMotionY == -1) {
                        return false;
                    }
                    mInitialMotionY = initialMotionY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mActivePointerId == INVALID_POINTER) {
                        DevLogTool.getInstance(getContext()).saveLog("mActivePointerId == INVALID_POINTER");
                        return false;
                    }
                    final float y = getMotionEventY(ev, mActivePointerId);
                    if (y == -1) {
                        DevLogTool.getInstance(getContext()).saveLog("getMotionEventY == -1");
                        return false;
                    }
                    final float yDiff = y - mInitialMotionY;
                    DevLogTool.getInstance(getContext()).saveLog("-----onInterceptTouchEvent MotionEvent.ACTION_MOVE"
                            + " y:" + y + " mInitialMotionY:" + mInitialMotionY + " yDiff:" + yDiff
                            + " mTouchSlop:" + mTouchSlop + " mIsBeingDragged:" + mIsBeingDragged
                    );
                    if (yDiff > mTouchSlop && !mIsBeingDragged) {
                        mIsBeingDragged = true;
                    }
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mIsBeingDragged = false;
                    mActivePointerId = INVALID_POINTER;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    onSecondaryPointerUp(ev);
                    break;
            }
        } else {
            mIsBeingDragged = false;
        }

        DevLogTool.getInstance(BangApplication.getInstance()).saveLog(
                "------onInterceptTouchEvent isIntercept------>" + isIntercept
        );
        return mIsBeingDragged;
    }


    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        DevLogTool.getInstance(getContext()).saveLog(
                "-----onTouchEvent mIsBeingDragged:" + mIsBeingDragged
                        + "\nev:" + ev
        );

        if (!mIsBeingDragged) {
            return super.onTouchEvent(parent, child, ev);
        }

        final int action = ev.getAction();

        switch (action) {
            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    DevLogTool.getInstance(getContext()).saveLog("-----onTouchEvent MotionEvent.ACTION_MOVE: pointerIndex < 0");
                    return false;
                }

                final float y         = MotionEventCompat.getY(ev, pointerIndex);
                final float yDiff     = y - mInitialMotionY;
                final float scrollTop = yDiff * DRAG_RATE;
                mCurrentDragPercent = scrollTop / mTotalDragDistance;
                DevLogTool.getInstance(getContext()).saveLog(
                        "-----onTouchEvent MotionEvent.ACTION_MOVE"
                                + "\nDRAG_RATE:" + DRAG_RATE
                                + "\nmInitialMotionY:" + mInitialMotionY
                                + "\ny:" + y
                                + "\nyDiff:" + yDiff
                                + "\nscrollTop:" + scrollTop
                                + "\nmCurrentDragPercent:" + mCurrentDragPercent
                );

                if (mCurrentDragPercent < 0) {
                    return false;
                }
                float boundedDragPercent = Math.min(1f, Math.abs(mCurrentDragPercent));
                float extraOS            = Math.abs(scrollTop) - mTotalDragDistance;
                float slingshotDist      = mTotalDragDistance;
                //张力弹力百分比
                float tensionSlingshotPercent = Math.max(0,
                        Math.min(extraOS, slingshotDist * 2) / slingshotDist);
                float tensionPercent = (float) ((tensionSlingshotPercent / 4) - Math.pow(
                        (tensionSlingshotPercent / 4), 2)) * 2f;
                float extraMove = (slingshotDist) * tensionPercent / 2;
                int   targetY   = (int) ((slingshotDist * boundedDragPercent) + extraMove);

                DevLogTool.getInstance(getContext()).saveLog(
                        "-----onTouchEvent MotionEvent.ACTION_MOVE"
                                + "\nboundedDragPercent:" + boundedDragPercent
                                + "\nextraOS:" + extraOS
                                + "\nslingshotDist:" + slingshotDist
                                + "\ntensionSlingshotPercent:" + tensionSlingshotPercent
                                + "\ntensionPercent:" + tensionPercent
                                + "\nslingshotDist:" + slingshotDist
                                + "\nextraMove:" + extraMove
                                + "\ntargetY:" + targetY
                                + "\nmCurrentOffsetTop:" + mCurrentOffsetTop
                );
                //                mRefreshView.setPercent(mCurrentDragPercent);
                setTargetOffsetTop(targetY - mCurrentOffsetTop);
                break;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
                final int index = ev.getActionIndex();
                mActivePointerId = ev.getPointerId(index);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }
                final int   pointerIndex  = MotionEventCompat.findPointerIndex(ev, mActivePointerId);
                final float y             = MotionEventCompat.getY(ev, pointerIndex);
                final float overScrollTop = (y - mInitialMotionY) * DRAG_RATE;
                mIsBeingDragged = false;
                DevLogTool.getInstance(getContext()).saveLog("-----onInterceptTouchEvent MotionEvent.ACTION_CANCEL"
                        + "\noverScrollTop:     " + overScrollTop
                        + "\nmTotalDragDistance:    " + mTotalDragDistance
                );
                if (overScrollTop > mTotalDragDistance) {
                    setRefreshing(true, true);
                    if (mRefreshView != null) {
                        mRefreshView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setRefreshing(false, true);
                            }
                        }, 3000);
                    }
                } else {
                    mRefreshing = false;
                    animateOffsetToPosition(mAnimateToStartPosition);
                }
                mActivePointerId = INVALID_POINTER;
                return false;
            }
        }

        return true;
    }

    private void setTargetOffsetTop(int offset) {
        if (mTarget != null) {
            mTarget.offsetTopAndBottom(offset);
        }

        if (mRefreshView != null) {
            mRefreshView.offsetTopAndBottom(offset);
        }
        if (mTarget != null) {
            mCurrentOffsetTop = mTarget.getTop();
        }
    }

    public void setRefreshing(boolean refreshing) {
        if (mRefreshing != refreshing) {
            setRefreshing(refreshing, false /* notify */);
        }
    }

    private void setRefreshing(boolean refreshing, final boolean notify) {
        DevLogTool.getInstance(getContext()).saveLog("-----setRefreshing"
                + " refreshing: " + refreshing
                + " notify: " + notify
        );
        if (mRefreshing != refreshing) {
            mNotify = notify;
            //            ensureTarget();
            mRefreshing = refreshing;
            if (mRefreshing) {
                //                mRefreshView.setPercent(1f);
                animateOffsetToCorrectPosition();
            } else {
                //                mRefreshView.setEndOfRefreshing(true);
                animateOffsetToPosition(mAnimateToEndPosition);
            }
        }
    }

    private int          mFrom;
    private float        mFromDragPercent;
    private Interpolator mDecelerateInterpolator;

    private void animateOffsetToPosition(Animation animation) {
        mFrom = mCurrentOffsetTop;
        mFromDragPercent = mCurrentDragPercent;
        long animationDuration = (long) Math.abs(MAX_OFFSET_ANIMATION_DURATION * mFromDragPercent);

        animation.reset();
        animation.setDuration(animationDuration);
        animation.setInterpolator(mDecelerateInterpolator);
        animation.setAnimationListener(mToStartListener);
        //        mRefreshImageView.clearAnimation();
        //        mRefreshImageView.startAnimation(animation);
        if (mRefreshView != null) {
            mRefreshView.clearAnimation();
            mRefreshView.startAnimation(animation);
        }
    }

    private Animation.AnimationListener mToStartListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //            mRefreshView.stop();
            mCurrentOffsetTop = mTarget.getTop();
        }
    };

    private final Animation mAnimateToStartPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, @NonNull Transformation t) {
            moveToStart(interpolatedTime);
        }
    };

    private Animation mAnimateToEndPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, @NonNull Transformation t) {
            moveToEnd(interpolatedTime);
        }
    };


    private void moveToStart(float interpolatedTime) {
        int   targetTop     = mFrom - (int) (mFrom * interpolatedTime);
        float targetPercent = mFromDragPercent * (1.0f - interpolatedTime);
        int   offset        = targetTop - mTarget.getTop();

        mCurrentDragPercent = targetPercent;
        //        mRefreshView.setPercent(mCurrentDragPercent);
        setTargetOffsetTop(offset);
    }

    private void moveToEnd(float interpolatedTime) {
        int   targetTop     = mFrom - (int) (mFrom * interpolatedTime);
        float targetPercent = mFromDragPercent * (1.0f + interpolatedTime);
        int   offset        = targetTop - mTarget.getTop();

        mCurrentDragPercent = targetPercent;
        //        mRefreshView.setPercent(mCurrentDragPercent);
        setTargetOffsetTop(offset);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    private void animateOffsetToCorrectPosition() {
        mFrom = mCurrentOffsetTop;
        mFromDragPercent = mCurrentDragPercent;

        mAnimateToCorrectPosition.reset();
        mAnimateToCorrectPosition.setDuration(RESTORE_ANIMATION_DURATION);
        mAnimateToCorrectPosition.setInterpolator(mDecelerateInterpolator);
        if (mRefreshView != null) {
            mRefreshView.clearAnimation();
            mRefreshView.startAnimation(mAnimateToCorrectPosition);
        }
        //        mRefreshImageView.clearAnimation();
        //        mRefreshImageView.startAnimation(mAnimateToCorrectPosition);

        if (mRefreshing) {
//            mRefreshView.start();
            if (mNotify) {
//                if (mListener != null) {
//                    mListener.onRefresh();
//                }
            }
        } else {
//            mRefreshView.stop();
            animateOffsetToPosition(mAnimateToStartPosition);
        }
        mCurrentOffsetTop = mTarget.getTop();
    }

    private final Animation mAnimateToCorrectPosition = new Animation() {
        @Override
        public void applyTransformation(float interpolatedTime, @NonNull Transformation t) {
            int targetTop;
            int endTarget = mTotalDragDistance;
            targetTop = (mFrom + (int) ((endTarget - mFrom) * interpolatedTime));
            int offset = targetTop - mTarget.getTop();

            mCurrentDragPercent = mFromDragPercent - (mFromDragPercent - 1.0f) * interpolatedTime;
//            mRefreshView.setPercent(mCurrentDragPercent);

            setTargetOffsetTop(offset);
        }

    };


}
