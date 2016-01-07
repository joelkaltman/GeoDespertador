package com.bigbambu.geodespertador;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;

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
        new AlertDialog.Builder(this)
                .setTitle("Alerta")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // Some stuff to do when ok got clicked
                    }
                })
                .show();
        MediaPlayer mp = MediaPlayer.create(this, R.raw.asd);
        mp.start();
//        Intent i = new Intent(getApplicationContext(), sonando.class);
  //      i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //    startActivity(i);
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
