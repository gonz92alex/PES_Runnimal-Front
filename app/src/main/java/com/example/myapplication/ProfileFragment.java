package com.example.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ProfileFragment extends Fragment {
    String TextNombre;
    String TextCorreo;
    TextView textViewNombre;
    TextView textViewCorreo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewNombre = (TextView) profileView.findViewById(R.id.NombreText);
        TextNombre = getArguments().getString("nombre");
        textViewNombre.setText(TextNombre);

        textViewCorreo = (TextView) profileView.findViewById(R.id.CorreoText);
        TextCorreo = getArguments().getString("correo");
        textViewCorreo.setText(TextCorreo);
        return profileView;
    }

    public static final ProfileFragment newInstance(String Nombre, String Mail, Bitmap foto){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle bundle = new Bundle(1);
        bundle.putString("nombre", Nombre);
        bundle.putString("correo", Mail);
        bundle.putParcelable("foto", foto);
        //falta saber passar la imagen de perfil
        profileFragment.setArguments(bundle);
        return profileFragment;
    }
}