package com.chiquita.mcspsa.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.ui.CoreBaseActivity;
import com.chiquita.mcspsa.core.viewmodel.ViewModelProviderFactory;
import com.chiquita.mcspsa.data.model.CoreUserEntity;
import com.chiquita.mcspsa.ui.SplashActivity;
import com.chiquita.mcspsa.ui.evaluation.EvaluationActivity;
import com.chiquita.mcspsa.viewmodel.MainViewModel;
import com.chiquita.mcspsa.viewmodel.contracts.MainContract;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.octicons_typeface_library.Octicons;
import butterknife.BindView;

public class MainActivity extends CoreBaseActivity implements MainContract.View {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private MainFragment menuFragment;

    private long exitTime = 0;

    private Toast toast = null;

    private AccountHeader headerResult = null;

    private Drawer result = null;

    private Bundle savedInstanceState;

    private MainContract.ViewModel csViewModel;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        this.savedInstanceState = savedInstanceState;
        init();

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            menuFragment = new MainFragment();
            menuFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_content, menuFragment, MainFragment.class.getName())
                    .commit();
        }
    }

    @Override
    protected MainContract.ViewModel getViewModel() {
        return csViewModel;
    }

    @Override
    protected int getContentView() {
        return R.layout.view_main;
    }

    @Override
    protected void initializeUI() {
        super.initializeUI();
        setSupportActionBar(toolbar);
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        if (result != null)
            result.closeDrawer();
    }

    @Override
    protected void initializeVM() {
        CoreConfigurationManager.getInstance().setOfflineMode(false);// added by asif true previous false
        csViewModel = ViewModelProviders.of(this, new ViewModelProviderFactory<>(new MainViewModel(getApplication()))).get(MainViewModel.class);
    }

    @Override
    protected void onUserLoaded() {
        addResult(user);
    }

    @Override
    protected void onPermissionGranted(int requestCode) {

    }

    private void addResult(CoreUserEntity user) {

        if (user != null) {
            IProfile profile = new ProfileDrawerItem().withName(user.getUsername()).withEmail(user.getServerName()).withIcon(getResources().getDrawable(R.drawable.account)).withIdentifier(100);

            headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withCurrentProfileHiddenInList(true)
                    .withHeaderBackground(R.color.cool)
                    .addProfiles(
                            profile,
                            new ProfileSettingDrawerItem().withName(getString(R.string.logoff)).withIcon(GoogleMaterial.Icon.gmd_exit_to_app).withIdentifier(99))
                    .withSelectionListEnabledForSingleProfile(false)
                    .withOnAccountHeaderListener((view, iProfile, b) -> {

                        if (iProfile instanceof IDrawerItem && iProfile.getIdentifier() == 99) {
                            Disconnect();
                        }

                        if (view.getId() == R.id.material_drawer_account_header_current) {
                            //goNext(MainActivity.this, UserProfile.class, 300);
                        }

                        return false;
                    })
                    .withSavedInstance(savedInstanceState)
                    .build();

            result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .withHasStableIds(true)
                    .withItemAnimator(new AlphaCrossFadeAnimator())
                    .withAccountHeader(headerResult)
                    .withSelectedItem(-1)
                    .addDrawerItems(
                            new PrimaryDrawerItem().withName(R.string.profile).withIcon(Octicons.Icon.oct_person).withIdentifier(1).withEnabled(true),
                            new SectionDrawerItem().withName(R.string.device),
                            new PrimaryDrawerItem().withName(R.string.settings).withIcon(Octicons.Icon.oct_settings).withIdentifier(3).withEnabled(true)
                    )
                    .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() == 1) {
                                //goNext(MainActivity.this, UserProfile.class, 300);
                            } else if (drawerItem.getIdentifier() == 3) {
                                //goNext(MainActivity.this, AppSettings.class, 300);
                            }
                        }
                        return false;
                    })
                    .withSavedInstance(savedInstanceState)
                    .withShowDrawerOnFirstLaunch(true)
                    .build();

            // added by asif
            if (result != null && result.isDrawerOpen()) {
                result.closeDrawer();
            }
            // end by asif
        }

        if (savedInstanceState == null) {
            //result.setSelection(1, false);
        }
    }

    private void Disconnect() {
        user.setActive(false);
        saveUser(user);
        goHome(SplashActivity.class, 300);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void doExitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toast.setText(R.string.press_again_exit_app);
            toast.show();
            exitTime = System.currentTimeMillis();
        } else {
            toast.cancel();
            moveTaskToBack(true);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            doExitApp();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (result != null)
            outState = result.saveInstanceState(outState);

        if (headerResult != null)
            outState = headerResult.saveInstanceState(outState);

        super.onSaveInstanceState(outState);
    }
}
