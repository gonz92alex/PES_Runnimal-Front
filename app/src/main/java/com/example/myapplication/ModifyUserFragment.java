package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ModifyUserFragment extends Fragment {
    TextView textViewNombre;
    TextView textViewCorreo;
    TextView textViewPassword;


    public static Fragment newInstance() {
        ModifyUserFragment fragment = new ModifyUserFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_user, container, false);
        textViewNombre = (TextView) view.findViewById(R.id.EditTextAlias);
        textViewNombre.setText(SingletonSession.Instance().getUsername());

        textViewCorreo = (TextView) view.findViewById(R.id.EditTextMail);
        textViewCorreo.setText(SingletonSession.Instance().getMail());

        textViewPassword = (TextView) view.findViewById(R.id.EditTextPassword);
        textViewPassword.setText("jajajajajajajaja");

        Button modPetButton = view.findViewById(R.id.btn_mod_pets);
        modPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyUserEv();
            }
        });
        return view;

    }

    private void modifyUserEv() {
    }


}