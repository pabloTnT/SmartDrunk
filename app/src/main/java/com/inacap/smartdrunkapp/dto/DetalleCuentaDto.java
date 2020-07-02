package com.inacap.smartdrunkapp.dto;

import java.io.Serializable;

public class DetalleCuentaDto implements Serializable {

    int trx;
    int mesa;
    int producto;
    int cantidadProd;
    int st;

    public DetalleCuentaDto() {
    }

    public DetalleCuentaDto(int trx, int mesa, int producto, int cantidadProd, int st) {
        this.trx = trx;
        this.mesa = mesa;
        this.producto = producto;
        this.cantidadProd = cantidadProd;
        this.st = st;
    }

    public int getTrx() {
        return trx;
    }

    public void setTrx(int trx) {
        this.trx = trx;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public int getCantidadProd() {
        return cantidadProd;
    }

    public void setCantidadProd(int cantidadProd) {
        this.cantidadProd = cantidadProd;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

}
