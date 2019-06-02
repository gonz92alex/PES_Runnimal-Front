package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.activity.FriendRequestsActivity;
import com.runnimal.app.android.view.presenter.FriendRequestsPresenter;
import com.runnimal.app.android.view.viewmodel.FriendshipViewModel;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FriendRequestsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final FriendRequestsPresenter presenter;
    private final FriendRequestsActivity friendRequestsActivity;
    private final List<FriendshipViewModel> usersList;
    private final List<FriendshipViewModel> filteredList;

    public FriendRequestsListAdapter(FriendRequestsPresenter presenter, FriendRequestsActivity friendRequestsActivity) {
        this.presenter = presenter;
        this.friendRequestsActivity = friendRequestsActivity;
        this.usersList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
    }

    //ToDo cambiar los SearchListViewHolder

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_request_row, parent, false);
        return new FriendRequestsListViewHolder(view, presenter, this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FriendRequestsListViewHolder usersListViewHolder = (FriendRequestsListViewHolder) holder;
        FriendshipViewModel friend = filteredList.get(position);
        usersListViewHolder.render(friend);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void addAll(Collection<FriendshipViewModel> collection) {
        usersList.addAll(collection);
        filteredList.addAll(collection);
    }


    public void refresh() {
        friendRequestsActivity.refresh();
    }

    public void clearAll() {
        usersList.clear();
        filteredList.clear();
    }
}