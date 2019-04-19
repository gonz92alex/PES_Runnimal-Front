package com.runnimal.app.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
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

import java.util.ArrayList;

public class NotificacionesFragment extends Fragment {

    ListView listView;
    NotificacionListViewAdapter adapter;
    String[] title = new String[]{"user1", "user2", "user3", "user4"};
    String[] mail =  new String[]{"user1@gmail.com", "jaja@gmail.com", "jaja@gmail.com", "user4@gmail.com"};
    int[] icon = new int[]{R.mipmap.ic_launcher_round};

    ArrayList<ModelNotificaciones> arrayList = new ArrayList<ModelNotificaciones>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        listView = view.findViewById(R.id.listView);


        FriendRequests();
        return view;
    }

    //llamada API

    private void FriendRequests(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue((GodActivity) getActivity());
        String url ="http://nidorana.fib.upc.edu/api/friendRequests/" + SingletonSession.Instance().getMail();

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog((GodActivity) getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("VOLLEY", response);
                        if(response != null){
                            Log.i("VOLLEY", "yo me lo guiso yo me lo como");
                            cargaRequests(response);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText((GodActivity) getActivity(),"Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void cargaRequests  (String response){

        try {
            JSONArray responseArray = new JSONArray(response);


            if (arrayList.size() > 0) {
                arrayList = new ArrayList<ModelNotificaciones>();
            }
            for (int i = 0; i < responseArray.length(); ++i) {
                Log.i("VOLLEYcarga",responseArray.getJSONObject(i).getString("_id") );
                InfoRequestant(responseArray.getJSONObject(i).getString("requestingId"), responseArray.getJSONObject(i).getString("_id"),  i, responseArray.length());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    //llamada API

    private void InfoRequestant(final String idUser, final String idReq, final int i, final int fin){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue((GodActivity) getActivity());
        String url ="http://nidorana.fib.upc.edu/api/users/id/" + idUser;

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog((GodActivity) getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Log.i("VOLLEYINFO", idUser);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i("VOLLEY", response);
                        if(response != null){
                            try {
                                JSONObject user = new JSONObject(response);
                                CargaArray(user.getString("alias"), user.getString("email"), idReq, i,  fin);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText((GodActivity) getActivity(),"Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public void CargaArray(String name, String mail, String id, int i,  int fin){

        ModelNotificaciones model = new ModelNotificaciones(name, icon[0],mail, id);
        Log.i("VOLLEYCARGA", mail);
        //necessito el nombre y la foto
        arrayList.add(model);
        if(i == fin -1){
            adapter = new NotificacionListViewAdapter((GodActivity) getActivity(), arrayList);
            listView.setAdapter(adapter);
        }

    }
}
