package com.runnimal.app.android.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.service.fileUploader;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.view.presenter.OwnerModifyPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;

import java.net.URI;

import javax.inject.Inject;

import butterknife.BindView;

public class OwnerModifyActivity extends BaseActivity implements OwnerModifyPresenter.View {

    private static final int CAMERA_REQUEST = 1888;
    private Bitmap bitmapPhoto;


    @Inject
    OwnerModifyPresenter presenter;

    @BindView(R.id.container_owner_modify)
    ScrollView container;
    @BindView(R.id.image_owner_modify)
    ImageView image;
    @BindView(R.id.text_owner_modify_alias)
    TextView alias;
    @BindView(R.id.button_owner_modify_save)
    Button saveButton;
    @BindView(R.id.button_owner_modify_camera)
    Button cameraButton;

    @BindView(R.id.owner_modify_progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.navigation_menu)
    NavigationView navigationView;

    public static void open(Context context) {
        Intent intent = new Intent(context, OwnerModifyActivity.class);
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
        return R.layout.activity_owner_modify;
    }

    @Override
    protected int getBottomMenuItemId() {
        return -1;
    }

    @Override
    protected void initView() {
        initializeDagger();
        initializePresenter();
        initializeOwner();
        initializeSaveButton();
        initializeCameraButton();
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
    public void onUpdatedOwner(OwnerViewModel owner) {
        SingletonSession.Instance().setUsername(owner.getAlias());
        refreshMenuInfo();
        finish();
    }

    @Override
    public void onUploadPhoto(){
        finish();
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
    }

    private void initializeOwner() {
        //ToDO: la id aqui(?)
        ImageUtils.setImage(SingletonSession.Instance().getPhoto(), image);
        alias.setText(SingletonSession.Instance().getUsername());
    }

    private void initializeSaveButton() {
        saveButton.setOnClickListener(view -> {
            if (alias.getText().toString().equals("")) {
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
                        .setAlias(alias.getText().toString());
                presenter.modifyOwner(owner);

                fileUploader fileUploader = new fileUploader(this, "/users/" + SingletonSession.Instance().getMail());
                if (bitmapPhoto!=null) fileUploader.uploadImage(bitmapPhoto);
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
