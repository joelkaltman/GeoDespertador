package com.bigbambu.geodespertador;

/**
 * Created by Sebas on 06/01/2016.
 */
class Alarma{
    String nombre;
    String longitud;
    String latitud;
    String distancia;

    public Alarma(String nombre, String latitud, String longitud, String distancia){
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
        this.distancia = distancia;
    }
}