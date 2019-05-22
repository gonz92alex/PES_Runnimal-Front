package com.runnimal.app.android.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.runnimal.app.android.R;
import com.runnimal.app.android.RunnimalApplication;
import com.runnimal.app.android.domain.PointType;
import com.runnimal.app.android.util.PermissionUtils;
import com.runnimal.app.android.view.adapter.CustomInfoWindowAdapter;
import com.runnimal.app.android.view.domain.InfoWindowData;
import com.runnimal.app.android.view.presenter.PointsPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.PointViewModel;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import butterknife.BindView;

public class MapActivity extends BaseActivity implements
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        PointsPresenter.View,
        GoogleMap.OnMarkerClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 6506;

    @Inject
    PointsPresenter pointsPresenter;
    CustomInfoWindowAdapter infoWindowAdapter;

    @BindView(R.id.radio_button_point_type_all)
    RadioButton allButton;
    @BindView(R.id.radio_button_point_type_pipican)
    RadioButton pipicanButton;
    @BindView(R.id.radio_button_point_type_park)
    RadioButton parkButton;
    @BindView(R.id.radio_button_point_type_other)
    RadioButton otherButton;
    @BindView(R.id.button_map_walk)
    Button walkButton;

    private boolean mPermissionDenied = false;
    private GoogleMap map;

    public static void open(Context context) {
        Intent intent = new Intent(context, MapActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        infoWindowAdapter.onMarkerClicked(map, marker);
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected int getBottomMenuItemId() {
        return R.id.menu_bottom_map;
    }

    @Override
    protected void initView() {
        initMap();
        initializeDagger();
        initializePresenter();
        initializeAdapter();
        initFilterButtons();
        initWalkButton();
        pointsPresenter.initialize();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        map.setOnMarkerClickListener(this);
        map.setInfoWindowAdapter(infoWindowAdapter);

        enableMyLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation();
        } else {
            mPermissionDenied = true;
        }
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showPointsList(List<PointViewModel> points) {
        infoWindowAdapter.addAll(points);
        initMarkers(points);
    }

    @Override
    public void onPointsListChanged(List<PointViewModel> points) {
        map.clear();
        initMarkers(points);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    private void initializeDagger() {
        RunnimalApplication app = (RunnimalApplication) getApplication();
        app.getMainComponent().inject(this);
    }

    private void initializePresenter() {
        pointsPresenter.setView(this);
    }

    private void initializeAdapter() {
        infoWindowAdapter = new CustomInfoWindowAdapter(this, pointsPresenter);
    }

    private void initFilterButtons() {
        allButton.setChecked(true);

        allButton.setOnClickListener(view -> {
            infoWindowAdapter.filter(null);
        });
        pipicanButton.setOnClickListener(view -> {
            infoWindowAdapter.filter(PointType.PIPICAN);
        });
        parkButton.setOnClickListener(view -> {
            infoWindowAdapter.filter(PointType.PARK);
        });
        otherButton.setOnClickListener(view -> {
            infoWindowAdapter.filter(PointType.OTHER);
        });
    }

    private void initWalkButton() {
        Drawable background = walkButton.getBackground();
        ColorStateList color = walkButton.getTextColors();
        CharSequence text = walkButton.getText();
        AtomicBoolean buttonPressed = new AtomicBoolean(false);

        walkButton.setOnClickListener(view -> {
            if (! buttonPressed.get()) {
                walkButton.setBackgroundResource(R.drawable.btn_light_rounded);
                walkButton.setTextColor(Color.parseColor("#000000"));
                walkButton.setText(R.string.map_walk_end);

                buttonPressed.set(true);
            }
            else {
                walkButton.setBackground(background);
                walkButton.setTextColor(color);
                walkButton.setText(text);

                buttonPressed.set(false);
            }
        });
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (map != null) {
            initMyLocationButtonPosition();
            map.setMyLocationEnabled(true);

            centerMap();
        }
    }

    private void initMyLocationButtonPosition() {
        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        // position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 180, 180, 0);
    }

    private void centerMap() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude()));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            map.moveCamera(center);
            map.animateCamera(zoom);
        } catch (SecurityException e) {
        }
    }

    private void initMarkers(List<PointViewModel> points) {
        Bitmap originalPipicanImg = BitmapFactory.decodeResource(getResources(), R.drawable.pipican);
        Bitmap resizedPipicanImg = ImageUtils.resizeImage(originalPipicanImg, 128, 128);
        BitmapDescriptor pipicanImgDescriptor = BitmapDescriptorFactory.fromBitmap(resizedPipicanImg);

        BitmapDescriptor parkImgDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.park));

        points.forEach(point -> {
            MarkerOptions markerOptions = new MarkerOptions() //
                    .position(new LatLng(point.getLat(), point.getLon())) //
                    .title(point.getTitle());

            switch (point.getType()) {
                case PIPICAN:
                    markerOptions.icon(pipicanImgDescriptor);
                    break;
                case PARK:
                    markerOptions.icon(parkImgDescriptor);
                    break;
                case OTHER:
                    break;
            }

            Marker marker = map.addMarker(markerOptions);

            marker.hideInfoWindow();

            InfoWindowData info = new InfoWindowData() //
                    .setTitle(point.getTitle()) //
                    .setDescription(point.getDescription()) //
                    .setPhoto(point.getPhotoUrl());
            marker.setTag(info);
        });
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

}