package com.bigbambu.geodespertador;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Sebas on 04/01/2016.
 */

public class MyLocation implements LocationListener {
    SettingsAlarma settings;

    public MyLocation(SettingsAlarma clase){
        setMapsActivity(clase);
    }

    public SettingsAlarma getMapsActivity(){ return this.settings; }

    public void setMapsActivity(SettingsAlarma mapsActivity){ this.settings = mapsActivity; }

    public void onLocationChanged(Location loc){
        settings.map.ubicacionUsuario = new LatLng(loc.getLatitude(),loc.getLongitude());
        if(settings.map.marcadorUsuario != null)
            settings.map.marcadorUsuario.remove();
        LatLng latlng = new LatLng(1,1);
        settings.map.actualizarMarcador(latlng,Mapa.USUARIO);
    }

    public void onProviderDisabled(String provider){}

    public void onProviderEnabled(String provider){}

    public void onStatusChanged(String provider, int status, Bundle extras){}
}
