package com.runnimal.app.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.domain.StatsTraining;
import com.runnimal.app.android.service.MediaService;
import com.runnimal.app.android.service.TrainingService;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.adapter.PetsListAdapter;
import com.runnimal.app.android.view.adapter.TrainingStepsAdapter;
import com.runnimal.app.android.view.presenter.PetsPresenter;
import com.runnimal.app.android.view.presenter.StatisticsPresenter;
import com.runnimal.app.android.view.presenter.TrainingDetailPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.StatisticsViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.spark.submitbutton.SubmitButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class StatisticsActivity extends BaseActivity implements StatisticsPresenter.View {

    @Inject
    StatisticsPresenter presenter;


    @BindView(R.id.number_trainings)
    TextView trainingNumber;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_stats;
    }

    @Override
    protected int getBottomMenuItemId() {
        return R.id.menu_bottom_pets;
    }

    @Override
    protected void initView() {
        initializeDagger();
        initializePresenter();
        presenter.initialize();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void showStats(StatisticsViewModel statisticsViewModel) {
        trainingNumber.setText(statisticsViewModel.getNumber());

    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }


}