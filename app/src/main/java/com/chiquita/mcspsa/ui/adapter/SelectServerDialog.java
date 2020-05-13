package com.chiquita.mcspsa.ui.adapter;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.ui.adapter.CommonAdapterListener;
import com.chiquita.mcspsa.data.model.CoreServerEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SelectServerDialog extends DialogFragment implements CommonAdapterListener<CoreServerEntity> {

    public static final String EXTRA_OBJECT_SERVER = "SERVER";

    public CallbackResult callbackResult;

    private ArrayList<CoreServerEntity> list;

    private ServerAdapter adapter;

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    private int request_code = 0;

    private View root_view;

    public static SelectServerDialog getInstance(ArrayList<CoreServerEntity> list) {
        SelectServerDialog fragment = new SelectServerDialog();
        Bundle args = new Bundle();
        Gson gSon = new Gson();
        args.putString(EXTRA_OBJECT_SERVER, gSon.toJson(list));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_OBJECT_SERVER)) {
                Gson gSon = new Gson();
                list = gSon.fromJson(bundle.getString(EXTRA_OBJECT_SERVER), new TypeToken<ArrayList<CoreServerEntity>>() {
                }.getType());
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root_view = inflater.inflate(R.layout.view_common_fragment_dialog, container, false);
        initComponent();
        return root_view;
    }

    private void initComponent() {
        final ImageView img_clear = root_view.findViewById(R.id.img_clear);
        final EditText et_search = root_view.findViewById(R.id.et_search);
        RecyclerView recyclerView = root_view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new ServerAdapter(getActivity(), list, this, false);
        recyclerView.setAdapter(adapter);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(et_search.getText().toString());
                if (!et_search.getText().toString().trim().equals("")) {
                    img_clear.setVisibility(View.VISIBLE);
                } else {
                    img_clear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        img_clear.setOnClickListener(v -> et_search.setText(""));
        (root_view.findViewById(R.id.img_back)).setOnClickListener(v -> dismissDialog());
    }

    private void sendDataResult(CoreServerEntity method) {
        if (callbackResult != null) {
            callbackResult.sendResult(request_code, method);
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

    @Override
    public void onSelected(CoreServerEntity selectable) {
        sendDataResult(selectable);
        dismissDialog();
    }

    public interface CallbackResult {
        void sendResult(int requestCode, CoreServerEntity method);
    }
}