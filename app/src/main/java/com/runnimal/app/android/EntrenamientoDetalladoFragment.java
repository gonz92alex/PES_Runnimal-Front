package com.runnimal.app.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.runnimal.app.android.entrenamiento.EntrenamientoContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntrenamientoDetalladoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntrenamientoDetalladoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "detail";
    private static final String ARG_PARAM3 = "id";

    // TODO: Rename and change types of parameters
    private String mContent;
    private String mDescription;
    private String mId;

    private TextView mContentText;
    private TextView mDescriptionText;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public EntrenamientoDetalladoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param content Parameter 1.
     * @param description Parameter 2.
     * @return A new instance of fragment EntrenamientoDetalladoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntrenamientoDetalladoFragment newInstance(String content, String description, String id) {
        EntrenamientoDetalladoFragment fragment = new EntrenamientoDetalladoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, content);
        args.putString(ARG_PARAM2, description);
        args.putString(ARG_PARAM3, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContent = getArguments().getString(ARG_PARAM1);
            mDescription = getArguments().getString(ARG_PARAM2);
            mId = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entrenamiento_detallado, container, false);
        mContentText = view.findViewById(R.id.content_tv);
        mDescriptionText = view.findViewById(R.id.description_tv);
        mContentText.setText(mContent);
        mDescriptionText.setText(mDescription);

        recyclerView = (RecyclerView) view.findViewById(R.id.steps_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if (EntrenamientoContent.getSteps(mId).size() == 0){
            loadSteps();
        }


        adapter = new StepsAdapter(EntrenamientoContent.getSteps(mId), Objects.requireNonNull(getActivity()).getApplicationContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadSteps(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://nidorana.fib.upc.edu/api/trainnings/"+mId;

        //Loading Message
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Log.d("VOLLEY", "here we go!");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("VOLLEY", "onResponse: respondido!");
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray steps = jsonObject.getJSONArray("steps");
                            Log.d("VOLLEY", "tamaño steps: " + steps.length());
                            ArrayList<String> stepLista = new ArrayList<>();
                            for (int i = 0; i < steps.length(); i++){
                                stepLista.add(steps.getString(i));
                            }
                            EntrenamientoContent.setSteps(mId,stepLista);
                            adapter = new StepsAdapter(EntrenamientoContent.getSteps(mId), Objects.requireNonNull(getActivity()).getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("apiError", error.toString());
                Toast.makeText(getContext(), "Error " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }




    //Clase Adaptador para mostrar los pasos en el recycler view
    public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

        ArrayList<String> stepsList;

        public StepsAdapter(ArrayList<String> stepsList, Context context) {
            this.stepsList = stepsList;
        }

        @Override
        public StepsAdapter.StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_row, parent, false);
            return new StepsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final StepsAdapter.StepsViewHolder holder, int position) {
            holder.mText.setText(stepsList.get(position));
        }

        @Override
        public int getItemCount() {
            return stepsList.size();
        }

        public class StepsViewHolder extends RecyclerView.ViewHolder{

            protected TextView mText;

            public StepsViewHolder(View itemView) {
                super(itemView);
                mText = (TextView) itemView.findViewById(R.id.step_tv);
            }
        }
    }
}

