package com.laisontech.hoverviewlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by SDP on 2018/4/9.
 */

public class HoverScrollView extends ScrollView {
    private OnScrollListener onScrollListener;
    private float mFirstTouchY;
    private float mTouchSlop;

    public HoverScrollView(Context context) {
        this(context, null);
    }

    public HoverScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HoverScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScroll(t);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstTouchY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveListener != null) {
                    moveListener.move(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (moveListener != null) {
                    moveListener.move(false);
                }
                break;
        }
         return super.onTouchEvent(event);
    }

    public interface OnActionMoveListener {
        void move(boolean isMove);
    }

    private OnActionMoveListener moveListener;

    public void setMoveListener(OnActionMoveListener moveListener) {
        this.moveListener = moveListener;
    }

    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 滚动的回调接口
     */
    public interface OnScrollListener {
        /**
         * 回调方法， 返回MyScrollView滑动的Y方向距离
         */
        void onScroll(int scrollY);
    }
}
