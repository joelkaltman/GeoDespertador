package com.bigbambu.geodespertador;

import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Sebas on 06/01/2016.
 */
public class Mapa implements GoogleMap.OnMapLongClickListener {
    public GoogleMap map;
    SettingsAlarma settings;
    public LatLng miUbicacion;
    public Marker miMarcador;
    public LatLng miMrk;

    public Mapa(GoogleMap map,SettingsAlarma setings){
        this.map = map;
        this.settings = setings;
        map.setOnMapLongClickListener(this);
    }

    public void centrarMapa(){

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-34.6229419, -58.4491101), 11f));
    }

    public void centrarMapa(LatLng ubic){
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ubic.latitude, ubic.longitude), 11f));
        map.animateCamera(CameraUpdateFactory.zoomIn());
        map.animateCamera(CameraUpdateFactory.zoomIn());
        map.animateCamera(CameraUpdateFactory.zoomIn());
    }

    public void actualizarMarcador(LatLng nueva_pos){
        try{
            this.miMarcador.remove();
        }catch(Exception a){

        }
        this.miMarcador = map.addMarker(new MarkerOptions().position(nueva_pos));
        this.miMrk = nueva_pos;
    }
    @Override
    public void onMapLongClick(LatLng point) {
        this.miUbicacion = point;
        actualizarMarcador(this.miUbicacion);
    }


}
