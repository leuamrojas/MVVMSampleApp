package com.chiquita.mcspsa.ui.evaluation;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.ui.CoreBaseActivity;
import com.chiquita.mcspsa.viewmodel.EvaluationViewModel;
import com.chiquita.mcspsa.viewmodel.contracts.CoreContract;

import com.chiquita.mcspsa.viewmodel.contracts.EvaluationContract;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;

public class EvaluationActivity extends CoreBaseActivity implements EvaluationContract.View {

   /* @BindView(R.id.bottomNavigationView)
    public BottomNavigationView bottomNavigationView;*/

    private Bundle savedInstanceState;
    private DashboardFragment menuFragment;
    private EvaluationContract.ViewModel evaluationViewModel;

    @Override
    protected CoreContract.ViewModel getViewModel() {
        return evaluationViewModel;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_evaluation;
    }

    @Override
    protected void initializeUI() {
        super.initializeUI();

    }

    @Override
    protected void initializeVM() {
        evaluationViewModel = ViewModelProviders.of(this).get(EvaluationViewModel.class);
    }

    @Override
    protected void onPermissionGranted(int requestCode) {

    }

    @Override
    protected void onUserLoaded() {

    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        this.savedInstanceState = savedInstanceState;
        init();

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            menuFragment = new DashboardFragment();
            menuFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_content, menuFragment, DashboardFragment.class.getName())
                    .commit();
        }
    }

}
