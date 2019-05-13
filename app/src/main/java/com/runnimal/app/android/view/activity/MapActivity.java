package com.runnimal.app.android.view.activity;

import android.content.Context;
import android.content.Intent;

import com.runnimal.app.android.R;

public class MapActivity extends BaseActivity {

    public static void open(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected int getBottomMenuItemId() {
        return R.id.menu_bottom_map;
    }

    @Override
    protected void initView() {
    }
}
