package com.chiquita.mcspsa.ui.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.helper.api.Resource;
import com.chiquita.mcspsa.core.ui.CoreBaseActivity;
import com.chiquita.mcspsa.data.api.request.CoreApiUtil;
import com.chiquita.mcspsa.data.api.request.CoreCommonParameter;
import com.chiquita.mcspsa.data.api.request.CoreTunnelTransform;
import com.chiquita.mcspsa.data.model.location.CoreLocationEntity;
import com.chiquita.mcspsa.data.model.location.CorePackerEntity;
import com.chiquita.mcspsa.ui.adapter.SelectServerDialog;
import com.chiquita.mcspsa.ui.adapter.location.SelectLocationDialog;
import com.chiquita.mcspsa.ui.adapter.location.SelectPackerDialog;
import com.chiquita.mcspsa.ui.menu.MainActivity;
import com.chiquita.mcspsa.viewmodel.LocationViewModel;
import com.chiquita.mcspsa.viewmodel.contracts.CoreContract;
import com.chiquita.mcspsa.viewmodel.contracts.LocationContract;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class LocationActivity extends CoreBaseActivity implements LocationContract.View {

    private LocationContract.ViewModel locationViewModel;

    protected FragmentTransaction transaction;
    protected FragmentManager fragmentManager;

    private ArrayList<CorePackerEntity> packerEntities = new ArrayList<>();
    private ArrayList<CoreLocationEntity> locationEntities = new ArrayList<>();

    private TextView tvLocationName, tvPackerName;

    /*@BindView(R.id.txv_location)
    public TextView select_location;
*/
    @Override
    protected CoreContract.ViewModel getViewModel() {
        return locationViewModel;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_location;
    }

    @Override
    protected void initializeVM() {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
    }

    @Override
    protected void onPermissionGranted(int requestCode) {

    }

    @Override
    protected void initializeUI() {
        super.initializeUI();

        tvLocationName = findViewById(R.id.txv_location);
        tvPackerName = findViewById(R.id.tv_empacadora);

        findViewById(R.id.select_location).setOnClickListener(v -> showLocationDialog(locationEntities));

        findViewById(R.id.select_empacadora).setOnClickListener(v -> showPackerDialog(packerEntities));

        findViewById(R.id.submit_tv).setOnClickListener(view -> startActivity(new Intent(LocationActivity.this, MainActivity.class)));
    }


    @Override
    protected void onUserLoaded() {
        if (user != null && user.isActive()) {
            loadLocations();
            loadPackers();
            CoreConfigurationManager.getInstance().load(this);
        }
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        init();
    }

    private void loadLocations() {
        LiveData<Resource<List<CoreLocationEntity>>> locations = locationViewModel.postLocations(getLocationTransform());

        locations.observeForever(data -> {
            if (data.status == Resource.Status.ERROR) {
                showMessage(data.message);
                //showMenuList(data);
                Timber.i("Locations Response: " + data.data.toString());
                locations.removeObservers(this);
                showProgress(false);
            }

            if (data.status == Resource.Status.SUCCESS) {
                //showMenuList(data);
                Timber.i("Locations Response: " + data.data.toString());
                locationEntities.addAll(data.data);
                locations.removeObservers(this);
                showProgress(false);
            }
        });
    }

    private void loadPackers() {
        LiveData<Resource<List<CorePackerEntity>>> packers = locationViewModel.postPackers(getPackerTransform());

        packers.observeForever(data -> {
            if (data.status == Resource.Status.ERROR) {
                showMessage(data.message);
                //showMenuList(data);
                Timber.i("Packers Response: " + data.data.toString());
                packers.removeObservers(this);
                showProgress(false);
            }

            if (data.status == Resource.Status.SUCCESS) {
                //showMenuList(data);
                Timber.i("Packers Response: " + data.data.toString());
                packerEntities.addAll(data.data);
                packers.removeObservers(this);
                showProgress(false);
            }
        });
    }


    private void showLocationDialog(List<CoreLocationEntity> locationList) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        Fragment previous = getSupportFragmentManager().findFragmentByTag(SelectServerDialog.class.getName());
        if (previous != null) transaction.remove(previous);
        transaction.addToBackStack(null);

        fragmentManager = getSupportFragmentManager();
        SelectLocationDialog fragment = SelectLocationDialog.getInstance((ArrayList<CoreLocationEntity>) locationList);
        fragment.setRequestCode(1002);

        fragment.setOnCallbackResult((requestCode, p) -> {
            /*if (requestCode != 1002) return;
            currentServer = p;
            Hawk.put(CoreMobileManager.getInstance().getHawkKeys(2), p);
            ConstantsWorker.addNetworkMonitorWorker();
            restore();*/
            tvLocationName.setText(p.getCCR_DESCRIPTION());
        });
        fragment.show(transaction, SelectLocationDialog.class.getName());
    }

    private void showPackerDialog(List<CorePackerEntity> packerList) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();

        Fragment previous = getSupportFragmentManager().findFragmentByTag(SelectPackerDialog.class.getName());
        if (previous != null) transaction.remove(previous);
        transaction.addToBackStack(null);

        fragmentManager = getSupportFragmentManager();
        SelectPackerDialog fragment = SelectPackerDialog.getInstance((ArrayList<CorePackerEntity>) packerList);
        fragment.setRequestCode(1002);

        fragment.setOnCallbackResult((requestCode, p) -> {
            /*if (requestCode != 1002) return;
            currentServer = p;
            Hawk.put(CoreMobileManager.getInstance().getHawkKeys(2), p);
            ConstantsWorker.addNetworkMonitorWorker();
            restore();*/
            tvPackerName.setText(p.getCCR_DESCRIPTION());
        });
        fragment.show(transaction, SelectPackerDialog.class.getName());
    }


    public CoreTunnelTransform getLocationTransform() {
        CoreCommonParameter p1;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "list_type_localition", "QUBD0013", p1));
        ir.setUser(user);

        return ir;
    }

    public CoreTunnelTransform getPackerTransform() {
        CoreCommonParameter p1;

        p1 = new CoreCommonParameter.CoreCommonParameterBuilder()
                .setName("p_lista")
                .setDirection("OUT")
                .setType("CURSOR").createCommonRequestParameter();

        CoreTunnelTransform ir = new CoreTunnelTransform();
        ir.setParameters(CoreApiUtil.createSingleRequestObject(user, "list_packstation", "QUBD0013", p1));
        ir.setUser(user);

        return ir;
    }
}
