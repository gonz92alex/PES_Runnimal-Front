package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {

    private static final String URL_DATA = "http://nidoqueen.fib.upc.edu:3000/api/users";

    //llamada API
    private void loadUrlData(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.50:80/api/palabras";

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("apiRes", "onResponse: " + response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("apiError", "onErrorResponse: " + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void getUser(String email){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://nidoqueen.fib.upc.edu:3000/api/user/" + email;

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("apiRes", "onResponse: " + response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("apiError", "onErrorResponse: " + error.toString());
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

    public void LoginEv(View view){
        EditText editEmail = (EditText) findViewById(R.id.EditTextMail);
        String email = editEmail.getText().toString();

        EditText editPassword = (EditText) findViewById(R.id.EditTextPassword);
        String password = editPassword.getText().toString();

        loadUrlData();

        //Intent LoginIntent = new Intent(this, GodActivity.class);
        //startActivity(LoginIntent);
    }
}
