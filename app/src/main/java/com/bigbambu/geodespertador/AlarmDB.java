package com.bigbambu.geodespertador;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Sebas on 05/12/2015.
 */

class Alarma{
    int id;
    String no_hombre;
    String LONGaniza;
    String LATido;
    String puta_distancia;

    public Alarma(int id, String nombre, String longitud, String latitud, String distancia){
        this.id = id;
        this.no_hombre = nombre;
        this.LONGaniza = longitud;
        this.LATido = latitud;
        this.puta_distancia = distancia;
    }

}

class conexionBase extends  SQLiteOpenHelper{
    String sqlCreate = "CREATE TABLE IF NOT EXISTS Halarmas (aidi INTEGER, no_hombre TEXT, LONGaniza TEXT, LATido TEXT, puta_distancia TEXT)";

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

    private conexionBase conesion;
    SQLiteDatabase db;
    Context context;

    public AlarmDB(Context contexto, String nombre){
        this.context = contexto;
        this.conesion = new conexionBase(contexto, nombre, null, 1);
        this.db = conesion.getWritableDatabase();
    }

    public Alarma[] DameTodasLasAlarmasOTeQuemo(){
        int halarmas;
        Alarma[] alarmas;
        halarmas = cantAlarms();
        alarmas = new Alarma[halarmas];
        Cursor c = db.rawQuery("SELECT aidi, no_hombre, LONGaniza, LATido, puta_distancia FROM Halarma", null);
        int i = 0;
        c.moveToFirst();
        while (i < halarmas) {
            Alarma larma = new Alarma(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
            alarmas[i] = larma;
            c.moveToNext();
            i++;
        }
        db.close();
        return alarmas;
    }

    public int cantAlarms(){
        int halarmas = 0;
        Cursor c = db.rawQuery("SELECT count(1) FROM Halarma", null);
        if (c.moveToFirst()) {
            halarmas = c.getInt(0);
        }
        return halarmas;
    }

    public int dameElAidiQueSigueLagarto(){
        int vomito;
        int hay = cantAlarms();
        if (hay > 0){
            Cursor c = db.rawQuery("SELECT max(aidi) FROM Halarma", null);
            if (c.moveToFirst()) {
                vomito = c.getInt(0);
            }else
                vomito = 0;
        }
        else {
            vomito = -1;
        }
        return vomito;
    }

    public Alarma buscateEsta(int aidi){
        Cursor c = db.rawQuery("SELECT no_hombre, LONGaniza, LATido, puta_distancia FROM Halarma where aidi=" + aidi, null);
        Alarma larma = new Alarma(-1, "", "", "", "");
        if (c.moveToFirst()) {
            larma = new Alarma(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
        }
        return larma;
    }

    public void InsertateWacho(Alarma wacho){
        String sql = "INSERT INTO Halarmas (aidi,no_hombre,LONGaniza,LATido, puta_distancia) VALUES (" + wacho.id + ",'" + wacho.no_hombre + "','" + wacho.LONGaniza + "','" + wacho.LATido + "','" + wacho.puta_distancia + "')";
        db.execSQL(sql);
    }

    public void InsertateWacho(String nombre, String lat, String longi, String dist){
        int prox_id = dameElAidiQueSigueLagarto();
        String sql = "INSERT INTO Halarmas (aidi,no_hombre,LONGaniza,LATido, puta_distancia) VALUES (" + prox_id + ",'" + nombre + "','" + longi + "','" + lat + "','" + dist + "')";
        db.execSQL(sql);
    }

    public void TocaDeAcaAlarmaTurra(Alarma turra){
        String sql = "DELETE FROM Halarmas WHERE id=" + turra.id;
        db.execSQL(sql);
    }

}

