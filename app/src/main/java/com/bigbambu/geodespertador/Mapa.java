package com.bigbambu.geodespertador;

import com.bigbambu.geodespertador.Layouts.SettingsAlarma;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


/**
 * Created by Sebas on 06/01/2016.
 */
public class Mapa implements GoogleMap.OnMapLongClickListener {
    public static final boolean DESTINO = true;
    public static final boolean USUARIO = false;
    public static final LatLng BSAS = new LatLng(-34.6229419,-58.4491101);
    public static GoogleMap map;
    public static SettingsAlarma settings;
    public static LatLng ubicacionDestino;
    public static Marker marcadorDestino;
    public static CircleOptions opcionesCirculo;
    public static Circle miCirculo;
    public static LatLng ubicacionUsuario;
    public static Marker marcadorUsuario;

    public Mapa(GoogleMap map, SettingsAlarma setings) {
        Mapa.map = map;
        Mapa.settings = setings;
        map.setOnMapLongClickListener(this);
        opcionesCirculo = new CircleOptions()
                .radius(100)
                .strokeWidth(5)
                .strokeColor(0x994682B4)
                .fillColor(0x504682B4) //50 = nivel transparencia, 4682B4 = color
                .zIndex(10)
                .visible(true);
    }

    public void centrarMapa() {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Mapa.BSAS, 15f));
    }

    public void centrarMapa(LatLng ubic) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ubic.latitude, ubic.longitude), 15f));
        zoomin();
    }

    public void actualizarMarcador(LatLng nueva_pos, boolean esDestino) {
        Mapa.actualizarMarcadorEstatico(nueva_pos, esDestino);
    }
    public static void actualizarMarcadorEstatico(LatLng nueva_pos, boolean esDestino) {
        if (esDestino) {
            try {
                Mapa.marcadorDestino.remove();
            } catch (Exception a) {
            }
            Mapa.marcadorDestino = map.addMarker(new MarkerOptions().position(nueva_pos));
            Mapa.ubicacionDestino = nueva_pos;
        }else {
            try {
                Mapa.marcadorUsuario.remove();
            } catch (Exception a) {
            }
            Mapa.marcadorUsuario = Mapa.map.addMarker(new MarkerOptions().position(nueva_pos).icon(BitmapDescriptorFactory.fromResource(R.drawable.usuario)));
            Mapa.ubicacionUsuario = nueva_pos;
        }

    }


    public void actualizarCirculo(LatLng nueva_pos, int radio){
        try{
            Mapa.miCirculo.remove();
        }catch(Exception a){

        }
        Mapa.opcionesCirculo.radius(radio);
        Mapa.opcionesCirculo.center(nueva_pos);
        Mapa.miCirculo = Mapa.map.addCircle(opcionesCirculo);
    }

    public void actualizarRadioCirculo(int radio){
        if(Mapa.miCirculo != null)
            Mapa.miCirculo.setRadius(radio);
    }

    public void zoomin(){
        Mapa.map.animateCamera(CameraUpdateFactory.zoomTo( 16.0f ) );
    }

    @Override
    public void onMapLongClick(LatLng point) {
        Mapa.ubicacionDestino = point;
        actualizarMarcador(Mapa.ubicacionDestino, Mapa.DESTINO);
        actualizarCirculo(point, (int)Mapa.miCirculo.getRadius());

      }

}
