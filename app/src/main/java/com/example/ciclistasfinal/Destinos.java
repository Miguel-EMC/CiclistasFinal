package com.example.ciclistasfinal;

public class Destinos {
    private Double latitud;
    private Double longitud;
    private String codigo;
    private String nombre;
    private String telefono;

    public Destinos() {
        //super();
    }

    public Destinos(Double latitud, Double longitud, String codigo, String nombre, String telefono) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.codigo = codigo;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setBillete(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
