package com.example.veterinaria.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;


import com.example.veterinaria.Conexion.Conexion;
import com.example.veterinaria.Modelo.ClsLugar;

import java.util.ArrayList;
import java.util.List;

public class DaoUbicacion {
    Conexion conex;

    public DaoUbicacion(Activity a) {
        this.conex = new Conexion(a);
    }

    public boolean guardar(ClsLugar u) {
        ContentValues registro = new ContentValues();
        registro.put("id", generarIdUbicacion());
        registro.put("nombre", u.getNombre());
        registro.put("descripcion", u.getDescripcion());
        registro.put("color", u.getColor());
        registro.put("latitud", u.getLatitud());
        registro.put("longitud", u.getLongitud());
        registro.put("idUsuario", u.getIdUsuario());
        //int id = generarIdUbicacion();
        //registro.put("id", id);
        return conex.ejecutarInsert("ubicacion", registro);
    }

    private int generarIdUbicacion() {
        int idMax = 0;
        String consulta = "select max(id) from ubicacion";
        Cursor temp = conex.ejecutarSearch(consulta);

        if (temp.getCount() > 0) {
            temp.moveToFirst();
            idMax = temp.getInt(0);
        }
        conex.cerrarConexion();
        idMax++;
        return idMax;
    }

    public ClsLugar buscarPorId(int id) {
        ClsLugar ubicacionBuscada = null;
        String consulta = "select id, nombre, descripcion, color, latitud, longitud, idUsuario  " +
                "from ubicacion where id = " + id;
        Cursor temp = conex.ejecutarSearch(consulta);

        if (temp.getCount() > 0) {
            temp.moveToFirst();
            ubicacionBuscada = new ClsLugar();
            ubicacionBuscada.setId(id);
            ubicacionBuscada.setNombre(temp.getString(1));
            ubicacionBuscada.setDescripcion(temp.getString(2));
            ubicacionBuscada.setColor(temp.getString(3));
            ubicacionBuscada.setLatitud(temp.getString(4));
            ubicacionBuscada.setLongitud(temp.getString(5));
            ubicacionBuscada.setIdUsuario(temp.getInt(6));
        }

        conex.cerrarConexion();
        return ubicacionBuscada;
    }

    public ClsLugar buscarPorNombre(String nombre) {
        ClsLugar ubicacionBuscada = null;
        String consulta = "select id, nombre, descripcion, color, latitud, longitud, idUsuario  " +
                "from ubicacion where nombre = " + nombre;
        Cursor temp = conex.ejecutarSearch(consulta);

        if (temp.getCount() > 0) {
            temp.moveToFirst();
            ubicacionBuscada = new ClsLugar();
            ubicacionBuscada.setId(temp.getInt(0));
            ubicacionBuscada.setNombre(temp.getString(1));
            ubicacionBuscada.setDescripcion(temp.getString(2));
            ubicacionBuscada.setColor(temp.getString(3));
            ubicacionBuscada.setLatitud(temp.getString(4));
            ubicacionBuscada.setLongitud(temp.getString(5));
            ubicacionBuscada.setIdUsuario(temp.getInt(6));
        }

        conex.cerrarConexion();
        return ubicacionBuscada;
    }

    public List<ClsLugar> listarUbicacionesPorUsuario(int idUsuario) {
        List<ClsLugar> lista = new ArrayList<>();

        String consulta = "select id, nombre, descripcion, color, latitud, longitud, idUsuario from " +
                "ubicacion where idUsuario = " + idUsuario;

        Cursor temp = conex.ejecutarSearch(consulta);

        if (temp.moveToFirst()) {
            do {
                ClsLugar ubicacionBuscada = new ClsLugar();
                ubicacionBuscada.setId(temp.getInt(0));
                ubicacionBuscada.setNombre(temp.getString(1));
                ubicacionBuscada.setDescripcion(temp.getString(2));
                ubicacionBuscada.setColor(temp.getString(3));
                ubicacionBuscada.setLatitud(temp.getString(4));
                ubicacionBuscada.setLongitud(temp.getString(5));
                ubicacionBuscada.setIdUsuario(temp.getInt(6));
                lista.add(ubicacionBuscada);
            } while (temp.moveToNext());
        }
        return lista;
    }

    public boolean modificar(ClsLugar u) {
        String tabla = "ubicacion";
        String condicion = "id = " + u.getId();

        ContentValues registro = new ContentValues();

        registro.put("nombre", u.getNombre());
        registro.put("descripcion", u.getDescripcion());
        registro.put("color", u.getColor());
        registro.put("latitud", u.getLatitud());
        registro.put("longitud", u.getLongitud());

        return conex.ejecutarUpdate(tabla, condicion, registro);
    }

    public boolean eliminar(int id) {
        String tabla = "ubicacion";
        String condicion = "id = " + id;
        return conex.ejecutarDelete(tabla, condicion);
    }


}
