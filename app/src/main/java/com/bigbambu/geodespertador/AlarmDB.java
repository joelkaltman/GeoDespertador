package com.bigbambu.geodespertador;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebas on 05/12/2015.
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

class conexionBase extends  SQLiteOpenHelper{
    String sqlCreate = "CREATE TABLE IF NOT EXISTS Alarmas (nombre TEXT, longitud TEXT, latitud TEXT, distancia TEXT)";

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

public class AlarmDB implements Serializable{

    public conexionBase conexion;
    public SQLiteDatabase db;
    public Context context;


    public AlarmDB(Context contexto) {
        context = contexto;
        conexion = new conexionBase(contexto, "Alarmas", null, 1);
        db = conexion.getWritableDatabase();
    }

    public List<Alarma> obtenerTodasAlarmas(){
        int cant_alarmas = cantidadAlarmas();
        List<Alarma> alarmas = new ArrayList<Alarma>();
        Cursor c = db.rawQuery("SELECT nombre, latitud, longitud, distancia FROM Alarmas", null);
        int i = 0;
        c.moveToFirst();
        while (i < cant_alarmas) {
            Alarma una_alarma = new Alarma(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
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

    public Alarma obtenerAlarmaPorId(String nombre){
        Cursor c = db.rawQuery("SELECT nombre, latitud, longitud, distancia FROM Alarmas where nombre=" + nombre, null);
        Alarma mi_alarma = new Alarma("","","","");
        if (c.moveToFirst()) {
            mi_alarma = new Alarma(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
        }
        return mi_alarma;
    }

    public void actualizarAlarma(Alarma alarma){
        borrarAlarma(alarma);
        insertarAlarma(alarma);
    }

    public void insertarAlarma(Alarma alarma){
        Cursor c = db.rawQuery("SELECT 1 FROM Alarmas where nombre = '"+ alarma.nombre + "'", null);
        if (c.moveToFirst()) {
            Toast t = Toast.makeText(context, "Ya existe una alrma con ese nombre", Toast.LENGTH_SHORT);
        }
        else {
            String sql = "INSERT INTO Alarmas (nombre,longitud,latitud,distancia) VALUES ('" + alarma.nombre + "','" + alarma.longitud + "','" + alarma.latitud + "','" + alarma.distancia + "')";
            db.execSQL(sql);
        }
    }

    public void insertarAlarma(String nombre, String lat, String longi, String dist){
        Alarma alarma = new Alarma(nombre, lat, longi, dist);
        insertarAlarma(alarma);
    }

    public void borrarAlarma(Alarma alarma){
        String sql = "DELETE FROM Alarmas WHERE nombre='" + alarma.nombre+ "'";
        db.execSQL(sql);
    }

    public void borrarAlarma(String nombre){
        String sql = "DELETE FROM Alarmas WHERE nombre='" + nombre + "'";
        db.execSQL(sql);
    }
}