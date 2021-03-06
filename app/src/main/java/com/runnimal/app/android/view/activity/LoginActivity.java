package com.runnimal.app.android.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.presenter.LoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;

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
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void loginOk(String token, JSONObject user) throws JSONException {
        String alias = user.getString("alias");
        String id = user.getString("_id");

        //Intent LoginIntent = new Intent(this, MapActivity.class);
        SingletonSession.Instance().setId(id);
        SingletonSession.Instance().setUsername(alias);
        SingletonSession.Instance().setMail(email.getText().toString());
        SingletonSession.Instance().setToken(token);
        SingletonSession.Instance().setPhoto(URI.create("http://nidorana.fib.upc.edu/api/photo/users/" + email.getText().toString()));

        SharedPreferences userDetail = getSharedPreferences("userdetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetail.edit();
        editor.putString("token", token);
        editor.putString("email", email.getText().toString());
        editor.putString("alias", alias);
        editor.putString("id", id);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), MapActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
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
                        .setView(R.layout.alert_dialog)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            } else {
                presenter.login(emailTxt, passwordTxt);
            }
        });
    }

}
