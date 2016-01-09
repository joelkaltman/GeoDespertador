package com.bigbambu.geodespertador.Alarma;

import android.location.Location;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sebas on 06/01/2016.
 */
public class Alarma{

    public static final char ACTIVADA = 'n';
    public static final char DESACTIVADA = 's';

    private String nombre;
    private LatLng ubicacion;
    private int distancia;
    private boolean sonando;
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
    public boolean getSonando(){return this.sonando;}
    public char getActiva(){return this.activa;}
    //endregion

    /**
     * Modificara el valor "sonando" de la alarma
     * en caso de que este la ubicacion pasada por paramentro
     * este dentro del rango que contiene la alarma
     * @param destino la ubicacion a verificar si esta en el rango de la alarma
     */
    public void estaSonando(LatLng destino){
        if (activa == 's') {
            Location ubicacionAlarma = new Location("");
            ubicacionAlarma.setLatitude(this.ubicacion.latitude);
            ubicacionAlarma.setLongitude(this.ubicacion.longitude);
            Location ubicacionDestino = new Location("");
            ubicacionDestino.setLatitude(destino.latitude);
            ubicacionDestino.setLongitude(destino.longitude);
            this.sonando = false;
            if (ubicacionDestino.distanceTo(ubicacionAlarma) < this.distancia && !this.sonando) {
                this.sonando = true;
                activa = Alarma.DESACTIVADA;
            } else if (ubicacionDestino.distanceTo(ubicacionAlarma) > this.distancia) {
                this.sonando = false;
            }
        }
    }
}