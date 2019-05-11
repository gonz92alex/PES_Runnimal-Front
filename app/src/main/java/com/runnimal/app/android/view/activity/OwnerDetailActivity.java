package com.runnimal.app.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.SingletonSession;
import com.runnimal.app.android.domain.Owner;
import com.runnimal.app.android.view.adapter.PetsListAdapter;
import com.runnimal.app.android.view.adapter.TrainingsListAdapter;
import com.runnimal.app.android.view.presenter.OwnerProfilePresenter;
import com.runnimal.app.android.view.presenter.PetAddPresenter;
import com.runnimal.app.android.view.presenter.PetDetailPresenter;
import com.runnimal.app.android.view.presenter.PetsPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class OwnerDetailActivity extends BaseActivity implements OwnerProfilePresenter.View, PetsPresenter.View {

    private final static String OWNER_ID_KEY = "owner_id_key";
    private final static String OWNER_EMAIL_KEY = "owner_email_key";

    @Inject
    OwnerProfilePresenter ownerProfilePresenter;
    @Inject
    PetsPresenter petsPresenter;
    PetsListAdapter petsListAdapter;

    @BindView(R.id.owner_profile_container)
    ScrollView container;
    @BindView(R.id.image_owner_profile)
    ImageView image;
    @BindView(R.id.text_owner_profile_email)
    TextView email;
    @BindView(R.id.text_owner_profile_alias)
    TextView alias;
    @BindView(R.id.list_owner_pets)
    RecyclerView petList;
    @BindView(R.id.image_owner_profile_edit)
    ImageView editImage;
    @BindView(R.id.owner_profile_progress_bar)
    ProgressBar progressBar;

    public static void open(Context context, String petId) {
        Intent intent = new Intent(context, PetDetailActivity.class);
        intent.putExtra(OWNER_ID_KEY, petId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_owner_profile;
    }

    @Override
    protected int getNavigationMenuItemId() {
        return R.id.navigation_pets;
    }

    @Override
    protected void initView() {
        initializeDagger();
        initializeOwnerProfilePresenter();
        initializePetsPresenter();
        initializePetsListAdapter();
        initializeRecyclerView();
        ownerProfilePresenter.initialize();
        petsPresenter.initialize();
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
    public void showOwner(OwnerViewModel owner) {
        ImageUtils.setImage(owner.getImageUrl(), image);
        email.setText(owner.getEmail());
        alias.setText(owner.getAlias());

        //Check if owner is the current user
        if (SingletonSession.Instance().getMail().equals(pet.getOwner().getEmail())) {
            editImage.setImageResource(R.drawable.icon_edit);
            initializeEditImageButton(owner);
        }
        else{   //Si estas viendo el perfil de otra persona:
            getRelation();
        }

        //petList
    }

    @Override
    public void showPetsList(List<PetViewModel> petsList) {
        petsListAdapter.addAll(petsList);
        petsListAdapter.notifyDataSetChanged();
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializeOwnerProfilePresenter() {
        ownerProfilePresenter.setView(this);
        String ownerId = getIntent().getExtras().getString(OWNER_ID_KEY);
        ownerProfilePresenter.setOwnerId(ownerId);
    }

    private void initializePetsPresenter() {
        petsPresenter.setView(this);
        String ownerEmail = getIntent().getExtras().getString(OWNER_EMAIL_KEY);
        petsPresenter.setOwnerEmail(ownerEmail);
    }

    private void initializePetsListAdapter() {
        petsListAdapter = new PetsListAdapter(petsPresenter);
    }

    private void initializeRecyclerView() {
        petList.setLayoutManager(new LinearLayoutManager(this));
        petList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        petList.setHasFixedSize(true);
        petList.setAdapter(petsListAdapter);
    }

    private void initializeEditImageButton(OwnerViewModel owner) {
        editImage.setOnClickListener(view -> {
            //TODO
            //OwnerModifyActivity.open(this, owner.getId());
        });
    }
}
