package com.lgpgit.open.toolutils.others;

import android.content.Context;

import com.lgpgit.open.toolutils.activity.base.ActivityManager;
import com.lgpgit.open.toolutils.common.Common;
import com.lgpgit.open.toolutils.common.Constant;
import com.lgpgit.open.toolutils.database.Database;
import com.lgpgit.open.toolutils.entiy.ApiException;
import com.lgpgit.open.toolutils.entiy.TException;
import com.lgpgit.open.toolutils.exception.ErrorException;
import com.lgpgit.open.toolutils.util.ActivityUtils;

import java.util.Calendar;

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

    private Context context;
    private Database database;
    private int addTime = Constant.INT_THIRTIETH;

    //访问地址
    private String url = getUrl();
    //方法名称
    private String fun = getFun();

    protected abstract String getUrl();

    protected abstract String getFun();

    /**
     * 使用默认删除时间
     * @param context
     * @param database
     */
    public BaseObserver(Context context, Database database) {
        this.context = context;
        this.database = database;
    }

    /**
     * 传递错误信息的保存时间
     * @param context
     * @param database
     * @param addTime
     */
    public BaseObserver(Context context, Database database, int addTime) {
        this.context = context;
        this.database = database;
        this.addTime = addTime;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        try {
            ActivityUtils.dismissProgressDialog();
            updateUI(t);
        } catch (Exception e) {
            ApiException apiException = errorException.handleException(e, fun);
            //保存到数据库中
            saveExce(apiException, ON_NEXT);
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
        //保存到数据库中
        saveExce(apiException, ON_ERROR);
        handleException(apiException, ON_ERROR);
    }

    protected void saveExce(ApiException apiException, String onError) {
        TException exception = new TException();
        exception.setMessage(apiException.getMessage());
        exception.setType(onError);
        exception.setCode(Constant.EXCEPTION_CODE);
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        exception.setSaveTime(calendar.getTime());
        Common.addTime(Calendar.DATE, calendar.getTime(), addTime);
        database.saveInfo(exception);
    }

    @Override
    public void onComplete() {

    }
}
