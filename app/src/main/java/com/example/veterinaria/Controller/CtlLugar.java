package com.example.veterinaria.Controller;

import android.app.Activity;


import com.example.veterinaria.DAO.DaoUbicacion;
import com.example.veterinaria.Modelo.ClsLugar;

import java.io.Serializable;
import java.util.List;


public class CtlLugar implements Serializable {
    DaoUbicacion daoUbicacion;

    public CtlLugar(Activity a) {
        this.daoUbicacion = new DaoUbicacion(a);
    }

    public boolean guardar(ClsLugar u) {
        return this.daoUbicacion.guardar(u);
    }

    public ClsLugar buscarUbicacionPorNombre(String nombre) {
        return this.daoUbicacion.buscarPorNombre(nombre);
    }

    public ClsLugar buscarPorId(int id) {
        return this.daoUbicacion.buscarPorId(id);
    }

    public List<ClsLugar> listarUbicacionPorUsuario(int idUsuario) {
        return this.daoUbicacion.listarUbicacionesPorUsuario(idUsuario);
    }

    public boolean modificar(ClsLugar u) {
        return this.daoUbicacion.modificar(u);
    }

    public boolean eliminar(int id) {
        return this.daoUbicacion.eliminar(id);
    }

    public boolean guardarMarca(String nombre, String descripciones) {

        return false;
    }

    public ClsLugar getLugar() {

        return null;
    }
}
