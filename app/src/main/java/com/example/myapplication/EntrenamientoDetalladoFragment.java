package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.entrenamiento.EntrenamientoContent;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntrenamientoDetalladoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntrenamientoDetalladoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "content";
    private static final String ARG_PARAM2 = "detail";
    private static final String ARG_PARAM3 = "id";

    // TODO: Rename and change types of parameters
    private String mContent;
    private String mDetail;
    private String mId;

    private TextView mContentText;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<String> stepsList = new ArrayList();

    public EntrenamientoDetalladoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param content Parameter 1.
     * @param detail Parameter 2.
     * @return A new instance of fragment EntrenamientoDetalladoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntrenamientoDetalladoFragment newInstance(String content, String detail, String id) {
        EntrenamientoDetalladoFragment fragment = new EntrenamientoDetalladoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, content);
        args.putString(ARG_PARAM2, detail);
        args.putString(ARG_PARAM3, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContent = getArguments().getString(ARG_PARAM1);
            mDetail = getArguments().getString(ARG_PARAM2);
            mId = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entrenamiento_detallado, container, false);
        mContentText = view.findViewById(R.id.content_tv);
        mContentText.setText(mContent);

        recyclerView = (RecyclerView) view.findViewById(R.id.steps_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        stepsList = EntrenamientoContent.getSteps(mId);
        adapter = new StepsAdapter(stepsList,getActivity().getApplicationContext());
        recyclerView.setAdapter(adapter);

        return view;
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

