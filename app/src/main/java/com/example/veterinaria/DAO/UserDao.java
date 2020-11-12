package com.example.veterinaria.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.veterinaria.Conexion.Conexion;
import com.example.veterinaria.Modelo.ClsCliente;


public class UserDao {
    Conexion conex;

    public UserDao(Activity activity){
        conex = new Conexion(activity);
    }

    public boolean guardar(ClsCliente user) {
        ContentValues registro = new ContentValues();
        registro.put("cedula", user.getCedula());
        registro.put("nombres", user.getNombre());
        registro.put("apellidos", user.getApellido());
        registro.put("correo", user.getCorreo());
        registro.put("telefono", user.getTelefono());
        registro.put("usuario", user.getUsuario());
        registro.put("password", user.getContrasena());
        return conex.ejecutarInsert("user", registro);
    }

    public ClsCliente buscar(String usuario) {
        ClsCliente user= null;
        String consulta = "select nombres, apellidos, password from user where usuario ='" + usuario + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            user = new ClsCliente(temp.getString(0), temp.getString(1), temp.getString(2), temp.getString(3), temp.getString(4),usuario, temp.getString(5));
        }
        conex.cerrarConexion();
        return user;
    }
}
