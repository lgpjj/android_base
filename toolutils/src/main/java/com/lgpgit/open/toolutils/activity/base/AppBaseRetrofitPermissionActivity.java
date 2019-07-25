package com.lgpgit.open.toolutils.activity.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lgpgit.open.toolutils.common.Constant;
import com.lgpgit.open.toolutils.enums.ActivityLifeCycleEvent;
import com.lgpgit.open.toolutils.others.BaseObserver;
import com.lgpgit.open.toolutils.others.PermissionsActivity;
import com.lgpgit.open.toolutils.others.PermissionsChecker;
import com.lgpgit.open.toolutils.others.RetryFunction;
import com.lgpgit.open.toolutils.util.ActivityUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import retrofit2.Retrofit;

/**
 * @author lugp
 * @date 2019/02/14
 */
public abstract class AppBaseRetrofitPermissionActivity extends Activity {

    // 所需的全部权限
    protected String[] permissions = initializePermissions();

    //初始化所需的全部权限
    protected abstract String[] initializePermissions();

    private static final int REQUEST_CODE = 0; // 请求码

    private PermissionsChecker mPermissionsChecker; // 权限检测器

    protected int permissionLayout = initializePermissionLayout();
    //初始化所需的全部权限的弹出框
    protected abstract int initializePermissionLayout();

    protected Context mContext;

    protected Retrofit retrofit;

    private final PublishSubject<ActivityLifeCycleEvent> lifeCycleEventSubject = PublishSubject.create();

    private Observable<ActivityLifeCycleEvent> observableLife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lifeCycleEventSubject.onNext(ActivityLifeCycleEvent.CREATE);
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);

        mContext = getApplicationContext();

        observableLife = lifeCycleEventSubject.filter(new Predicate<ActivityLifeCycleEvent>() {
            @Override
            public boolean test(ActivityLifeCycleEvent lifeCycleEvent) throws Exception {
                //当生命周期为DESTROY状态时，发射事件
                return lifeCycleEvent.equals(ActivityLifeCycleEvent.PAUSE);
            }
        }).take(1);
        retrofit = setupRetrofit();

        setContentView(getLayoutId());
        // 初始化组件
        setupView();
        // 初始化数据
        initializData();
    }

    //初始化Retrofit
    protected abstract Retrofit setupRetrofit();

    // 布局文件ID
    protected abstract int getLayoutId();

    // 初始化组件
    protected void setupView() {
        mPermissionsChecker = new PermissionsChecker(this);
        startupView();
    }

    protected abstract void startupView();

    // 初始化数据
    protected abstract void initializData();

    protected <T> void doObservable(Observable<T> observable, BaseObserver<T> observer,
                                    @NonNull String type, int retryDelaySeconds, int retryCount) {
        RetryFunction retryFunction;
        if (retryDelaySeconds >= 0) {
            if (retryCount >= 0) {
                retryFunction = new RetryFunction(retryDelaySeconds, retryCount);
            } else {
                retryFunction = new RetryFunction(retryDelaySeconds, Constant.STR_ZERO);
            }
        } else {
            if (retryCount >= 0) {
                retryFunction = new RetryFunction(retryCount, Constant.STR_ONE);
            } else {
                retryFunction = new RetryFunction();
            }
        }
        if (type.equals(Constant.THREAD_PERSIST)) {
            observable.retryWhen(retryFunction)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            observable.takeUntil(observableLife)
                    .retryWhen(retryFunction)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    @Override
    protected void onRestart() {
        lifeCycleEventSubject.onNext(ActivityLifeCycleEvent.START);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        lifeCycleEventSubject.onNext(ActivityLifeCycleEvent.RESUME);
        super.onResume();
        ActivityUtils.dismissProgressDialog();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(permissions)) {
            startPermissionsActivity();
        }
        onResumeOther();
    }

    protected abstract void onResumeOther();

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, permissionLayout, permissions);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
            onActivityResultOther(requestCode, resultCode, data);
        }
    }

    protected abstract void onActivityResultOther(int requestCode, int resultCode, Intent data);

    @Override
    protected void onPause() {
        ActivityUtils.dismissProgressDialog();
        lifeCycleEventSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        ActivityUtils.dismissProgressDialog();
        lifeCycleEventSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.dismissProgressDialog();
        lifeCycleEventSubject.onNext(ActivityLifeCycleEvent.DESTROY);
        super.onDestroy();
    }
}
