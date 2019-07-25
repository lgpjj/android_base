package com.lgpgit.open.toolutils.application.base;

import android.app.Application;
import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Application基础类
 *
 * @author lugp
 * @date 2019/02/14
 */
public abstract class baseApplication extends Application {

    private int time_out = initializTime();

    protected abstract int initializTime();

    private String baseUrl = initializBaseUrl();

    protected abstract String initializBaseUrl();

    public Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        createEx();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(time_out, TimeUnit.SECONDS)
                .readTimeout(time_out, TimeUnit.SECONDS)
                .writeTimeout(time_out, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder().setDateFormat("YYYY-MM-DD HH:mm:ss").create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * app异常退出后的操作
     */
    public abstract void createEx();
}
