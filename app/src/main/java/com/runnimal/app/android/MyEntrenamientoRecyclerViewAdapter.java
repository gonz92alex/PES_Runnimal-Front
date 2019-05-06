package com.runnimal.app.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnimal.app.android.EntrenamientoFragment.OnListFragmentInteractionListener;
import com.runnimal.app.android.entrenamiento.EntrenamientoContent.EntrenamientoItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EntrenamientoItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEntrenamientoRecyclerViewAdapter extends RecyclerView.Adapter<MyEntrenamientoRecyclerViewAdapter.ViewHolder> {

    private final List<EntrenamientoItem> mValues;
    private final ArrayList<EntrenamientoItem> arrayList;
    private final OnListFragmentInteractionListener mListener;

    public MyEntrenamientoRecyclerViewAdapter(List<EntrenamientoItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.arrayList = new ArrayList<EntrenamientoItem>();
        this.arrayList.addAll(mValues);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_entrenamiento2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);
        Picasso.get()
                .load("https://t2.uc.ltmcdn.com/images/0/5/2/img_como_ensenar_a_un_perro_a_dar_la_pata_22250_600.jpg")
                .resize(425,350)
                .onlyScaleDown()
                .into(holder.mImage);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public EntrenamientoItem mItem;
        public ImageView mImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            mImage = (ImageView) view.findViewById(R.id.imagenEntrenamiento);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public void filter(String charText){
        charText =  charText.toLowerCase(Locale.getDefault());
        mValues.clear();

        if(charText.length() == 0){
            mValues.addAll(arrayList);
        }

        else {
            for(EntrenamientoItem model : arrayList){
                if(model.getContent().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    mValues.add(model);
                }

            }
        }
        notifyDataSetChanged();
    }

}
