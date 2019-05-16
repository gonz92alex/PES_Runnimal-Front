package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.FriendRequestsPresenter;
import com.runnimal.app.android.view.presenter.SearchPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.SearchViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestsListViewHolder extends RecyclerView.ViewHolder {

    private final FriendRequestsPresenter friendRequestsPresenter;

    @BindView(R.id.image_user)
    CircleImageView image;
    @BindView(R.id.name_user)
    TextView name;
    @BindView(R.id.mail_user)
    TextView mail;
    @BindView(R.id.button_aceptar)
    Button buttonAceptar;
    @BindView(R.id.button_rechazar)
    Button buttonRechazar;


    public FriendRequestsListViewHolder(View itemView, FriendRequestsPresenter friendRequestsPresenter ) {
        super(itemView);
        this.friendRequestsPresenter = friendRequestsPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(OwnerViewModel user) {
        onAcceptClick();
        onRejectClick();
        onItemClick(user);
        ImageUtils.setImage(user.getImageUrl(), image);
        name.setText(user.getAlias());
        mail.setText(user.getEmail());
    }

    private void onAcceptClick() {
    }
    private void onRejectClick() {
    }

    private void onItemClick(final OwnerViewModel user) {
        itemView.setOnClickListener(v -> friendRequestsPresenter.onTrainingClicked(user));
    }

}
