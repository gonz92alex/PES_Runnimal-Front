package com.runnimal.app.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class NotificacionesFragment extends Fragment {

    ListView listView;
    NotificacionListViewAdapter adapter;
    String[] title = new String[]{"user1", "user2", "user3", "user4"};
    String[] mail =  new String[]{"user1@gmail.com", "jaja@gmail.com", "jaja@gmail.com", "user4@gmail.com"};
    int[] icon = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};

    ArrayList<ModelNotificaciones> arrayList = new ArrayList<ModelNotificaciones>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        listView = view.findViewById(R.id.listView);


        if(arrayList.size() > 0) {
            arrayList = new ArrayList<ModelNotificaciones>();
        }
        for (int i = 0; i < title.length; ++i) {
            ModelNotificaciones model = new ModelNotificaciones(title[i], icon[i], mail[i]);
            arrayList.add(model);
        }

        adapter = new NotificacionListViewAdapter((GodActivity)getActivity(), arrayList);
        listView.setAdapter(adapter);
        return view;
    }
}
