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
import com.runnimal.app.android.domain.FriendRequestState;
import com.runnimal.app.android.view.adapter.PetsListAdapter;
import com.runnimal.app.android.view.presenter.OwnerDetailPresenter;
import com.runnimal.app.android.view.presenter.PetsPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.PetViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class OwnerDetailActivity extends BaseActivity implements OwnerDetailPresenter.View, PetsPresenter.View {

    private final static String OWNER_ID_KEY = "owner_id_key";
    private final static String OWNER_EMAIL_KEY = "owner_email_key";

    @Inject
    OwnerDetailPresenter ownerProfilePresenter;
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
    @BindView(R.id.image_owner_profile_edit_or_friend)
    ImageView editOrFriendImage;
    @BindView(R.id.owner_profile_progress_bar)
    ProgressBar progressBar;

    public static void open(Context context, String ownerId, String ownerEmail) {
        Intent intent = new Intent(context, PetDetailActivity.class);
        intent.putExtra(OWNER_ID_KEY, ownerId);
        intent.putExtra(OWNER_EMAIL_KEY, ownerEmail);
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
        if (SingletonSession.Instance().getMail().equals(owner.getEmail())) {
            editOrFriendImage.setImageResource(R.drawable.icon_edit);
            initializeEditImageButton(owner);
        } else {
            ownerProfilePresenter.checkFriendRequestState();
        }
    }

    @Override
    public void showFriendRequestState(FriendRequestState friendRequestType) {
        switch (friendRequestType) {
            case KO:
                editOrFriendImage.setImageResource(R.drawable.ic_add_user);
                editOrFriendImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ownerProfilePresenter.createFriendRequest();
                        editOrFriendImage.setImageResource(R.drawable.ic_clock);
                    }
                });
                break;

            case PENDING:
                editOrFriendImage.setImageResource(R.drawable.ic_clock);
                break;

            case OK:
                editOrFriendImage.setImageResource(R.drawable.ic_remove);
                initializeDeleteFriendImageButton();
                break;
        }
    }

    @Override
    public void showPetsList(List<PetViewModel> petsList) {
        petsListAdapter.addAll(petsList);
        petsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void openPetScreen(PetViewModel pet) {
        PetDetailActivity.open(this, pet.getName());
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializeOwnerProfilePresenter() {
        ownerProfilePresenter.setView(this);
        String ownerId = getIntent().getExtras().getString(OWNER_ID_KEY);
        ownerProfilePresenter.setOwnerId(ownerId);
        String ownerEmail = getIntent().getExtras().getString(OWNER_EMAIL_KEY);
        ownerProfilePresenter.setOwnerEmail(ownerEmail);
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
        editOrFriendImage.setOnClickListener(view -> {
            //TODO
            //OwnerModifyActivity.open(this, owner.getId());
        });
    }

    private void initializeDeleteFriendImageButton() {
        editOrFriendImage.setOnClickListener(view -> {
            ownerProfilePresenter.deleteFriend();
        });
    }
}
