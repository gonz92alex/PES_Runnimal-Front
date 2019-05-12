package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TrainingsListViewHolder extends RecyclerView.ViewHolder {

    private final TrainingsPresenter trainingPresenter;

    @BindView(R.id.image_training)
    CircleImageView image;
    @BindView(R.id.text_training_name)
    TextView name;

    public TrainingsListViewHolder(View itemView, TrainingsPresenter trainingPresenter) {
        super(itemView);
        this.trainingPresenter = trainingPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(TrainingViewModel training) {
        onItemClick(training);
        ImageUtils.setImage(training.getImageUrl(), image);
        name.setText(training.getName());
    }

    private void onItemClick(final TrainingViewModel training) {
        itemView.setOnClickListener(v -> trainingPresenter.onTrainingClicked(training));
    }

}
