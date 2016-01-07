package com.bigbambu.geodespertador;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Joel on 06-Jan-16.
 */
public class GPS extends AppCompatActivity {
    LocationManager locman;
    MyLocation loclist;

    public GPS(){
        establecerConexionGPS();
    }

    public void establecerConexionGPS(){
        locman = (LocationManager) getSystemService(LOCATION_SERVICE);
        loclist = new MyLocation();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locman.requestLocationUpdates(locman.GPS_PROVIDER, 0L, 0.0F, loclist);
    }

    public LatLng consultarPosicion(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return new LatLng(0,0);
        }
        Location mi_ubicacion = locman.getLastKnownLocation(locman.GPS_PROVIDER);
        return new LatLng(mi_ubicacion.getLatitude(), mi_ubicacion.getLongitude());
    }

}
