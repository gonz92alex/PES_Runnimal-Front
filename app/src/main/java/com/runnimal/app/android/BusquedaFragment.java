package com.runnimal.app.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class BusquedaFragment extends Fragment {


    ListView listView;
    BusquedaListViewAdapter adapter;
    String[] title = new String[]{"user1", "user2", "user3", "user4"};
    String[] mail =  new String[]{"user1@gmail.com", "jaja@gmail.com", "jaja@gmail.com", "user4@gmail.com"};
    int[] icon = new int[]{R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round};
    SearchView searchView;
    ArrayList<ModelBusqueda> arrayList = new ArrayList<ModelBusqueda>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_busqueda, container, false);

        listView = view.findViewById(R.id.listView);
        searchView =  view.findViewById(R.id.searchView);




        if(arrayList.size() == 0) {
            for (int i = 0; i < title.length; ++i) {
                ModelBusqueda model = new ModelBusqueda(title[i], icon[i], mail[i]);
                arrayList.add(model);
            }
        }
        //(GodActivity)getActivity())
        adapter = new BusquedaListViewAdapter((GodActivity)getActivity(), arrayList);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else{
                    adapter.filter(newText);
                }
                return true;
            }
        });


        return view;
    }




}
