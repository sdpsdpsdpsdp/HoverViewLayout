package com.laisontech.hoverviewlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by SDP on 2018/4/9.
 * 悬停布局的view
 */

public class HoverViewLayout extends RelativeLayout implements HoverScrollView.OnScrollListener {

    private LinearLayout llBottomLayout;
    private LinearLayout llTopLayout;
    private LinearLayout llShowOrHideLayout;
    private LinearLayout llAlwaysShowLayout;
    private Context context;
    private int rlLayoutTop;
    private View hoverView;
    private View topView;
    private View bottomView;

    public HoverViewLayout(Context context) {
        this(context, null);
    }

    public HoverViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoverViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        View view = View.inflate(context, R.layout.hover_view, this);
        HoverScrollView scrollView = view.findViewById(R.id.scrollView);
        llBottomLayout = view.findViewById(R.id.ll_outside_layout);
        llTopLayout = view.findViewById(R.id.ll_top_layout);
        llShowOrHideLayout = view.findViewById(R.id.ll_show_or_hide_view);
        llAlwaysShowLayout = view.findViewById(R.id.ll_always_show_view);
        scrollView.setOnScrollListener(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            rlLayoutTop = llTopLayout.getBottom();//获取searchLayout的顶部位置
        }
    }

    /**
     * 添加顶部布局
     */
    public HoverViewLayout addTopLayout(int topID) {
        View topView = LayoutInflater.from(context).inflate(topID, null);
        addTopView(topView);
        return this;
    }

    public HoverViewLayout addTopLayout(View topView) {
        addTopView(topView);
        return this;
    }

    private void addTopView(View topView) {
        if (topView instanceof ListView) {
            topView.setFocusable(false);
        }
        this.topView = topView;
        llTopLayout.addView(topView);
    }

    /**
     * 添加底部布局
     */
    public HoverViewLayout addBottomLayout(int bottomID) {
        View bottomView = LayoutInflater.from(context).inflate(bottomID, null);
        addBottomView(bottomView);
        return this;
    }

    public HoverViewLayout addBottomLayout(View bottomView) {
        addBottomView(bottomView);
        return this;
    }

    private void addBottomView(View bottomView) {
        if (bottomView instanceof ListView) {
            bottomView.setFocusable(false);
        }
        this.bottomView = bottomView;
        llBottomLayout.addView(bottomView);
    }


    /**
     * 添加悬停布局
     */
    public HoverViewLayout addHoverLayout(View hoverView) {
        this.hoverView = hoverView;
        return this;
    }

    public HoverViewLayout addHoverLayout(int hoverViewID) {
        this.hoverView = LayoutInflater.from(context).inflate(hoverViewID, null);

        return this;
    }

    public void showHoverView() {
        View hoverView = getHoverView();
        if (hoverView != null) {
            llShowOrHideLayout.addView(hoverView);
        }
    }

    public View getTopView() {
        return topView;
    }

    public View getBottomView() {
        return bottomView;
    }

    //获取悬停布局
    public View getHoverView() {
        return hoverView;
    }

    @Override
    public void onScroll(int scrollY) {
        if (getHoverView() == null) return;
        if (scrollY >= rlLayoutTop) {
            if (getHoverView().getParent() != llAlwaysShowLayout) {
                llShowOrHideLayout.removeView(getHoverView());
                llAlwaysShowLayout.addView(getHoverView());
            }
        } else {
            if (getHoverView().getParent() != llShowOrHideLayout) {
                llAlwaysShowLayout.removeView(getHoverView());
                llShowOrHideLayout.addView(getHoverView());
            }
        }
    }

    //减少冲突
    public void setListViewHeightBasedOnChildren(ListView listView, Adapter adapter) {
        if (listView == null) return;
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);//计算每项   Item的高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        //循环完之后,要计算出getDividerHeight(空白处占得height)   才是Listview的高度
        listView.setLayoutParams(params);
    }
}
