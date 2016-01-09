package com.bigbambu.geodespertador.Layouts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bigbambu.geodespertador.Alarma.AlarmDB;
import com.bigbambu.geodespertador.Alarma.Alarma;
import com.bigbambu.geodespertador.Excepciones.ExisteAlarmaException;
import com.bigbambu.geodespertador.Ubicacion.Mapa;
import com.bigbambu.geodespertador.R;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class SettingsAlarma extends AppCompatActivity {

    public static final String MODIFICAR = "Modificar";
    public static final String NUEVO = "Nuevo";

    //region vaiables
    //elementos del layout
    Button btn_guardar;
    Button btn_volver;
    Button btn_borrar;
    ImageButton btn_centrar_usuario;
    ImageButton btn_centrar_destino;
    EditText txt_nombre;
    TextView txt_km;
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
        configurarBotones();
        base = new AlarmDB(this);

        String accion = getIntent().getAction();
        if (accion.equals("NUEVO")){
            nuevaAlarma();
        }else{
            modificarAlarma();
        }
        map.centrarMapa(map.ubicacionDestino);
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
        btn_centrar_destino = (ImageButton)findViewById(R.id.btn_centrar_destino);
        btn_centrar_usuario = (ImageButton)findViewById(R.id.btn_centrar_usuario);
        skb_distancia = (SeekBar)findViewById(R.id.skb_distancia);
        btn_borrar = (Button)findViewById(R.id.btn_borrar);
        txt_km = (TextView)findViewById(R.id.txt_km);



        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Mapa.marcadorDestino != null || txt_nombre.getText().toString() == "") {
                    Alarma alarmaActual = getDatosPantalla();
                    String accion = getIntent().getAction();
                    try {
                        if (accion.equals(SettingsAlarma.NUEVO)){
                            base.insertarAlarma(alarmaActual);
                        } else if (accion.equals(SettingsAlarma.MODIFICAR)){
                            String nombre_anterior = getIntent().getStringExtra("nombre");
                            base.borrarAlarma(nombre_anterior);
                            base.insertarAlarma(alarmaActual);
                        }
                    }catch (ExisteAlarmaException fallo){
                        crearMensaje(fallo.getMessage());
                    }
                    catch (Exception e){}
                    Volver();
                } else {
                    if (Mapa.marcadorDestino == null)
                        crearMensaje("Debe ingresar una ubicacion destino");
                    if (txt_nombre.getText().toString() == "")
                        crearMensaje("Nombre de alarma invalido");
                }
            }
        });

        btn_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accion = getIntent().getAction();
                if (accion.equals(SettingsAlarma.NUEVO)) {
                    Volver();
                } else if (accion.equals(SettingsAlarma.MODIFICAR)){
                    String nombre = getIntent().getStringExtra("nombre");
                    base.borrarAlarma(nombre);
                    Volver();
                }

            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Volver();
            }
        });

        btn_centrar_destino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.ubicacionDestino != null)
                    map.centrarMapa(map.ubicacionDestino);
            }
        });

        btn_centrar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (map.ubicacionUsuario != null)
                    map.centrarMapa(map.ubicacionUsuario);
            }
        });

        map.actualizarRadioCirculo(skb_distancia.getProgress());
        skb_distancia.setMax(1000);
        skb_distancia.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar barraRadio, int progreso, boolean fromUser) {
                int valor = skb_distancia.getProgress();
                map.actualizarRadioCirculo(skb_distancia.getProgress());
                String texto = String.valueOf(valor) + "m";
                txt_km.setText(texto);
            }

            @Override
            public void onStartTrackingTouch(SeekBar barraRadio) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar barraRadio) {

            }
        });


    }

    private Alarma getDatosPantalla(){
        Double nueva_lon = Mapa.ubicacionDestino.longitude;
        Double nueva_lat = Mapa.ubicacionDestino.latitude;
        LatLng nueva_latlong = new LatLng(nueva_lat,nueva_lon);
        String nuevo_nombre = txt_nombre.getText().toString();
        int nueva_distancia = skb_distancia.getProgress() + Mapa.MINDISTANCE;
        return new Alarma(nuevo_nombre, nueva_latlong, nueva_distancia,Alarma.ACTIVADA);
    }

    private void crearMensaje(String mensaje){
        //crear dialogo
    }

    private void Volver(){
        Intent i = new Intent(getApplicationContext(), PrincipalActivity.class);
        startActivity(i);
    }

   private void nuevaAlarma(){
       txt_nombre.setText("Nueva Alarma");
       txt_nombre.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               txt_nombre.setText("");
           }
       });
       map.actualizarMarcador(Mapa.BSAS, Mapa.DESTINO);
       map.actualizarCirculo(Mapa.BSAS, skb_distancia.getProgress());
    }



}
