package com.bigbambu.geodespertador.Ubicacion;

import android.app.FragmentManager;

import com.bigbambu.geodespertador.Constants.Constants;
import com.bigbambu.geodespertador.Layouts.SettingsAlarma;
import com.bigbambu.geodespertador.Layouts.sonando;
import com.bigbambu.geodespertador.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Sebas on 06/01/2016.
 */
public class Mapa implements GoogleMap.OnMapLongClickListener{

    public static GoogleMap map;
    public static SettingsAlarma settings;
    public static sonando sonando;
    public static LatLng ubicacionDestino;
    public static LatLng ubicacionUsuario;
    public static Marker marcadorDestino;
    public static Marker marcadorUsuario;
    public static CircleOptions opcionesCirculo;
    public static Circle miCirculo;
    public static boolean ready = false;

    public Mapa(FragmentManager fragmentManager, SettingsAlarma setings) {
        map = ((MapFragment) fragmentManager.findFragmentById(R.id.map)).getMap();
        Mapa.settings = setings;
        configCirculo();
        Mapa.map.setOnMapLongClickListener(this);
        Mapa.ready = true;
        centrarMapa(Constants.BSAS);

    }

    public Mapa(FragmentManager fragmentManager, sonando setings) {
        map = ((MapFragment) fragmentManager.findFragmentById(R.id.map_sonando)).getMap();
        Mapa.sonando = setings;
        configCirculo();
        Mapa.map.setOnMapLongClickListener(this);
        Mapa.ready = true;
        centrarMapa(Constants.BSAS);

    }

    //region MOVER CAMARA MAPA
    public void zoomin(float zoom){
        if(Mapa.ready)
            Mapa.map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
    }
    public void centrarMapa(LatLng ubic) {
        if(Mapa.ready)
            Mapa.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ubic.latitude, ubic.longitude), 11f));
    }
    public void centrarMapa(LatLng ubic,float zoom) {
        if(Mapa.ready)
            Mapa.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(ubic.latitude, ubic.longitude), zoom));
    }
    //endregion

    //region METODOS CIRCULO
    public void actualizarCirculo(LatLng nueva_pos, int radio) {
        if (Mapa.ready) {
            try {
                Mapa.miCirculo.remove();
            } catch (Exception a) {

            }
            Mapa.opcionesCirculo.radius(radio+Constants.MINDISTANCE);
            Mapa.opcionesCirculo.center(nueva_pos);
            Mapa.miCirculo = Mapa.map.addCircle(Mapa.opcionesCirculo);
        }
    }

    public void actualizarRadioCirculo(int radio){
        if(Mapa.miCirculo != null && Mapa.ready)
            Mapa.miCirculo.setRadius(radio+Constants.MINDISTANCE);
    }

    private void configCirculo(){
        Mapa.opcionesCirculo = new CircleOptions()
                .radius(100)
                .strokeWidth(5)
                .strokeColor(0x994682B4)
                .fillColor(0x504682B4) //50 = nivel transparencia, 4682B4 = color
                .zIndex(10)
                .visible(true);
    }
    //endregion

    //region MARCADOR
    public void actualizarMarcador(LatLng nueva_pos, boolean esDestino) {
        if (Mapa.ready) {
            Mapa.actualizarMarcadorEstatico(nueva_pos, esDestino);
        }
    }
    public static void actualizarMarcadorEstatico(LatLng nueva_pos, boolean esDestino) {
        if (esDestino) {
            try {
                Mapa.marcadorDestino.remove();
            } catch (Exception a) {
            }
            if (Mapa.ready)
                Mapa.marcadorDestino = Mapa.map.addMarker(new MarkerOptions().position(nueva_pos));
            Mapa.ubicacionDestino = nueva_pos;
        } else {
            try {
                Mapa.marcadorUsuario.remove();
            } catch (Exception a) {
            }
            if (Mapa.ready)
                Mapa.marcadorUsuario = Mapa.map.addMarker(new MarkerOptions().position(nueva_pos));//.icon(BitmapDescriptorFactory.fromResource(R.drawable.usuario);));
            Mapa.ubicacionUsuario = nueva_pos;
        }
    }
    //endregion

    /**
     * Evento de mantener apretado el mapa
     * @param point punto donde se clickeo
     */
    @Override
    public void onMapLongClick(LatLng point) {
        Mapa.ubicacionDestino = point;
        actualizarMarcador(Mapa.ubicacionDestino, Constants.DESTINO);
        actualizarCirculo(point, (int)Mapa.miCirculo.getRadius());
      }

}
