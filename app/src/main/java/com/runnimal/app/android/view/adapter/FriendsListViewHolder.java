package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.FriendsPresenter;
import com.runnimal.app.android.view.presenter.TrainingsPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.FriendsViewModel;
import com.runnimal.app.android.view.viewmodel.FriendshipViewModel;
import com.runnimal.app.android.view.viewmodel.TrainingViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsListViewHolder extends RecyclerView.ViewHolder {

    private final FriendsPresenter friendsPresenter;

    @BindView(R.id.image_friend)
    CircleImageView image;
    @BindView(R.id.text_friend_name)
    TextView name;

    public FriendsListViewHolder(View itemView, FriendsPresenter friendsPresenter ) {
        super(itemView);
        this.friendsPresenter = friendsPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(FriendshipViewModel friend) {
        onItemClick(friend);
        ImageUtils.setImage(friend.getImageUrl(), image);
        name.setText(friend.getAlias());
    }

    private void onItemClick(final FriendshipViewModel friend) {
        itemView.setOnClickListener(v -> friendsPresenter.onTrainingClicked(friend));
    }

}
