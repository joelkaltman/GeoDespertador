package com.bigbambu.geodespertador;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class SettingsAlarma extends AppCompatActivity {
    Button btn_guardar;
    Button btn_volver;
    Button btn_borrar;
    EditText txt_nombre;
    SeekBar skb_distancia;
    AlarmDB base;

    Mapa map;
    LocationManager locman;
    MyLocation loclist;
    Alarma alarmaActual;
    int radio = 100;
    Circle miCirculo;
    CircleOptions opcionesCirculo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings_alarma);
        map = new Mapa(((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(),this);

        map.centrarMapa();
        configurarBotones();

        base = new AlarmDB(this);

        String accion = getIntent().getAction();
        if (accion.equals("NUEVO")){
            nuevaAlarma();
        }else{
            modificarAlarma();
        }
    }

    private void modificarAlarma(){
        String lat = getIntent().getStringExtra("lat");
        String lng = getIntent().getStringExtra("long");
        String distancia = getIntent().getStringExtra("distancia");
        fijarDistancia(Integer.parseInt(distancia));
        txt_nombre.setText(getIntent().getStringExtra("nombre"));
        alarmaActual = new Alarma(txt_nombre.getText().toString(), lat, lng, distancia);
        LatLng ubic = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        map.actualizarMarcador(ubic);
        map.centrarMapa(ubic);
    }



    private void configurarBotones(){
        txt_nombre = (EditText)findViewById(R.id.txt_nombre);
        btn_guardar = (Button)findViewById(R.id.btn_guardar);
        btn_volver = (Button)findViewById(R.id.btn_volver);
        skb_distancia = (SeekBar)findViewById(R.id.skb_distancia);
        btn_borrar = (Button)findViewById(R.id.btn_borrar);


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (map.marcado) {
                    map.marcado = false; //porque sino queda siempre como "marcado"
                    String lng = String.valueOf(map.miUbicacion.longitude);
                    String lat = String.valueOf(map.miUbicacion.latitude);
                    String nombre = txt_nombre.getText().toString();
                    int distancia = skb_distancia.getProgress();
                    alarmaActual = new Alarma(nombre, lat, lng, String.valueOf(distancia));
                    base.actualizarAlarma(alarmaActual);
                    Volver();
                } else {
                    //debe ingresar una ubicacion
                }
            }
        });

        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String nombre = getIntent().getStringExtra("nombre");
                base.borrarAlarma(nombre);
                Volver();
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Volver();
            }
        });


    }

    private void fijarDistancia(int distancia){
        opcionesCirculo = new CircleOptions()
                .radius(radio)
                .strokeWidth(5)
                .strokeColor(0x994682B4)
                .fillColor(0x504682B4) //50 = nivel transparencia, 4682B4 = color
                .zIndex(10)
                .visible(true);
        skb_distancia.setMax(1000);
        skb_distancia.setProgress(distancia);
        skb_distancia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar skb_distancia, int progreso, boolean fromUser) {
                radio = skb_distancia.getProgress();
                if (miCirculo != null) {
                    miCirculo.setRadius(radio);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar barraRadio) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar barraRadio) {

            }
        });
    }


    private void Volver(){
        Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(i);
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

}
