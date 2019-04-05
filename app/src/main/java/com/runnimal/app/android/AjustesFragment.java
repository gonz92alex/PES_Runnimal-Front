package com.runnimal.app.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AjustesFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ajustes, container, false);
        Button modUserButton = view.findViewById(R.id.btn_mod_user);
        modUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEvUser();
            }
        });
        Button modPetButton = view.findViewById(R.id.btn_mod_pets);
        modPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEvPet(view);
            }
        });


        return view;
    }

    private void AddEvPet(View view) {
        ((GodActivity)getActivity()).loadFragment(ModifyPetFragment.newInstance());
    }

    public void AddEvUser() {
        ((GodActivity)getActivity()).loadFragment(ModifyUserFragment.newInstance());
    }
}
