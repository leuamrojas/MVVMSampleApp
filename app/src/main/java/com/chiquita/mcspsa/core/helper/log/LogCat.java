/*
 * Copyright (c) 2015. Property of Rafael Ambruster
 */

package com.chiquita.mcspsa.core.helper.log;
import timber.log.Timber;

public class LogCat
        implements Log {

    public void error(String paramString1, String paramString2) {
        Timber.tag(paramString1);
        Timber.e(paramString2);
    }

    public void info(String paramString1, String paramString2) {
        Timber.tag(paramString1);
        Timber.i(paramString2);
    }

    public void warning(String paramString1, String paramString2) {
        Timber.tag(paramString1);
        Timber.w(paramString2);
    }

    public void wl(boolean paramBoolean) {
    }

}

