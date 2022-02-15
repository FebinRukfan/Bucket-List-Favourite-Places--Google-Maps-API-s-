package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.databinding.ActivityMainBinding;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    private final String TAG = this.getClass().getName();
    private static int AUTOCOMPLETE_REQUEST_CODE = 100;
    ActivityMainBinding binding;

    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Places.initialize(getApplicationContext(), getString(R.string.api_key));

//        ActivityResultLauncher<String[]> locationPermissionRequest =
//                registerForActivityResult(new ActivityResultContracts
//                                .RequestMultiplePermissions(), result -> {
//                            Boolean fineLocationGranted = result.getOrDefault(
//                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
//                            Boolean coarseLocationGranted = result.getOrDefault(
//                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
//                            if (fineLocationGranted != null && fineLocationGranted) {
//
//                                binding.mapView.getMapAsync(this);
//                                binding.mapView.onCreate(savedInstanceState);
//
//                                // Precise location access granted.
//                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
//                                // Only approximate location access granted.
//
//                            } else {
//                                // No location access granted.
//                            }
//                        }
//                );
//
//// ...
//
//// Before you perform the actual permission request, check whether your app
//// already has the permissions, and whether your app needs to show a permission
//// rationale dialog. For more details, see Request permissions.
//        locationPermissionRequest.launch(new String[]{
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//        });

        binding.mapView.onCreate(savedInstanceState);

        binding.mapView.onResume();

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        binding.mapView.getMapAsync(new OnMapReadyCallback() {
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
                            googleMap.addMarker(new MarkerOptions().position(new LatLng(arg0.getLatitude(), arg0.getLongitude())).title("It's Me!"));
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(arg0.getLatitude(), arg0.getLongitude())).zoom(12).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

            // Do something
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                    .build(getApplicationContext());
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            return true;
        }


        return super.onOptionsItemSelected(item);
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
//        mMap = googleMap;
//
//        // For showing a move to my location button
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//
//        // For dropping a marker at a point on the Map
//        LatLng sydney = new LatLng(-34, 151);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));
//
//        // For zooming automatically to the location of the marker
//        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mapView.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.mapView.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        binding.mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        binding.mapView.onLowMemory();

    }
}