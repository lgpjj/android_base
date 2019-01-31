package com.lgpgit.open.toolutils.entiy;

/**
 * exception对象类
 *
 * @author lugp
 * @date 2019/01/30
 */
public class ApiException extends Exception {

    private int code;
    private String displayMessage;
    private String describe;
    private String urlOrFun;

    public ApiException(int code, String displayMessage, String describe, String urlOrFun) {
        this.code = code;
        this.displayMessage = displayMessage;
        this.describe = describe;
        this.urlOrFun = urlOrFun;
    }

    public ApiException(int code, String displayMessage, String urlOrFun) {
        this.code = code;
        this.displayMessage = displayMessage;
        this.urlOrFun = urlOrFun;
    }

    public ApiException(String message, int code, String displayMessage, String urlOrFun) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
        this.urlOrFun = urlOrFun;
    }

    public ApiException(String message, int code, String displayMessage, String describe, String urlOrFun) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
        this.describe = describe;
        this.urlOrFun = urlOrFun;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getUrlOrFun() {
        return urlOrFun;
    }

    public void setUrlOrFun(String urlOrFun) {
        this.urlOrFun = urlOrFun;
    }
}
