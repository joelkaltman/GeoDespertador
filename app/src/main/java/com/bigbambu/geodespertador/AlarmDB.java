package com.bigbambu.geodespertador;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sebas on 05/12/2015.
 */





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