package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.SearchPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.SearchViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SearchListViewHolder extends RecyclerView.ViewHolder {

    private final SearchPresenter searchPresenter;

    @BindView(R.id.image_friend)
    CircleImageView image;
    @BindView(R.id.text_friend_name)
    TextView name;

    public SearchListViewHolder(View itemView, SearchPresenter searchPresenter ) {
        super(itemView);
        this.searchPresenter = searchPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(SearchViewModel user) {
        onItemClick(user);
        ImageUtils.setImage(user.getImageUrl(), image);
        name.setText(user.getName());
    }

    private void onItemClick(final SearchViewModel user) {
        itemView.setOnClickListener(v -> searchPresenter.onTrainingClicked(user));
    }

}
