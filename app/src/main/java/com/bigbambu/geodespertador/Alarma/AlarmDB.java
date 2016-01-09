package com.bigbambu.geodespertador.Alarma;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bigbambu.geodespertador.Excepciones.ExisteAlarmaException;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebas on 05/12/2015.
 */

public class AlarmDB implements Serializable{

    //Consultas prearmadas
    public static final String INSERTAR = "INSERT INTO Alarmas (nombre,longitud,latitud,distancia,activa) VALUES";
    public static final String SELECTALL = "SELECT nombre, latitud, longitud, distancia, activa FROM Alarmas";
    public static final String COUNT = "SELECT count(1) FROM Alarmas";
    public static final String DELETE = "DELETE FROM Alarmas WHERE nombre='";

    //Variables utilizadas
    conexionBase conexion;
    SQLiteDatabase db;
    Context context;

    //region CONSTRUCTORES
    /**
     * Creacion de una AlarmaDB.
     */
    public AlarmDB(Context contexto) {
        context = contexto;
        conexion = new conexionBase(contexto, "Alarmas", null, 1);
    }
    //endregion

    //region METODOS PUBLICOS
    //region TODAS LAS ALARMAS
    /**
     * Devuelve la lista de todas las alarmas en la base de datos
     */
    public List<Alarma> obtenerTodasAlarmas(){
        abrir();
        int cant_alarmas = cantidadAlarmas();
        List<Alarma> alarmas = new ArrayList<Alarma>();
        Cursor cursor = db.rawQuery(AlarmDB.SELECTALL, null);
        int i = 0;
        cursor.moveToFirst();
        while (i < cant_alarmas) {
            alarmas.add(casteoString(cursor));
            cursor.moveToNext();
            i++;
        }
        cursor.close();
        cerrar();
        return alarmas;
    }
    //endregion

    //region CANTIDAD ALARMAS
    /**
     * Retorna la cantidad de alarmas en la base de datos
     */
    public int cantidadAlarmas(){
        abrir();
        int retornar = 0;
        Cursor c = db.rawQuery(AlarmDB.COUNT, null);
        if (c.moveToFirst()) {
            retornar = c.getInt(0);
        }
        c.close();
        cerrar();
        return retornar;
    }
    //endregion

    //region INSERTAR
    /**
     * Guarda en la base de datos la alarma enviada
     *
     * @param alarma la alarma que se va a insertar
     *
     * @throws ExisteAlarmaException En caso de que el nombre de la alarma ya exista
     */
    public void insertarAlarma(Alarma alarma) throws ExisteAlarmaException{
        abrir();
        Cursor c = db.rawQuery(cantidadDeAlarmasNombre(alarma.getNombre()), null);
        if (c.moveToFirst()) {
            if (c.getInt(0) > 0) {
                throw new ExisteAlarmaException("Existe una alarma con el mismo nombre");
            }
            else {
                String sql = AlarmDB.INSERTAR + "('"
                        + alarma.getNombre() + "','"
                        + alarma.getLatLong().longitude + "','"
                        + alarma.getLatLong().latitude + "','"
                        + String.valueOf(alarma.getDistancia()) + "',' +"
                        + Alarma.ACTIVADA + "')";
                db.execSQL(sql);
            }
        }
        c.close();
        cerrar();
    }
    //endregion

    //region BORRAR ALARMAS
    /**
     * Borra la Alarma segun nombre recibido por parametro
     * @param nombre El nombre de la alarma que se va a borrar
     */
    public void borrarAlarma(String nombre){
        abrir();
        String sql = armarDelete(nombre);
        db.execSQL(sql);
        cerrar();
    }

    /**
     * Borra la Alarma recibida por parametro
     * @param alarma La alarma que se va a borrar
     */
    public void borrarAlarma(Alarma alarma){
        abrir();
        String sql = armarDelete(alarma.getNombre());
        db.execSQL(sql);
        cerrar();
    }
    //endregion
    //endregion

    //region METODOS PRIVADOS
    //region ABRIR Y CERRAR DB
    private void abrir(){
        db = conexion.getWritableDatabase();
    }

    private void cerrar(){
        db.close();
    }
    //endregion

    //region ARMADO DE QUERYS
    /**
     * Arma la query delete
     * @param nombre Nombre de la alarma que forma el query
     */
    private String armarDelete(String nombre){
        return AlarmDB.DELETE + nombre + "'";
    }

    /**
     * Arma la query para ver cuantas alarmas hay con ese nombre
     * @param nombre Nombre de la alarma que forma el query
     */
    private String cantidadDeAlarmasNombre(String nombre){
        return AlarmDB.COUNT + " where nombre = '"+ nombre + "'";
    }

    //endregion

    //region CASTEO DE DATOS
    /**
     * Devuelve un verctor de 5 strings con los datos de la alarma
     *
     * @param cursor alarma que va a pasarse a Strings
     */
    private Alarma casteoString(Cursor cursor){
        String nombre = cursor.getString(0);
        Double latitud = Double.parseDouble(cursor.getString(1));
        Double longitud = Double.parseDouble(cursor.getString(2));
        LatLng latlong = new LatLng(latitud,latitud);
        int distancia = Integer.parseInt(cursor.getString(3));
        char activa = cursor.getString(4).charAt(0);
        return new Alarma(nombre,latlong,distancia,activa);
    }

    /**
     * Devuelve un verctor de 5 strings con los datos de la alarma
     *
     * @param alarma alarma que va a pasarse a Strings
     */
    private String[] casteoAlarma(Alarma alarma){
        String[] datos = new String[5];
        datos[0] = alarma.getNombre();
        datos[1] = String.valueOf(alarma.getLatLong().latitude);
        datos[2] = String.valueOf(alarma.getLatLong().longitude);
        datos[3] = String.valueOf(alarma.getDistancia());
        datos[4] = String.valueOf(alarma.getActiva());
        return datos;
    }
    //endregion
    //endregion
}