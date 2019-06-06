package com.runnimal.app.android.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.view.presenter.PetAddOwnerPresenter;

import javax.inject.Inject;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class PetAddOwnerActivity extends BaseActivity implements PetAddOwnerPresenter.View {

    private final static String PET_ID_KEY = "pet_id_key";

    @Inject
    PetAddOwnerPresenter presenter;

    @BindView(R.id.container_pet_add_owner)
    ScrollView container;
    @BindView(R.id.pet_add_owner_email)
    EditText email;
    @BindView(R.id.add_owner_button)
    Button addOwnerButton;
    @BindView(R.id.pet_add_owner_progress_bar)
    ProgressBar progressBar;

    public static void open(Context context, String petid) {
        Intent intent = new Intent(context, PetAddOwnerActivity.class);
        intent.putExtra(PET_ID_KEY, petid);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pet_addowner;
    }

    @Override
    protected int getBottomMenuItemId() {
        return -1;
    }

    @Override
    protected void initView() {
        initializeDagger();
        initializePresenter();
        initializeAddButton();
        presenter.initialize();
    }

    private void initializeAddButton() {
        addOwnerButton.setOnClickListener(view -> {
            if (email.getText().toString().equals(""))
                new AlertDialog.Builder(this)
                        .setView(R.layout.alert_dialog)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            else{
                presenter.addOwner(email.getText().toString());
            }
        });
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

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        presenter.setView(this);
        String petId = getIntent().getExtras().getString(PET_ID_KEY);
        presenter.setPetId(petId);
    }

    @Override
    public void onOwnerAdded() {
        Toasty.success(this, email.getText().toString() + " " + getText(R.string.isNowOwner), Toast.LENGTH_SHORT, true).show();
        email.setText("");
    }

    @Override
    public void onError() {
        Toasty.warning(this, R.string.standardError_Msg, Toast.LENGTH_SHORT, true).show();
    }
}
