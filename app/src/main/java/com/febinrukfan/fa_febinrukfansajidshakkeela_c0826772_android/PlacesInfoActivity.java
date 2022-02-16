package com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.febinrukfan.fa_febinrukfansajidshakkeela_c0826772_android.databinding.ActivityPlacesInfoBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlacesInfoActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,RoutingListener {
    private static int AUTOCOMPLETE_REQUEST_CODE = 100;

    ActivityPlacesInfoBinding binding;
    private GoogleMap googleMap;

    private String pName,pAddrss,pPlaceId,pDate;
    private Double pLat,pLong;
    private Boolean pVisited;
    String pId;
    private final String TAG = this.getClass().getName();

    //current and destination location objects
    Location myLocation=null;
    Location destinationLocation=null;
    protected LatLng start=null;
    protected LatLng end=null;

    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission=false;

    //polyline object
    private List<Polyline> polylines=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlacesInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        PlacesRoomDb database = PlacesRoomDb.getInstance(this);

        pId = getIntent().getStringExtra("id");
        Log.v(TAG,"here  " +pId);


        getSupportActionBar().setTitle(database.placesDao().getPlaceName(Long.valueOf(pId)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Places.initialize(getApplicationContext(), getString(R.string.api_key));

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

                            pName = database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_name();
                            pAddrss = database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_address();
                            pPlaceId = database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_id();
                            pLat = database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_latitude();
                            pLong = database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_longitude();

                            googleMap.addMarker(new MarkerOptions().position(new LatLng(database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_latitude(),database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_longitude())).title(database.placesDao().getPlaceName(Long.valueOf(pId))));
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_latitude(),database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_longitude())).zoom(12).build();
                            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                             start=new LatLng(arg0.getLatitude(), arg0.getLongitude());
                             end=new LatLng(database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_latitude(),database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_longitude());

                        }
                    });
                }
            }
        });

        binding.btnDirections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Findroutes(start,end);

            }
        });


        binding.txtDate.setText(database.placesDao().getPlaceDetails(Long.valueOf(pId)).getAdded_date());
        binding.txtAddress.setText(database.placesDao().getPlaceDetails(Long.valueOf(pId)).getPlaces_address());

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pLat!=null) {


                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String formattedDate = df.format(c);


                        Boolean visit = false;
                        if (binding.chkVisit.isChecked()) {
                            visit = true;
                        }
                        database.placesDao().update(Long.valueOf(pId), pName, pAddrss, pLat, pLong, formattedDate, visit);
                        Toast.makeText(PlacesInfoActivity.this, "Place Updated", Toast.LENGTH_SHORT).show();
                        clearAllData();
                        startActivity(new Intent(PlacesInfoActivity.this, FavouriteListActivity.class));
                        finish();

                }else {
                    Toast.makeText(PlacesInfoActivity.this, "Search a place to add", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void clearAllData() {

        pName = null;
        pAddrss = null;
        pPlaceId = null;
        pLat = null;
        pLong = null;
        pDate = null;
        pVisited = null;
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
        }else if(id == android.R.id.home){

            Intent intent = new Intent(this, FavouriteListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
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

    // function to find Routes.
    public void Findroutes(LatLng Start, LatLng End)

    {
        if(Start==null || End==null) {
            Toast.makeText(PlacesInfoActivity.this,"Unable to get location", Toast.LENGTH_LONG).show();
        }
        else
        {

            Log.v(TAG," st" + Start+" end"+ end);

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyC-nA_5kZkEl_6kb7FeT8jgAPhPAvP0Utw")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar= Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(PlacesInfoActivity.this,"Finding Route...",Toast.LENGTH_LONG).show();
    }

    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if(polylines!=null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng=null;
        LatLng polylineEndLatLng=null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {

            if(i==shortestRouteIndex)
            {
                polyOptions.color(getResources().getColor(R.color.black));
                polyOptions.width(20);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = googleMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);


                // create an alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Name");
                // set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.custom_alert, null);
                builder.setView(customLayout);
                // add a button
                int finalI = i;
                TextView dur = customLayout.findViewById(R.id.txtDuration);
                TextView dis = customLayout.findViewById(R.id.txtDistance);
                dur.setText(""+route.get(finalI).getDistanceText());
                dis.setText(""+route.get(finalI).getDurationText());

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // send data from the AlertDialog to the Activity
                       dialog.dismiss();
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
            else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        googleMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        googleMap.addMarker(endMarker);
    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start,end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start,end);

    }

}