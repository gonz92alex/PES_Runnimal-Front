package com.runnimal.app.android.view.activity;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.Presenter;

public class FriendsActivity extends BaseActivity implements FriendsPresenter.View {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_trainings;
    }


}
