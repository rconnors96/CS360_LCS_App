package com.ryanconnors.cs360;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    private TextView editLocationTextview;
    private Button selectThisLocationButton;
    private Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        //assign resources
        res = this.getResources();

        //get username from intent
        username = getIntent().getStringExtra("EXTRA_USERNAME");

        //assign the location textview
        editLocationTextview = findViewById(R.id.edit_location_textview);

        //assign the select location button
        selectThisLocationButton = findViewById(R.id.select_location_button);

        //initialize Places SDK
        //unused right now
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
                //sets the selected location textview and "select this location" button to invisible
                //this ensures that they will be invisible to start whenever a new location is selected
                //this is probably unnecessary
                editLocationTextview.setVisibility(View.INVISIBLE);
                selectThisLocationButton.setVisibility(View.INVISIBLE);

                //assigns different elements of the Place selected
                latLng = place.getLatLng();
                address = place.getAddress();
                name = place.getName();

                //moves the marker and camera to the selected location
                mMap.addMarker(new MarkerOptions().position(latLng).title(name));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));

                //sets the selected location textview text, and makes both that and the button visible
                editLocationTextview.setText(String.format(res.getString(R.string.display_location), name, address));
                editLocationTextview.setVisibility(View.VISIBLE);
                selectThisLocationButton.setVisibility(View.VISIBLE);
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
    }


    public void onSelectThisLocationClicked(View view) {
        Intent intent = new Intent(this, OrderCoffee.class);
        intent.putExtra("EXTRA_USERNAME", username);
        intent.putExtra("EXTRA_LOCATION", name + " (" + address + ")");
        startActivity(intent);
    }


    public void onGoBackClicked(View view) {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("EXTRA_USERNAME", username);
        startActivity(intent);
    }

}
