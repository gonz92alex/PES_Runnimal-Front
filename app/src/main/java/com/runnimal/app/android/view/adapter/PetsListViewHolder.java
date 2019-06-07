package com.runnimal.app.android.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.view.presenter.PetsPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PetsListViewHolder extends RecyclerView.ViewHolder {

    private final PetsPresenter petsPresenter;

    @BindView(R.id.image_pet)
    CircleImageView image;
    @BindView(R.id.text_pet_name)
    TextView name;

    public PetsListViewHolder(View itemView, PetsPresenter petsPresenter) {
        super(itemView);
        this.petsPresenter = petsPresenter;
        ButterKnife.bind(this, itemView);
    }

    public void render(PetViewModel pet) {
        onItemClick(pet);
        ImageUtils.setImage(pet.getImageUrl(), image);
        name.setText(pet.getName());
    }

    private void onItemClick(final PetViewModel pet) {
        itemView.setOnClickListener(v -> petsPresenter.onPetClicked(pet));
    }

}
