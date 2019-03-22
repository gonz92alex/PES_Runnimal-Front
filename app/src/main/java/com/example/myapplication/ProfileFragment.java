package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProfileFragment extends Fragment {
    String TextNombre;
    TextView textViewTest;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);
        textViewTest = (TextView) profileView.findViewById(R.id.TextoTest);
        TextNombre = getArguments().getString("nombre");
        textViewTest.setText(TextNombre);
        return profileView;
    }

    public static final ProfileFragment newInstance(String test){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("nombre", test);
        profileFragment.setArguments(bundle);
        return profileFragment;
    }
}