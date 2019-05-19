package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.PetsPresenter;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PetsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final PetsPresenter presenter;
    private final List<PetViewModel> petsList;

    public PetsListAdapter(PetsPresenter presenter) {
        this.presenter = presenter;
        this.petsList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_pets_row, parent, false);
        return new PetsListViewHolder(view, presenter);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PetsListViewHolder petsListViewHolder = (PetsListViewHolder) holder;
        PetViewModel pet = petsList.get(position);
        petsListViewHolder.render(pet);
    }

    @Override
    public int getItemCount() {
        return petsList.size();
    }

    public void addAll(Collection<PetViewModel> collection) {
        petsList.addAll(collection);
    }
}
