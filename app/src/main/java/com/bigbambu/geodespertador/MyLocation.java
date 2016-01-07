package com.bigbambu.geodespertador;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


/**
 * Created by Sebas on 04/01/2016.
 */

public class MyLocation implements LocationListener {

    public MyLocation (){

    }

    public void onLocationChanged(Location loc){
        //avisa()
    }

    public void onProviderDisabled(String provider){}

    public void onProviderEnabled(String provider){}

    public void onStatusChanged(String provider, int status, Bundle extras){}
}
