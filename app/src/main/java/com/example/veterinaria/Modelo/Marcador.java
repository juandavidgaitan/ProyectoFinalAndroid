package com.example.veterinaria.Modelo;

import java.io.Serializable;

public class Marcador implements Serializable {
    private String nombre;
    private String descripcion;
    private String color;
    private double latitud;
    private double longitud;
    private String usuario;

    public Marcador() {
    }

    public Marcador(String nombre, String usuario) {
        this.nombre = nombre;
        this.usuario = usuario;
    }

    public Marcador(String nombre, String descripcion, String color, double latitud, double longitud, String usuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.color = color;
        this.latitud = latitud;
        this.longitud = longitud;
        this.usuario = usuario;
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

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Marcador{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", color='" + color + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
