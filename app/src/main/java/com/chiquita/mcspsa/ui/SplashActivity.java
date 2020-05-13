package com.chiquita.mcspsa.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;

import androidx.appcompat.app.AppCompatActivity;

import com.chiquita.mcspsa.R;
import com.chiquita.mcspsa.core.CoreConfigurationManager;
import com.chiquita.mcspsa.core.helper.background.BackgroundProcess;
import com.chiquita.mcspsa.core.helper.background.BackgroundProcessBuilder;
import com.chiquita.mcspsa.core.helper.background.BackgroundProcessEvent;
import com.chiquita.mcspsa.core.helper.log.LogManager;
import com.chiquita.mcspsa.ui.security.LoginSignInActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * An Splash screen.
 */
public class SplashActivity extends AppCompatActivity {
    private boolean error = true;
    private static long MIN_SPLASH_TIME = 4000;
    private long time;
    TimerTask tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_splash);

        init();

        /*
         *
         * Hide @ActionBar
         */
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.hide();
        }

        tt = new TimerTask() {
            public void run() {
                CoreConfigurationManager.getInstance().setOfflineMode(false);// added by asif true previous false
                CoreConfigurationManager.getInstance().setUpdated(false);
                CoreConfigurationManager.getInstance().setRestartApp(false);
                SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginSignInActivity.class));
                SplashActivity.this.finish();
            }
        };
    }

    protected void initializeDC() {
        Timer timer = new Timer();
        BackgroundProcess local = new BackgroundProcessBuilder()
                .setContext(this)
                .setCancelable(false)
                .setStyle(0).createBackgroundProcess();

        local.startWithoutDialog(new BackgroundProcessEvent() {
            @Override
            public void postProcess() {
                SplashActivity.this.time = System.currentTimeMillis() - SplashActivity.this.time;
                if (SplashActivity.this.time < SplashActivity.MIN_SPLASH_TIME) {
                    SplashActivity.this.time = SplashActivity.MIN_SPLASH_TIME - SplashActivity.this.time;
                } else {
                    SplashActivity.this.time = 0;
                }
                if (!SplashActivity.this.error) {
                    timer.schedule(tt, SplashActivity.this.time);
                }
            }

            @Override
            public void process() {
                if (Environment.getExternalStorageState().equals("mounted")) {
                    try {
                        doSomeLogic();
                    } catch (Exception e) {
                        LogManager.getInstance().error(getClass().getCanonicalName(), e.getMessage());
                    }
                }
            }
        });
    }

    protected void init() {
        initializeDC();
    }

    @Override
    public void onBackPressed() {
        /*
         * Not closeable
         */
    }

    private void doSomeLogic() {
        try {
            Thread.sleep(1000);
            error = false;
        } catch (InterruptedException ignored) {
        }
    }
}
