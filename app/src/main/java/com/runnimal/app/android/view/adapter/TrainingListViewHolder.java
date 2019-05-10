package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android._service.fileUploader;
import com.runnimal.app.android.view.activity.TrainingsActivity;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.squareup.picasso.Picasso;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TrainingListViewHolder extends RecyclerView.ViewHolder {

    private final TrainingsPresenter trainingPresenter;

    @BindView(R.id.image_training)
    CircleImageView image;
    @BindView(R.id.text_training_name)
    TextView name;

    public TrainingListViewHolder(View itemView, TrainingsPresenter trainingPresenter) {
        super(itemView);
        this.trainingPresenter = trainingPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(TrainingViewModel training) {
        onItemClick(training);
        getImage(training.getId(), image);
        name.setText(training.getName());
    }

    private void onItemClick(final TrainingViewModel training) {
        itemView.setOnClickListener(v -> trainingPresenter.onTrainingClicked(training));
    }

    /*private void getImage(URI photo, ImageView photoImageView) {
        if (photo != null) {
            Picasso.get().load(photo.toString()).fit().centerCrop().into(photoImageView);
        }
    }*/

    private void getImage(String id, ImageView photoImageView){
        //toDO Esta feo que la url este aqui
        String url = "http://nidorana.fib.upc.edu/api/photo/trainnings/" + id;
        Picasso.get().load(url)
                .fit()
                .centerCrop()
                .into(photoImageView);
    }

}
