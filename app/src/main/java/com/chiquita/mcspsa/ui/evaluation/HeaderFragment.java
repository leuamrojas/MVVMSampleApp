package com.chiquita.mcspsa.ui.evaluation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.core.helper.util.CustomDateFormat;
import com.chiquita.mcspsa.core.ui.CoreBaseFragment;
import com.chiquita.mcspsa.data.api.request.CoreApiUtil;
import com.chiquita.mcspsa.data.api.request.CoreCommonParameter;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.model.CoreMenuEntity;
import com.chiquita.mcspsa.data.model.CoreViajeEntity;
import com.chiquita.mcspsa.ui.adapter.menu.MainMenuAdapter;
import com.chiquita.mcspsa.viewmodel.HeaderViewModel;
import com.chiquita.mcspsa.viewmodel.contracts.CoreContract;
import com.chiquita.mcspsa.viewmodel.contracts.HeaderContract;


import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderFragment extends CoreBaseFragment implements
        HeaderContract.View{

    @BindView(R.id.tv_back)
    public TextView tv_back;

    @BindView(R.id.et_date)
    public TextView et_date;

    @BindView(R.id.txv_viaje)
    public TextView txv_viaje;

    @BindView(R.id.txv_export)
    public TextView txv_export;

    private HeaderContract.ViewModel headerViewModel;

    private MainMenuAdapter mAdapter;

    private ArrayList<CoreMenuEntity> menuList = new ArrayList<>();

    private ArrayList<CoreMenuEntity> applications;



    public static HeaderFragment getInstance() {
        HeaderFragment f = new HeaderFragment();
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
        mParentView = inflater.inflate(R.layout.fragment_header, container, false);
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
        headerViewModel = ViewModelProviders.of(this).get(HeaderViewModel.class);
        observeNetworkEvent();
    }

    @Override
    protected void initializeUI() {

        et_date.setText(CustomDateFormat.formateCurrentDate());
        tv_back.setOnClickListener(view -> {
            getFragmentManager().popBackStack();
        });
    }

    @Override
    protected CoreContract.ViewModel getViewModel() {
        return headerViewModel;
    }

    private void loadViaje() {
        LiveData<Resource<List<CoreViajeEntity>>> sl = headerViewModel.postViaje(getMenuTransform());
        sl.observeForever(data -> {

            if (data.status == Resource.Status.ERROR) {
                showMessage(data.message);
                showViaje(data);
                sl.removeObservers(this);
                showProgress(false);
            }

            if (data.status == Resource.Status.SUCCESS) {
                showViaje(data);
                sl.removeObservers(this);
                showProgress(false);
            }
        });
    }

    private void showViaje(Resource<List<CoreViajeEntity>> data) {
        if (data.data != null && data.data.size() > 0) {
            Toast.makeText(getActivity(), "viaje loaded "+data.data.size(), Toast.LENGTH_SHORT).show();
            txv_viaje.setText(data.data.get(0).getCCR_BC_ALIAS()+"-"+data.data.get(0).getCCR_DESCRIPTION());
//            menuList.clear();
//            menuList.addAll(validate(data.data));
//            mAdapter.notifyDataSetChanged();
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
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "list_travelnumber", "QUBD0013", p1, p2, p3, p4));
        ir.setUser(user);

        return ir;
    }


    @Override
    protected void onUserLoaded() {
        if (user != null && user.isActive()) {
            if (isAdded() && getActivity() != null){
                loadViaje();
                loadExport();
            }

            CoreConfigurationManager.getInstance().load(getContext());
        }
    }

    private void loadExport() {
        LiveData<Resource<List<CoreViajeEntity>>> sl = headerViewModel.postExport(getMenuTransform());
        sl.observeForever(data -> {

            if (data.status == Resource.Status.ERROR) {
                showMessage(data.message);
                showViaje(data);
                sl.removeObservers(this);
                showProgress(false);
            }

            if (data.status == Resource.Status.SUCCESS) {
                showViaje(data);
                sl.removeObservers(this);
                showProgress(false);
            }
        });
    }

    @Override
    protected void onPermissionGranted(int requestCode) {
    }
}
