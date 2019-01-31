package com.lgpgit.open.toolutils.service;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;

import com.lgpgit.open.toolutils.util.PhoneUtil;

import java.io.File;

/**
 * 下载服务
 *
 * @author lugp
 * @date 2019/01/31
 */
@SuppressLint("Registered")
public class DownloadService extends Service {
    private DownloadManager mDownloadManager;
    private long enqueue;
    private BroadcastReceiver receiver;
    private String apk_url;//= IPAddress.DEFAULT_IP+"/portrait/app-youni.apk";
    //    private  static final String APK_NAME="youni.apk";
    private String apk_name;//="youni_"+ System.currentTimeMillis()+"_.apk";

    private String name;

    private String app_name;

    //文件地址
    private String fileUri;

    @Override
    public IBinder onBind(Intent intent) {
        System.err.println("onBind");
        return null;
    }
    @Override
    public void onCreate() {
        System.err.println("onCreate");
        super.onCreate();
        /*删除以前下载的安装包*/
        RecursionDeleteFile(new File(Environment.getExternalStorageDirectory() + "/download/"));
    }

    @Override
    public void onStart(Intent intent, int startId) {
        System.err.println("onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.err.println("onStartCommand");
        apk_url = intent.getStringExtra("app_url");
        apk_name = intent.getStringExtra("apk_name");
        name = intent.getStringExtra("name");
        app_name = intent.getStringExtra("app_name");
        final String application_id = intent.getStringExtra("application_id");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (enqueue == id) {

                    File file = new File(Environment.getExternalStorageDirectory() + "/download/" +apk_name);

                    intent = new Intent(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(context, application_id + ".fileProvider", file);
                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    } else {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    }
                    startActivity(intent);
                }
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startDownload();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        System.err.println("onDestroy");
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startDownload() {
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apk_url));
        request.setDescription("正在为您下载" + app_name +"的最新版本");
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        if (PhoneUtil.ExistSDCard()) {
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, apk_name);
        } else {
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, apk_name);
        }
        enqueue = mDownloadManager.enqueue(request);
    }
    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    public void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
}
