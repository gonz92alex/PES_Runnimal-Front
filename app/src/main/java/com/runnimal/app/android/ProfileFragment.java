package com.runnimal.app.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.runnimal.app.android.entrenamiento.MascotaContent;
import com.runnimal.app.android.entrenamiento.MascotaContent.MascotaItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {


    TextView textViewNombre;
    TextView textViewCorreo;
    ImageView imageRelation;

    RecyclerView petList;
    private OnListFragmentInteractionListener mListener;

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

        //Scroll lateral con las mascotas
        petList = (RecyclerView) profileView.findViewById(R.id.profilePetList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        petList.setLayoutManager(layoutManager);
        //showPets();
        petList.setAdapter(new MascotaHorizontalAdapter(MascotaContent.ITEMS, mListener));//quitar esta linea una vez este el showPets()

        //boton sistema de amisatdes
        imageRelation = (ImageView) profileView.findViewById(R.id.imgEdit);
        //Si estas viendo tu perfil:
        if (mCorreo.equals(SingletonSession.Instance().getMail())){
            imageRelation.setImageResource(R.drawable.editar);
            imageRelation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((GodActivity)getActivity()).loadFragment(ModifyUserFragment.newInstance());
                }
            });
        }
        else{   //Si estas viendo el perfil de otra persona:
            getRelation();
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

    private void showPets() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        //toDO falta una ruta en lel back que dado un user muestre sus pets
        String url ="http://nidorana.fib.upc.edu/api/pets/"+mCorreo;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<MascotaContent.MascotaItem> mascotas = new ArrayList<MascotaItem>();
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject mascota = jsonArray.getJSONObject(i);
                                mascotas.add(new MascotaItem(mascota.getString("_id"),mascota.getString("name"),mascota.getString("description"),mascota.getString("size"), mascota.getString("birth"),mascota.getString("weight"), mascota.getString("race"),mascota.getString("owner")));
                            }
                            petList.setAdapter(new MascotaHorizontalAdapter(mascotas, mListener));
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

    //busca si hay alguna solicitud pendiente
    private void getRelation(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://nidorana.fib.upc.edu/api/friendRequests/"+mCorreo;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            //obtener relacion de la respuesta
                            int relacion = -1;
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject peticion = jsonArray.getJSONObject(i);
                                if ( peticion.getString("requestingId").equals(SingletonSession.Instance().getId()) || peticion.getString("requestedId").equals(SingletonSession.Instance().getId())) {
                                    relacion = 1;
                                    break;
                                }
                            }
                            if (relacion!=-1) mostrarBoton(relacion, "-1");
                            else getRelation2();
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

    //busca si son amigos
    private void getRelation2(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://nidorana.fib.upc.edu/api/friends/"+SingletonSession.Instance().getMail()+"/"+mCorreo;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            //obtener relacion de la respuesta
                            if (jsonArray.length() > 0) mostrarBoton(2, jsonArray.getJSONObject(0).getString("_id"));
                            else mostrarBoton(0, "-1");
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

    //muestra un boton u otro según el parametro de la relación indicado
    private void mostrarBoton(int relacion, final String id_amistad){
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
                        eliminarAmistad(id_amistad);
                    }
                });
                break;
        }
    }

    //envia una solcitud de amistad al usuario de quien se esta visitndo el perfil
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

    //elimina de la lista de amigos al usuario
    private void eliminarAmistad(String id){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://nidorana.fib.upc.edu/api/friends/delete/" + id;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API", "onResponse: amistad borrada");
                        mostrarBoton(0,"-1");
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



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void HorizontalListFragmentInteractionListener(MascotaItem item);
    }
}


