package com.chiquita.mcspsa.ui.evaluation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.CoreMobileManager;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.core.ui.CoreBaseFragment;
import com.chiquita.mcspsa.core.ui.adapter.CommonAdapterListener;
import com.chiquita.mcspsa.data.api.request.CoreApiUtil;
import com.chiquita.mcspsa.data.api.request.CoreCommonParameter;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;
import com.chiquita.mcspsa.ui.adapter.menu.MainMenuAdapter;
import com.chiquita.mcspsa.ui.security.LoginSignInActivity;
import com.chiquita.mcspsa.viewmodel.DashboardViewModel;
import com.chiquita.mcspsa.viewmodel.HeaderViewModel;
import com.chiquita.mcspsa.viewmodel.contracts.CoreContract;
import com.chiquita.mcspsa.viewmodel.contracts.DashboardContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardFragment extends CoreBaseFragment implements DashboardContract.View, CommonAdapterListener<CoreMenuEntity> {

    private DashboardContract.ViewModel dashboardViewModel;

    private MainMenuAdapter mAdapter;

    private ArrayList<CoreMenuEntity> menuList = new ArrayList<>();

    private ArrayList<CoreMenuEntity> applications;
    @BindView(R.id.rv_dashboard)
    public RecyclerView recyclerView;
    @BindView(R.id.iv_add_evaluation)
    public ImageView iv_add_evaluation;


    public static DashboardFragment getInstance() {
        DashboardFragment f = new DashboardFragment();
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
        mParentView = inflater.inflate(R.layout.fragment_dashboard, container, false);
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
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
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

        iv_add_evaluation.setOnClickListener(view -> launchHeaderFragment());
    }

    private void launchHeaderFragment() {
        Bundle arguments = new Bundle();
        HeaderFragment headerFragment = new HeaderFragment();
        headerFragment.setArguments(arguments);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_content, headerFragment, HeaderFragment.class.getName()).addToBackStack(HeaderFragment.class.getName())
                .commit();
    }

    @Override
    protected CoreContract.ViewModel getViewModel() {
        return dashboardViewModel;
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
        LiveData<Resource<List<CoreMenuEntity>>> sl = dashboardViewModel.postMenu(getMenuTransform());
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
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "INBD0712.list_obj_mobile_access", "", p1, p2, p3, p4));
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
                .setValue(user.getServerAddress().substring(0, 35))
                .createCommonRequestParameter();

        p6 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "INBD0712.validate_obj_access", "", p1, p2, p3, p4, p5, p6));
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

    @Override
    public void onSelected(CoreMenuEntity selectable) {

    }
}
