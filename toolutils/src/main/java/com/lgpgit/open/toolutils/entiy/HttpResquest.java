package com.lgpgit.open.toolutils.entiy;

/**
 * http访问返回对象类
 *
 * @author lugp
 * @date 2019/01/30
 */
public class HttpResquest<T> {

    private String code;
    private String msg;
    private String func;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}