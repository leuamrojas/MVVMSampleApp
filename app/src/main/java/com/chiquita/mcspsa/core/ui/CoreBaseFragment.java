package com.chiquita.mcspsa.core.ui;

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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.HttpException;

public abstract class CoreBaseFragment extends Fragment implements CoreContract.View {

    public final CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected Unbinder unbinder;

    protected abstract CoreContract.ViewModel getViewModel();

    protected View mProgressView;

    protected View mErrorView;

    protected View mEmptyView;

    protected View mNetworkView;

    protected View mParentView;

    public CoreUserEntity user;

    protected FragmentTransaction transaction;

    protected FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        CoreConfigurationManager.getInstance().load(getContext());
    }

    protected abstract void initializeVM();

    protected abstract void initializeUI();

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
                    onUserLoaded();//added by asif becuase menu load api doesn't called
                    return;
                } else {
                    user = null;
                    onUserLoaded();
                    ld.removeObservers(this);
                }
            });
        } else {
            user = null;
            onUserLoaded();
            showLoginView();
        }
    }

    private void showLoginView() {

    }

    protected void init() {
        initializeUI();
        initializeVM();
        loadUser();
        observeNetworkEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().onViewResumed();
    }

    @Override
    public void onStart() {
        super.onStart();
        getViewModel().onViewAttached(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        getViewModel().onViewDetached();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        compositeDisposable.dispose();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Implementable methods
     */
    protected abstract void onPermissionGranted(int requestCode);

    protected abstract void onUserLoaded();

    /**
     * Save user to room
     */
    protected void saveUser(CoreUserEntity user) {
        getViewModel().saveUser(user);
    }

    /**
     * Flow methods
     */
    protected void goNext(Class next, int delay) {
        if (isAdded() && mParentView != null) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent i = new Intent(mParentView.getContext(), next);
                    startActivity(i);
                }
            };
            new Timer().schedule(task, delay);
        }
    }

    protected void goNextWithBundle(Class next, Bundle bundle, int delay) {
        if (isAdded() && mParentView != null) {
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
        if (isAdded() && mParentView != null) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    user.setActive(false);
                    getViewModel().saveUser(user);
                    getActivity().finishAffinity();
                    Intent intent = new Intent(mParentView.getContext(), home);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            };
            new Timer().schedule(task, delay);
        }
    }

    /**
     * Contract Views
     */
    public void showProgress(boolean show) {
        try {
            if (isAdded() && mParentView != null && getActivity() != null) {
                mProgressView = mParentView.findViewById(R.id.layout_progress);
                mProgressView.bringToFront();
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getMessage());
        }
    }

    public void showProgressLockable(boolean show) {
        try {
            if (isAdded() && mParentView != null && getActivity() != null) {
                mProgressView = mParentView.findViewById(R.id.layout_progress);
                mProgressView.bringToFront();
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                if (show) {
                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                } else {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getMessage());
        }
    }

    public void showError(boolean show, View contentView) {
        try {
            if (isAdded() && mParentView != null && getActivity() != null) {
                mErrorView = mParentView.findViewById(R.id.layout_error);
                mErrorView.setVisibility(show ? View.VISIBLE : View.GONE);

                if (contentView != null) {
                    contentView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            }
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getLocalizedMessage());
        }
    }

    public void showEmpty(boolean show, View contentView) {
        try {
            if (isAdded() && mParentView != null && getActivity() != null) {
                mEmptyView = mParentView.findViewById(R.id.layout_empty);
                mEmptyView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getLocalizedMessage());
        }
    }

    public void showMessage(String message) {
        try {
            if (isAdded() && mParentView != null && getActivity() != null) {
                Snackbar snackbar = Snackbar
                        .make(getActivity().findViewById(R.id.core_root), message, Snackbar.LENGTH_LONG);

                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(R.id.snackbar_text);
                textView.setMaxLines(5);
                textView.setTextColor(Color.YELLOW);
                snackbar.setAction(R.string.dismiss, view -> snackbar.dismiss());
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getLocalizedMessage());
        }
    }

    public void showAdvert(String message) {
        try {
            if (isAdded() && mParentView != null && getActivity() != null) {

            }
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getLocalizedMessage());
        }
    }

    public void showOfflineMessage(boolean show) {
        try {
            if (isAdded() && mParentView != null && getActivity() != null) {
                mNetworkView = mParentView.findViewById(R.id.view_offline);
                mNetworkView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getLocalizedMessage());
        }
    }

    public void showLoginMessage() {
        try {
            if (isAdded() && mParentView != null && getActivity() != null) {
                Snackbar snackbar = Snackbar
                        .make(getActivity().findViewById(R.id.core_root), getString(R.string.login_again), Snackbar.LENGTH_INDEFINITE);
                View sbView = snackbar.getView();
                TextView textView = sbView.findViewById(R.id.snackbar_text);
                textView.setMaxLines(5);
                textView.setTextColor(Color.YELLOW);
                snackbar.setAction(R.string.login, (View view) -> {
                    user.setActive(false);
                    saveUser(user);
                    goHome(SplashActivity.class, 300);
                });
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.show();
            }
        } catch (Exception ex) {
            LogManager.getInstance().error(getClass().getCanonicalName(), ex.getLocalizedMessage());
        }
    }

    /**
     * @Network Events
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
                            onUserLoaded();
                            break;

                        //CIS-added-Start
                        case ConstantsWorker.ERR_CONNECT:
                            showOfflineMessage(true);
                            onUserLoaded();
                            break;
                        //CIS-added-End
                    }
                }
            }
        });
    }

    /**
     * @Permissions
     */
    protected void handlePermissions(String[] permissionsRequired, int REQUEST_PERMISSION_ID, String permissionText) {
        if (isAdded() && mParentView != null) {
            new RxPermissions(this)
                    .request(permissionsRequired)
                    .subscribe(granted -> {
                        if (granted) {
                            onPermissionGranted(REQUEST_PERMISSION_ID);
                        } else {
                            Snackbar.make(mParentView.findViewById(android.R.id.content), permissionText,
                                    Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                    v -> {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                                        intent.setData(Uri.parse("package_plan:" + getActivity().getPackageName()));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                        startActivity(intent);
                                    }).show();
                        }
                    });
        }
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    protected boolean dismissFragment() {
        if (fragmentManager == null) return false;
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null || fragments.size() == 0) return false;

        transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_dialog_in, R.anim.anim_dialog_out);
        transaction.detach(fragments.get(0)).commit();
        return true;
    }

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
        } else if (error instanceof UnknownServiceException) {
            showMessage(error.getMessage());
        } else
            showMessage(error.getMessage());
    }
}
