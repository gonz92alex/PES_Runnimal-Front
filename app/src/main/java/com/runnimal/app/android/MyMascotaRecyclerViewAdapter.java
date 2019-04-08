package com.runnimal.app.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import com.runnimal.app.android.MascotasFragment.OnListFragmentInteractionListener;
import com.runnimal.app.android.entrenamiento.MascotaContent;
import com.runnimal.app.android.entrenamiento.MascotaContent.MascotaItem;
import com.squareup.picasso.Picasso;

class MyMascotaRecyclerViewAdapter extends RecyclerView.Adapter<MyMascotaRecyclerViewAdapter.ViewHolder> {

    private final List<MascotaItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMascotaRecyclerViewAdapter(List<MascotaItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mascota2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).name);
        Picasso.get()
                .load("https://cdn.shopify.com/s/files/1/0257/6087/products/Pikachu_Single_Front_dc998741-c845-43a8-91c9-c1c97bec17a4.png?v=1523938908")
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
        public MascotaItem mItem;
        public ImageView mImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.contentMascota);
            mImage = (ImageView) view.findViewById(R.id.imagenMascota);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
