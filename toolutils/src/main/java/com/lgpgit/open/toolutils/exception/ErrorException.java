package com.lgpgit.open.toolutils.exception;

import android.net.ParseException;

import com.google.gson.JsonParseException;
import com.lgpgit.open.toolutils.entiy.ApiException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * 网络访问exception的收集类
 *
 * @author lugp
 * @date 2019/01/30
 */
public class ErrorException extends BaseException {

    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;

    /**
     * 协议错误
     */
    public static final int HTTP_ERROR = 1003;

    @Override
    public ApiException handleException(Throwable e, String urlOrFun) {
        ApiException ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //解析错误
            ex = new ApiException(PARSE_ERROR, e.getMessage(), urlOrFun);
            ex.setDescribe("解析错误");
            return ex;
        } else if (e instanceof ConnectException) {
            //网络错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage(), urlOrFun);
            ex.setDescribe("网络错误");
            return ex;
        } else if (e instanceof UnknownHostException || e instanceof SocketTimeoutException || e instanceof SocketException) {
            //连接错误
            ex = new ApiException(NETWORK_ERROR, e.getMessage(), urlOrFun);
            ex.setDescribe("连接错误");
            return ex;
        } else {
            //未知错误
            ex = new ApiException(UNKNOWN, e.getMessage(), urlOrFun);
            ex.setDescribe("未知错误");
            return ex;
        }
    }
}
