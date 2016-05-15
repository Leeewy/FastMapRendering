package com.lewy.googlemapsutil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.lewy.googlemapsutil.managers.FastClusterManager;
import com.lewy.googlemapsutil.managers.MyDefaultClusterRenderer;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FastClusterManager<MapItem> mFastClusterManager;

    private GoogleMap mMap;

    private LatLng latLng = new LatLng(54.32, 18.64); // GDAŃSK

    private boolean mManyMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

        mFastClusterManager = new FastClusterManager<>(this, mMap, true);

        mMap.setOnCameraChangeListener(mFastClusterManager);

        new CreateMarkers().execute();

        setSwitchAction();
    }

    private void setSwitchAction() {
        Switch switchView = (Switch) findViewById(R.id.faster_cluster_switch);
        switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mManyMarkers = isChecked;
                new CreateMarkers().execute();
            }
        });
    }

    class CreateMarkers extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            mFastClusterManager.clearItems();
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    LatLng newLatLng = new LatLng(latLng.latitude + ((double) i) / 1000, latLng.longitude + ((double) j) / 1000);
                    MapItem mapItem = new MapItem(newLatLng.latitude, newLatLng.longitude);
                    mFastClusterManager.addItem(mapItem);
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            mFastClusterManager.clearItems();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mFastClusterManager.setRenderer(new MyDefaultClusterRenderer(MainActivity.this, mMap, mFastClusterManager, mManyMarkers));
        }
    }
}
