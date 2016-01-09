package com.bigbambu.geodespertador.Constants;

/**
 * Created by Sebas on 09/01/2016.
 */
public class Constants {


    //region NO PARAMETRIZABLE
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
    //endregion

}
