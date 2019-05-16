package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.domain.Friend;
import com.runnimal.app.android.domain.FriendRequest;
import com.runnimal.app.android.view.presenter.FriendRequestsPresenter;
import com.runnimal.app.android.view.presenter.SearchPresenter;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.SearchViewModel;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRequestsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final FriendRequestsPresenter presenter;
    private final List<OwnerViewModel> usersList;
    private final List<OwnerViewModel> filteredList;

    public FriendRequestsListAdapter(FriendRequestsPresenter presenter) {
        this.presenter = presenter;
        this.usersList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
    }

    //ToDo cambiar los SearchListViewHolder

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_request_row, parent, false);
        return new FriendRequestsListViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FriendRequestsListViewHolder usersListViewHolder = (FriendRequestsListViewHolder) holder;
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

}