package com.runnimal.app.android;

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
import android.widget.Spinner;
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
import com.runnimal.app.android.entrenamiento.MascotaContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ModifyPetFragment extends Fragment {
    private static String nombre;
    private static String nombreViejo;
    private static String descripcion;
    private static String raza;
    private static String peso;
    private static int tamano;
    private static String nacimiento;
    private static String id;



    public static Fragment newInstance(String ids, String name, String descr, String race, int size, String weight, String birth) {
        id = ids;
        nombreViejo = name;
        nombre = name;
        descripcion = descr;
        raza = race;
        tamano = size;
        peso = weight;
        nacimiento = birth;
        ModifyPetFragment fragment = new ModifyPetFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_modify_pet, container, false);


        EditText descripcionText = (EditText) view.findViewById(R.id.EditTextDogDescription);
        descripcionText.setText(descripcion);

        EditText razaText = (EditText) view.findViewById(R.id.EditTextDogBreed);
        razaText.setText(raza);

        EditText pesoText = (EditText) view.findViewById(R.id.EditTextDogWeight);
        pesoText.setText(peso);

        Spinner tamanoText = (Spinner) view.findViewById(R.id.EditTextDogSize);
        tamanoText.setSelection(tamano);

        EditText anoNacimientoText = (EditText) view.findViewById(R.id.EditTextDogBirthdate);
        anoNacimientoText.setText(nacimiento);



        Button saveUserButton = view.findViewById(R.id.buttonSavePet);
        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    modifyPetEv(view);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;

    }



    //llamada API

    private void modifier( final String description, final String breed, final String size, final String weight, final String birth) throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue((GodActivity)getActivity());
        String url ="http://nidorana.fib.upc.edu/api/pets/" + SingletonSession.Instance().getMail() +"/" + nombreViejo;

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog((GodActivity)getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        //Construir el cuerpo del request con la información a enviar
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("description", description);
        jsonBody.put("race", breed);
        jsonBody.put("weight", weight);
        jsonBody.put("size", size);
        jsonBody.put("birth", birth);
        //ToDo hay que poner en el json lo que toca que por ahora no se lo que es
        final String requestBody = jsonBody.toString();


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("VOLLEY", response);
                        //ToDo -> si la respuesta es 'OK' redirigir a pantalla de login/loguear directamente con el user creado?
                        modPetOk(id,description, breed, weight, size, birth);

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

    private void modPetOk(String id,String desc,String breed,String weight,String size,String bd) {
        //GodActivity godActivity = (GodActivity)getActivity();
        //godActivity.refreshDrawer(nombre);
        //godActivity.refreshPet();
        MascotaContent.actualizarMascota(id,bd,desc,weight,breed);
        //ToDO hay que hablar de donde esta guardada la info de las mascotas en nuestra session para actaulizarla properly
    }
    private void modifyPetEv(View view) throws JSONException {
        EditText description = (EditText) view.findViewById(R.id.EditTextDogDescription);
        EditText breed = (EditText) view.findViewById(R.id.EditTextDogBreed);
        Spinner size = (Spinner) view.findViewById(R.id.EditTextDogSize);
        EditText birth = (EditText) view.findViewById(R.id.EditTextDogBirthdate);
        EditText weight = (EditText) view.findViewById(R.id.EditTextDogWeight);

        if( description.getText().toString().equals("") || breed.getText().toString().equals("") || size.getSelectedItem().toString().equals("") || birth.getText().toString().equals("") || weight.getText().toString().equals("") ){
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
            modifier( description.getText().toString(), breed.getText().toString(), size.getSelectedItem().toString(), birth.getText().toString(), weight.getText().toString());
        }
    }


}
