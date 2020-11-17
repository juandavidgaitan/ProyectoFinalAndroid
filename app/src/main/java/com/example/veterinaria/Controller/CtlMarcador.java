package com.example.veterinaria.Controller;

import android.app.Activity;


import com.example.veterinaria.DAO.MarcadorDao;
import com.example.veterinaria.Modelo.Marcador;

import java.util.List;

public class CtlMarcador {

    MarcadorDao dao;

    public static Marcador marcador;

    public CtlMarcador(Activity activity) {
        dao = new MarcadorDao(activity);
    }

    public static Marcador getMarcador(){
        return marcador;
    }
    public static void setMarcador(Marcador marcador) {
        CtlMarcador.marcador = marcador;
    }

    public boolean registrar(Marcador marcador) throws Exception {
        if (marcador == null) {
            throw new Exception("los datos se encuentran incompletos");
        }else {
            Marcador existe = null;
            try {
                existe = dao.buscar(marcador);
            }catch (Exception e) {
            }
            if (existe == null){
                dao.guardar(marcador);
                return true;
            }else {
                return false;
            }
        }
    }

    /*public void eliminar(Marcador marcador) throws Exception {
        if (dao.eliminar(marcador)) {
            throw new Exception("se elimino con exito");
        }else{
            throw new Exception("el marcador que intenta elimnar no existe");
        }
    }

    public void actualizar(Marcador marcador) throws Exception {
        if (marcador == null) {
            throw new Exception("los datos se encuentran incompletos");
        }else {
            if (dao.modificar(marcador)) {
                throw new Exception("se actualizo con exito");
            }else{
                throw new Exception("");
            }
        }
    }*/



    public Marcador buscar(Marcador marcador) {
        return dao.buscar(marcador);
    }

    public List<Marcador> listarPuntosUsuario() {
        return dao.listarPuntosUsuario();
    }

    public List<String> listarPuntosUsuarioString(){return  dao.listarPuntosUsuarioString();}
}
