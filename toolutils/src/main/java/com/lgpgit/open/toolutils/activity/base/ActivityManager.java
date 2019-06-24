package com.lgpgit.open.toolutils.activity.base;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Activity页面管理器
 * @author lugp
 * @date 2019/02/14
 */
public class ActivityManager extends Application {

	private static List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 添加一个Activity
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 遍历所有Activity,finish给定的Activity
     * @param activityName
     */
    public static void finishActivityByName(String activityName) {
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            if (activity.getClass().getName().equals(activityName)) {
                activity.finish();
                activityList.remove(i);
                i--;
            }
        }
    }

    //获得主菜单的位置
    private static int getMenu(String name) {
        for (int i = 0; i < activityList.size(); i++) {
            Activity activity = activityList.get(i);
            String strClassName = activity.getClass().getSimpleName();
            if (name.equals(strClassName)) {
                return i;
            } else {
                continue;
            }
        }
        return 0;
    }

    /**
     * 遍历所有Activity，回到主菜单，并finish其他Activity
     */
    public static void goMenu(String homeName) {
        int num = getMenu(homeName);
        for (int i = 0; i < activityList.size(); i++) {
            if (i > num) {
                Activity activity = activityList.get(i);
                activity.finish();
                activityList.remove(i);
                i--;
            }
        }
    }

    /**
     * 退出
     */
    public static void terminateApp() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
