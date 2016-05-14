package com.lewy.googlemapsutil.managers;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

/**
 * Created by dawid on 14.05.2016.
 */
public class FastClusterManager<T extends ClusterItem> extends ClusterManager {
    private CameraPosition mPreviousCameraPosition;

    private boolean mManyMarkers;

    public FastClusterManager(Context context, GoogleMap map) {
        super(context, map);
    }

    public FastClusterManager(Context context, GoogleMap map, boolean mManyMarkers) {
        this(context, map);
        this.mManyMarkers = mManyMarkers;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if(mManyMarkers) {
            if (mPreviousCameraPosition != null && mPreviousCameraPosition.zoom == cameraPosition.zoom) {
                cluster();
            }
            mPreviousCameraPosition = cameraPosition;
        }

        super.onCameraChange(cameraPosition);
    }
}