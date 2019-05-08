package com.runnimal.app.android.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        bindViews();
        initView();
    }

    /**
     * @return The layout id that's gonna be the activity view.
     */
    protected abstract int getLayoutId();

    /**
     * Use this method to initialize view components. This method is called after {@link
     * BaseActivity#bindViews()}
     */
    protected abstract void initView();

    /**
     * Every object annotated its gonna injected trough butterknife
     */
    private void bindViews() {
        ButterKnife.bind(this);
    }
}