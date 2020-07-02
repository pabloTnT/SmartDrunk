package com.inacap.smartdrunkapp.dto;

import java.io.Serializable;

public class ProductoDto implements Serializable {

    int codProducto;
    String nombre;
    int tipoProd;
    int precio;
    int st;

    public ProductoDto() {
    }

    public ProductoDto(int codProducto, String nombre, int tipoProd, int precio, int st) {
        this.codProducto = codProducto;
        this.nombre = nombre;
        this.tipoProd = tipoProd;
        this.precio = precio;
        this.st = st;
    }

    public int getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipoProd() {
        return tipoProd;
    }

    public void setTipoProd(int tipoProd) {
        this.tipoProd = tipoProd;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    @Override
    public String toString(){
        return nombre + "    "+precio;
    }
}
