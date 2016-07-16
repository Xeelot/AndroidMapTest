package com.apps.xeelot.maptest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private Circle mCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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
        try {
            mMap.setMyLocationEnabled(true);
        } catch(SecurityException e) {
            e.printStackTrace();
        }
        UiSettings ui = mMap.getUiSettings();
        ui.setTiltGesturesEnabled(false);
        ui.setIndoorLevelPickerEnabled(false);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);

        // Add our single circle
        LatLng latLng = new LatLng(0,0);
        CircleOptions co = new CircleOptions()
                .center(latLng)
                .radius(100)
                .strokeColor(Color.CYAN)
                .fillColor(Color.CYAN & 0x55FFFFFF);
        mCircle = mMap.addCircle(co);
        // Set our custom info window
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
    }


    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter, SeekBar.OnSeekBarChangeListener {
        private final View mWindow;
        private final View mContents;

        private EditText eRadius;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.map_popup, null);
            mContents = getLayoutInflater().inflate(R.layout.map_popup, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker, mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            EditText name = (EditText)findViewById(R.id.editName);
            Spinner category = (Spinner)findViewById(R.id.spinnerCategory);
            eRadius = (EditText)findViewById(R.id.editRadius);
            SeekBar sRadius = (SeekBar)findViewById(R.id.seekRadius);
            Button uButton = (Button)findViewById(R.id.buttonUpdate);
            Button dButton = (Button)findViewById(R.id.buttonDelete);
            Button cButton = (Button)findViewById(R.id.buttonCancel);


            //sRadius.setOnSeekBarChangeListener(view.getContext());
            //eRadius.setText(sRadius.getProgress());
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            eRadius.setText(seekBar.getProgress());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }




    @Override
    public void onMapClick(LatLng latLng) {
        Marker newMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("New Marker")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        newMarker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Create our popup window
//        LinearLayout ll = new LinearLayout(getApplicationContext());
//        ll.setLayoutParams(new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        ll.setOrientation(LinearLayout.VERTICAL);
//        TextView tv = new TextView(getApplicationContext());
//        tv.setText(newMarker.getTitle());
//        SeekBar sb = new SeekBar(getApplicationContext());
//
//        ll.addView(tv);
//        ll.addView(sb);
//        addContentView(ll, ll.getLayoutParams());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast t = Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT);
        t.show();
        return false;
    }
}
