package com.lgpgit.open.androidbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lgpgit.open.androidbase.BuildConfig;
import com.lgpgit.open.androidbase.R;
import com.lgpgit.open.androidbase.StartApplication;
import com.lgpgit.open.androidbase.common.AppCommon;
import com.lgpgit.open.androidbase.common.AppConstant;
import com.lgpgit.open.androidbase.entiy.TLogin;
import com.lgpgit.open.toolutils.activity.base.AppBaseRetrofitPermissionActivity;
import com.lgpgit.open.toolutils.common.Constant;
import com.lgpgit.open.toolutils.common.Permissions;
import com.lgpgit.open.toolutils.database.Database;
import com.lgpgit.open.toolutils.util.ActivityUtils;

import java.util.List;

import retrofit2.Retrofit;

public class MainActivity extends AppBaseRetrofitPermissionActivity {

    private Database database;

    private EditText user;
    private EditText password;
    private Button login;
    private Button register;

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
        user = findViewById(R.id.user_text);
        password = findViewById(R.id.password_text);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取登录信息
                String userText = String.valueOf(user.getText());
                String passwordText = String.valueOf(password.getText());
                //验证帐号
                List<TLogin> loginList = database.getInfoByKey(TLogin.class, AppConstant.CODE, AppConstant.CODE_LOGIN);
                for (TLogin login : loginList) {
                    String userName = login.getUserName();
                    String passWord = login.getPassWord();
                    if (userName.equals(userText)) {
                        if (passWord.equals(passwordText)) {
                            //登录成功
                            ActivityUtils.showProgressDialogLogin(MainActivity.this);
                            AppCommon.login = login;
                            Intent intent = new Intent(MainActivity.this, HomeMenuActivity.class);
                            ActivityUtils.dismissProgressDialog();
                            startActivity(intent);
                        } else {
                            ActivityUtils.showShort(MainActivity.this, "密码输入错误");
                        }
                    } else {
                        ActivityUtils.showShort(MainActivity.this, "用户名输入错误");
                    }
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent, Constant.INT_ONE);
            }
        });
    }

    @Override
    protected void initializData() {
        database = Database.getDatabase(MainActivity.this, BuildConfig.DB_NAME);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey("userName")) {
                user.setText(bundle.getString("userName"));
                password.setText(bundle.getString("passWord"));
            }
        }
    }

    @Override
    protected void onResumeOther() {

    }

    @Override
    protected void onActivityResultOther(int requestCode, int resultCode, Intent data) {
        Bundle dataBundle = data.getExtras();
        if (requestCode == Constant.INT_ONE) {
            TLogin login = new TLogin();
            login.setUserName(dataBundle.getString("userName"));
            login.setPassWord(dataBundle.getString("passWord"));
            AppCommon.login = login;
        }
    }
}
