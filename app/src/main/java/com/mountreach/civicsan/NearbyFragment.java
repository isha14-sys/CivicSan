package com.mountreach.civicsan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.*;
import android.widget.PopupMenu;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NearbyFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nearby, container, false);

        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(requireActivity());

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        FloatingActionButton btnMapType = view.findViewById(R.id.btnMapType);
        btnMapType.setOnClickListener(this::showMapTypeMenu);

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Enable Google default UI
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        if (hasLocationPermission()) {
            enableLocationAndMoveCamera();
        } else {
            requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private boolean hasLocationPermission() {
        return ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private final ActivityResultLauncher<String> requestPermission =
            registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    granted -> {
                        if (granted) {
                            enableLocationAndMoveCamera();
                        }
                    }
            );

    @SuppressLint("MissingPermission")
    private void enableLocationAndMoveCamera() {

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
        ).addOnSuccessListener(location -> {
            if (location != null) {
                moveCameraToLocation(location);
            }
        });
    }

    private void moveCameraToLocation(Location location) {
        LatLng latLng = new LatLng(
                location.getLatitude(),
                location.getLongitude()
        );

        mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(latLng, 16f)
        );
    }

    private void showMapTypeMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);

        popupMenu.getMenu().add("Normal");
        popupMenu.getMenu().add("Satellite");
        popupMenu.getMenu().add("Terrain");
        popupMenu.getMenu().add("Hybrid");

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getTitle().toString()) {
                case "Normal":
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case "Satellite":
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case "Terrain":
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    break;
                case "Hybrid":
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
            }
            return true;
        });

        popupMenu.show();
    }
}