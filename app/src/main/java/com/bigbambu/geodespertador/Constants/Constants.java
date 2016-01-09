package com.bigbambu.geodespertador.Constants;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Sebas on 09/01/2016.
 */
public class Constants {

    //region PARAMETROS AJUSTABLES
    public static final int MINDISTANCE = 100;
    public static final LatLng BSAS = new LatLng(-34.6229419,-58.4491101);
    public static final Float ZOOMALARMA = 14f;
    public static final long TIEMPOLOCALIZAR = 1000 * 30 * 1;   //30 segundos
    public static final long DISTANCIALOCALIZAR = 50;      // 50 meteros
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
    public static final String INSERTAR = "INSERT INTO Alarmas (nombre,latitud,longitud,distancia,activa) VALUES";
    public static final String SELECTALL = "SELECT nombre, latitud, longitud, distancia, activa FROM Alarmas";
    public static final String COUNT = "SELECT count(1) FROM Alarmas";
    public static final String DELETE = "DELETE FROM Alarmas WHERE nombre='";

    //Acciones SettingsAlarma
    public static final String MODIFICAR = "Modificar";
    public static final String NUEVO = "Nuevo";

    //endregion

}
