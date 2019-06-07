package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.SearchPresenter;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.SearchViewModel;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SearchListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final SearchPresenter presenter;
    private final List<OwnerViewModel> usersList;
    private final List<OwnerViewModel> filteredList;

    public SearchListAdapter(SearchPresenter presenter) {
        this.presenter = presenter;
        this.usersList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_user_row, parent, false);
        return new SearchListViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchListViewHolder usersListViewHolder = (SearchListViewHolder) holder;
        OwnerViewModel friend = filteredList.get(position);
        usersListViewHolder.render(friend);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void addAll(Collection<OwnerViewModel> collection) {
        usersList.addAll(collection);
        filteredList.addAll(collection);
    }

    public void filter(String text) {
        filteredList.clear();

        final String textLowerCase = text.toLowerCase();
        usersList.stream() //
                .filter(t -> t.getAlias().toLowerCase().contains(textLowerCase)) //
                .collect(Collectors.toCollection(() -> filteredList));

        notifyDataSetChanged();
    }
}