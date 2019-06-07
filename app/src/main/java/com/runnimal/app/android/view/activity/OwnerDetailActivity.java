package com.runnimal.app.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.domain.FriendRequestState;
import com.runnimal.app.android.domain.LatLon;
import com.runnimal.app.android.util.SingletonSession;
import com.runnimal.app.android.view.adapter.PetsListAdapter;
import com.runnimal.app.android.view.adapter.WalksListAdapter;
import com.runnimal.app.android.view.presenter.OwnerDetailPresenter;
import com.runnimal.app.android.view.presenter.PetsPresenter;
import com.runnimal.app.android.view.presenter.WalkPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.OwnerViewModel;
import com.runnimal.app.android.view.viewmodel.PetViewModel;
import com.runnimal.app.android.view.viewmodel.WalkViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class OwnerDetailActivity extends BaseActivity implements OwnerDetailPresenter.View, PetsPresenter.View, WalkPresenter.View {

    private final static String OWNER_ID_KEY = "owner_id_key";
    private final static String OWNER_EMAIL_KEY = "owner_email_key";

    @Inject
    OwnerDetailPresenter ownerProfilePresenter;
    @Inject
    PetsPresenter petsPresenter;
    PetsListAdapter petsListAdapter;
    @Inject
    WalkPresenter walkPresenter;
    WalksListAdapter walksListAdapter;

    @BindView(R.id.container_owner_detail)
    ScrollView container;
    @BindView(R.id.image_owner_detail)
    ImageView image;
    @BindView(R.id.text_owner_detail_email)
    TextView email;
    @BindView(R.id.text_owner_detail_alias)
    TextView alias;
    @BindView(R.id.list_owner_detail_pets)
    RecyclerView petList;
    @BindView(R.id.list_owner_detail_walks)
    RecyclerView walksList;
    @BindView(R.id.image_owner_detail_edit_or_friend)
    ImageView editOrFriendImage;

    @BindView(R.id.walks_layout)
    RelativeLayout walksLayout;

    @BindView(R.id.owner_detail_progress_bar)
    ProgressBar progressBar;

    public static void open(Context context, String ownerId, String ownerEmail) {
        Intent intent = new Intent(context, OwnerDetailActivity.class);
        intent.putExtra(OWNER_ID_KEY, ownerId);
        intent.putExtra(OWNER_EMAIL_KEY, ownerEmail);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_owner_detail;
    }

    @Override
    protected int getBottomMenuItemId() {
        return -1;
    }

    @Override
    protected void initView() {
        initializeDagger();
        initializeOwnerProfilePresenter();
        initializePetsPresenter();
        initializeWalkPresenter();
        initializePetsListAdapter();
        initializeWalksListAdapter();
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
            walkPresenter.initialize();
        } else {
            walksLayout.setVisibility(View.GONE);
            ownerProfilePresenter.checkFriend();
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

            case FRIEND:
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
        PetDetailActivity.open(this, pet.getName(), pet.getOwner().getEmail());
    }

    @Override
    public void showWalksList(List<WalkViewModel> walks) {
        walksListAdapter.addAll(walks);
        walksListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNewWalk(WalkViewModel walk) {
    }

    @Override
    public void invalidNewWalk() {
    }

    @Override
    public void drawCurrentRoute(List<LatLon> route) {
    }

    @Override
    public void openWalkScreen(WalkViewModel walk) {
        MapActivity.open(this, walk);
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

    private void initializeWalkPresenter() {
        walkPresenter.setView(this);
    }

    private void initializePetsListAdapter() {
        petsListAdapter = new PetsListAdapter(petsPresenter);
    }

    private void initializeWalksListAdapter() {
        walksListAdapter = new WalksListAdapter(walkPresenter);
    }

    private void initializeRecyclerView() {
        petList.setLayoutManager(new LinearLayoutManager(this));
        petList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        petList.setHasFixedSize(true);
        petList.setAdapter(petsListAdapter);

        walksList.setLayoutManager(new LinearLayoutManager(this));
        walksList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        walksList.setHasFixedSize(true);
        walksList.setAdapter(walksListAdapter);
    }

    private void initializeEditImageButton(OwnerViewModel owner) {
        editOrFriendImage.setOnClickListener(view -> {
            OwnerModifyActivity.open(this);
        });
    }

    private void initializeDeleteFriendImageButton() {
        editOrFriendImage.setOnClickListener(view -> {
            ownerProfilePresenter.deleteFriend();
        });
    }
}
