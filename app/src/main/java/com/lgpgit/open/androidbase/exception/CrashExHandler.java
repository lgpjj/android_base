package com.lgpgit.open.androidbase.entiy;

import android.content.Context;
import android.content.Intent;

import com.lgpgit.open.androidbase.R;
import com.lgpgit.open.androidbase.activity.Crash_dialog;
import com.lgpgit.open.toolutils.exception.CrashHandler;

/**
 * @author lugp
 * @date 2019/2/15
 */
public class CrashExHandler extends CrashHandler {
    @Override
    protected Class getDialogContext() {
        return Crash_dialog.class;
    }

    @Override
    protected int getFilePage() {
        return R.string.app_name;
    }
}
