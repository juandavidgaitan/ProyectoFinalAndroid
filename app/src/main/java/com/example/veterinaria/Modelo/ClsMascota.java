package com.example.veterinaria.Modelo;

public class ClsMascota {
    private String nombre;
    private String edad;
    private String raza;
    private String cliente;

    public ClsMascota(String nombre, String edad, String raza, String cliente) {
        this.nombre = nombre;
        this.edad = edad;
        this.raza = raza;
        this.cliente = cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public ClsMascota() {
    }
}
