package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.RankingPresenter;
import com.runnimal.app.android.view.viewmodel.RankingViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RankingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final RankingPresenter presenter;
    private final List<RankingViewModel> rankingList;
    private final List<RankingViewModel> filteredList;//tengo que crear el viewholder

    public RankingListAdapter(RankingPresenter presenter) {
        this.presenter = presenter;
        this.rankingList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_ranking_row, parent, false);
        return new RankingListViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RankingListViewHolder trainingViewHolder = (RankingListViewHolder) holder;
        RankingViewModel training = filteredList.get(position);
        trainingViewHolder.render(training);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void addAll(Collection<RankingViewModel> collection) {
        rankingList.addAll(collection);
        filteredList.addAll(collection);
    }
}