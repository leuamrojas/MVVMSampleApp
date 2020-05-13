package com.chiquita.mcspsa.ui.setup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.helper.api.ApiResponseSingle;
import com.chiquita.mcspsa.core.helper.util.CustomDateFormat;
import com.chiquita.mcspsa.core.ui.CoreBaseFragment;
import com.chiquita.mcspsa.core.ui.adapter.CommonAdapterListener;
import com.chiquita.mcspsa.data.model.CampaignEntity;
import com.chiquita.mcspsa.data.model.CampaignStoreEntity;
import com.chiquita.mcspsa.data.model.bean.CoreBean;
import com.chiquita.mcspsa.ui.adapter.CampaignStoreAdapter;
import com.chiquita.mcspsa.ui.bean.SetupBean;
import com.chiquita.mcspsa.viewmodel.SetupViewModel;
import com.chiquita.mcspsa.viewmodel.contracts.SetupContract;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Setup extends CoreBaseFragment implements SetupContract.View, CommonAdapterListener {

    private static Setup fragment;

    public static final String TAG = Setup.class.getCanonicalName();

    private SetupContract.ViewModel viewModel;

    private SetupBean bean = new SetupBean();

    @BindView(R.id.ll_survey_date)
    public LinearLayout ll_survey_date;

    @BindView(R.id.ll_survey_campaign)
    public LinearLayout ll_survey_campaign;

    @BindView(R.id.txv_campaign_details)
    public TextView txv_campaign_details;

    @BindView(R.id.txv_date_details)
    public TextView txv_date_details;

    @BindView(R.id.rcv_campaigns)
    public RecyclerView rcv_campaigns;

    List<CampaignStoreEntity> listCampaignStore = new ArrayList<>();

    CampaignStoreAdapter adapter;

    public static Setup newInstance() {
        fragment = new Setup();
        Bundle args = new Bundle();
        Gson gSon = new Gson();
        fragment.setArguments(args);
        return fragment;
    }

    public Setup() {
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mParentView = inflater.inflate(R.layout.view_setup_details, container, false);
        unbinder = ButterKnife.bind(this, mParentView);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();
        init();
        restore();
        return mParentView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        getActivity().invalidateOptionsMenu();
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected SetupContract.ViewModel getViewModel() {
        return viewModel;
    }

    @Override
    protected void initializeVM() {
        viewModel = ViewModelProviders.of(getActivity()).get(SetupViewModel.class);
        observerBean();
    }

    private void observerBean() {
        getViewModel().getBusinessBean().observe(this, this::refreshBusiness);
    }

    public void refreshBusiness(CoreBean business) {

        if (bean != null)
            bean = (SetupBean) business;

        restore();
    }

    protected void restore() {
        if (bean == null)
            return;

        if (bean.getSchDate() != null) {
            txv_date_details.setText(CustomDateFormat.getCurrentTimeYMD(bean.getSchDate()));
        }

        if (bean.getCampaign() != null) {
            txv_campaign_details.setText(bean.getCampaign().getDescr());
        }

        if(bean.getCampaign() != null){
            loadCampaignStore();
        }
    }

    @Override
    protected void initializeUI() {
        rcv_campaigns.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcv_campaigns.setHasFixedSize(true);
        adapter = new CampaignStoreAdapter(getActivity(), listCampaignStore, this, true);
        rcv_campaigns.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    private void loadCampaigns() {

        if (bean == null)
            return;

        getViewModel().getCampaigns(SetupApiUtil.getCampaignsTransform(user, bean));
        LiveData<ApiResponseSingle> sl = getViewModel().getCampaigns();
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
                        showActiveCampaigns((ArrayList<CampaignEntity>) response.data);
                        sl.removeObservers(this);
                        return;
                }
            }
        });
    }

    private void loadCampaignStore() {

        if (bean == null)
            return;

        getViewModel().getStores(SetupApiUtil.getStoresTransform(user, bean));
        LiveData<ApiResponseSingle> sl = getViewModel().getStores();
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
                        showCampaignStoreList(response.data);
                        sl.removeObservers(this);
                        return;
                }
            }
        });
    }

    private void showCampaignStoreList(Object data) {
        ArrayList<CampaignStoreEntity> list = (ArrayList<CampaignStoreEntity>) data;

        if (list == null || list.isEmpty()) {
            showEmpty(true, null);
            showMessage("No available stores founds for you!");
            return;
        }

        showEmpty(false, null);
        listCampaignStore.clear();
        listCampaignStore.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.ll_survey_campaign)
    public void activeCampaigns() {
        if (bean == null)
            return;

        loadCampaigns();
    }

    private void showActiveCampaigns(ArrayList<CampaignEntity> campaigns) {
        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();

        Fragment previous = getFragmentManager().findFragmentByTag(SetupActiveCampaignDialog.class.getName());
        if (previous != null) transaction.remove(previous);
        transaction.addToBackStack(null);

        fragmentManager = getFragmentManager();
        SetupActiveCampaignDialog fragment = SetupActiveCampaignDialog.getInstance(campaigns);
        fragment.setRequestCode(1000);
        fragment.setOnCallbackResult((requestCode, p) -> {
            if (requestCode != 1000) return;
            bean.setCampaign(p);
            getViewModel().setBusinessBean(bean);
        });
        fragment.show(transaction, SetupActiveCampaignDialog.class.getName());
    }

    @OnClick(R.id.ll_survey_date)
    public void bookDate() {

        if (bean.getCampaign() == null) {
            showMessage("A campaign must be selected first!.");
            return;
        }

        fragmentManager = getFragmentManager();
        transaction = fragmentManager.beginTransaction();

        Fragment previous = getFragmentManager().findFragmentByTag(SetupBookDateDialog.class.getName());
        if (previous != null) transaction.remove(previous);
        transaction.addToBackStack(null);

        fragmentManager = getFragmentManager();
        SetupBookDateDialog fragment = new SetupBookDateDialog();
        fragment.setRequestCode(1001);
        fragment.setDate(bean.getSchDate() == null ? new Date() : bean.getSchDate());
        fragment.setOnCallbackResult((requestCode, date) -> {
            if (requestCode != 1001) return;

            bean.setSchDate(date);
            getViewModel().setBusinessBean(bean);

        });
        fragment.show(transaction, SetupBookDateDialog.class.getName());
    }

    @Override
    protected void onPermissionGranted(int requestCode) {

    }

    @Override
    protected void onUserLoaded() {
        if (user != null && user.isActive()) {
            bean = new SetupBean();
            bean.setUserName(user.getUsername());
            getViewModel().setBusinessBean(bean);
        }
    }

    @Override
    public void onSelected(Object selectable) {
    }
}
