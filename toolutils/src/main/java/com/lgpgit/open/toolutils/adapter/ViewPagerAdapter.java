package com.lgpgit.open.toolutils.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.lgpgit.open.toolutils.common.Constant;

import java.util.List;

/**
 * @author lugp
 * @date 2019/6/26
 */
public abstract class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<View> viewList;

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    public ViewPagerAdapter(Context context, List<View> viewList, int layoutId) {
        this.context = context;
        this.viewList = viewList;
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewList.get(position));
        super.destroyItem(container, position, object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = viewList.get(position);
        container.addView(view);
        return super.instantiateItem(container, position);
    }

    protected abstract int getPosition(ViewGroup container);

    public List<View> getViewList() {
        return viewList;
    }

    public void setViewList(List<View> viewList) {
        this.viewList = viewList;
    }

}
