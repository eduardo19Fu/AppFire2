package com.example.abyte.appfire.Modelo;

/**
 * Created by BYTE on 23/05/2019.
 */


public class Producto {
    private  String id;
    private  String nombre;
    private  String stock;


    public Producto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
