package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.RankingPresenter;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.viewmodel.RankingViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;
import com.squareup.picasso.Picasso;

import java.net.URI;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RankingListViewHolder extends RecyclerView.ViewHolder {

    private final RankingPresenter rankingPresenter;

    @BindView(R.id.image_user)
    CircleImageView image;
    @BindView(R.id.text_user_alias)
    TextView name;
    @BindView(R.id.text_user_points)
    TextView points;
    //cambiar los binds para que sean los de ranking

    public RankingListViewHolder(View itemView, RankingPresenter rankingPresenter) {
        super(itemView);
        this.rankingPresenter = rankingPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(RankingViewModel ranking) {
        onItemClick(ranking);
        getImage(ranking.getImageUrl(), image);
        name.setText(ranking.getName());
        points.setText(ranking.getPoints());
    }

    private void onItemClick(final RankingViewModel ranking) {
        itemView.setOnClickListener(v -> rankingPresenter.onUserClicked(ranking));
    }

    private void getImage(URI photo, ImageView photoImageView) {
        if (photo != null) {
            Picasso.get().load(photo.toString()).fit().centerCrop().into(photoImageView);
        }
    }
}