package com.runnimal.app.android.view.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.view.adapter.RankingListAdapter;
import com.runnimal.app.android.view.presenter.RankingPresenter;
import com.runnimal.app.android.view.viewmodel.RankingViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RankingActivity extends BaseActivity implements RankingPresenter.View {


    @Inject
    RankingPresenter presenter;
    RankingListAdapter adapter;

    @BindView(R.id.button_worlwide)
    Button buttonWorld;
    @BindView(R.id.button_local)
    Button buttonLocal;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.list_users_ranking)
    RecyclerView rankingList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ranking;
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
        presenter.initialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rankingList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        rankingList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showRankingList(List<RankingViewModel> rankingList) {
        adapter.addAll(rankingList);
        adapter.notifyDataSetChanged();

        //añadir las funciones del boton

    }

    @Override
    //esta funcion deberia abrir el perfil del user que se seleccione
    public void openUserScreen(RankingViewModel training) {
        //TrainingDetailActivity.open(RankingActivity.this, training.getId());
    }



    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeAdapter() {
        adapter = new RankingListAdapter(presenter);
    }

    private void initializeRecyclerView() {
        rankingList.setLayoutManager(new LinearLayoutManager(this));
        rankingList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rankingList.setHasFixedSize(true);
        rankingList.setAdapter(adapter);
    }

}