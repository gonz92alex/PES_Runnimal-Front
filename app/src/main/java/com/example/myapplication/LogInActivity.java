package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void BackEv(View view) {
        Intent BackIntent = new Intent(this, MainActivity.class);
        startActivity(BackIntent);
    }

    public void SignUpEv(View view) {
        Intent SignUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(SignUpIntent);
    }

    public void LoginEv(View view){
        EditText editEmail = (EditText) findViewById(R.id.EditTextMail);
        String email = editEmail.getText().toString();

        EditText editPassword = (EditText) findViewById(R.id.EditTextPassword);
        String password = editPassword.getText().toString();

        //llamada API


        if (pass_correct){
            Intent LoginIntent = new Intent(this, GodActivity.class);
            startActivity(LoginIntent);
        }
    }
}
