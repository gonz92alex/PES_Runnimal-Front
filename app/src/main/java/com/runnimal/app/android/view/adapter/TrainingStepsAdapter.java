package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.TrainingDetailPresenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrainingStepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final TrainingDetailPresenter presenter;
    private final List<String> steps;

    public TrainingStepsAdapter(TrainingDetailPresenter presenter) {
        this.presenter = presenter;
        this.steps = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_steps_row, parent, false);
        return new TrainingStepsViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrainingStepsViewHolder trainingStepsViewHolder = (TrainingStepsViewHolder) holder;
        trainingStepsViewHolder.render(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public void addAll(Collection<String> collection) {
        steps.addAll(collection);
    }
}
