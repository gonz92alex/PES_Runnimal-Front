package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MascotasFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mascotas, container,false);

        Button addButton = view.findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, AnadirMascotaFragment.newInstance("elpapito@mipito.es"))
                        .commit();*/
                AddEv();
            }
        });
        return view;
    }

    public void AddEv() {
        //ToDO pasamos el mail del usuario logueado como parametro?
        ((GodActivity)getActivity()).loadFragment(AnadirMascotaFragment.newInstance());
    }
}