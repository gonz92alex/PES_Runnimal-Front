package com.runnimal.app.android.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.domain.Pet;
import com.runnimal.app.android.service.fileUploader;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.presenter.PetAddPresenter;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class PetAddActivity extends BaseActivity implements PetAddPresenter.View {

    private final static String PET_ID_KEY = "pet_id_key";
    private static final int CAMERA_REQUEST = 1888;
    private Bitmap bitmapPhoto;

    @Inject
    PetAddPresenter presenter;

    @BindView(R.id.detail_pet_add_container)
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
    @BindView(R.id.pet_detail_add_progress_bar)
    ProgressBar progressBar;


    public static void open(Context context) {
        Intent intent = new Intent(context, PetAddActivity.class);
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
        return R.layout.activity_pet_add;
    }

    @Override
    protected int getBottomMenuItemId() {
        return R.id.menu_bottom_pets;
    }

    @Override
    protected void initView() {
        hideLoading();
        initializeDagger();
        initializePresenter();
        initializeAddButton();
        requestMultiplePermissions();
        initializeCameraButton();
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
    public void successfullyCreated(PetViewModel pet) {
        finish();
        startActivity(new Intent(this, PetsActivity.class));
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
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
                        .setView(R.layout.alert_dialog)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            } else {
                Pet pet = new Pet() //
                        .setName(name.getText().toString())
                        .setDescription(description.getText().toString()) //
                        .setBreed(breed.getText().toString()) //
                        .setWeight(Integer.valueOf(weight.getText().toString())) //
                        .setSize(Pet.PetSize.valueOf(size.getSelectedItem().toString())) //
                        .setBirth(Integer.valueOf(birthYear.getText().toString())) //
                        .setOwner(new Owner().setEmail(SingletonSession.Instance().getMail()));
                presenter.addPet(pet);

                fileUploader fileUploader = new fileUploader(this, "/pets/" + pet.getOwner().getEmail() + "/" + pet.getName());
                if (bitmapPhoto!=null) fileUploader.uploadImage(bitmapPhoto);
            }
        });
    }

    //toDO creo que esto no es necesario (not sure)
    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        //TODO
                    }
                })
                .onSameThread()
                .check();
    }

    private void initializeCameraButton() {
        cameraButton.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        });
    }
}
