package com.runnimal.app.android.view.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.view.adapter.TrainingsListAdapter;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class TrainingsActivity extends BaseActivity implements TrainingsPresenter.View {

    @Inject
    TrainingsPresenter presenter;
    TrainingsListAdapter adapter;

    @BindView(R.id.search_trainings)
    SearchView searchView;
    @BindView(R.id.list_trainings)
    RecyclerView trainingList;
    @BindView(R.id.trainings_progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trainings;
    }

    @Override
    protected int getBottomMenuItemId() {
        return R.id.menu_bottom_trainings;
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
        trainingList.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        trainingList.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTrainingList(List<TrainingViewModel> trainingList) {
        adapter.addAll(trainingList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void openTrainingScreen(TrainingViewModel training) {
        TrainingDetailActivity.open(this, training.getId());
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeAdapter() {
        adapter = new TrainingsListAdapter(presenter);
    }

    private void initializeRecyclerView() {
        trainingList.setLayoutManager(new LinearLayoutManager(this));
        trainingList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        trainingList.setHasFixedSize(true);
        trainingList.setAdapter(adapter);
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
