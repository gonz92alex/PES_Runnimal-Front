package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {

    private static final String URL_DATA = "http://nidoqueen.fib.upc.edu:3000/api/users";

    //llamada API
    private void getUser(final String email, final String password){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://nidorana.fib.upc.edu/api/user/" + email;

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("apires", "onResponse: " + response);
                            JSONObject user = new JSONObject(response);
                            Log.d("apidata", "onResponse: " + user.getString("password"));
                            if (password.equals(user.getString("password"))) LoginOk(email, user.getString("alias"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LogInActivity.this,"Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

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

    public void LoginEv(View view) throws JSONException {
        EditText editEmail = (EditText) findViewById(R.id.EditTextMail);
        String email = editEmail.getText().toString();

        EditText editPassword = (EditText) findViewById(R.id.EditTextPassword);
        String password = editPassword.getText().toString();

        if(email.equals("") || password.equals("")){
            new AlertDialog.Builder(this)
                    .setTitle("Missing parameters")
                    .setMessage("You have to fill first all the text camps")

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            getUser(email, password);
        }
    }

    public void LoginOk(String email, String nombre /* falta añadir las fotos */){
        Intent LoginIntent = new Intent(this, GodActivity.class);
        SingletonSession.Instance().setMail(email);
        SingletonSession.Instance().setUsername(nombre);
        //ToDO llamada coger nombre mascotas
        String mascotas[] = getMascotas(email);
        SingletonSession.Instance().setMascotas(mascotas);
        startActivity(LoginIntent);
    }


    public void directEv(View view) {
        Intent GodIntent = new Intent(this, GodActivity.class);
        SingletonSession.Instance().setMail("arthur@gmail.com");
        SingletonSession.Instance().setUsername("arthur");
        startActivity(GodIntent);

    }
}
