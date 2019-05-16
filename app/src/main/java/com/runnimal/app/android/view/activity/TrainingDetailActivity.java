package com.runnimal.app.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.adapter.TrainingStepsAdapter;
import com.runnimal.app.android.view.presenter.TrainingDetailPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;

public class TrainingDetailActivity extends BaseActivity implements TrainingDetailPresenter.View {

    private final static String TRAINING_ID_KEY = "training_id_key";

    @Inject
    TrainingDetailPresenter presenter;
    TrainingStepsAdapter adapter;

    @BindView(R.id.detail_training_container)
    ScrollView container;
    @BindView(R.id.text_detail_training_name)
    TextView name;
    @BindView(R.id.imagenTraining)
    ImageView image;
    @BindView(R.id.text_detail_training_description)
    TextView description;
    @BindView(R.id.button_add_points)
    Button buttonPoints;
    @BindView(R.id.list_training_steps)
    RecyclerView trainingStepsList;
    @BindView(R.id.training_detail_progress_bar)
    ProgressBar progressBar;

    public static void open(Context context, String trainingId) {
        Intent intent = new Intent(context, TrainingDetailActivity.class);
        intent.putExtra(TRAINING_ID_KEY, trainingId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_training_detail;
    }

    @Override
    protected int getBottomMenuItemId() {
        return R.id.menu_bottom_trainings;
    }

    @Override
    public void initView() {
        initializeDagger();
        initializePresenter();
        initializeAdapter();
        initializeRecyclerView();
        initializeAddPointButton();
        presenter.initialize();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTraining(TrainingViewModel training) {
        name.setText(training.getName());
        description.setText(training.getDescription());
        ImageUtils.setImage(training.getImageUrl(), image);
        adapter.addAll(training.getSteps());
        adapter.notifyDataSetChanged();
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
        String trainingId = getIntent().getExtras().getString(TRAINING_ID_KEY);
        presenter.setTrainingId(trainingId);
    }

    private void initializeAdapter() {
        adapter = new TrainingStepsAdapter(presenter);
    }

    private void initializeRecyclerView() {
        trainingStepsList.setLayoutManager(new LinearLayoutManager(this));
        trainingStepsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        trainingStepsList.setHasFixedSize(true);
        trainingStepsList.setAdapter(adapter);
    }

    private void initializeAddPointButton(){
        buttonPoints.setOnClickListener(view -> {
            presenter.addPoints(20, SingletonSession.Instance().getMail());
        });
    }

}
