package com.runnimal.app.android;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BusquedaListViewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<ModelBusqueda> modelslist;
    ArrayList<ModelBusqueda> arrayList;

    public BusquedaListViewAdapter(Context Context, List<ModelBusqueda> modelslist) {
        mContext = Context;
        this.modelslist = modelslist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<ModelBusqueda>();
        this.arrayList.addAll(modelslist);
    }

    public class ViewHolder{
         TextView mTitleTv;
         TextView mMailTv;
         ImageView mIconIv;
    }

    @Override
    public int getCount() {
        return modelslist.size();
    }

    @Override
    public Object getItem(int position) {
        return modelslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_busqueda, null);

            holder.mTitleTv = convertView.findViewById(R.id.mainTitle);
            holder.mIconIv = convertView.findViewById(R.id.fotoUser);
            holder.mMailTv = convertView.findViewById(R.id.mailUsers);

            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.mTitleTv.setText(modelslist.get(position).getTitle());
        holder.mIconIv.setImageResource(modelslist.get(position).getIcon());
        holder.mMailTv.setText(modelslist.get(position).getMail());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                GodActivity activity = (GodActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,ProfileFragment.newInstance(modelslist.get(position).getTitle(),modelslist.get(position).getMail() ,modelslist.get(position).getIcon() )).addToBackStack(null).commit();


            }
        });
        return convertView;
    }

    public void filter(String charText){
        charText =  charText.toLowerCase(Locale.getDefault());
        modelslist.clear();

        if(charText.length() == 0){
            modelslist.addAll(arrayList);
        }

        else {
            for(ModelBusqueda model : arrayList){
                if(model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText) || model.getMail().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modelslist.add(model);
                }

            }
        }
        notifyDataSetChanged();
    }
}
