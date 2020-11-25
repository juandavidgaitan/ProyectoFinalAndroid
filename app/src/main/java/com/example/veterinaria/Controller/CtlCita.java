package com.example.veterinaria.Controller;

import android.app.Activity;

import com.example.veterinaria.DAO.DaoCitas;
import com.example.veterinaria.ListaCitasActivity;
import com.example.veterinaria.Modelo.ClsCita;

public class CtlCita {
    DaoCitas dao;

    public CtlCita(Activity activity) {
    dao = new DaoCitas(activity);
    }
}
