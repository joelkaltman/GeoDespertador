package com.bigbambu.geodespertador;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Joel on 06-Jan-16.
 */
public class Servicio extends Service {

    LatLng ubic;
    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateMyCurrentLoc(location);
        }

        public void onProviderDisabled(String provider){
            updateMyCurrentLoc(null);
        }

        public void onProviderEnabled(String provider){ }
        public void onStatusChanged(String provider, int status,
                                    Bundle extras){ }
    };

    private void updateMyCurrentLoc(Location location) {
        if (location != null) {
            ubic = new LatLng(location.getLatitude(), location.getLongitude());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
        while (true){
            try {
                Thread.sleep(10000);
            }catch (Exception a){

            }

            verificar(ubic);
        }
    }

    private void verificar(LatLng ubicacionActual){
        AlarmDB base = new AlarmDB(this);
        List<Alarma> alarmas = base.obtenerTodasAlarmas();
        for (Alarma a: alarmas) {
            a.estaSonando(ubicacionActual);
            if (a.sonando){
                //aca es cuando esta en el rango
            }
        }
    }


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
