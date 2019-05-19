package com.runnimal.app.android.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.LoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Inject
    LoginPresenter presenter;

    @BindView(R.id.button_main_login)
    Button loginButton;
    @BindView(R.id.button_main_signup)
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializePresenter();
        getUserData();
        setContentView(R.layout.activity_main);
        bindViews();
        initializeLoginButton();
        initializeSignupButton();
    }

    private void getUserData() {
            SharedPreferences prefs =
                    getSharedPreferences("user", this.getApplicationContext().MODE_PRIVATE);
            String token =  prefs.getString("token", "");
            // llamada a la api para obtener el usuario y guardarlo.
            System.out.println("TOKEN: "+token);
            //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            //

    }

    private void initializePresenter() {
        //presenter.setView(this);
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
