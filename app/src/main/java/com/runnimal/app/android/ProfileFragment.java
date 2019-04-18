package com.runnimal.app.android;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


public class ProfileFragment extends Fragment {


    TextView textViewNombre;
    TextView textViewCorreo;
    ImageView imageRelation;

    String mNombre;
    String mCorreo;
    String mFoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNombre = getArguments().getString("nombre");
            mCorreo = getArguments().getString("correo");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewNombre = (TextView) profileView.findViewById(R.id.NombreText);
        textViewNombre.setText(mNombre);

        textViewCorreo = (TextView) profileView.findViewById(R.id.CorreoText);
        textViewCorreo.setText(mCorreo);


        imageRelation = (ImageView) profileView.findViewById(R.id.imgEdit);
        //Si estas viendo tu perfil:
        if (mCorreo.equals(SingletonSession.Instance().getMail())){
            imageRelation.setImageResource(R.drawable.editar);
            imageRelation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, AnadirMascotaFragment.newInstance("elpapito@mipito.es"))
                        .commit();*/
                    ((GodActivity)getActivity()).loadFragment(ModifyUserFragment.newInstance());
                }
            });
        }
        else{   //Si estas viendo el perfil de otra persona:
            //toDo
            //getRelation()
            mostrarBoton(0);
        }

        return profileView;
    }

    public static final ProfileFragment newInstance(String Nombre, String Mail, int foto){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("nombre", Nombre);
        bundle.putString("correo", Mail);
        bundle.putInt("foto", foto);
        //falta saber passar la imagen de perfil
        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    private void getRelation(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://nidorana.fib.upc.edu/api/friendRequests/" /* + añadir parametros necesarios*/;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //obtener relacion de la respuesta
                            //mostrarBoton(relacion)
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void mostrarBoton(int relacion){
        switch (relacion) {
            case 0://no amigos
                imageRelation.setImageResource(R.mipmap.ic_add);
                imageRelation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            enviar_peticion();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        imageRelation.setImageResource(R.drawable.ic_clock);
                    }
                });
                break;

            case 1://solicitud pendiente
                imageRelation.setImageResource(R.drawable.ic_clock);
                break;

            case 2://son amigos
                imageRelation.setImageResource(R.drawable.ic_remove);
                imageRelation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //toDo - eliminar amigo
                        //llamada a la API para eliminar amistad
                    }
                });
                break;
        }
    }

    private void enviar_peticion() throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://nidorana.fib.upc.edu/api/friendRequests/new";
        Log.d("API", "emails son : " + SingletonSession.Instance().getMail() + "& " + mCorreo);

        //Construir el cuerpo del request con la información a enviar
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("requestingEmail", SingletonSession.Instance().getMail());
        jsonBody.put("requestedEmail", mCorreo);
        final String requestBody = jsonBody.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API", "peticion amistad enviada correctamente");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response){
                int mStatusCode = response.statusCode;
                Log.d("VOLLEY", "parseNetworkResponse:" + Integer.toString(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}