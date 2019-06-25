package com.lgpgit.open.toolutils.widget.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.lgpgit.open.toolutils.common.Constant;

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
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
        return super.onTouchEvent(ev);
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

    }

    //放入是否移动item的值
    public void setMove_is(int move_is) {
        this.move_is = move_is;
    }
}
