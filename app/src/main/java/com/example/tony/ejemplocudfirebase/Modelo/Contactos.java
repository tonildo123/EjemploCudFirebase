package com.example.tony.ejemplocudfirebase.Modelo;

public class Contactos {


    public String telefono;
    public String nombre;
    public String correo;

    public Contactos(String telefono, String nombre, String correo) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.correo = correo;
    }

    public Contactos() {

    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    @Override
    public String toString() {
        return  "TELEFONO : " + telefono + '\n' +
                "NOMBRE   : " + nombre   + '\n' +
                "CORREO   : " + correo   + '\n' ;
    }
}
