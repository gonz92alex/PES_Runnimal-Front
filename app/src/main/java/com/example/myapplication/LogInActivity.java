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
    private JSONObject user;

    //llamada API
    private boolean getUser(String email){
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
                        progressDialog.dismiss();
                        try {
                            JSONObject jo = new JSONObject(response);
                            Log.d("apiInfo", "user info: " + jo.getString("password"));
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
        return true;
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

        getUser(email);

        /*Log.d("userPass", user.getString("password"));
        if (password == user.getString("password")){
            Intent LoginIntent = new Intent(this, GodActivity.class);
            startActivity(LoginIntent);
        }*/
    }
}
