package com.lgpgit.open.androidbase;

import com.lgpgit.open.androidbase.exception.CrashExHandler;
import com.lgpgit.open.toolutils.application.base.baseApplication;

/**
 * @author lugp
 * @date 2019/2/15
 */
public class StartApplication extends baseApplication {
    @Override
    protected int initializTime() {
        return 3;
    }

    @Override
    protected String initializBaseUrl() {
        return "http://localhost:9000/fbms/";
    }

    @Override
    public void createEx() {
        CrashExHandler crashExHandler = new CrashExHandler();
        crashExHandler.init(this);
    }
}
