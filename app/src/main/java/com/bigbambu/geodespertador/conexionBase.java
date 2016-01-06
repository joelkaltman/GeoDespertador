package com.bigbambu.geodespertador;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sebas on 06/01/2016.
 */
class conexionBase extends SQLiteOpenHelper {
    String sqlCreate = "CREATE TABLE IF NOT EXISTS Alarmas (nombre TEXT, longitud TEXT, latitud TEXT, distancia TEXT)";

    public conexionBase(Context contexto, String nombre,SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {

    }
}