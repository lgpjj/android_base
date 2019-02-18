package com.lgpgit.open.androidbase.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lgpgit.open.androidbase.R;
import com.lgpgit.open.androidbase.StartApplication;
import com.lgpgit.open.androidbase.entiy.TExFile;
import com.lgpgit.open.androidbase.httpcontant.Crash_dialogService;
import com.lgpgit.open.toolutils.activity.base.AppBaseRetrofitActivity;
import com.lgpgit.open.toolutils.database.Database;
import com.lgpgit.open.toolutils.entiy.ApiException;
import com.lgpgit.open.toolutils.entiy.HttpResquest;
import com.lgpgit.open.toolutils.others.BaseObserver;
import com.lgpgit.open.toolutils.util.ActivityUtils;

import java.io.File;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import static com.lgpgit.open.toolutils.common.Constant.THREAD_DELETE;

public class Crash_dialog extends AppBaseRetrofitActivity {

    //接收到的Intent对象
    private Intent intent;

    private Database database;
    private String filePath;

    private Button submit;
    private Button cancel;
    private TextView context;

    private Crash_dialogService crash_dialogService;

    @Override
    protected Retrofit setupRetrofit() {
        return ((StartApplication)getApplication()).retrofit;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crash_dialog;
    }

    @Override
    protected void setupView() {

        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);
        context = findViewById(R.id.content);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEx();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出app
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        File file = new File(filePath);
        String strFile = "请将日志文件" + file.getName() + "上传，方便问题的解决！对给您带来的不便，表示深深的抱歉！";
        context.setText(strFile);
    }

    @Override
    protected void initializData() {
        intent = getIntent();
        filePath = intent.getStringExtra("filePath");

        crash_dialogService = retrofit.create(Crash_dialogService.class);
    }

    private void submitEx() {
        //访问后的操作
        BaseObserver<HttpResquest<Object>> tExFileBaseObserver = new BaseObserver<HttpResquest<Object>>() {
            @Override
            protected String getUrl() {
                return "";
            }

            @Override
            protected String getFun() {
                return "submitEx";
            }

            @Override
            protected Context getActivityContext() {
                return Crash_dialog.this;
            }

            @Override
            protected void handleException(ApiException apiException, String type) {

            }

            @Override
            protected void updateUI(HttpResquest<Object> objectHttpResquest) throws Exception {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        };

        //读出文件信息
        File file = new File(filePath);
        RequestBody filebody = RequestBody.create(MediaType.parse("text/plain"), file);

        //定义访问
        Observable<HttpResquest<Object>> ex = crash_dialogService.postEx(filebody);
        ActivityUtils.showProgressDialogUpload(Crash_dialog.this);
        doObservable(ex, tExFileBaseObserver, THREAD_DELETE, 3, 3);
    }
}
