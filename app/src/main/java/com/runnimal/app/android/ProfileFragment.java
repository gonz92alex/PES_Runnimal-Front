package com.runnimal.app.android;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ProfileFragment extends Fragment {


    TextView textViewNombre;
    TextView textViewCorreo;

    String mNombre;
    String mCorreo;
    String mFoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNombre = getArguments().getString("nombre");
            mCorreo = getArguments().getString("correo");
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewNombre = (TextView) profileView.findViewById(R.id.NombreText);
        textViewNombre.setText(mNombre);

        textViewCorreo = (TextView) profileView.findViewById(R.id.CorreoText);
        textViewCorreo.setText(mCorreo);

        //Si estas viendo tu perfil:
        if (mCorreo.equals(SingletonSession.Instance().getMail())){
            ImageView ImgEdit = (ImageView) profileView.findViewById(R.id.imgEdit);
            ImgEdit.setImageResource(R.drawable.editar);

            ImgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                /*FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, AnadirMascotaFragment.newInstance("elpapito@mipito.es"))
                        .commit();*/
                    ((GodActivity)getActivity()).loadFragment(ModifyUserFragment.newInstance());
                }
            });
        }
        else{   //Si estas viendo el perfil de otra persona:

        }

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