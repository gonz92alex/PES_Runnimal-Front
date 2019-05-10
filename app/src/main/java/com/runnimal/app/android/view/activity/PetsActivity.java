package com.runnimal.app.android.view.activity;

import com.runnimal.app.android.R;

public class PetsActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pets;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_pets;
    }

    @Override
    protected void initView() {
    }
}
