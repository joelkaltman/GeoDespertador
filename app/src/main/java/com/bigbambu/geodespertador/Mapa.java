package com.bigbambu.geodespertador;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
    public GoogleMap map;
    SettingsAlarma settings;
    public LatLng ubicacionDestino;
    public Marker marcadorDestino;
    public CircleOptions opcionesCirculo;
    public Circle miCirculo;
    public LatLng ubicacionUsuario;
    public Marker marcadorUsuario;

    public Mapa(GoogleMap map, SettingsAlarma setings) {
        this.map = map;
        this.settings = setings;
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

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(Mapa.BSAS, 11f));
    }

    public void centrarMapa(LatLng ubic) {
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ubic.latitude, ubic.longitude), 11f));
        zoomin();
    }

    public void actualizarMarcador(LatLng nueva_pos, boolean esDestino) {
        if (esDestino) {
            try {
                this.marcadorDestino.remove();
            } catch (Exception a) {
            }
            this.marcadorDestino = map.addMarker(new MarkerOptions().position(nueva_pos));
            this.ubicacionDestino = nueva_pos;
        }else {
            try {
                this.marcadorUsuario.remove();
            } catch (Exception a) {
            }
            this.marcadorUsuario = map.addMarker(new MarkerOptions().position(nueva_pos));
            this.ubicacionUsuario = nueva_pos;
        }

    }


    public void actualizarCirculo(LatLng nueva_pos, int radio){
        try{
            this.miCirculo.remove();
        }catch(Exception a){

        }
        opcionesCirculo.radius(radio);
        opcionesCirculo.center(nueva_pos);
        miCirculo = map.addCircle(opcionesCirculo);
    }

    public void actualizarRadioCirculo(int radio){
        if(this.miCirculo != null)
            this.miCirculo.setRadius(radio);
    }

    public void zoomin(){
        map.animateCamera(CameraUpdateFactory.zoomTo( 16.0f ) );
    }

    @Override
    public void onMapLongClick(LatLng point) {
        this.ubicacionDestino = point;
        actualizarMarcador(this.ubicacionDestino, Mapa.DESTINO);
        actualizarCirculo(point, (int)miCirculo.getRadius());

      }

}
