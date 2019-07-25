package com.lgpgit.open.toolutils.widget.drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.lgpgit.open.toolutils.widget.drag.impListener.ViewPagerListener;
import com.lgpgit.open.toolutils.widget.drag.interfaces.ScrollerViewPagerFun;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lugp
 * @date 2019/6/26
 */
public class ScrollerViewPager extends ViewPager {

    private int scrollDuration = 800;

    private ScrollerViewPagerFun scrollerViewPagerFun;

    private ViewPagerListener viewPagerListener;

    public ScrollerViewPager(@NonNull Context context) {
        super(context);
    }

    public ScrollerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //数据初始化
    private void initView() {

        setViewPagerScrollSpeed(scrollDuration, this);

        addOnPageChangeListener(viewPagerListener);
    }

    //刷新时间
    public void setViewPagerScrollSpeed(int duration, ScrollerViewPager scrollerViewPager) {
        try {
            Field scroller = null;
            scroller = ViewPager.class.getDeclaredField("scroller");
            scroller.setAccessible(true);
            FixedSpeedScroller fixedSpeedScroller = new FixedSpeedScroller(scrollerViewPager.getContext(), duration);
            scroller.set(scrollerViewPager, fixedSpeedScroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        }
    }

    /**
     * 获得viewPager的数据
     * @param layoutInflater
     * @param ints
     * @return
     */
    public List<View> setPagerList(LayoutInflater layoutInflater, int[] ints) {
        List<View> viewList = new ArrayList<>();

        for (int i = 0; i < ints.length; i++ ) {
            viewList.add(layoutInflater.inflate(ints[i], null));
        }
        return viewList;
    }

    public void setScrollDuration(int scrollDuration) {
        this.scrollDuration = scrollDuration;
    }

    public void setOnScrooler(ScrollerViewPagerFun scrollerViewPagerFun) {
        this.scrollerViewPagerFun = scrollerViewPagerFun;
    }

    public void setScrollerViewPagerFun(ScrollerViewPagerFun scrollerViewPagerFun) {
        this.scrollerViewPagerFun = scrollerViewPagerFun;
    }

    public void setViewPagerListener(ViewPagerListener viewPagerListener) {
        this.viewPagerListener = viewPagerListener;
    }

    public class FixedSpeedScroller extends Scroller {

        private int scrollDuration;

        public FixedSpeedScroller(Context context) {
            super(context);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        public FixedSpeedScroller(Context context, int scrollDuration) {
            super(context);
            this.scrollDuration = scrollDuration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, scrollDuration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy);
        }
    }
}
