package com.runnimal.app.android.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.SingletonSession;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.view.presenter.SignUpPresenter;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements SignUpPresenter.View {

    private static final int CAMERA_REQUEST = 1888;

    @Inject
    SignUpPresenter presenter;

    @BindView(R.id.text_sign_up_back_button)
    TextView textBackButton;
    @BindView(R.id.image_sign_up)
    ImageView image;
    @BindView(R.id.text_sign_up_alias)
    TextView alias;
    @BindView(R.id.text_sign_up_email)
    TextView email;
    @BindView(R.id.text_sign_up_password)
    TextView password;
    @BindView(R.id.button_sign_up_ok)
    Button createButton;
    @BindView(R.id.button_sign_up_camera)
    Button cameraButton;

    public static void open(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == CAMERA_REQUEST) {
                Bitmap bitmapPhoto = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(bitmapPhoto);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDagger();
        setContentView(R.layout.activity_sign_up);
        bindViews();
        initializeBackButton();
        initializeCreateButton();
        initializeCameraButton();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void successfullyCreated(OwnerViewModel owner) {
        SingletonSession.Instance().setMail(owner.getEmail());
        SingletonSession.Instance().setUsername(owner.getAlias());
        SingletonSession.Instance().setId(owner.getId());
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void bindViews() {
        ButterKnife.bind(this);
    }

    private void initializeBackButton() {
        textBackButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void initializeCreateButton() {
        createButton.setOnClickListener(view -> {
            if (alias.getText().toString().equals("") || email.getText().toString().equals("") || password.getText().toString().equals("")) {
                new AlertDialog.Builder(this)
                        .setTitle("Missing parameters")
                        .setMessage("You have to fill first all the text camps")

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } else {
                Owner owner = new Owner() //
                        .setAlias(alias.getText().toString()) //
                        .setEmail(email.getText().toString()) //
                        .setPassword(password.getText().toString());
                presenter.createOwner(owner);

                Bitmap bitmapImage = null;
                if (image != null) {
                    bitmapImage = ((BitmapDrawable) image.getDrawable()).getBitmap();
                }
                presenter.uploadImage(bitmapImage, "/photo/users/" + owner.getEmail());
            }
        });
    }

    private void initializeCameraButton() {
        cameraButton.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_REQUEST);
        });
    }
}
