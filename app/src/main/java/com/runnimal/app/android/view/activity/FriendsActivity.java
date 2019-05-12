package com.runnimal.app.android.view.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.view.adapter.FriendsListAdapter;
import com.runnimal.app.android.view.presenter.FriendsPresenter;
import com.runnimal.app.android.view.viewmodel.FriendsViewModel;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class FriendsActivity extends BaseActivity implements FriendsPresenter.View {

    @Inject
    FriendsPresenter presenter;
    FriendsListAdapter adapter;

    @BindView(R.id.search_friends)
    SearchView searchView;
    @BindView(R.id.list_friends)
    RecyclerView friendsList;
    @BindView(R.id.friends_progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_friends;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_challenges;
    }

    @Override
    protected void initView() {
        initializeDagger();
        initializePresenter();
        initializeAdapter();
        initializeRecyclerView();
        initializeSearch();
        presenter.initialize();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        friendsList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        friendsList.setVisibility(View.VISIBLE);
    }
    @Override
    public void showFriendsList(List<FriendsViewModel> friendsList) {
        adapter.addAll(friendsList);
        adapter.notifyDataSetChanged();
    }

    //esta funcion deberia abrir la pantalla de un user
    @Override
    public void openUserScreen(FriendsViewModel friend) {
       // UserDetailActivity.open(this, friend.getId());
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeAdapter() {
        adapter = new FriendsListAdapter(presenter);
    }

    private void initializeRecyclerView() {
        friendsList.setLayoutManager(new LinearLayoutManager(this));
        friendsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        friendsList.setHasFixedSize(true);
        friendsList.setAdapter(adapter);
    }

    private void initializeSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }


}
