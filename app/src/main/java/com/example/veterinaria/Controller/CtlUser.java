package com.example.veterinaria.Controller;

import android.app.Activity;
import android.widget.EditText;

import com.example.veterinaria.DAO.UserDao;
import com.example.veterinaria.Modelo.ClsCliente;

public class CtlUser {
    UserDao dao;

    public CtlUser(Activity activity) {
        dao = new UserDao(activity);
    }



    public boolean registrar(ClsCliente user) throws Exception {
        if (user == null) {
            throw new Exception("el usuario se encuentra vacio");
        }else {
            ClsCliente existe = null;
            try {
                existe = dao.buscar(user.getUsuario());
            }catch (Exception e) {
            }

            if (existe == null){
                dao.guardar(user);
                return true;
            }else {
                return false;
            }
        }
    }

    public ClsCliente buscar(String usuario){
        return dao.buscar(usuario);
    }
}
