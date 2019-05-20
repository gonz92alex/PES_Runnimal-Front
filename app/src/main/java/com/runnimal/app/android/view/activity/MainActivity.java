package com.runnimal.app.android.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.presenter.MainPresenter;

import java.net.URI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    @Inject
    MainPresenter presenter;

    @BindView(R.id.button_main_login)
    Button loginButton;
    @BindView(R.id.button_main_signup)
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDagger();
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
            if (!token.equals("")){
                presenter.login(token);
            }
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
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

    @Override
    public void loginOk(Owner owner) {
        SingletonSession.Instance().setMail(owner.getEmail());
        SingletonSession.Instance().setUsername(owner.getAlias());
        SingletonSession.Instance().setId(owner.getId());
        SingletonSession.Instance().setPhoto(URI.create("http://nidorana.fib.upc.edu/api/photo/users/" + owner.getEmail()));
        MapActivity.open(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
