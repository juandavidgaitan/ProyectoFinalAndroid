package com.example.veterinaria.Conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexion extends SQLiteOpenHelper {

    private static final String database = "ubicacion.db";
    private static final SQLiteDatabase.CursorFactory factory = null;
    private static final int version = 2;
    SQLiteDatabase bd;

    public Conexion(Context context, String name,
                    SQLiteDatabase.CursorFactory factory,
                    int version) {
        super(context, name, factory, version);
    }

    public Conexion(Context context) {
        super(context, database, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        // el idUsuario en la tabla ubicacion estaba como tipo text, lo pasé a number pensando que
        // tal vez más adelante podría mostrar un error.
        db.execSQL("create table ubicacion(" +
                "id number primary key," +
                "nombre text," +
                "descripcion text," +
                "color text," +
                "latitud text," +
                "longitud text," +
                "idUsuario number references usuario on delete cascade)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists ubicacion");
        onCreate(db);
    }

    public void cerrarConexion() {
        bd.close();
    }

    public boolean ejecutarInsert(String tabla, ContentValues registro) {
        try {
            bd = this.getWritableDatabase();
            int res = (int) bd.insert(tabla, null, registro);
            cerrarConexion();

            if (res != -1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public boolean ejecutarDelete(String tabla, String condicion) {
        bd = this.getWritableDatabase();

        int cant = bd.delete(tabla, condicion, null);
        cerrarConexion();

        if (cant >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ejecutarUpdate(String tabla, String condicion,
                                  ContentValues registro) {
        try {
            bd = this.getWritableDatabase();

            int cant = bd.update(tabla, registro, condicion, null);
            cerrarConexion();

            if (cant == 1) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public Cursor ejecutarSearch(String consulta) {
        try {
            bd = this.getWritableDatabase();

            Cursor fila = bd.rawQuery(consulta, null);
            return fila;

        } catch (Exception e) {
            cerrarConexion();
            return null;
        }
    }
}
