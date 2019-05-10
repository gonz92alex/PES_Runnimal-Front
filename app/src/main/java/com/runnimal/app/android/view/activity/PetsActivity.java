package com.runnimal.app.android.view.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.view.adapter.PetsListAdapter;
import com.runnimal.app.android.view.presenter.PetsPresenter;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class PetsActivity extends BaseActivity implements PetsPresenter.View {

    @Inject
    PetsPresenter presenter;
    PetsListAdapter adapter;

    @BindView(R.id.list_pets)
    RecyclerView petsList;
    @BindView(R.id.pets_progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pets;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_pets;
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
        petsList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        petsList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPetsList(List<PetViewModel> petsList) {
        adapter.addAll(petsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openPetScreen(PetViewModel training) {
        //startActivity(new Intent(this, PetDetailActivity.class));
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeAdapter() {
        adapter = new PetsListAdapter(presenter);
    }

    private void initializeRecyclerView() {
        petsList.setLayoutManager(new LinearLayoutManager(this));
        petsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        petsList.setHasFixedSize(true);
        petsList.setAdapter(adapter);
    }
}
