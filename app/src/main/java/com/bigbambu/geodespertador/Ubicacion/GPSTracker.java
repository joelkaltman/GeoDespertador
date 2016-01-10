package com.bigbambu.geodespertador.Ubicacion;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;

import com.bigbambu.geodespertador.Alarma.AlarmDB;
import com.bigbambu.geodespertador.Alarma.Alarma;
import com.bigbambu.geodespertador.Constants.Constants;
import com.bigbambu.geodespertador.Layouts.sonando;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Joel on 07-Jan-16.
 */
public class GPSTracker extends Service implements LocationListener {
    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = Constants.DISTANCIALOCALIZAR;

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = Constants.TIEMPOLOCALIZAR;

    // Declaring a Location Manager
    protected LocationManager locationManager;

    public static LatLng ubicacion_actual;

    @Override
    public void onCreate() {
        if (ubicacion_actual == null)
            ubicacion_actual = Constants.BSAS;
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // Getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
            } else {
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                }
                // If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopUsingGPS(){
        if(locationManager != null){
            //locationManager.removeUpdates(GPSTracker.this);
        }
    }


    //overrides locationListener

    @Override
    public void onLocationChanged(Location location) {
        ubicacion_actual = new LatLng(location.getLatitude(), location.getLongitude());
        Mapa.actualizarMarcadorEstatico(ubicacion_actual, Constants.USUARIO);
        AlarmDB base = new AlarmDB(this);
        List<Alarma> alarmas = base.obtenerTodasAlarmas();
        for (Alarma a: alarmas) {
            if (a.getActiva() == 's') {
                a.estaSonando(ubicacion_actual);
                if (a.getSonando()) {
                    Intent i = new Intent(getApplicationContext(), sonando.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("nombre", a.getNombre());
                    i.putExtra("latitud", a.getLatLong().latitude);
                    i.putExtra("longitud", a.getLatLong().longitude);
                    startActivity(i);
                }
            }
        }
    }


    @Override
    public void onProviderDisabled(String provider) {
    }


    @Override
    public void onProviderEnabled(String provider) {
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    //overrides servicio

    @Override
    public void onDestroy() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}