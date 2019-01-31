package com.lgpgit.open.toolutils.common;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 共同方法类
 *
 * @author lugp
 * @date 2018/11/8
 */
public class Common {

    /**
     * 控件修改时，保留小数位
     *
     * @param editText：控件
     * @param val：数值
     * @param num：小数位数
     */
    public static void retainedDecimal(EditText editText, CharSequence val, int num) {
        //判断小数位数是否不为小于零的数
        if (num >= 1) {
            //删除“.”后面超过num位后的数据
            if (val.toString().contains(".")) {
                if (val.length() - 1 - val.toString().indexOf(".") > num) {
                    val = val.toString().subSequence(0,
                            val.toString().indexOf(".") + 1 + num);
                    editText.setText(val);
                    editText.setSelection(val.length()); //光标移到最后
                }
            }
            //如果"."在起始位置,则起始位置自动补0
            if (val.toString().trim().substring(0).equals(".")) {
                val = "0" + val;
                editText.setText(val);
                editText.setSelection(2);
            }

            //如果起始位置为0,且第二位跟的不是".",则无法后续输入
            if (val.toString().startsWith("0")
                    && val.toString().trim().length() > 1) {
                if (!val.toString().substring(1, 2).equals(".")) {
                    editText.setText(val.subSequence(0, 1));
                    editText.setSelection(1);
                    return;
                }
            }
        }
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static Boolean stringIsNull(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * 判断object类型是否为空
     *
     * @param obj
     * @return
     */
    public static Boolean ObjectIsNull(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    /**
     * 沉浸式标头
     *
     * @param activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarNoColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * 文件是否存在
     *
     * @param strFile
     * @return
     */
    public boolean fileIsExists(String strFile) {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    /**
     * 获得文件内的内容
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public byte[] getFileInfo(String filePath, String fileName) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(file, fileName));
                int length = fileInputStream.available();
                byte[] bytes = new byte[length];
                return bytes;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断是否有网络
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 增加时间/减少时间
     * @param type：时间格式——Calendar.MONTH
     * @param time：时间
     * @param timeNum：增加数/减少数
     * @return
     */
    public static Date addTime(@NonNull int type, @NonNull Date time, int timeNum) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(type, timeNum);
        return c.getTime();
    }

    /**
     * 时间转字符串
     *
     * @param type
     * @param time
     * @return
     */
    public static String dateToString(@NonNull String type, @NonNull Date time) {
        SimpleDateFormat df = new SimpleDateFormat("type");
        return df.format(time);
    }

    /**
     * 字符串转时间
     *
     * @param type
     * @param time
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(@NonNull String type, @NonNull String time) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("type");
        return df.parse(time);
    }
}
