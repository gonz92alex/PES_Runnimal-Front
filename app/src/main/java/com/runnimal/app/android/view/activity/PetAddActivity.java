package com.runnimal.app.android.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.view.presenter.PetAddPresenter;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import javax.inject.Inject;

import butterknife.BindView;

public class PetAddActivity extends BaseActivity implements PetAddPresenter.View {

    private final static String PET_ID_KEY = "pet_id_key";

    @Inject
    PetAddPresenter presenter;

    @BindView(R.id.detail_pet_modify_container)
    ScrollView container;
    @BindView(R.id.image_add_pet)
    ImageView image;
    @BindView(R.id.text_add_pet_name)
    TextView name;
    @BindView(R.id.text_add_pet_description)
    TextView description;
    @BindView(R.id.text_add_pet_breed)
    TextView breed;
    @BindView(R.id.text_add_pet_weight)
    TextView weight;
    @BindView(R.id.text_add_pet_birth)
    TextView birthYear;
    @BindView(R.id.spinner_add_pet_size)
    Spinner size;
    @BindView(R.id.button_add_pet)
    Button addButton;
    @BindView(R.id.button_add_pet_camera_edit)
    Button cameraButton;

    public static void open(Context context) {
        Intent intent = new Intent(context, PetModifyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pet_add;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_pets;
    }

    @Override
    protected void initView() {
        initializeDagger();
        initializeAddButton();
        initializeCameraButton();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void successfullyCreated(PetViewModel pet) {
        finish();
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializeCameraButton() {
        cameraButton.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //startActivityForResult(intent, CAMERA_REQUEST);
        });
    }

    private void initializeAddButton() {
        addButton.setOnClickListener(view -> {

            if (name.getText().toString().equals("") //
                    || description.getText().toString().equals("") //
                    || breed.getText().toString().equals("") //
                    || size.getSelectedItem().toString().equals("") //
                    || birthYear.getText().toString().equals("") //
                    || weight.getText().toString().equals("")) {
                new AlertDialog.Builder(this)
                        .setTitle("Missing parameters")
                        .setMessage("You have to fill first all the text camps")
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                Pet pet = new Pet() //
                        .setDescription(description.getText().toString()) //
                        .setBreed(breed.getText().toString()) //
                        .setWeight(Integer.valueOf(weight.getText().toString())) //
                        .setSize(Pet.PetSize.valueOf(size.getSelectedItem().toString())) //
                        .setBirth(Integer.valueOf(birthYear.getText().toString()));
                //presenter.modifyPet(pet);
            }
        });
    }
}
