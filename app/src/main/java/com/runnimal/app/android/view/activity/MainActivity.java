package com.runnimal.app.android.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.runnimal.app.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button_main_login)
    Button loginButton;
    @BindView(R.id.button_main_signup)
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        initializeLoginButton();
        initializeSignupButton();
    }

    private void bindViews() {
        ButterKnife.bind(this);
    }

    public void initializeLoginButton() {
        loginButton.setOnClickListener(view -> {
            LoginActivity.open(this);
        });
    }

    public void initializeSignupButton() {
        signupButton.setOnClickListener(view -> {
            SignUpActivity.open(this);
        });
    }
}
