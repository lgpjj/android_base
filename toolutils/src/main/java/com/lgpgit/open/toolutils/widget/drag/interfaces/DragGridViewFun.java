package com.lgpgit.open.toolutils.widget.drag.interfaces;

/**
 * @author lugp
 * @date 2019/6/25
 */
public interface DragGridViewFun {

    /**
     * 生成镜像，并赋值（如果使用的是CommonAdapter,可以调用其中的getItemView）
     * @param position
     */
    public void achieveView(int position);

    /**
     * 交换数据，并更新显示（如果使用的是CommonAdapter,可以调用其中的exchangePosition）
     */
    public void exchangePosition();
}
