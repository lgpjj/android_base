package com.lgpgit.open.androidbase.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lgpgit.open.androidbase.R;
import com.lgpgit.open.androidbase.StartApplication;
import com.lgpgit.open.toolutils.activity.base.AppBaseRetrofitActivity;

import retrofit2.Retrofit;

public class HomeMenuActivity extends AppBaseRetrofitActivity {

    @Override
    protected Retrofit setupRetrofit() {
        return ((StartApplication)getApplication()).retrofit;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_menu;
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void initializData() {

    }
}
