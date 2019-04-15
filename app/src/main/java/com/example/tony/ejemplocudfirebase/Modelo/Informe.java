package com.example.tony.ejemplocudfirebase.Modelo;

import android.widget.ImageView;

public class Informe {

    private String nombre, descripcion;
    private ImageView foto;

    public Informe() {

    }

    public Informe(String nombre, String descripcion, ImageView foto) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
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

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return  "" + nombre + "\n" +
                "" + foto   + "\n" +
                "" + descripcion;
    }
}
