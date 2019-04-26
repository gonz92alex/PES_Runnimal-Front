package com.runnimal.app.android;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnimal.app.android.entrenamiento.MascotaContent;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MascotaPerfilFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "detail";
    private static final String ARG_PARAM3 = "id";

    // TODO: Rename and change types of parameters
    private String mId;
    private String mName;
    private String mDescription;

    private TextView mContentText;
    private TextView mDescriptionText;
    private ImageView mProfileImage;
    private ImageView mOwner;
    private TextView mBreed;
    private TextView mWeight;
    private TextView mAge;

    ImageView imageEdit;

    int fotoPerfil;


    public MascotaPerfilFragment (){

    }

    // TODO: Rename and change types and number of parameters
    public static MascotaPerfilFragment newInstance(String name, String description, String id) {
        MascotaPerfilFragment fragment = new MascotaPerfilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, description);
        args.putString(ARG_PARAM3, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(ARG_PARAM1);
            mDescription = getArguments().getString(ARG_PARAM2);
            mId = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mascota_perfil, container, false);

        mContentText = view.findViewById(R.id.petName_tv);
        mDescriptionText = view.findViewById(R.id.petDesc_tv);
        mProfileImage = view.findViewById(R.id.imagenPerfilMascota);
        mOwner = view.findViewById(R.id.Dueño_tv);
        mBreed = view.findViewById(R.id.DogBreed_tv);
        mAge = view.findViewById(R.id.DogAge_tv);
        mWeight = view.findViewById(R.id.DogWeight_tv);

        mContentText.setText(mName);
        mDescriptionText.setText(mDescription);
        mBreed.setText(MascotaContent.ITEM_MAP.get(mId).getBreed());
        mAge.setText(MascotaContent.ITEM_MAP.get(mId).getBirthdate());
        mWeight.setText(MascotaContent.ITEM_MAP.get(mId).getWeight());
        //mOwner.setText(MascotaContent.ITEM_MAP.get(mId).getOwner());


        //boton editar
        imageEdit = (ImageView) view.findViewById(R.id.imgEditPet);
        //Si estas viendo perfil de tu mascota:
        if (SingletonSession.Instance().getMail().equals(MascotaContent.ITEM_MAP.get(mId).getOwner())){
            imageEdit.setImageResource(R.drawable.ic_edit2);
            imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int size = 0;
                    switch (MascotaContent.ITEM_MAP.get(mId).getSize()){
                        case "Big":
                            size=1;
                            break;
                        case "Medium":
                            size=2;
                            break;
                        case "Small":
                            size=0;
                            break;
                    }
                    ((GodActivity)getActivity()).loadFragment(ModifyPetFragment.newInstance(mName, mDescription, MascotaContent.ITEM_MAP.get(mId).getBreed(), size, MascotaContent.ITEM_MAP.get(mId).getWeight(), MascotaContent.ITEM_MAP.get(mId).getBirthdate()));
                }
            });
        }


        //Todo añadir imagen de la api
        Picasso.get()
                .load("https://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908")
                .resize(425,350)
                .onlyScaleDown()
                .into(mProfileImage);


        //toDo sacar imagen del dueño de la api
        Picasso.get()
                .load("https://pbs.twimg.com/profile_images/482542323230732288/0a4a_buH.jpeg")
                .resize(425,350)
                .onlyScaleDown()
                .into(mOwner);


        mOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GodActivity)getActivity()).loadFragment(ProfileFragment.newInstance(MascotaContent.ITEM_MAP.get(mId).getOwnerAlias(), MascotaContent.ITEM_MAP.get(mId).getOwner(), fotoPerfil));
            }
        });

        return view;
    }

}
