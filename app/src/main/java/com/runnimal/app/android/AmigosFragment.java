package com.runnimal.app.android;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class AmigosFragment extends Fragment {


    ListView listView;
    BusquedaListViewAdapter adapter;
    String[] title = new String[]{"user1", "user2", "user3", "user4"};
    String[] mail = new String[]{"userloco2@gmail.com", "jaja@gmail.com", "jaja@gmail.com", "user4@gmail.com"};
    int[] icon = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    SearchView searchView;
    ArrayList<ModelBusqueda> arrayList = new ArrayList<ModelBusqueda>();
    String textoAux = "no lo que quiero";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_amigos, container, false);

        listView = view.findViewById(R.id.listView);
        searchView = view.findViewById(R.id.searchView);

        AmigosApi();



        return view;
    }


    //llamada API

    private void AmigosApi(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue((GodActivity) getActivity());
        String url ="http://nidorana.fib.upc.edu/api/friends/user/" + SingletonSession.Instance().getMail() ; //aqui tendre que hacer la llamada

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

                        if(response != null){
                            Log.i("VOLLEY", response);
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
                arrayList = new ArrayList<ModelBusqueda>();
            }
            for (int i = 0; i < responseArray.length(); ++i) {
                Log.i("VOLLEYmiid",SingletonSession.Instance().getId() );
                Log.i("VOLLEYrelated", responseArray.getJSONObject(i).getString("relatedUserId"));
                Log.i("VOLLEYrelating", responseArray.getJSONObject(i).getString("relatingUserId"));
                if(SingletonSession.Instance().getId().equals(responseArray.getJSONObject(i).getString("relatedUserId"))) {
                    Log.i("VOLLEYIF", "dentro del if");
                    InfoFriend(responseArray.getJSONObject(i).getString("relatingUserId"), i, responseArray.length());
                }
                else{
                    Log.i("VOLLEYIF", "dentro del else");
                    InfoFriend(responseArray.getJSONObject(i).getString("relatedUserId"), i, responseArray.length());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void InfoFriend(final String idFriend, final int i, final int fin){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue((GodActivity) getActivity());
        String url ="http://nidorana.fib.upc.edu/api/users/id/" + idFriend;

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
                        Log.i("VOLLEYidFriend", idFriend);
                        if(response != null){
                            try {
                                JSONObject user = new JSONObject(response);
                                CargaArray(user.getString("alias"), user.getString("email"), i,  fin);
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

    public void CargaArray(String name, String mail, int i,  int fin) {

        ModelBusqueda model = new ModelBusqueda(name, icon[0], mail);
        Log.i("VOLLEY2", name);
        Log.i("VOLLEY3", String.valueOf(i));
        Log.i("VOLLEY4", String.valueOf(fin));
        arrayList.add(model);
        if (i == fin -1) {
            Log.i("VOLLEY2", "llego hasta aqui");
            adapter = new BusquedaListViewAdapter((GodActivity) getActivity(), arrayList);
            listView.setAdapter(adapter);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if (TextUtils.isEmpty(newText)) {
                        adapter.filter("");
                        listView.clearTextFilter();
                    } else {
                        adapter.filter(newText);
                    }
                    return true;
                }
            });
        }
    }

}

