package com.runnimal.app.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.runnimal.app.android.R;

import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {

    public static void open(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindViews();
    }

    private void bindViews() {
        ButterKnife.bind(this);
    }
}
