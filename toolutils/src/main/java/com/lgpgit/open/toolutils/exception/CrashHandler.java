package com.lgpgit.open.toolutils.exception;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接受app没有捕获的异常并记载到文件中
 *
 * @author lugp
 * @date 2019/01/30
 */
public abstract class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";
    private Thread.UncaughtExceptionHandler mDefaultHandler;// 系统默认的UncaughtException处理类
    private Context mContext;// 程序的Context对象
    private Map<String, String> info = new HashMap<String, String>();// 用来存储设备信息和异常信息
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 用于格式化日期,作为日志文件名的一部分
    private SimpleDateFormat formatSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 用于格式化日期,作为日志文件内容分割线
    private String mfilePage;
    private Class dialogContext = getDialogContext();

    protected abstract Class getDialogContext();

    //文件路径
    protected abstract int getFilePage();

    public CrashHandler() {
    }

    //初始化
    public void init(Context context) {
        mContext = context;
        mfilePage = mContext.getString(getFilePage());
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果自定义的没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);// 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String time = format.format(new Date());
            String fileName = "crash-" + time + ".log";
            String filePath = Environment.getExternalStorageDirectory() + "/" + mfilePage;
            DialogActivity(filePath + "/" + fileName, mContext);
            System.exit(0);
        }
    }

    protected void DialogActivity(String filePath, Context context) {
        Intent intent = new Intent(context, dialogContext);
        intent.putExtra("filePath", filePath);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     *            异常信息
     * @return true 如果处理了该异常信息;否则返回false.
     */
    public boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        } else {
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出。", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }.start();
            // 收集设备参数信息
            collectDeviceInfo(mContext);
            // 保存日志文件
            saveCrashInfo2File(ex);
            return true;
        }
    }

    private String saveCrashInfo2File(Throwable ex) {
        String time = format.format(new Date());
        String fileName = "crash-" + time + ".log";
        String filePath = Environment.getExternalStorageDirectory() + "/" + mfilePage;
        File file = new File(filePath + "/" + fileName);


        StringBuffer sb = new StringBuffer();
        if (!file.exists()) {
            for (Map.Entry<String, String> entry : info.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\r\n");
            }
        }
        sb.append("********************************" + formatSS.format(new Date()) +"********************************\r\n");
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();// 记得关闭
        String result = writer.toString();
        sb.append(result);
        // 保存文件
        //long timetamp = System.currentTimeMillis();

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(filePath);
                //Log.i("CrashHandler", dir.toString());
                if (!dir.exists()) {
                    dir.mkdir();
                }
                if (!file.exists()) {
                    FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
                    fos.write(sb.toString().getBytes());
                    fos.close();
                } else {
                    FileWriter fileWriter = new FileWriter(filePath + "/" + fileName, true);
                    fileWriter.write(sb.toString());
                    fileWriter.close();
                }
                return fileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void collectDeviceInfo(Context mContext) {
        try {
            PackageManager pm = mContext.getPackageManager();// 获得包管理器
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);// 得到该应用的信息，即主Activity
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
                info.put("versionName", versionName);
                info.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Field[] fields = Build.class.getDeclaredFields();// 反射机制
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                info.put(field.getName(), field.get("").toString());
                //Log.d(TAG, field.getName() + ":" + field.get(""));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
