package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.WalkPresenter;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.WalkViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WalksListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final WalkPresenter presenter;
    private final List<WalkViewModel> walksList;

    public WalksListAdapter(WalkPresenter presenter) {
        this.presenter = presenter;
        this.walksList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_walk_row, parent, false);
        return new WalksListViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WalksListViewHolder walksListViewHolder = (WalksListViewHolder) holder;
        WalkViewModel walk = walksList.get(position);
        walksListViewHolder.render(walk);
    }

    @Override
    public int getItemCount() {
        return walksList.size();
    }

    public void addAll(Collection<WalkViewModel> collection) {
        walksList.addAll(collection);
    }
}
