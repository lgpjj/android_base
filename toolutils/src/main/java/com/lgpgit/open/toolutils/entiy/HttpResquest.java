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
    private Boolean success;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}