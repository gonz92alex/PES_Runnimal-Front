package com.runnimal.app.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.runnimal.app.android.R;
import com.runnimal.app.android.util.SingletonSession;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(getLayoutId());
        initNavigation();
        bindViews();
        initView();
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateNavigationBarState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        bottomNavigationView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_map) {
                startActivity(new Intent(this, MapActivity.class));
            } else if (itemId == R.id.navigation_trainings) {
                startActivity(new Intent(this, TrainingsActivity.class));
            } else if (itemId == R.id.navigation_challenges) {
                startActivity(new Intent(this, ChallengesActivity.class));
            } else if (itemId == R.id.navigation_pets) {
                startActivity(new Intent(this, PetsActivity.class));
            }
            finish();
        }, 300);
        return true;
    }

    protected abstract int getLayoutId();

    protected abstract int getNavigationMenuItemId();

    protected abstract void initView();

    private void initNavigation() {
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void bindViews() {
        ButterKnife.bind(this);
    }

    private void updateNavigationBarState() {
        int actionId = getNavigationMenuItemId();
        if (actionId != -1) {
            MenuItem item = bottomNavigationView.getMenu().findItem(actionId);
            item.setChecked(true);
        }
    }

}