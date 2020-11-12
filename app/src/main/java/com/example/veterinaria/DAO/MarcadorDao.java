package com.example.veterinaria.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;


import com.example.veterinaria.Conexion.Conexion;
import com.example.veterinaria.Modelo.Marcador;

import java.util.ArrayList;
import java.util.List;

public class MarcadorDao {

    Conexion conex;

    public MarcadorDao(Activity activity){
        conex = new Conexion(activity);
    }

    public boolean guardar(Marcador marcador) {
        ContentValues registro = new ContentValues();
        registro.put("nombre", marcador.getNombre());
        registro.put("descripcion", marcador.getDescripcion());
        registro.put("color", marcador.getColor());
        registro.put("latitud", marcador.getLatitud());
        registro.put("longitud", marcador.getLongitud());
        registro.put("usuario", marcador.getUsuario());
        return conex.ejecutarInsert("punto", registro);
    }

    public Marcador buscar(Marcador marcador) {
        Marcador p= null;
        String consulta = "select descripcion, color, latitud, longitud from punto where nombre = '" + marcador.getNombre()+ "' and usuario ='"+ marcador.getUsuario()+"'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            p =  new Marcador(marcador.getNombre(), temp.getString(0), temp.getString(1), temp.getDouble(2), temp.getDouble(3), marcador.getUsuario());
        }
        conex.cerrarConexion();
        return p;
    }

    public boolean eliminar(Marcador marcador) {
        String condicion = "nombre='" + marcador.getNombre() +"' and usuario = '"+ marcador.getUsuario()+"'";
        return conex.ejecutarDelete( "punto", condicion);
    }

    public boolean modificar(Marcador marcador) {
        String condicion = "nombre='" + marcador.getNombre() +"' and usuario = '"+ marcador.getUsuario()+"'";
        ContentValues registro = new ContentValues();
        registro.put("nombre", marcador.getNombre());
        registro.put("descripcion", marcador.getDescripcion());
        registro.put("color", marcador.getColor());
        registro.put("latitud", marcador.getLatitud());
        registro.put("longitud", marcador.getLongitud());
        return conex.ejecutarUpdate("punto", condicion, registro);
    }

    public List<Marcador> listarPuntosUsuario(String usuario) {
        List<Marcador> lista = new ArrayList();
        String consulta = "select descripcion, color, latitud, longitud, nombre from punto where usuario = '" + usuario+ "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.moveToFirst()) {
            do {
                Marcador marcador =  new Marcador(temp.getString(4), temp.getString(0), temp.getString(1), temp.getDouble(2), temp.getDouble(3), usuario);
                lista.add(marcador);
            } while (temp.moveToNext());
        }
        return lista;
    }
}
