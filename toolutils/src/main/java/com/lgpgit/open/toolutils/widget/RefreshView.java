package com.lgpgit.open.toolutils.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.lgpgit.open.toolutils.common.Constant;

/**
 * @author lugp
 * @date 2019/6/12
 */
public abstract class RefreshView<T extends View> extends SwipeRefreshLayout {

    private int headerLayout = getHeaderLayout();
    private int contentLayout = getContentLayout();
    private int scrollerViewId = getScrollerViewId();
    private int footerLayout = getFooterLayout();

    private Boolean header = getHeader();
    private Boolean footer = getFooter();

    protected T scroolerView;

    public RefreshView(@NonNull Context context) {
        super(context);
        initView();
    }

    public RefreshView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        String infServie = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(infServie);
        View view = layoutInflater.inflate(contentLayout, this, true);
        scroolerView = view.findViewById(scrollerViewId);
        scroolerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollerClick();
            }
        });
    }

    protected abstract void scrollerClick();

    public int getHeaderLayout() {
        if (header) {
            return headerLayoutView();
        } else {
            return -1;
        }
    }

    protected abstract int headerLayoutView();

    public abstract int getContentLayout();

    public abstract int getScrollerViewId();

    public int getFooterLayout() {
        if (footer) {
            return footerLayoutView();
        } else {
            return -1;
        }
    }

    protected abstract int footerLayoutView();

    public abstract Boolean getHeader();

    public abstract Boolean getFooter();
}
