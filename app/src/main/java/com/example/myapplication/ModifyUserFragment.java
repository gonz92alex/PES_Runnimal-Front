package com.example.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class ModifyUserFragment extends Fragment {
    TextView textViewNombre;
    TextView textViewCorreo;
    TextView textViewPassword;



    public static Fragment newInstance() {
        ModifyUserFragment fragment = new ModifyUserFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_modify_user, container, false);
        textViewNombre = (TextView) view.findViewById(R.id.EditTextAlias);
        textViewNombre.setText(SingletonSession.Instance().getUsername());


       Button saveUserButton = view.findViewById(R.id.buttonSave);
       saveUserButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               try {
                   modifyUserEv(view);
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });
        return view;

    }

    //llamada API

    private void modifier(final String nombre) throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue((GodActivity)getActivity());
        String url ="http://nidorana.fib.upc.edu/api/user/"  + SingletonSession.Instance().getMail();

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog((GodActivity)getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //Construir el cuerpo del request con la información a enviar
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("alias", nombre);
        final String requestBody = jsonBody.toString();


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("VOLLEY", response);
                        //ToDo -> si la respuesta es 'OK' redirigir a pantalla de login/loguear directamente con el user creado?
                        modUserOk(nombre);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText((GodActivity)getActivity(),"Error: " + error.toString(), Toast.LENGTH_LONG).show();
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

    private void modUserOk(String nombre) {
        GodActivity godActivity = (GodActivity)getActivity();
        godActivity.refreshDrawer(nombre);
    }
    private void modifyUserEv(View view) throws JSONException {
        EditText nombre = (EditText) view.findViewById(R.id.EditTextAlias);

        if(nombre.getText().toString().equals("") ){
            new AlertDialog.Builder((GodActivity)getActivity())
                    .setTitle("Missing parameters")
                    .setMessage("You have to fill first all the text camps")

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else {
            modifier(nombre.getText().toString());
        }
    }



}

