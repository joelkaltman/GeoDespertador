package com.bigbambu.geodespertador.Alarma;

import android.location.Location;

import com.bigbambu.geodespertador.Layouts.PrincipalActivity;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sebas on 06/01/2016.
 */
public class Alarma{


    private String nombre;
    private LatLng ubicacion;
    private int distancia;
    private char activa;

    public Alarma(String nombre, LatLng latlong, int distancia, char activar){
        this.nombre = nombre;
        this.ubicacion = latlong;
        this.distancia = distancia;
        this.activa = activar;
    }

    //region GETERS
    public String getNombre(){return this.nombre;}
    public LatLng getLatLong(){ return this.ubicacion;}
    public int getDistancia(){return this.distancia;}
    public char getActiva(){return this.activa;}
    //endregion

    public void switchear() {
        AlarmDB db = new AlarmDB(PrincipalActivity.contexto);
        if (this.activa == 's') {
            this.activa = 'n';
            db.desactivarAlarma(this);
        } else {
            this.activa = 's';
            db.activarAlarma(this);
        }
    }

    /**
     * Modificara el valor "sonando" de la alarma
     * en caso de que este la ubicacion pasada por paramentro
     * este dentro del rango que contiene la alarma
     * @param destino la ubicacion a verificar si esta en el rango de la alarma
     */
    public boolean estaEnRango(LatLng destino){
        Location ubicacionAlarma = new Location("");
        ubicacionAlarma.setLatitude(this.ubicacion.latitude);
        ubicacionAlarma.setLongitude(this.ubicacion.longitude);
        Location ubicacionDestino = new Location("");
        ubicacionDestino.setLatitude(destino.latitude);
        ubicacionDestino.setLongitude(destino.longitude);
        if (ubicacionDestino.distanceTo(ubicacionAlarma) < this.distancia) {
            return true;
        }
        return false;
    }
}