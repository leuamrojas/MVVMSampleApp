package com.chiquita.mcspsa.ui.security;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.balysv.materialripple.MaterialRippleLayout;
import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.CoreMobileManager;
import com.chiquita.mcspsa.core.CoreSecurityManager;
import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.core.ui.CoreBaseActivity;
import com.chiquita.mcspsa.data.api.request.CoreApiUtil;
import com.chiquita.mcspsa.data.api.response.security.CoreLoginResp;
import com.chiquita.mcspsa.data.model.CoreServerEntity;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.ui.adapter.SelectServerDialog;
import com.chiquita.mcspsa.ui.location.LocationActivity;
import com.chiquita.mcspsa.viewmodel.SecurityViewModel;
import com.chiquita.mcspsa.viewmodel.contracts.SecurityContract;
import com.chiquita.mcspsa.worker.ConstantsWorker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnTextChanged;

/**
 * A login screen that offers login via username/password. Extends from {@link CoreBaseActivity}
 */

public class LoginSignInActivity extends CoreBaseActivity implements SecurityContract.View {

    @BindView(R.id.txv_email)
    public TextInputEditText txv_email;

    @BindView(R.id.txv_password)
    public TextInputEditText txv_password;

    @BindView(R.id.txv_password_reset)
    public TextView txv_password_reset;

    @BindView(R.id.txv_email_sign_in)
    public TextView txv_email_sign_in;

    @BindView(R.id.passwordWrapper)
    public TextInputLayout passwordWrapper;

    @BindView(R.id.emailWrapper)
    public TextInputLayout emailWrapper;

    @BindView(R.id.txv_server)
    public AppCompatTextView txv_server_name;

    @BindView(R.id.select_server)
    public MaterialRippleLayout select_server;

    private SecurityContract.ViewModel securityViewModel;

    private CoreServerEntity currentServer;

    protected FragmentTransaction transaction;

    protected FragmentManager fragmentManager;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        init();
    }

    @Override
    protected SecurityContract.ViewModel getViewModel() {
        return securityViewModel;
    }

    @Override
    protected int getContentView() {
        return R.layout.view_login_page_signin;
    }

    @Override
    protected void initializeUI() {
        super.initializeUI();
        txv_email_sign_in.setOnClickListener(view -> doApplicationLogin());
        select_server.setOnClickListener(view -> selectServer());
        txv_password_reset.setOnClickListener(view -> showMessage(getString(R.string.no_implemented_yet)));
    }

    @Override
    protected void onUserLoaded() {
        if (user != null && user.isActive()) {
            continueTo();
        }
    }

    @OnTextChanged(R.id.txv_email)
    public void changedTextOnEmail() {
        emailWrapper.setError(null);
    }

    @OnTextChanged(R.id.txv_password)
    public void changedTextOnPassword() {
        passwordWrapper.setError(null);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void initializeVM() {
        securityViewModel = ViewModelProviders.of(this).get(SecurityViewModel.class);
        currentServer = Hawk.get(CoreMobileManager.getInstance().getHawkKeys(2));
        restore();
    }

    private void restore() {
        if (currentServer != null)
            this.txv_server_name.setText(this.currentServer.getServerName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean validate() {
        passwordWrapper.setErrorEnabled(true);
        emailWrapper.setErrorEnabled(true);
        passwordWrapper.setError(null);
        emailWrapper.setError(null);

        String username = txv_email.getText().toString();
        if (TextUtils.isEmpty(username)) {
            emailWrapper.setError(getString(R.string.error_invalid_username));
            showMessage(getString(R.string.error_invalid_username));
            txv_email.requestFocus();
            return false;
        }

        String password = txv_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordWrapper.setError(getString(R.string.error_invalid_password));
            showMessage(getString(R.string.error_invalid_password));
            txv_password.requestFocus();
            return false;
        }

        user = new CoreUserEntity();
        user.setPassword(CoreSecurityManager.getInstance().encrypt(password));
        user.setUsername(username.toUpperCase());
        user.setServerId(currentServer.getServerId());
        user.setServerName(currentServer.getServerName());
        user.setServerAddress(currentServer.getServerAddress());

        return true;
    }

    public void doApplicationLogin() {
            if (validate()) {
                securityViewModel.doApplicationLogin(CoreApiUtil.createLoginRequest(user));
                LiveData<ApiResponseSingle> sl = securityViewModel.doApplicationLogin();
                sl.observe(this, response -> {
                        if (response != null) {
                        switch (response.status) {
                            case LOADING:
                                return;

                            case ERROR:
                                showProgressLockable(false);
                                processErrorResponse(response.error);
                                sl.removeObservers(this);
                                return;

                            case SUCCESS:
                                showProgressLockable(false);
                                processLoginResponse(response.data);
                                sl.removeObservers(this);
                                return;
                        }
                    }
                });
            }
    }

    private void processLoginResponse(Object data) {

        if (data instanceof CoreLoginResp) {

            if (data == null) {
                showMessage(getString(R.string.invalid_request));
                return;
            }

            if (((CoreLoginResp) data).getToken() == null || ((CoreLoginResp) data).getToken().isEmpty()) {
                showMessage(((CoreLoginResp) data).getUser());
                return;
            }

            if (((CoreLoginResp) data).getIsAccessibleResp() == null || ((CoreLoginResp) data).getRegisterAccessResp() == null) {
                showMessage(getString(R.string.a_r));
                return;
            }

            if (!((CoreLoginResp) data).getIsAccessibleResp().getRows().get(0).getStatus().equalsIgnoreCase("OK")) { // changes by Ankit: previously the method was equals(), now it is equalIgnoreCase
                showMessage(((CoreLoginResp) data).getIsAccessibleResp().getRows().get(0).getStatus());
                return;
            }

            if (((CoreLoginResp) data).getRegisterAccessResp().getErrorMessage() != null) {
                showMessage(((CoreLoginResp) data).getRegisterAccessResp().getErrorMessage());
                return;
            }

            user.setToken(((CoreLoginResp) data).getToken());
            user.setActive(true);
            Hawk.put(CoreMobileManager.getInstance().getHawkKeys(1), user.getUsername());
            CoreConfigurationManager.getInstance().save(this);
            saveUser(user);

            continueTo();
        }
    }

    private void continueTo() {
        finishAffinity();
        Intent intent = new Intent(getApplicationContext(), LocationActivity.class); //Originally, was redirected to MainActivity.class
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void selectServer() {
        LiveData<List<CoreServerEntity>> sl = securityViewModel.loadServers();
        sl.observe(this, serverList -> {
            if (serverList != null && !serverList.isEmpty()) {
                showServerDialog(serverList);
                sl.removeObservers(this);
            }
        });
    }

    private void showServerDialog(List<CoreServerEntity> serverList) {

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        Fragment previous = getSupportFragmentManager().findFragmentByTag(SelectServerDialog.class.getName());
        if (previous != null) transaction.remove(previous);
        transaction.addToBackStack(null);

        fragmentManager = getSupportFragmentManager();
        SelectServerDialog fragment = SelectServerDialog.getInstance((ArrayList<CoreServerEntity>) serverList);
        fragment.setRequestCode(1002);

        fragment.setOnCallbackResult((requestCode, p) -> {
            if (requestCode != 1002) return;
            currentServer = p;
            Hawk.put(CoreMobileManager.getInstance().getHawkKeys(2), p);
            ConstantsWorker.addNetworkMonitorWorker();
            restore();
        });
        fragment.show(transaction, SelectServerDialog.class.getName());
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onPermissionGranted(int requestCode) {

    }
}

