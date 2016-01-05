package com.bigbambu.geodespertador;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class SettingsAlarma extends AppCompatActivity {
    Button btn_guardar;
    Button btn_volver;
    EditText txt_nombre;
    SeekBar skb_distancia;
    AlarmDB base;
    public LatLng miUbicacion;
    GoogleMap map;
    LocationManager locman;
    MyLocation loclist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_alarma);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        base = new AlarmDB(this);
        txt_nombre = (EditText)findViewById(R.id.txt_nombre);
        btn_guardar = (Button)findViewById(R.id.btn_guardar);
        btn_volver = (Button)findViewById(R.id.btn_volver);
        skb_distancia = (SeekBar)findViewById(R.id.skb_distancia);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String lng = String.valueOf(miUbicacion.longitude);
                String lat = String.valueOf(miUbicacion.latitude);
                String nombre = txt_nombre.getText().toString();
                int distancia = skb_distancia.getProgress();
                base.insertarAlarma(nombre, lat, lng, String.valueOf(distancia));
            }
        });


        btn_volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(i);

            }
        });

        String acc = "NUEVO";
        String accion = getIntent().getAction();
        if (accion.equals(acc)){
            nuevaAlarma();
        }else{
            txt_nombre.setText(accion);
        }

    }


    private void nuevaAlarma(){
        locman = (LocationManager) getSystemService(LOCATION_SERVICE);
        loclist = new MyLocation(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locman.requestLocationUpdates(locman.GPS_PROVIDER, 0L, 0.0F, loclist);
        locman.getLastKnownLocation(locman.GPS_PROVIDER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_alarma, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
