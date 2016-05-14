package com.lewy.googlemapsutil;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by dawid on 14.05.2016.
 */
public class MapItem implements ClusterItem{
    private final LatLng mPosition;

    public MapItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
