package com.runnimal.app.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.presenter.PetDetailPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import java.time.LocalDate;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Single;

public class PetDetailActivity extends BaseActivity implements PetDetailPresenter.View {

    private final static String PET_ID_KEY = "pet_id_key"; //lo que le llega es el nombre de la pet
    private final static String PET_OWNER_EMAIL = "pet_owner_email";

    @Inject
    PetDetailPresenter presenter;

    @BindView(R.id.detail_pet_container)
    ScrollView container;
    @BindView(R.id.image_detail_pet)
    ImageView image;
    @BindView(R.id.text_detail_pet_name)
    TextView name;
    @BindView(R.id.text_detail_pet_description)
    TextView description;
    @BindView(R.id.image_detail_pet_edit)
    ImageView editImage;
    @BindView(R.id.addOwner)
    ImageView addOwnerButton;
    @BindView(R.id.image_detail_pet_owner)
    ImageView ownerImage;
    @BindView(R.id.text_detail_pet_breed)
    TextView breed;
    @BindView(R.id.text_detail_pet_weight)
    TextView weight;
    @BindView(R.id.text_detail_pet_age)
    TextView age;
    @BindView(R.id.pet_detail_progress_bar)
    ProgressBar progressBar;

    public static void open(Context context, String petId, String petOwner) {
        Intent intent = new Intent(context, PetDetailActivity.class);
        intent.putExtra(PET_ID_KEY, petId);
        intent.putExtra(PET_OWNER_EMAIL, petOwner);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pet_detail;
    }

    @Override
    protected int getBottomMenuItemId() {
        return R.id.menu_bottom_pets;
    }

    @Override
    protected void initView() {
        initializeDagger();
        initializePresenter();
        presenter.initialize();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPet(PetViewModel pet) {
        ImageUtils.setImage(pet.getImageUrl(), image);
        name.setText(pet.getName());
        description.setText(pet.getDescription());
        breed.setText(pet.getBreed());
        weight.setText(pet.getWeight() + " kg");
        age.setText(String.valueOf(LocalDate.now().getYear() - pet.getBirth()));

        ImageUtils.setImage(pet.getOwner().getImageUrl(), ownerImage);
        initializeOwnerImageButton(pet);

        //Check if owner is the current user
        if (SingletonSession.Instance().getMail().equals(pet.getOwner().getEmail())) {
            editImage.setImageResource(R.drawable.icon_edit);
            initializeEditImageButton(pet, 0);

            addOwnerButton.setImageResource(R.drawable.ic_add_user);
            initializeAddOwnerButton(pet);
        }
        else{
            if (pet.checkOwner(SingletonSession.Instance().getMail())){
                editImage.setImageResource(R.drawable.ic_remove);
                initializeEditImageButton(pet, 1);
            }
        }
    }

    @Override
    public void onDeleteOwner() {
        finish();
        startActivity(new Intent(this, PetsActivity.class));
    }


    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
        String petId = getIntent().getExtras().getString(PET_ID_KEY);
        String ownerEmail = getIntent().getExtras().getString(PET_OWNER_EMAIL);
        presenter.setPetId(petId);
        presenter.setOwnerEmail(ownerEmail);
    }

    private void initializeOwnerImageButton(PetViewModel pet) {
        ownerImage.setOnClickListener(view -> {
            OwnerDetailActivity.open(this, pet.getOwner().getId(), pet.getOwner().getEmail());
        });
    }

    private void initializeEditImageButton(PetViewModel pet, int state) {
        if (state==0){
            editImage.setOnClickListener(view -> {
                PetModifyActivity.open(this, pet.getName(), pet.getOwner().getEmail());
            });
        }
        else{
            Log.d("refactor", "initializeEditImageButton: ");
            editImage.setOnClickListener(view -> {
                presenter.deleteOwner(pet.getId(), SingletonSession.Instance().getMail());
            });
        }
    }

    private void initializeAddOwnerButton(PetViewModel pet) {
        addOwnerButton.setOnClickListener(view -> {
            PetAddOwnerActivity.open(this,pet.getId());
        });
    }

}
