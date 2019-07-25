package com.lgpgit.open.toolutils.widget.drag.impListener;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lugp
 * @date 2019/6/27
 */
public abstract class ViewPagerListener<T extends View> implements ViewPager.OnPageChangeListener {

    private Context context;
    //判断是否添加切换图形
    private boolean roll;
    //切换图形的布局
    private LinearLayout linearLayout;
    //切换图形的个数
    private int size;
    //选中切换图形的drawble
    private int rollSelect;
    //未选中切换图形的drawble
    private int rollNoSelect;
    //切换图形的间距上或者左
    private int paddingLeftOrTop;
    //切换图形的间距下或者右
    private int paddingRightOrBottom;
    //所有切换图形的数组
    private List<T> rollList;

    /**
     * 如果roll为false，后面的参数可以为null
     * @param context
     * @param roll
     * @param linearLayout
     * @param size
     * @param rollSelect
     * @param rollNoSelect
     * @param paddingLeftOrTop
     * @param paddingRightOrBottom
     */
    public ViewPagerListener(Context context, boolean roll, LinearLayout linearLayout, int size, int rollSelect, int rollNoSelect, int paddingLeftOrTop, int paddingRightOrBottom) {
        this.context = context;
        this.roll = roll;
        if (roll) {
            this.linearLayout = linearLayout;
            this.size = size;
            this.rollSelect = rollSelect;
            this.rollNoSelect = rollNoSelect;
            this.paddingLeftOrTop = paddingLeftOrTop;
            this.paddingRightOrBottom = paddingRightOrBottom;
        }
        initData();
    }

    private void initData() {
        rollList = new ArrayList<>();
        if (roll) {
            for (int i = 0; i < size; i++) {
                T imageView = (T) new View(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                params.leftMargin = paddingLeftOrTop;     //设置圆点相距距离
                params.rightMargin = paddingRightOrBottom;
                if (i == 0) {               //初始化为红点
                    imageView.setBackgroundResource(rollSelect);
                } else {
                    imageView.setBackgroundResource(rollNoSelect);
                }
                linearLayout.addView(imageView,params);
                rollList.add(imageView);
            }
        }
    }

    @Override
    public void onPageSelected(int i) {
        if (roll) {
            for (T t : rollList) {
                t.setBackgroundResource(rollNoSelect);
            }
            if (i == 0) {
                rollList.get(rollList.size() - 1).setBackgroundResource(rollSelect);
            } else if (i == rollList.size() - 1) {
                rollList.get(0).setBackgroundResource(rollSelect);
            } else {
                rollList.get(i - 1).setBackgroundResource(rollSelect);
            }
        }
        pageSelect(i);
    }

    abstract void pageSelect(int i);
}
