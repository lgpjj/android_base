package com.lgpgit.open.toolutils.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * 存放view视图中的控件id
 *
 * @author lugp
 * @date 2019/01/29
 */
public class CommonViewHolder {

    private SparseArray<View> sparseArray = new SparseArray<>();

    public CommonViewHolder() {
    }

    public void set(int viewId, View view) {
        sparseArray.put(viewId, view);
    }

    public <T extends View> T get(int viewId) {
        return (T) sparseArray.get(viewId);
    }
}
