package com.example.veterinaria.Modelo;

public class ClsServico {
    private String nombre;
    private String descripcion;
    private String veterinaria_fk;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVeterinaria_fk() {
        return veterinaria_fk;
    }

    public void setVeterinaria_fk(String veterinaria_fk) {
        this.veterinaria_fk = veterinaria_fk;
    }

    public ClsServico(String nombre, String descripcion, String veterinaria_fk) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.veterinaria_fk = veterinaria_fk;
    }

    public ClsServico() {
    }
}
