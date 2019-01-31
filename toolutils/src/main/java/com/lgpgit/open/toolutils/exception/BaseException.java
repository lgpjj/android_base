package com.lgpgit.open.toolutils.exception;

import com.lgpgit.open.toolutils.entiy.ApiException;

/**
 * 存放异常的接口
 *
 * @author lugp
 * @date 2019/01/30
 */
public abstract class BaseException {

    protected abstract ApiException handleException(Throwable e, String urlOrFun);
}
