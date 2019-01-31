package com.lgpgit.open.toolutils.others;

import android.content.Context;

import com.lgpgit.open.toolutils.entiy.ApiException;
import com.lgpgit.open.toolutils.exception.ErrorException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * rxjava+retrofit访问后操作
 *
 * @author lugp
 * @date 2019/01/30
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private static final String ON_NEXT = "ON_NEXT";
    private static final String ON_ERROR = "ON_ERROR";

    private ErrorException errorException = new ErrorException();

    private Context context = getActivityContext();

    //访问地址
    private String url = getUrl();
    //方法名称
    private String fun = getFun();

    protected abstract String getUrl();

    protected abstract String getFun();

    protected abstract Context getActivityContext();

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        try {
            updateUI(t);
        } catch (Exception e) {
            ApiException apiException = errorException.handleException(e, fun);
            handleException(apiException, ON_NEXT);
        }
    }

    /**
     * 处理exception方法
     *
     * @param apiException
     * @param type：类型（ON_NEXT，ON_ERROR）
     */
    protected abstract void handleException(ApiException apiException, String type);

    protected abstract void updateUI(T t) throws Exception;

    @Override
    public void onError(Throwable e) {
        ApiException apiException = errorException.handleException(e, url);
        handleException(apiException, ON_ERROR);
    }
    @Override
    public void onComplete() {

    }
}
