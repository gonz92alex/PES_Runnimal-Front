package com.example.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntrenamientoDetallado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntrenamientoDetallado extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "content";
    private static final String ARG_PARAM2 = "detail";

    // TODO: Rename and change types of parameters
    private String mContent;
    private String mDetail;

    private TextView mContentText;

    public EntrenamientoDetallado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param content Parameter 1.
     * @param detail Parameter 2.
     * @return A new instance of fragment EntrenamientoDetallado.
     */
    // TODO: Rename and change types and number of parameters
    public static EntrenamientoDetallado newInstance(String content, String detail) {
        EntrenamientoDetallado fragment = new EntrenamientoDetallado();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, content);
        args.putString(ARG_PARAM2, detail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContent = getArguments().getString(ARG_PARAM1);
            mDetail = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_entrenamiento_detallado, container, false);
        mContentText = view.findViewById(R.id.content_tv);
        mContentText.setText(mContent);
        return view;
    }
}
