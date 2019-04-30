package com.runnimal.app.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    protected LocationManager locationManager;
    protected Double latitude, longitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            Location loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(loc.getLatitude(), loc.getLongitude()));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            map.moveCamera(center);
            map.animateCamera(zoom);
        } catch (SecurityException e) {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
        }

        initMarkers(map);
    }

    private void initMarkers(GoogleMap map) {
        Bitmap walkImage = BitmapFactory.decodeResource(getResources(), R.drawable.walk);
        map.addMarker(new MarkerOptions() //
                .position(new LatLng(41.3891234,2.1212519)) //
                .title("Jardins de la Font dels Ocellet") //
                .icon(BitmapDescriptorFactory.fromBitmap(walkImage)));
        map.addMarker(new MarkerOptions() //
                .position(new LatLng(41.3875943,2.1166048)) //
                .title("Jardines del Palacio de Pedralbes") //
                .icon(BitmapDescriptorFactory.fromBitmap(walkImage)));
        map.addMarker(new MarkerOptions() //
                .position(new LatLng(41.3901778,2.1155211)) //
                .title("Jardines de William Shakespeare") //
                .icon(BitmapDescriptorFactory.fromBitmap(walkImage)));
        map.addMarker(new MarkerOptions() //
                .position(new LatLng(41.3925127,2.1205195)) //
                .title("Jardines de Villa Amelia") //
                .icon(BitmapDescriptorFactory.fromBitmap(walkImage)));
        map.addMarker(new MarkerOptions() //
                .position(new LatLng(41.3843101,2.103513)) //
                .title("Parc de Cervantes") //
                .icon(BitmapDescriptorFactory.fromBitmap(walkImage)));
    }
}