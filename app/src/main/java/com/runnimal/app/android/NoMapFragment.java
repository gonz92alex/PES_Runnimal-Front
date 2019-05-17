package com.runnimal.app.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NoMapFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_map, container,false);

        Button addButton = view.findViewById(R.id.btn_walks_start);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionUtils.checkLocation(getActivity(), getResources().getString(R.string.location_error_title), getResources().getString(R.string.location_error_descr));
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        PermissionUtils.checkPermissionsResult(getActivity(), requestCode, permissions, grantResults, getResources().getString(R.string.location_error_title), getResources().getString(R.string.location_error_descr));
    }
}
