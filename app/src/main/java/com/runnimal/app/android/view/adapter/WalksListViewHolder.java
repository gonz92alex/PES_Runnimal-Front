package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.PetsPresenter;
import com.runnimal.app.android.view.presenter.WalkPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.WalkViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class WalksListViewHolder extends RecyclerView.ViewHolder {

    private final WalkPresenter walkPresenter;

    @BindView(R.id.walk_title)
    TextView title;
    @BindView(R.id.walk_duration)
    TextView duration;
    @BindView(R.id.walk_distance)
    TextView distance;

    public WalksListViewHolder(View itemView, WalkPresenter walkPresenter) {
        super(itemView);
        this.walkPresenter = walkPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(WalkViewModel walk) {
        onItemClick(walk);
        title.setText(walk.getTitle());
        duration.setText(walk.getDuration() + " min.");
        distance.setText(walk.getDistance() + " m.");
    }

    private void onItemClick(final WalkViewModel walk) {
        itemView.setOnClickListener(v -> walkPresenter.onWalkClicked(walk));
    }

}
