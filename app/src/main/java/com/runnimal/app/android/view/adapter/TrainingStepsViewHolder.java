package com.runnimal.app.android.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.TrainingDetailPresenter;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.squareup.picasso.Picasso;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TrainingStepsViewHolder extends RecyclerView.ViewHolder {

    private final TrainingDetailPresenter presenter;

    @BindView(R.id.text_detail_training_steps_row)
    TextView step;

    public TrainingStepsViewHolder(View itemView, TrainingDetailPresenter presenter) {
        super(itemView);
        this.presenter = presenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(String stepInfo) {
        step.setText(stepInfo);
    }

}
