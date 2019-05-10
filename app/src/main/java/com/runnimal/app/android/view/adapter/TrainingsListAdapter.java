package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.models.EntrenamientoContent;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TrainingsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final TrainingsPresenter presenter;
    private final List<TrainingViewModel> trainingList;
    private final List<TrainingViewModel> filteredList;

    public TrainingsListAdapter(TrainingsPresenter presenter) {
        this.presenter = presenter;
        this.trainingList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_trainings_row, parent, false);
        return new TrainingListViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrainingListViewHolder trainingListViewHolder = (TrainingListViewHolder) holder;
        TrainingViewModel training = filteredList.get(position);
        trainingListViewHolder.render(training);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void addAll(Collection<TrainingViewModel> collection) {
        trainingList.addAll(collection);
        filteredList.addAll(collection);
    }

    public void filter(String text) {
        filteredList.clear();

        final String textLowerCase = text.toLowerCase();
        trainingList.stream() //
                .filter(t -> t.getName().toLowerCase().contains(textLowerCase)) //
                .collect(Collectors.toCollection(() -> filteredList));

        notifyDataSetChanged();
    }
}
