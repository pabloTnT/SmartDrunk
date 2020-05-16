package com.inacap.smartdrunkapp.dto;

import java.io.Serializable;

public class TipoProductoDto implements Serializable {

    int codTipoProd;
    String nombreTipo;

    public TipoProductoDto() {
    }

    public TipoProductoDto(int codTipoProd, String nombreTipo) {
        this.codTipoProd = codTipoProd;
        this.nombreTipo = nombreTipo;
    }

    public int getCodTipoProd() {
        return codTipoProd;
    }

    public void setCodTipoProd(int codTipoProd) {
        this.codTipoProd = codTipoProd;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }
}
