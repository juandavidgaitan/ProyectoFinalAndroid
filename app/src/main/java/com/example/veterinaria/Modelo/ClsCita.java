package com.example.veterinaria.Modelo;

public class ClsCita {
    private String id_cita;
    private String veterinaria_fk;
    private String mascota_fk;
    private String hora;
    private String descripcion;

    public ClsCita() {
    }

    public ClsCita(String id_cita, String veterinaria_fk, String mascota_fk, String hora, String descripcion) {
        this.id_cita = id_cita;
        this.veterinaria_fk = veterinaria_fk;
        this.mascota_fk = mascota_fk;
        this.hora = hora;
        this.descripcion = descripcion;
    }

    public String getId_cita() {
        return id_cita;
    }

    public void setId_cita(String id_cita) {
        this.id_cita = id_cita;
    }

    public String getVeterinaria_fk() {
        return veterinaria_fk;
    }

    public void setVeterinaria_fk(String veterinaria_fk) {
        this.veterinaria_fk = veterinaria_fk;
    }

    public String getMascota_fk() {
        return mascota_fk;
    }

    public void setMascota_fk(String mascota_fk) {
        this.mascota_fk = mascota_fk;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}