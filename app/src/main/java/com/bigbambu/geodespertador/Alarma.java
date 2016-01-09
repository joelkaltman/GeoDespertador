package com.bigbambu.geodespertador;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sebas on 06/01/2016.
 */
public class Alarma{
    String nombre;
    String longitud;
    String latitud;
    String distancia;
    boolean sonando;
    char activa;

    public Alarma(String nombre, String latitud, String longitud, String distancia, String activar){
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
        this.distancia = distancia;
        this.activa = activar.charAt(0);
    }

    public void estaSonando(LatLng ubicacion){
        if (activa == 's') {
            Location ubicacionAlarma = new Location("");
            ubicacionAlarma.setLatitude(Double.parseDouble(this.latitud));
            ubicacionAlarma.setLongitude(Double.parseDouble(this.longitud));
            Location ubicacionDestino = new Location("");
            ubicacionDestino.setLatitude(ubicacion.latitude);
            ubicacionDestino.setLongitude(ubicacion.longitude);
            int radio = Integer.parseInt(distancia);
            sonando = false;
            if (ubicacionDestino.distanceTo(ubicacionAlarma) < radio && !sonando) {
                sonando = true;
            } else if (ubicacionDestino.distanceTo(ubicacionAlarma) > radio) {
                sonando = false;
            }
        }
    }
}