package com.example.veterinaria.Modelo;


public class ClsVeterinaria {


    private String nit_veterinaria;
    private String nombre;
    private String direccion;
    private String telefono;
    private String usuario;
    private String contrasena;

    public ClsVeterinaria(String nit_veterinaria, String nombre, String direccion, String telefono, String usuario, String contrasena) {
        this.nit_veterinaria = nit_veterinaria;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public ClsVeterinaria() {
    }

    public String getNit_veterinaria() {
        return nit_veterinaria;
    }

    public void setNit_veterinaria(String nit_veterinaria) {
        this.nit_veterinaria = nit_veterinaria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}