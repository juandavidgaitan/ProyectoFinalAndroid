package com.example.veterinaria.DAO;

import android.app.Activity;

import com.example.veterinaria.Conexion.Conexion;

public class DaoCitas {
    Conexion conex;

    public DaoCitas(Activity a) {
        this.conex = new Conexion(a);
    }
}

