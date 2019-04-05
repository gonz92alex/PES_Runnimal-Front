package com.example.myapplication;

import android.app.ProgressDialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import okhttp3.internal.http2.Http2Stream;

public class SignUpActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView ImageViewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_act);
        Button ButtonCamera = (Button) findViewById(R.id.buttonCameraEdit);
        ImageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        ButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_REQUEST){
            Bitmap bitmapPhoto = (Bitmap)data.getExtras().get("data");
            ImageViewProfile.setImageBitmap(bitmapPhoto);
        }
    }

    public void BackEv(View view) {
        Intent BackIntent = new Intent(this, MainActivity.class);
        startActivity(BackIntent);
    }


    public void EnterEv(View view) throws JSONException {
        EditText nombre = (EditText) findViewById(R.id.EditTextAlias);
        EditText pass = (EditText) findViewById(R.id.EditTextPassword);
        EditText mail = (EditText) findViewById(R.id.EditTextMail);

        if(nombre.getText().toString().equals("") || pass.getText().toString().equals("") || mail.getText().toString().equals("")){
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

            signUp(nombre.getText().toString(), pass.getText().toString(), mail.getText().toString());
        }

    }

    private void signUp(final String nombre, String pass, final String mail) throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://nidorana.fib.upc.edu/api/users/";

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //Construir el cuerpo del request con la información a enviar
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("email", mail);
        jsonBody.put("password", pass);
        jsonBody.put("alias", nombre);
        final String requestBody = jsonBody.toString();


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("VOLLEY", response);
                        //ToDo -> si la respuesta es 'OK' redirigir a pantalla de login/loguear directamente con el user creado?
                        signUpOk(mail, nombre);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity.this,"Error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }



    public void signUpOk(String email, String nombre /*ToDO falta añadir las fotos */){
        Intent LoginIntent = new Intent(this, GodActivity.class);
        SingletonSession.Instance().setMail(email);
        SingletonSession.Instance().setUsername(nombre);
        startActivity(LoginIntent);
    }

}
