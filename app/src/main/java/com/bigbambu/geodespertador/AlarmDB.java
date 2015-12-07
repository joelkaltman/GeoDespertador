package com.bigbambu.geodespertador;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebas on 05/12/2015.
 */

class Alarma{
    int id;
    String nombre;
    String longitud;
    String latitud;
    String distancia;

    public Alarma(int id, String nombre, String longitud, String latitud, String distancia){
        this.id = id;
        this.nombre = nombre;
        this.longitud = longitud;
        this.latitud = latitud;
        this.distancia = distancia;
    }

}

class conexionBase extends  SQLiteOpenHelper{
    String sqlCreate = "CREATE TABLE IF NOT EXISTS Alarmas (id INTEGER, nombre TEXT, longitud TEXT, latitud TEXT, distancia TEXT)";

    public conexionBase(Context contexto, String nombre,CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior,
                          int versionNueva) {

    }
}

public class AlarmDB {

    private conexionBase conexion;
    SQLiteDatabase db;
    Context context;
    public static AlarmDB con;

    public AlarmDB(Context contexto, String nombre){
        this.context = contexto;
        this.conexion = new conexionBase(contexto, nombre, null, 1);
        this.db = conexion.getWritableDatabase();
    }

    public List<Alarma> obtenerTodasAlarmas(){
        int cant_alarmas = cantidadAlarmas();
        List<Alarma> alarmas = new ArrayList<Alarma>();
        Cursor c = db.rawQuery("SELECT id, nombre, longitud, latitud, distancia FROM Alarmas", null);
        int i = 0;
        c.moveToFirst();
        while (i < cant_alarmas) {
            Alarma una_alarma = new Alarma(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
            alarmas.add(una_alarma);
            c.moveToNext();
            i++;
        }
        db.close();
        return alarmas;
    }

    public int cantidadAlarmas(){
        int retornar = 0;
        Cursor c = db.rawQuery("SELECT count(1) FROM Alarmas", null);
        if (c.moveToFirst()) {
            retornar = c.getInt(0);
        }
        return retornar;
    }

    public int proximoId(){
        int proximo_id;
        int hay = cantidadAlarmas();
        if (hay > 0){
            Cursor c = db.rawQuery("SELECT max(id) FROM Alarmas", null);
            if (c.moveToFirst()) {
                proximo_id = c.getInt(0);
            }else
                proximo_id = 0;
        }
        else {
            proximo_id = -1;
        }
        return proximo_id;
    }

    public Alarma obtenerAlarmaPorId(int id){
        Cursor c = db.rawQuery("SELECT nombre, longitud, latitud, distancia FROM Alarmas where id=" + id, null);
        Alarma mi_alarma = new Alarma(-1, "", "", "", "");
        if (c.moveToFirst()) {
            mi_alarma = new Alarma(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
        }
        return mi_alarma;
    }

    public void insertarAlarma(Alarma alarma){
        String sql = "INSERT INTO Alarmas (id,nombre,longitud,latitud,distancia) VALUES (" + alarma.id + ",'" + alarma.nombre + "','" + alarma.longitud + "','" + alarma.latitud + "','" + alarma.distancia + "')";
        db.execSQL(sql);
    }

    public void insertarAlarma(String nombre, String lat, String longi, String dist){
        int prox_id = proximoId();
        String sql = "INSERT INTO Alarmas (id,nombre,longitud,latitud,distancia) VALUES (" + prox_id + ",'" + nombre + "','" + longi + "','" + lat + "','" + dist + "')";
        db.execSQL(sql);
    }

    public void borrarAlarma(Alarma alarma){
        String sql = "DELETE FROM Alarmas WHERE id=" + alarma.id;
        db.execSQL(sql);
    }

    public void borrarAlarmaPorNombre(String nombre){
        String sql = "DELETE FROM Alarmas WHERE nombre=" + nombre;
        db.execSQL(sql);
    }
}

