package com.chiquita.mcspsa.core.helper.background;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Button;
import android.widget.ProgressBar;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.helper.log.LogManager;

public class BackgroundProcess {

    private boolean cancelable;
    private Context context;
    private int currentProgress;
    private ProgressDialog dialog;
    private boolean inProgress;
    private int maxProgress;
    private String message;
    private int style;
    private BackgroundProcessEvent event;

    public BackgroundProcess(Context context) {
        this.context = context;
        this.style = 0;
        setCancelable(false);
    }

    public BackgroundProcess(Context context, String message) {
        this.context = context;
        this.style = 0;
        setCancelable(false);
        setMessage(message);
    }

    public BackgroundProcess(Context context, boolean cancelable, int maxProgress, String message, int style) {
        this.context = context;
        this.cancelable = cancelable;
        this.maxProgress = maxProgress;
        this.message = message;
        this.style = style;
    }

    public BackgroundProcess(boolean cancelable, Context context, int currentProgress, ProgressDialog dialog, boolean inProgress, int maxProgress, String message, int style, BackgroundProcessEvent event) {
        this.cancelable = cancelable;
        this.context = context;
        this.currentProgress = currentProgress;
        this.dialog = dialog;
        this.inProgress = inProgress;
        this.maxProgress = maxProgress;
        this.message = message;
        this.style = style;
        this.event = event;
    }

    public int getCurrentProgress() {
        return this.currentProgress;
    }

    public void setCurrentProgress(int paramInt) {
        this.currentProgress = paramInt;
    }

    public int getMaxProgress() {
        return this.maxProgress;
    }

    public void setMaxProgress(int paramInt) {
        this.maxProgress = paramInt;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String paramString) {
        this.message = paramString;
    }

    public int getStyle() {
        return this.style;
    }

    public void setStyle(int paramInt) {
        this.style = paramInt;
    }

    public boolean isCancelable() {
        return this.cancelable;
    }

    public void setCancelable(boolean paramBoolean) {
        this.cancelable = paramBoolean;
    }

    public boolean isInProgress() {
        return this.inProgress;
    }

    public void start(BackgroundProcessEvent paramBackgroundProcessEvent) {
        this.dialog = new ProgressDialog(this.context);
        this.dialog.setProgressStyle(this.style);
        this.dialog.setProgress(0);
        this.dialog.setMax(this.maxProgress);
        this.dialog.setCancelable(false);
        this.dialog.setMessage(this.message);
        this.inProgress = true;
        this.event = paramBackgroundProcessEvent;

        if (this.cancelable)
            this.dialog.setButton(-2, this.context.getResources().getString(R.string.cancel), (paramDialogInterface, paramInt) -> {
                BackgroundProcess.this.inProgress = false;
                paramDialogInterface.dismiss();
            });

        this.dialog.show();
        Button localButton = this.dialog.getButton(-2);
        ProgressBar localProgressBar = (ProgressBar) this.dialog.findViewById(android.R.id.progress);

        new Thread(() -> {
            try {
                event.process();
                ((Activity) BackgroundProcess.this.context).runOnUiThread(() -> {
                    try {
                        BackgroundProcess.this.inProgress = false;
                        BackgroundProcess.this.dialog.dismiss();
                        event.postProcess();
                        return;
                    } catch (Exception localException) {
                        localException.printStackTrace();
                        LogManager.getInstance().error("BackgroundProcess", localException.getMessage());
                    }
                });
                return;
            } catch (Exception localException) {
                while (true) {
                    localException.printStackTrace();
                    LogManager.getInstance().error("BackgroundProcess", localException.getMessage());
                }
            }
        }).start();
    }

    public void startWithoutDialog(BackgroundProcessEvent paramBackgroundProcessEvent) {
        this.event = paramBackgroundProcessEvent;
        new Thread(() -> {
            try {
                event.process();
                ((Activity) BackgroundProcess.this.context).runOnUiThread(() -> event.postProcess());
                return;
            } catch (Exception localException) {
                while (true) {
                    localException.printStackTrace();
                    LogManager.getInstance().error("BackgroundProcess", localException.getMessage());
                }
            }
        }).start();
    }

    public void stepProgress() {
        stepProgress(1);
    }

    public void stepProgress(int paramInt) {
        this.currentProgress = (paramInt + this.currentProgress);
        if (this.dialog != null)
            this.dialog.setProgress(this.currentProgress);
    }

    public void setDialogMessage(String Message) {
        if (this.dialog != null)
            this.dialog.setMessage(Message);
    }
}

