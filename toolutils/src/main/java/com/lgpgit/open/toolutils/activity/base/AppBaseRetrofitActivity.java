package com.lgpgit.open.toolutils.activity.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.lgpgit.open.toolutils.common.Constant;
import com.lgpgit.open.toolutils.enums.ActivityLifeCycleEvent;
import com.lgpgit.open.toolutils.others.BaseObserver;
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
public abstract class AppBaseRetrofitActivity extends Activity {

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
    protected abstract void setupView();

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
    }

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
