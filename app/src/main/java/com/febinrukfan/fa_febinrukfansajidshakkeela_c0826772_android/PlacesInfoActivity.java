package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.databinding.ActivityPlacesInfoBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;

public class PlacesInfoActivity extends AppCompatActivity implements OnMapReadyCallback{
    private static int AUTOCOMPLETE_REQUEST_CODE = 100;

    ActivityPlacesInfoBinding binding;
    private GoogleMap googleMap;

    private String pName,pAddrss,pPlaceId,pDate;
    private Double pLat,pLong;
    private Boolean pVisited;

    private final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlacesInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        PlacesRoomDb database = PlacesRoomDb.getInstance(this);

        String placesId = getIntent().getStringExtra("id");
        Log.v(TAG,"here  " + placesId);
        getSupportActionBar().setTitle(database.placesDao().getPlaceName(placesId));

        binding.mvInfo.onCreate(savedInstanceState);

        binding.mvInfo.onResume();

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.mvInfo.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                googleMap.setMyLocationEnabled(true);
                // For zooming automatically to the location of the marker

                if (mMap != null) {

                    mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location arg0) {
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(database.placesDao().getPlaceDetails(placesId).getPlaces_latitude(),database.placesDao().getPlaceDetails(placesId).getPlaces_longitude())).title(database.placesDao().getPlaceName(placesId)));
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(database.placesDao().getPlaceDetails(placesId).getPlaces_latitude(),database.placesDao().getPlaceDetails(placesId).getPlaces_longitude())).zoom(12).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        }
                    });
                }
            }
        });

        binding.txtDate.setText(database.placesDao().getPlaceDetails(placesId).getAdded_date());
        binding.txtAddress.setText(database.placesDao().getPlaceDetails(placesId).getPlaces_address());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                googleMap.setMyLocationEnabled(true);
                // For zooming automatically to the location of the marker

                if (googleMap != null) {
                    googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                        @Override
                        public void onMyLocationChange(Location arg0) {
                            googleMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(place.getLatLng()).zoom(12).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                            pName = place.getName();
                            pAddrss = place.getAddress();
                            pPlaceId = place.getId();
                            pLat = place.getLatLng().latitude;
                            pLong = place.getLatLng().longitude;


                        }
                    });
                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.mvInfo.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mvInfo.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mvInfo.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.mvInfo.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mvInfo.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        binding.mvInfo.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        binding.mvInfo.onLowMemory();

    }

}