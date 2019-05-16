package com.runnimal.app.android.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.service.fileUploader;
import com.runnimal.app.android.view.presenter.PetModifyPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import java.util.stream.IntStream;

import javax.inject.Inject;

import butterknife.BindView;

public class PetModifyActivity extends BaseActivity implements PetModifyPresenter.View {

    private static final int CAMERA_REQUEST = 1888;
    private Bitmap bitmapPhoto;


    private final static String PET_ID_KEY = "pet_id_key";
    private final static String PET_OWNER_EMAIL = "pet_owner_email";

    @Inject
    PetModifyPresenter presenter;

    @BindView(R.id.detail_pet_modify_container)
    ScrollView container;
    @BindView(R.id.image_modify_pet)
    ImageView image;
    @BindView(R.id.text_modify_pet_description)
    TextView description;
    @BindView(R.id.text_modify_pet_breed)
    TextView breed;
    @BindView(R.id.text_modify_pet_weight)
    TextView weight;
    @BindView(R.id.text_modify_pet_birth_year)
    TextView birthYear;
    @BindView(R.id.spinner_modify_pet_size)
    Spinner size;
    @BindView(R.id.button_modify_pet_save)
    Button saveButton;
    @BindView(R.id.button_modify_pet_delete)
    Button deleteButton;
    @BindView(R.id.button_modify_pet_image_edit)
    Button imageEditButton;

    @BindView(R.id.pet_detail_modify_progress_bar)
    ProgressBar progressBar;

    public static void open(Context context, String petId, String ownerEmail) {
        Intent intent = new Intent(context, PetModifyActivity.class);
        intent.putExtra(PET_ID_KEY, petId);
        intent.putExtra(PET_OWNER_EMAIL, ownerEmail);
        context.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == CAMERA_REQUEST) {
                bitmapPhoto = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(bitmapPhoto);
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pet_modify;
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
        description.setText(pet.getDescription());
        breed.setText(pet.getBreed());

        weight.setText(String.valueOf(pet.getWeight()));
        birthYear.setText(String.valueOf(pet.getBirth()));

        //Set Size
        String[] sizesArray = getResources().getStringArray(R.array.size_array);
        int sizePosition = IntStream.range(0, sizesArray.length - 1) //
                .filter(i -> pet.getSize().name().toLowerCase().equals(sizesArray[i].toLowerCase())) //
                .findFirst() //
                .orElse(0);
        size.setSelection(sizePosition);

        initializeSaveButton(pet);
        initializeDeleteButton(pet);
        initializeEditImageButton();
    }

    @Override
    public void onDelete() {
        finish();
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

    private void initializeSaveButton(PetViewModel petOriginal) {
        Log.d("refactor", "initializeSaveButton: ");
        saveButton.setOnClickListener(view -> {
            if (description.getText().toString().equals("") || breed.getText().toString().equals("") || size.getSelectedItem().toString().equals("") || birthYear.getText().toString().equals("") || weight.getText().toString().equals("")) {
                new AlertDialog.Builder(this) //
                        .setTitle("Missing parameters") //
                        .setMessage("You have to fill first all the text camps") //
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, null) //
                        .setIcon(android.R.drawable.ic_dialog_alert) //
                        .show();
            } else {
                String petId = getIntent().getExtras().getString(PET_ID_KEY);
                Pet pet = new Pet() //
                        .setName(petId)
                        .setDescription(description.getText().toString()) //
                        .setBreed(breed.getText().toString()) //
                        .setWeight(Integer.valueOf(weight.getText().toString())) //
                        .setSize(Pet.PetSize.valueOf(size.getSelectedItem().toString())) //
                        .setBirth(Integer.valueOf(birthYear.getText().toString()));
                presenter.modifyPet(pet);

                //toDo imagen
                fileUploader fileUploader = new fileUploader(this, "/pets/" + petOriginal.getOwner().getEmail() + "/" + pet.getName());
                if (bitmapPhoto!=null) fileUploader.uploadImage(bitmapPhoto);
            }
        });
    }

    private void initializeDeleteButton(PetViewModel petOriginal) {
        deleteButton.setOnClickListener(view -> {
            presenter.deletePet(petOriginal.getOwner().getEmail(), petOriginal.getName());
        });
    }

    private void initializeEditImageButton() {
        imageEditButton.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        });
    }

}
