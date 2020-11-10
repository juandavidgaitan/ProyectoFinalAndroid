package com.example.veterinaria.Modelo;


public class ClsVeterinario {


    private String cedula;
    private String nombre;
    private String apellido;
    private String edad;
    private String correo;
    private String salario;
    private String veterinaria_fk;

    public ClsVeterinario(String cedula, String nombre, String apellido, String edad, String correo, String salario, String veterinaria_fk) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.correo = correo;
        this.salario = salario;
        this.veterinaria_fk = veterinaria_fk;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getVeterinaria_fk() {
        return veterinaria_fk;
    }

    public void setVeterinaria_fk(String veterinaria_fk) {
        this.veterinaria_fk = veterinaria_fk;
    }

    public ClsVeterinario() {
    }
}