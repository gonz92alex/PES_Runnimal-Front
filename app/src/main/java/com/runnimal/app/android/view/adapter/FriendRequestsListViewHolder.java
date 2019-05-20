package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.presenter.FriendRequestsPresenter;
import com.runnimal.app.android.view.presenter.SearchPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.FriendshipViewModel;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.SearchViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestsListViewHolder extends RecyclerView.ViewHolder {

    private final FriendRequestsPresenter friendRequestsPresenter;
    private final FriendRequestsListAdapter adapter;

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


    public FriendRequestsListViewHolder(View itemView, FriendRequestsPresenter friendRequestsPresenter, FriendRequestsListAdapter adapter ) {
        super(itemView);
        this.friendRequestsPresenter = friendRequestsPresenter;
        this.adapter = adapter;
        ButterKnife.bind(this, itemView);
    }

    public void render(FriendshipViewModel user) {
        onAcceptClick(user.getIdFriendship());
        onRejectClick(user.getIdFriendship());
        onItemClick(user);
        ImageUtils.setImage(user.getImageUrl(), image);
        name.setText(user.getAlias());
        mail.setText(user.getEmail());
    }

    private void onAcceptClick(String id) {
        buttonAceptar.setOnClickListener(view -> {
            friendRequestsPresenter.acceptFriend(id);
            adapter.refresh();
        });
    }
    private void onRejectClick(String id) {
        buttonRechazar.setOnClickListener(view -> {
            friendRequestsPresenter.rejectFriend(id);
            adapter.refresh();
        });
    }


    private void onItemClick(final FriendshipViewModel user) {
        itemView.setOnClickListener(v -> friendRequestsPresenter.onTrainingClicked(user));
    }

}
