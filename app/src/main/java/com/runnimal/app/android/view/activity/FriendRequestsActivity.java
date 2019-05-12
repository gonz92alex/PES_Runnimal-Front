package com.runnimal.app.android.view.activity;

public class FriendRequestsActivity {

    //TODO: Implementar
    /*
    ListView listView;
    SolicitudListViewAdapter adapter;
    String[] title = new String[]{"user1", "user2", "user3", "user4"};
    String[] mail =  new String[]{"user1@gmail.com", "jaja@gmail.com", "jaja@gmail.com", "user4@gmail.com"};
    int[] icon = new int[]{R.mipmap.ic_launcher_round};

    ArrayList<ModelSolicitud> arrayList = new ArrayList<ModelSolicitud>();

    public SolicitudesFragment(){

    }

    public static SolicitudesFragment newInstance() {
        SolicitudesFragment fragment = new SolicitudesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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
                arrayList = new ArrayList<ModelSolicitud>();
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

        ModelSolicitud model = new ModelSolicitud(name, icon[0],mail, id);
        Log.i("VOLLEYCARGA", mail);
        //necessito el nombre y la foto
        arrayList.add(model);
        if(i == fin -1){
            adapter = new SolicitudListViewAdapter((GodActivity) getActivity(), arrayList);
            listView.setAdapter(adapter);
        }

    }
     */
}
