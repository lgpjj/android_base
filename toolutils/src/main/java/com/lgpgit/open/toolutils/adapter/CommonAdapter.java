package com.lgpgit.open.toolutils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * adapter的通用类
 *
 * @author lugp
 * @date 2019/01/29
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> listData;
    private int layoutId;

    public CommonAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, List<T> listData, int layoutId) {
        this.context = context;
        this.listData = listData;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public T getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder viewHolder = null;
        boolean flag;
        if (convertView == null) {
            flag = true;
            convertView = LayoutInflater.from(context).inflate(layoutId, null);
            viewHolder = new CommonViewHolder();
        } else {
            flag = false;
            viewHolder = (CommonViewHolder) convertView.getTag();
        }
        T t = getItem(position);
        convertView(position, convertView, t, flag, viewHolder);
        return convertView;
    }

    /**
     * 需要去实现的对item中的view的设置操作
     * @param item
     * @param t
     * @param viewHolder
     */
    protected void convertView(int position, View item, T t, boolean flag, CommonViewHolder viewHolder) {
        if (flag) {
            setViewHolder(item, viewHolder);
            item.setTag(viewHolder);
        }
        setObject(position, item, t, viewHolder);
    }

    /**
     * 存放xml页面需要填充的view
     * @param item
     * @param t
     * @param viewHolder
     */
    protected abstract void setViewHolder(View item, CommonViewHolder viewHolder);

    /**
     * 存放view的显示值
     * @param item
     * @param t
     * @param viewHolder
     */
    protected abstract void setObject(int position, View item, T t, CommonViewHolder viewHolder);

    /**
     * 获取position对应的itemView
     * @param position
     * @return
     */
    public View getItemView(int position) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, null);
        CommonViewHolder viewHolder = new CommonViewHolder();
        T t = getItem(position);

        setViewHolder(itemView, viewHolder);
        setObject(position, itemView, t, viewHolder);
        return itemView;
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }
}
