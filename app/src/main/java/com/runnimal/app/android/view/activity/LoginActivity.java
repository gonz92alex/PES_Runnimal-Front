package com.runnimal.app.android.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.presenter.LoginPresenter;

import java.net.URI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginPresenter.View {

    @Inject
    LoginPresenter presenter;

    @BindView(R.id.text_login_email)
    TextView email;
    @BindView(R.id.text_login_password)
    TextView password;
    @BindView(R.id.text_login_back_button)
    TextView textBackButton;
    @BindView(R.id.text_login_sign_up_button)
    TextView signUpButton;
    @BindView(R.id.button_login)
    Button loginButton;
    @BindView(R.id.button_login_direct)
    Button directLoginButton;

    public static void open(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        initializeDagger();
        initializePresenter();
        initializeBackButton();
        initializeSignUpButton();
        initializeLoginButton();
        initializeDirectLoginButton();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void loginOk(String token) {
        /*
        Intent LoginIntent = new Intent(this, MapActivity.class);
        SingletonSession.Instance().setId(owner.getId());
        SingletonSession.Instance().setUsername(owner.getAlias());
        SingletonSession.Instance().setMail(owner.getEmail());
        */
        SharedPreferences prefs =
                getSharedPreferences("user",Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", token);
        editor.commit();
        System.out.println("TOKEN: "+token);
        MapActivity.open(this);
    }

    private void bindViews() {
        ButterKnife.bind(this);
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeBackButton() {
        textBackButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void initializeSignUpButton() {
        signUpButton.setOnClickListener(view -> {
            SignUpActivity.open(this);
        });
    }

    private void initializeLoginButton() {
        loginButton.setOnClickListener(view -> {
            String emailTxt = email.getText().toString();
            String passwordTxt = password.getText().toString();
            if (emailTxt.equals("") || passwordTxt.equals("")) {
                new AlertDialog.Builder(this)
                        .setTitle("Missing parameters")
                        .setMessage("You have to fill first all the text camps")

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                presenter.login(emailTxt, passwordTxt);
            }
        });
    }

    private void initializeDirectLoginButton() {
        directLoginButton.setOnClickListener(view -> {
            Intent GodIntent = new Intent(this, MapActivity.class);
            SingletonSession.Instance().setMail("ash@pokemon.com");
            SingletonSession.Instance().setUsername("Swafta");
            SingletonSession.Instance().setId("5c9518c262d914013dd5af3b");
            SingletonSession.Instance().setPhoto(URI.create("http://nidorana.fib.upc.edu/api/photo/users/" + "ash@pokemon.com"));
            MapActivity.open(this);
        });
    }
}
