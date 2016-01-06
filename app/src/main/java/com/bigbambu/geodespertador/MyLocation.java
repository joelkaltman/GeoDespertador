package com.bigbambu.geodespertador;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Sebas on 04/01/2016.
 */

public class MyLocation implements LocationListener {
    SettingsAlarma mapsActivity;

    public MyLocation(SettingsAlarma clase){
        setMapsActivity(clase);
    }

    public SettingsAlarma getMapsActivity(){ return this.mapsActivity; }

    public void setMapsActivity(SettingsAlarma mapsActivity){ this.mapsActivity = mapsActivity; }

    public void onLocationChanged(Location loc){
        mapsActivity.map.miUbicacion = new LatLng(loc.getLatitude(),loc.getLongitude());
     }

    public void onProviderDisabled(String provider){}

    public void onProviderEnabled(String provider){}

    public void onStatusChanged(String provider, int status, Bundle extras){}
}
