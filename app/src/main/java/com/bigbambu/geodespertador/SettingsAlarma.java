package com.bigbambu.geodespertador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class SettingsAlarma extends AppCompatActivity {

    //region vaiables
    //elementos del layout
    Button btn_guardar;
    Button btn_volver;
    Button btn_borrar;
    EditText txt_nombre;
    SeekBar skb_distancia;
    String nombre;

    //base de datos
    AlarmDB base;

    //mapa
    Mapa map;

    //endregion

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
        nombre = getIntent().getStringExtra("nombre");
        txt_nombre.setText(nombre);
        skb_distancia.setProgress(Integer.parseInt(distancia));
        LatLng ubic = new LatLng(Double.parseDouble(lat),Double.parseDouble(lng));
        map.actualizarMarcador(ubic, Mapa.DESTINO);
        map.actualizarCirculo(ubic, Integer.parseInt(distancia));
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
                if (Mapa.marcadorDestino != null) {
                    String lng = String.valueOf(Mapa.ubicacionDestino.longitude);
                    String lat = String.valueOf(Mapa.ubicacionDestino.latitude);
                    String nombre = txt_nombre.getText().toString();
                    int distancia = skb_distancia.getProgress();
                    Alarma alarmaActual = new Alarma(nombre, lat, lng, String.valueOf(distancia));
                    String accion = getIntent().getAction();
                    try {
                        if (accion.equals("NUEVO")) {
                            base.insertarAlarma(alarmaActual);
                        } else {
                            base.borrarAlarma(nombre);
                            base.insertarAlarma(alarmaActual);
                        }
                    }catch (Exception e){

                    }
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
        map.actualizarRadioCirculo(skb_distancia.getProgress());
        skb_distancia.setMax(1000);
        skb_distancia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar barraRadio, int progreso, boolean fromUser) {
                map.actualizarRadioCirculo(skb_distancia.getProgress());
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
       txt_nombre.setText("Nueva Alarma");
       map.actualizarMarcador(Mapa.BSAS, Mapa.DESTINO);
       map.actualizarCirculo(Mapa.BSAS, skb_distancia.getProgress());
    }


}
