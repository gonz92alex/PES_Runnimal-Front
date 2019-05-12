package com.runnimal.app.android.view.activity;

public class SearchActivity {
    //TODO: Implementar
    /*
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
        final View view = inflater.inflate(R.layout.fragment_busqueda, container, false);

        listView = view.findViewById(R.id.listView);
        searchView = view.findViewById(R.id.searchView);

        UsersApi();



        return view;
    }


    //llamada API

    private void UsersApi(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue((GodActivity) getActivity());
        String url ="http://nidorana.fib.upc.edu/api/users/" ;

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
                            cargaUsers(response);

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

    private void cargaUsers (String response){

        try {
            JSONArray responseArray = new JSONArray(response);
            textoAux = responseArray.getJSONObject(0).getString("alias");

            if (arrayList.size() > 0) {
                arrayList = new ArrayList<ModelBusqueda>();
            }
            for (int i = 0; i < responseArray.length(); ++i) {
                ModelBusqueda model = new ModelBusqueda(responseArray.getJSONObject(i).getString("alias"), icon[0], responseArray.getJSONObject(i).getString("email"));
                arrayList.add(model);
            }


            //(GodActivity)getActivity())
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

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
     */
}
