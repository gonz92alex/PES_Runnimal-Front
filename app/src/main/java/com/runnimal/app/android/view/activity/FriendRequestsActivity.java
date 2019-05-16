package com.runnimal.app.android.view.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.view.activity.BaseActivity;
import com.runnimal.app.android.view.adapter.FriendRequestsListAdapter;
import com.runnimal.app.android.view.adapter.SearchListAdapter;
import com.runnimal.app.android.view.presenter.FriendRequestsPresenter;
import com.runnimal.app.android.view.presenter.SearchPresenter;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FriendRequestsActivity extends BaseActivity implements FriendRequestsPresenter.View {


    @Inject
    FriendRequestsPresenter presenter;
    FriendRequestsListAdapter adapter;

    @BindView(R.id.list_friends)
    RecyclerView usersList;
    @BindView(R.id.friends_progress_bar)
    ProgressBar progressBar;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_request;
    }

    @Override
    protected int getBottomMenuItemId() {
        return -1;
    }


    @Override
    protected void initView() {
        initializeDagger();
        initializePresenter();
        initializeAdapter();
        initializeRecyclerView();
        presenter.initialize();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        usersList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        usersList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUsersList(List<OwnerViewModel> usersList) {
        adapter.addAll(usersList);
        adapter.notifyDataSetChanged();
    }

    //esta funcion deberia abrir la pantalla de un user
    @Override
    public void openUserScreen(OwnerViewModel user) {
        OwnerDetailActivity.open(this, user.getId(), user.getEmail());
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeAdapter() {
        adapter = new FriendRequestsListAdapter(presenter);
    }

    private void initializeRecyclerView() {
        usersList.setLayoutManager(new LinearLayoutManager(this));
        usersList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        usersList.setHasFixedSize(true);
        usersList.setAdapter(adapter);
    }
}