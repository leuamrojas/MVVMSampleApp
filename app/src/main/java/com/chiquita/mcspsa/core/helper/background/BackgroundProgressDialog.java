package com.chiquita.mcspsa.core.helper.background;

import android.app.ProgressDialog;
import android.content.Context;
import com.chiquita.mcspsa.R;

public class BackgroundProgressDialog {

    private ProgressDialog m_Dialog;
    private Context mCon;
    private String message;
    private String Title;
    private int pdialogStyle = 0;

    public BackgroundProgressDialog(Context context, String message, String title, int style) {
        this.mCon = context;
        this.message = message;
        this.Title = title;
        this.pdialogStyle = style;

        m_Dialog = new ProgressDialog(mCon);
        m_Dialog.setIcon(R.drawable.drawer_shadow);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(pdialogStyle);
        m_Dialog.setCancelable(false);
        m_Dialog.setMax(100);
    }


    public ProgressDialog pDialog() {
        return m_Dialog;
    }

}