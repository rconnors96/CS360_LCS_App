package com.ryanconnors.cs360;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String username;
    private PlacesClient placesClient;
    private AutocompleteSupportFragment autocompleteSupportFragment;
    private LatLng latLng;
    private String address, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //get username from intent
        username = getIntent().getStringExtra("EXTRA_USERNAME");


        //initialize Places SDK
        Places.initialize(this, "AIzaSyDe_V5cEUg95ULogkTBYVdk2XwrEXwcQio");
        placesClient = Places.createClient(this);

        //links to the autocomplete fragment id
        autocompleteSupportFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        //decides what fields should be returned for a search result (NAME, LAT/LONG, ADDRESS in this case)
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                latLng = place.getLatLng();
                address = place.getAddress();
                name = place.getName();

                mMap.addMarker(new MarkerOptions().position(latLng).title(name));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }

            @Override
            public void onError(Status status) {
                System.out.println("onError  Error: " + status);
                Log.i("TAG", "An error occurred: " + status);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //set custom map .json
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));

        //enable zoom button controls
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //set zoom preferences
        mMap.setMaxZoomPreference(15);
        mMap.setMinZoomPreference(10);
    }
}
