package com.lgpgit.open.toolutils.widget.drag;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lgpgit.open.toolutils.common.Constant;
import com.lgpgit.open.toolutils.widget.drag.interfaces.DragGridViewFun;

public class DragGridView extends GridView implements AdapterView.OnItemLongClickListener {

    private WindowManager windowManager;
    //记录手指点下时所在的xy值
    private float windowX;
    private float windowY;

    //是否移动item
    private int MOVE_ADAPTER = Constant.INT_ZERO;
    private int NO_MOVE_ADAPTER = Constant.INT_ONE;

    private int move_is;

    //选中的item的view
    private View view;
    //选中的item的数据的位置
    private int position;
    //选中的item的数据的位置
    private int tempPosition;

    //手指点击的位置对应在item上时，对item的左边和上边的距离
    private float item_x;
    private float item_y;

    //拖拽的view
    private View gridView;

    private DragGridViewFun dragGridViewFun;

    private WindowManager.LayoutParams layoutParams;

    private Animation.AnimationListener animationListener;

    public DragGridView(Context context) {
        super(context);
    }

    public DragGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        setOnItemLongClickListener(this);

        initView();
    }

    private void initView() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dragGridViewFun.exchangePosition();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

//    /**
//     * 转换数据位置
//     * 继承后可重新实现
//     */
//    public void exchangePosition() {
//        // 在动画完成时将adapter里的数据交换位置
//        CommonAdapter adapter = (CommonAdapter) getAdapter();
//        if (adapter != null) {
//            adapter.exchangePosition(position, tempPosition, true);
//        }
//        position = tempPosition;
//    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                windowX = ev.getRawX();
                windowY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP :
                break;
            case MotionEvent.ACTION_MOVE :
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        if (move_is == MOVE_ADAPTER) {
            this.view = view;
            this.position = position;
            this.tempPosition = position;
            item_x = windowX - view.getLeft() - this.getLeft();
            item_y = windowY - view.getTop() - this.getTop();
            initWindow(position);
            return true;
        } else if (move_is == NO_MOVE_ADAPTER) {
            return false;
        } else {
            return false;
        }
    }

    private void initWindow(int position) {
        if (gridView == null) {
            dragGridViewFun.achieveView(position);
        }
        if (layoutParams == null) {
            layoutParams = new WindowManager.LayoutParams();
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;  //悬浮窗的行为，比如说不可聚焦，非模态对话框等等
            layoutParams.width = view.getWidth();
            layoutParams.height = view.getHeight();
            layoutParams.x = view.getLeft() + this.getLeft();  //悬浮窗X的位置
            layoutParams.y = view.getTop() + this.getTop();  //悬浮窗Y的位置
            view.setVisibility(INVISIBLE);
        }
        windowManager.addView(gridView, layoutParams);
        move_is = MOVE_ADAPTER;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                break;
            case MotionEvent.ACTION_UP :
                if (move_is == MOVE_ADAPTER) {
                    closeWindow(ev.getX(), ev.getY());
                }
                break;
            case MotionEvent.ACTION_MOVE :
                if (move_is == MOVE_ADAPTER) {
                    updateWindow(ev);
                }
        }
        return super.onTouchEvent(ev);
    }

    private void updateWindow(MotionEvent ev) {
        if (move_is == MOVE_ADAPTER) {
            float x = ev.getRawX() - item_x;
            float y = ev.getRawY() - item_y;
            if (layoutParams != null) {
                layoutParams.x = (int) x;
                layoutParams.y = (int) y;
                windowManager.updateViewLayout(gridView, layoutParams);
            }
            int dropPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
            if (dropPosition == tempPosition || dropPosition == GridView.INVALID_POSITION) {
                return;
            } else {
                itemWindow(dropPosition);
            }
        }
    }

    private void itemWindow(int dropPosition) {
        TranslateAnimation translateAnimation;
        // 移动的位置在原位置前面时
        if (dropPosition < tempPosition) {
            for (int i = dropPosition; i < tempPosition; i++) {
                View view = getChildAt(i);
                View nextView = getChildAt(i + 1);
                float xValue = (nextView.getLeft() - view.getLeft()) * 1f / view.getWidth();
                float yValue = (nextView.getTop() - view.getTop()) * 1f / view.getHeight();
                translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, xValue, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, yValue);
                translateAnimation.setInterpolator(new LinearInterpolator());
                translateAnimation.setFillAfter(true);
                translateAnimation.setDuration(300);
                if (i == tempPosition - 1) {
                    translateAnimation.setAnimationListener(animationListener);
                }
                view.startAnimation(translateAnimation);
            }
        } else {
            // 移动的位置在原位置后面时
            for (int i = tempPosition + 1; i <= dropPosition; i++) {
                View view = getChildAt(i);
                View nextView = getChildAt(i + 1);
                float xValue = (nextView.getLeft() - view.getLeft()) * 1f / view.getWidth();
                float yValue = (nextView.getTop() - view.getTop()) * 1f / view.getHeight();
                translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, xValue, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, yValue);
                translateAnimation.setInterpolator(new LinearInterpolator());
                translateAnimation.setFillAfter(true);
                translateAnimation.setDuration(300);
                if (i == tempPosition - 1) {
                    translateAnimation.setAnimationListener(animationListener);
                }
                view.startAnimation(translateAnimation);
            }
        }
    }

    private void closeWindow(float x, float y) {
        if (gridView != null) {
            windowManager.removeView(gridView);
            gridView = null;
            layoutParams = null;
        }
        itemDrop();
        move_is = NO_MOVE_ADAPTER;
    }

    private void itemDrop() {
        if (tempPosition == position || tempPosition == GridView.INVALID_POSITION) {
            getChildAt(position).setVisibility(VISIBLE);
        } else {
            dragGridViewFun.exchangePosition();
        }
    }

    /**
     * 子项事件触发前需要调用此方法，不然会有镜像显示出来。无法删除
     */
    public void closeView() {
        if (gridView != null) {
            windowManager.removeView(gridView);
            gridView = null;
            layoutParams = null;
        }
        move_is = NO_MOVE_ADAPTER;
    }

    //放入是否移动item的值
    public void setMove_is(int move_is) {
        this.move_is = move_is;
    }

    public void setDragGridViewFun(DragGridViewFun dragGridViewFun) {
        this.dragGridViewFun = dragGridViewFun;
    }
}
