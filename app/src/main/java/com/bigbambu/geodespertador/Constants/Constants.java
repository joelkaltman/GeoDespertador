package com.bigbambu.geodespertador.Constants;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sebas on 09/01/2016.
 */
public class Constants {

    //region PARAMETROS AJUSTABLES
    public static final int MINDISTANCE = 150;
    public static final LatLng BSAS = new LatLng(-34.6229419,-58.4491101);
    //endregion


    //region NO PARAMETRIZABLE
    //Mapa usuario o no usuario
    public static final boolean DESTINO = true;
    public static final boolean USUARIO = false;

    //Constantes de la alarma
    public static final char ACTIVADA = 'n';
    public static final char DESACTIVADA = 's';
    public static final boolean SONANDO = true;
    public static final boolean SINSONAR = false;

    //Constantes de querys AlarmaDB
    public static final String INSERTAR = "INSERT INTO Alarmas (nombre,longitud,latitud,distancia,activa) VALUES";
    public static final String SELECTALL = "SELECT nombre, latitud, longitud, distancia, activa FROM Alarmas";
    public static final String COUNT = "SELECT count(1) FROM Alarmas";
    public static final String DELETE = "DELETE FROM Alarmas WHERE nombre='";

    //Acciones SettingsAlarma
    public static final String MODIFICAR = "Modificar";
    public static final String NUEVO = "Nuevo";

    //endregion

}
