package com.runnimal.app.android.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.runnimal.app.android.R;
import com.runnimal.app.android.domain.PointType;
import com.runnimal.app.android.view.domain.InfoWindowData;
import com.runnimal.app.android.view.presenter.PointsPresenter;
import com.runnimal.app.android.view.util.ImageUtils;
import com.runnimal.app.android.view.viewmodel.PointViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View modalPointsInfoLayout;
    private final List<PointViewModel> pointsList;
    private final List<PointViewModel> filteredList;

    @BindView(R.id.text_point_title)
    TextView titleTextView;
    @BindView(R.id.image_point)
    CircleImageView imageView;
    @BindView(R.id.text_point_description)
    TextView descriptionTextView;

    private PointsPresenter pointsPresenter;
    private Marker lastMarkerClicked = null;

    public CustomInfoWindowAdapter(Context context, PointsPresenter pointsPresenter) {
        this.modalPointsInfoLayout = LayoutInflater.from(context).inflate(R.layout.modal_points_info, null);
        this.pointsList = new ArrayList<>();
        this.filteredList = new ArrayList<>();
        this.pointsPresenter = pointsPresenter;

        ButterKnife.bind(this, modalPointsInfoLayout);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        InfoWindowData data = (InfoWindowData) marker.getTag();
        titleTextView.setText(data.getTitle());
        if (data.getPhoto() != null) {
            ImageUtils.setImage(data.getPhoto(), imageView);
        }
        descriptionTextView.setText(data.getDescription());

        return modalPointsInfoLayout;
    }

    public void onMarkerClicked(GoogleMap map, Marker marker) {
        if (lastMarkerClicked != null && lastMarkerClicked.equals(marker)) {
            marker.hideInfoWindow();
            lastMarkerClicked = null;
        } else {
            showInfoWindow(map, marker);
            lastMarkerClicked = marker;
        }
    }

    public void addAll(Collection<PointViewModel> collection) {
        pointsList.addAll(collection);
        filteredList.addAll(collection);
    }

    public void filter(PointType pointType) {
        filteredList.clear();

        if (pointType == null) {
            filteredList.addAll(pointsList);
        } else {
            pointsList.stream() //
                    .filter(p -> p.getType() == pointType) //
                    .collect(Collectors.toCollection(() -> filteredList));
        }

        pointsPresenter.onPointsChanged(filteredList);
    }

    private void showInfoWindow(GoogleMap map, Marker marker) {
        marker.showInfoWindow();

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        map.moveCamera(center);
        map.animateCamera(zoom);
    }
}