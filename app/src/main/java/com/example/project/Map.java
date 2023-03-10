package com.example.project;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.project.databinding.ActivityMapBinding;

import java.io.IOException;
import java.util.List;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    public static String address_Order;
    private GoogleMap mMap;
    EditText addressText;
    LocationManager locManager;
    myLocationListener locListener;
    Button getLocation;
    private ActivityMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        addressText = (EditText) findViewById(R.id.editText);
        getLocation = (Button) findViewById(R.id.btn1);
        locListener = new myLocationListener(getApplicationContext());
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locListener);

        } catch (SecurityException ex) {
            Toast.makeText(getApplicationContext(), "you are not allowed to access Current Location", Toast.LENGTH_LONG).show();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960, 31.235711600), 8));


        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc = null;

                try {
                    loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                } catch (SecurityException x) {

                    Toast.makeText(getApplicationContext(), "you did not allow to access the current location", Toast.LENGTH_LONG).show();
                }
                if (loc != null) {
                    LatLng myPosition = new LatLng(loc.getLatitude(), loc.getLongitude());
                    try {
                        addressList = coder.getFromLocation(myPosition.latitude, myPosition.longitude, 1);
                        if (!addressList.isEmpty()) {
                            String address = "";
                            for (int i = 0; i <= addressList.get(0).getMaxAddressLineIndex(); i++)
                                address += addressList.get(0).getAddressLine(i) + " ,";

                            mMap.addMarker(new MarkerOptions().position(myPosition)
                                    .title("My location").snippet(address)).setDraggable(true);
                            address_Order = address;
                            addressText.setText(address_Order);

                        }
                    } catch (IOException e) {
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("My location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
                } else
                    Toast.makeText(getApplicationContext(), "Please wait until your position is determines", Toast.LENGTH_LONG).show();
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                Geocoder coder = new Geocoder(getApplicationContext());
                List<Address> addressList;
                try {
                    addressList = coder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                    if (!addressList.isEmpty()) {
                        String address = "";
                        for (int i = 0; i < addressList.get(0).getMaxAddressLineIndex(); i++)
                            address += addressList.get(0).getAddressLine(i) + ", ";
                        addressText.setText(address);
                    } else {
                        Toast.makeText(getApplicationContext(), "No address for this Location", Toast.LENGTH_LONG).show();
                        addressText.getText().clear();
                    }
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "Can't get the address , check your network", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onMarkerDrag(@NonNull Marker marker) {

            }


            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }
        });

    }

}