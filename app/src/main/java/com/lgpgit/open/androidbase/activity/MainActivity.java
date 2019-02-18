package com.lgpgit.open.androidbase.activity;

import android.content.Intent;

import com.lgpgit.open.androidbase.R;
import com.lgpgit.open.androidbase.StartApplication;
import com.lgpgit.open.toolutils.activity.base.AppBaseRetrofitPermissionActivity;
import com.lgpgit.open.toolutils.common.Permissions;

import retrofit2.Retrofit;

public class MainActivity extends AppBaseRetrofitPermissionActivity {

    @Override
    protected String[] initializePermissions() {
        return new String[]{Permissions.read_external_storage, Permissions.write_external_storage};
    }

    @Override
    protected int initializePermissionLayout() {
        return R.layout.activity_permission;
    }

    @Override
    protected Retrofit setupRetrofit() {
        return ((StartApplication)getApplication()).retrofit;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void startupView() {

    }

    @Override
    protected void initializData() {

    }

    @Override
    protected void onResumeOther() {

    }

    @Override
    protected void onActivityResultOther(int requestCode, int resultCode, Intent data) {

    }
}
