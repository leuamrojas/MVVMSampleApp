package com.chiquita.mcspsa.ui.setup;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.chiquita.mcspsa.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class SetupBookDateDialog extends DialogFragment {

    public CallbackResult callbackResult;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    private Date date = new Date();
    private int request_code = 0;
    private String title = "SELECT STORE VISIT DATE";
    private View root_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.view_common_picker_date, container, false);
        initComponent();
        return root_view;
    }

    private void initComponent() {

        (root_view.findViewById(R.id.img_back)).setOnClickListener(v -> dismissDialog());
        ((TextView) root_view.findViewById(R.id.tv_title)).setText(title);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 2);

        CalendarPickerView calendar = root_view.findViewById(R.id.calendar_view);
        Date today = new Date();
        calendar.init(today, nextYear.getTime()).withSelectedDate(date);

        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                sendDataResult(date);
                dismissDialog();
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    private void sendDataResult(Date date) {
        if (callbackResult != null) {
            callbackResult.sendResult(request_code, date);
        }
    }

    private void dismissDialog() {
        dismiss();
    }

    @Override
    public int getTheme() {
        return R.style.AppTheme_FullScreenDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setRequestCode(int request_code) {
        this.request_code = request_code;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public interface CallbackResult {
        void sendResult(int requestCode, Date date);
    }

}