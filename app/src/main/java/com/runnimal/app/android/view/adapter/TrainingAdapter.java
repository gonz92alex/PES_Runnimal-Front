package com.runnimal.app.android.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrainingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final TrainingsPresenter presenter;
    private final List<TrainingViewModel> trainingList;

    public TrainingAdapter(@NonNull TrainingsPresenter presenter) {
        this.presenter = presenter;
        this.trainingList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_training_list, parent, false);
        return new TrainingViewHolder(view, presenter);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrainingViewHolder trainingViewHolder = (TrainingViewHolder) holder;
        TrainingViewModel training = trainingList.get(position);
        trainingViewHolder.render(training);
    }

    @Override public int getItemCount() {
        return trainingList.size();
    }

    public void addAll(Collection<TrainingViewModel> collection) {
        trainingList.addAll(collection);
    }
}
