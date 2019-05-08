package com.runnimal.app.android.view.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.view.adapter.TrainingListAdapter;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class TrainingsActivity extends BaseActivity implements TrainingsPresenter.View {

    @Inject
    TrainingsPresenter presenter;
    TrainingListAdapter adapter;

    @BindView(R.id.list_trainings)
    RecyclerView trainingList;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

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
        TrainingDetailActivity.open(TrainingsActivity.this, training.getId());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trainings;
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

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeAdapter() {
        adapter = new TrainingListAdapter(presenter);
    }

    private void initializeRecyclerView() {
        trainingList.setLayoutManager(new LinearLayoutManager(this));
        trainingList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        trainingList.setHasFixedSize(true);
        trainingList.setAdapter(adapter);
    }
}
