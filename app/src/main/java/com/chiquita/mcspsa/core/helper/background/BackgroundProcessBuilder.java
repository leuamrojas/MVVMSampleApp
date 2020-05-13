package com.chiquita.mcspsa.core.helper.background;

import android.app.ProgressDialog;
import android.content.Context;

public class BackgroundProcessBuilder {
    private boolean cancelable;
    private Context context;
    private int currentProgress;
    private ProgressDialog dialog;
    private boolean inProgress;
    private int maxProgress;
    private String message;
    private int style;
    private BackgroundProcessEvent event;

    public BackgroundProcessBuilder setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    public BackgroundProcessBuilder setContext(Context context) {
        this.context = context;
        return this;
    }

    public BackgroundProcessBuilder setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        return this;
    }

    public BackgroundProcessBuilder setDialog(ProgressDialog dialog) {
        this.dialog = dialog;
        return this;
    }

    public BackgroundProcessBuilder setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
        return this;
    }

    public BackgroundProcessBuilder setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        return this;
    }

    public BackgroundProcessBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public BackgroundProcessBuilder setStyle(int style) {
        this.style = style;
        return this;
    }

    public BackgroundProcessBuilder setEvent(BackgroundProcessEvent event) {
        this.event = event;
        return this;
    }

    public BackgroundProcess createBackgroundProcess() {
        return new BackgroundProcess(cancelable, context, currentProgress, dialog, inProgress, maxProgress, message, style, event);
    }
}