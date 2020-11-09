package com.example.veterinaria.Modelo;

import java.io.Serializable;

public class ClsLugar implements Serializable {
    private int id;
    private String nombre;
    private String descripcion;
    private String color;
    private String longitud;
    private String latitud;
    private int idUsuario;

    public ClsLugar(int id, String nombre, String descripcion, String color, String longitud, String latitud, int idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.color = color;
        this.longitud = longitud;
        this.latitud = latitud;
        this.idUsuario = idUsuario;
    }

    public ClsLugar() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
