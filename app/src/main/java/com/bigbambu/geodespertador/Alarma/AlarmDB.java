package com.bigbambu.geodespertador.Alarma;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bigbambu.geodespertador.Constants.Constants;
import com.bigbambu.geodespertador.Excepciones.ExisteAlarmaException;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sebas on 05/12/2015.
 */

public class AlarmDB implements Serializable{

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
        abrir();
    }
    //endregion

    //region METODOS PUBLICOS
    //region TODAS LAS ALARMAS
    /**
     * Devuelve la lista de todas las alarmas en la base de datos
     */
    public ArrayList<Alarma> obtenerTodasAlarmas(){
        int cant_alarmas = cantidadAlarmas();
        ArrayList<Alarma> alarmas = new ArrayList<Alarma>();
        Cursor cursor = db.rawQuery(Constants.SELECTALL, null);
        int i = 0;
        cursor.moveToFirst();
        while (i < cant_alarmas) {
            alarmas.add(casteoString(cursor));
            cursor.moveToNext();
            i++;
        }
        cursor.close();
        return alarmas;
    }
    //endregion

    //region CANTIDAD ALARMAS
    /**
     * Retorna la cantidad de alarmas en la base de datos
     */
    public int cantidadAlarmas(){
        int retornar = 0;
        Cursor c = db.rawQuery(Constants.COUNT, null);
        if (c.moveToFirst()) {
            retornar = c.getInt(0);
        }
        c.close();
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
        Cursor c = db.rawQuery(cantidadDeAlarmasNombre(alarma.getNombre()), null);
        if (c.moveToFirst()) {
            if (c.getInt(0) > 0) {
                throw new ExisteAlarmaException("Existe una alarma con el mismo nombre");
            }
            else {
                String sql = Constants.INSERTAR + "('"
                        + alarma.getNombre() + "','"
                        + dobleAstring(alarma.getLatLong().latitude) + "','"
                        + dobleAstring(alarma.getLatLong().longitude) + "','"
                        + String.valueOf(alarma.getDistancia()) + "','"
                        + String.valueOf(Constants.ACTIVADA) + "')";
                db.execSQL(sql);
            }
        }
        c.close();
    }
    //endregion

    //region BORRAR ALARMAS
    /**
     * Borra la Alarma segun nombre recibido por parametro
     * @param nombre El nombre de la alarma que se va a borrar
     */
    public void borrarAlarma(String nombre) {
        String sql = armarDelete(nombre);
        db.execSQL(sql);
    }

    /**
     * Borra la Alarma recibida por parametro
     * @param alarma La alarma que se va a borrar
     */
    public void borrarAlarma(Alarma alarma){
        String sql = armarDelete(alarma.getNombre());
        db.execSQL(sql);
    }
    //endregion

    //region MODIFICAR ALARMAS
    /**
     * Modifica el campo "activa" de la alarma
     * @param alarma La alarma que se va a modificar
     */

    public void desactivarAlarma(Alarma alarma){
        String sql = Constants.DESACTIVAR + alarma.getNombre() + "'";
        db.execSQL(sql);
    }

    public void activarAlarma(Alarma alarma){
        String sql = Constants.ACTIVAR + alarma.getNombre() + "'";
        db.execSQL(sql);
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
        return Constants.DELETE + nombre + "'";
    }

    /**
     * Arma la query para ver cuantas alarmas hay con ese nombre
     * @param nombre Nombre de la alarma que forma el query
     */
    private String cantidadDeAlarmasNombre(String nombre){
        return Constants.COUNT + " where nombre = '"+ nombre + "'";
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
        LatLng latlong = new LatLng(latitud,longitud);
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

    private String dobleAstring(Double dato){
        return String.valueOf(dato);
    }
    //endregion
    //endregion
}