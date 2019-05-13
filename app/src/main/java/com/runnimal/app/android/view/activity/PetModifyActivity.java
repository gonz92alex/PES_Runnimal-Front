package com.runnimal.app.android.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.runnimal.app.android.view.presenter.PetModifyPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import java.util.stream.IntStream;

import javax.inject.Inject;

import butterknife.BindView;

public class PetModifyActivity extends BaseActivity implements PetModifyPresenter.View {

    private final static String PET_ID_KEY = "pet_id_key";

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

    public static void open(Context context, String petId) {
        Intent intent = new Intent(context, PetModifyActivity.class);
        intent.putExtra(PET_ID_KEY, petId);
        context.startActivity(intent);
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
        Log.d("refactor", "showLoad");
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        Log.d("refactor", "hideLoad");
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

        initializeSaveButton();
        initializeDeleteButton();
        initializeEditImageButton();
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
        Log.d("refactor", "DONE DAGGER");

    }

    private void initializePresenter() {
        presenter.setView(this);
        String petId = getIntent().getExtras().getString(PET_ID_KEY);
        presenter.setPetId(petId);
        Log.d("refactor", "DONE PRESENTER");

    }

    private void initializeSaveButton() {
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
                        .setId(petId)
                        .setDescription(description.getText().toString()) //
                        .setBreed(breed.getText().toString()) //
                        .setWeight(Integer.valueOf(weight.getText().toString())) //
                        .setSize(Pet.PetSize.valueOf(size.getSelectedItem().toString())) //
                        .setBirth(Integer.valueOf(birthYear.getText().toString()));
                presenter.modifyPet(pet);
            }
        });
    }

    private void initializeDeleteButton() {
        deleteButton.setOnClickListener(view -> {
            //TODO: implementar
        });
    }

    private void initializeEditImageButton() {
        imageEditButton.setOnClickListener(view -> {
            //TODO: implementar
        });
    }

}
