package com.chiquita.mcspsa.core.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.CoreMobileManager;
import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.ui.SplashActivity;
import com.chiquita.mcspsa.viewmodel.contracts.CoreContract;
import com.chiquita.mcspsa.worker.ConstantsWorker;
import com.google.android.material.snackbar.Snackbar;
import com.orhanobut.hawk.Hawk;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.net.UnknownServiceException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public abstract class CoreBaseActivity extends AppCompatActivity implements CoreContract.View {

    public final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private Unbinder unbinder;

    protected abstract CoreContract.ViewModel getViewModel();

    protected View mProgressView;

    protected View mErrorView;

    protected View mEmptyView;

    protected View mNetworkView;

    protected View mParentView;

    public CoreUserEntity user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        onViewReady(savedInstanceState, getIntent());
        CoreConfigurationManager.getInstance().load(this);
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getViewModel().onViewAttached(this);
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        compositeDisposable.dispose();
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().onViewResumed();
    }

    @Override
    public void onStop() {
        super.onStop();
        getViewModel().onViewDetached();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected abstract int getContentView();

    protected abstract void initializeVM();

    protected void initializeUI() {
        mParentView = findViewById(R.id.core_root);
        unbinder = ButterKnife.bind(this, mParentView);
    }

    protected void loadUser() {
        String lastUser;

        if (Hawk.contains(CoreMobileManager.getInstance().getHawkKeys(1))) {
            lastUser = Hawk.get(CoreMobileManager.getInstance().getHawkKeys(1));
        } else
            lastUser = null;

        if (lastUser != null && !lastUser.equals("")) {
            LiveData<CoreUserEntity> ld = getViewModel().getUser(lastUser);
            ld.observe(this, (CoreUserEntity mcsuser) -> {
                if (mcsuser != null) {
                    user = mcsuser;
                    ld.removeObservers(this);
                    onUserLoaded();
                    return;
                }
            });
        }
    }

    protected void init() {
        initializeUI();
        initializeVM();
        loadUser();
        observeNetworkEvent();
    }

    /**
     * Implementable methods
     */
    protected abstract void onPermissionGranted(int requestCode);

    protected abstract void onUserLoaded();

    protected void processErrorResponse(Throwable error) {
        if (error instanceof HttpException) {
            switch (((HttpException) error).code()) {
                case 401:
                    showMessage("\"API ERROR\"");
                    break;

                case 404:
                    showMessage("\"API ERROR\"");
                    break;

                default:
                    showMessage("\"API ERROR\"");
                    break;
            }
        } else if(error instanceof UnknownServiceException){
            showMessage(((UnknownServiceException) error).getMessage());
        }
    }

    /**
     * @Security Handle save user loaded from ViewModel method!
     */
    protected void saveUser(CoreUserEntity user) {
        getViewModel().saveUser(user);
    }


    /**
     * Contract Views
     */
    public void showProgress(boolean show) {
        mProgressView = findViewById(R.id.layout_progress);
        if (mProgressView != null) {
            mProgressView.bringToFront();
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public void showProgressLockable(boolean show) {
        mProgressView = findViewById(R.id.layout_progress);
        mProgressView.bringToFront();
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void showError(boolean show, View contentView) {
        mErrorView = findViewById(R.id.layout_error);
        mErrorView.setVisibility(show ? View.VISIBLE : View.GONE);
        contentView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void showEmpty(boolean show, final View contentView) {
        mEmptyView = findViewById(R.id.layout_empty);
        mEmptyView.setVisibility(show ? View.VISIBLE : View.GONE);
        contentView.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    public void showOfflineMessage(final boolean show) {
        mNetworkView = findViewById(R.id.view_offline);
        if (mNetworkView != null)
            mNetworkView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.core_root), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(5);
        textView.setTextColor(Color.YELLOW);
        snackbar.setAction(R.string.dismiss, view -> snackbar.dismiss());
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }

    public void showAdvert(String message) {
        try {

        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getLocalizedMessage());
        }
    }

    public void showLoginMessage() {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.core_root), getString(R.string.login_again), Snackbar.LENGTH_INDEFINITE);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(R.id.snackbar_text);
        textView.setMaxLines(5);
        textView.setTextColor(Color.YELLOW);
        snackbar.setAction(R.string.login, (View view) -> goHome(SplashActivity.class, 300));
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.show();
    }

    /**
     * Flow methods
     */
    protected void goNext(Context context, Class next, int delay) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(context, next);
                startActivity(i);
            }
        };
        new Timer().schedule(task, delay);
    }

    protected void goNextWithBundle(Class next, Bundle bundle, int delay) {
        if (mParentView != null) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent fav = new Intent(mParentView.getContext(), next);
                    fav.putExtras(bundle);
                    startActivity(fav);
                }
            };
            new Timer().schedule(task, delay);
        }
    }

    protected void goHome(Class home, int delay) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                user.setActive(false);
                getViewModel().saveUser(user);
                finishAffinity();
                Intent intent = new Intent(getApplicationContext(), home);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };
        new Timer().schedule(task, delay);
    }

    /**
     * Network Events
     */
    protected void observeNetworkEvent() {

        LiveData<List<WorkInfo>> ls = getViewModel().getWorkStatusLD(ConstantsWorker.NM_TAG);
        ls.observe(this, workers -> {

            if (workers == null || workers.isEmpty()) {
                return;
            }

            WorkInfo workInfo = workers.get(0);
            boolean finished = workInfo.getState().isFinished();
            if (finished) {

                Data outputData = workInfo.getOutputData();
                String response =
                        outputData.getString(ConstantsWorker.RESPONSE);

                if (!TextUtils.isEmpty(response)) {
                    switch (response) {
                        case ConstantsWorker.API_AUTH_CONNECT:
                        case ConstantsWorker.API_ERR_CONNECT:
                        case ConstantsWorker.DEVICE_ERR_CONNECT:
                            showOfflineMessage(true);
                            break;
                        case ConstantsWorker.CONNECTED_TAG:
                            showOfflineMessage(false);
                            break;
                    }
                }
            }
        });
    }

    /**
     * Permissions
     */
    protected void handlePermissions(String[] permissionsRequired, int REQUEST_PERMISSION_ID, String permissionText) {

        new RxPermissions(this)
                .request(permissionsRequired)
                .subscribe(granted -> {
                    if (granted) {
                        onPermissionGranted(REQUEST_PERMISSION_ID);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), permissionText,
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                v -> {
                                    Intent intent = new Intent();
                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    intent.setData(Uri.parse("package_plan:" + getPackageName()));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(intent);
                                }).show();
                    }
                });
    }
}
