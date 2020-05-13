package com.chiquita.mcspsa.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.CoreMobileManager;
import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.core.ui.CoreBaseFragment;
import com.chiquita.mcspsa.core.ui.adapter.CommonAdapterListener;
import com.chiquita.mcspsa.data.api.request.CoreApiUtil;
import com.chiquita.mcspsa.data.api.request.CoreCommonParameter;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.api.response.CoreCommonResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsResp;
import com.chiquita.mcspsa.data.api.response.CoreCommonRowsTypeResp;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;
import com.chiquita.mcspsa.ui.adapter.menu.MainMenuAdapter;
import com.chiquita.mcspsa.ui.evaluation.EvaluationActivity;
import com.chiquita.mcspsa.ui.security.LoginSignInActivity;
import com.chiquita.mcspsa.viewmodel.MenuViewModel;
import com.chiquita.mcspsa.viewmodel.contracts.CoreContract;
import com.chiquita.mcspsa.viewmodel.contracts.MenuContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends CoreBaseFragment implements
        MenuContract.View, CommonAdapterListener<CoreMenuEntity> {

    private MenuContract.ViewModel menuViewModel;

    private MainMenuAdapter mAdapter;

    private ArrayList<CoreMenuEntity> menuList = new ArrayList<>();

    private ArrayList<CoreMenuEntity> applications;

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    public static MainFragment getInstance() {
        MainFragment f = new MainFragment();
        return f;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.view_common_content, container, false);
        unbinder = ButterKnife.bind(this, mParentView);
        CoreConfigurationManager.getInstance().load(getContext());
        init();
        return mParentView;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void initializeVM() {
        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        observeNetworkEvent();
    }

    @Override
    protected void initializeUI() {
        mAdapter = new MainMenuAdapter(getActivity(), menuList, this);
        RecyclerView.LayoutManager mLayoutManager;

        if (CoreConfigurationManager.getInstance().isPortraitMode())
            mLayoutManager = new StaggeredGridLayoutManager(CoreConfigurationManager.getInstance().isTablet() ? 2 : 1,
                    StaggeredGridLayoutManager.VERTICAL);
        else
            mLayoutManager = new StaggeredGridLayoutManager(CoreConfigurationManager.getInstance().isTablet() ? 3 : 2,
                    StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected CoreContract.ViewModel getViewModel() {
        return menuViewModel;
    }

    public void launchLoginActivity() {
        user.setActive(false);
        saveUser(user);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(getContext(), LoginSignInActivity.class);
                startActivity(i);
                getActivity().finishAfterTransition();
            }
        };
        new Timer().schedule(task, 100);
    }

    private void loadMenu() {
        LiveData<Resource<List<CoreMenuEntity>>> sl = menuViewModel.postMenu(getMenuTransform());
        sl.observeForever(data -> {

            if (data.status == Resource.Status.ERROR) {
                showMessage(data.message);
                showMenuList(data);
                sl.removeObservers(this);
                showProgress(false);
            }

            if (data.status == Resource.Status.SUCCESS) {
                showMenuList(data);
                sl.removeObservers(this);
                showProgress(false);
            }
        });
    }

    private void showMenuList(Resource<List<CoreMenuEntity>> data) {
        if (data.data != null && data.data.size() > 0) {
            menuList.clear();
            menuList.addAll(validate(data.data));
            mAdapter.notifyDataSetChanged();
        }
    }

    public CoreTunnelTransform getMenuTransform() {

        CoreCommonParameter p1, p2, p3, p4;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_cenario")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue("72810").createCommonRequestParameter();

        p2 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_usuario")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(user.getUsername())
                .createCommonRequestParameter();

        p3 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_tipo")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue("2") // 2 for All, 1 for Favorites
                .createCommonRequestParameter();

        p4 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "list_obj_mobile_access", "inbd0309", p1, p2, p3, p4)); //added by asif
        //ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "INBD0712.list_obj_mobile_access", "", p1, p2, p3, p4));
        ir.setUser(user);

        return ir;
    }

    private ArrayList<CoreMenuEntity> validate(List<CoreMenuEntity> items) {
        List<CoreMenuEntity> valid = new ArrayList<>();

        applications = new ArrayList<>();
        applications = (ArrayList<CoreMenuEntity>) CoreMobileManager.getInstance().getApplicationsDeveloped();

        for (CoreMenuEntity item : items) {
            for (CoreMenuEntity be : applications) {
                if (item.getObjectCode().trim().contains(be.getObjectCode())) {
                    valid.add(item);
                    break;
                }
            }
        }
        return (ArrayList<CoreMenuEntity>) valid;
    }

    @Override
    public void onSelected(CoreMenuEntity selectable) {
        if (!CoreConfigurationManager.getInstance().isOfflineMode()) {

            menuViewModel.doMenuValidate(getValidateTransform(selectable), selectable.getObjectName());
            LiveData<ApiResponseSingle> sl = menuViewModel.doMenuValidate();

            sl.observe(this, response -> {
                if (response != null) {
                    switch (response.status) {

                        case ERROR:
                            showProgress(false);
                            processErrorResponse(response.error);
                            sl.removeObservers(this);
                            return;

                        case SUCCESS:
                            showProgress(false);
                            if (response.data instanceof CoreCommonResp) {

                                if (response.data == null) {
                                    showMessage(getString(R.string.invalid_request));
                                    return;
                                }

                                if (!((CoreCommonResp<CoreCommonRowsResp, CoreCommonRowsTypeResp>) response.data).getRows().isEmpty() && ((CoreCommonResp<CoreCommonRowsResp, CoreCommonRowsTypeResp>) response.data).getRows().get(0).status == "NEGADO") {
                                    showMessage(getString(R.string.access_denied));
                                    sl.removeObservers(this);
                                    return;
                                }
                                showApplication(selectable);
                            }
                            sl.removeObservers(this);
                            return;
                    }
                }
            });
        } else {
            showApplication(selectable);
        }
    }

    private CoreTunnelTransform getValidateTransform(CoreMenuEntity menu) {
        CoreCommonParameter p1, p2, p3, p4, p5, p6;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_obj_nome")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(menu.getObjectCode()).createCommonRequestParameter();

        p2 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_user")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue(user.getUsername())
                .createCommonRequestParameter();

        p3 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_validation_type")
                .setDirection("IN")
                .setType("NUMBER")
                .setValue("2")
                .createCommonRequestParameter();

        p4 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_ip_machine")
                .setDirection("IN")
                .setType("VARCHAR")
                .setValue("M:" + CoreMobileManager.getInstance().getLocalIpAddress())
                .createCommonRequestParameter();

        p5 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_net_server")
                .setDirection("IN")
                .setType("VARCHAR")
//                .setValue(user.getServerAddress().substring(0, 35))
                .setValue(user.getServerAddress().substring(0, 22)) //Change by asif, changed substring from 35 to 22.
                .createCommonRequestParameter();

        p6 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "validate_obj_access", "inbd0309", p1, p2, p3, p4, p5)); // added by asif
//        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "INBD0712.validate_obj_access", "", p1, p2, p3, p4, p5, p6));
        ir.setUser(user);

        return ir;

    }

    private void showApplication(CoreMenuEntity selectable) {
        Intent intent;
        switch (selectable.getObjectCode()) {
            case "PICQ1050":
//                intent = new Intent(getContext(), Picq1050_Main.class);
//                startActivity(intent);
                // Added by asif
                intent = new Intent(getContext(), EvaluationActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onUserLoaded() {
        if (user != null && user.isActive()) {
            if (isAdded() && getActivity() != null)
                loadMenu();
            CoreConfigurationManager.getInstance().load(getContext());
        }
    }

    @Override
    protected void onPermissionGranted(int requestCode) {
    }
}
