package com.bigbambu.geodespertador;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * Created by Sebas on 06/01/2016.
 */
public class Mapa implements GoogleMap.OnMapLongClickListener {
    public GoogleMap map;
    SettingsAlarma settings;
    public LatLng miUbicacion;
    public ArrayList<Marker> miMarcador;

    public Mapa(GoogleMap map,SettingsAlarma setings){
        this.map = map;
        this.settings = setings;
        map.setOnMapLongClickListener(this);
        miMarcador = new ArrayList<Marker>();
    }

    public void centrarMapa(){

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34.6229419, -58.4491101), 11f));
    }

    public void centrarMapa(LatLng ubic){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ubic.latitude, ubic.longitude), 11f));
        zoomin();
    }

    public void actualizarMarcador(LatLng nueva_pos){
        try{
            this.miMarcador.get(0).remove();
            this.miMarcador.clear();
        }catch(Exception a){

        }
        this.miMarcador.add(map.addMarker(new MarkerOptions().position(nueva_pos)));
        this.miUbicacion = nueva_pos;
    }

    public void zoomin(){
        map.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
    }

    @Override
    public void onMapLongClick(LatLng point) {
        this.miUbicacion = point;
        actualizarMarcador(this.miUbicacion);
    }


}
