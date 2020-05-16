package com.inacap.smartdrunkapp.dto;

import java.io.Serializable;

public class MesaDto implements Serializable {

    int codMesa;
    int codCliente;
    int st;

    public MesaDto() {
    }

    public MesaDto(int codMesa, int codCliente, int st) {
        this.codMesa = codMesa;
        this.codCliente = codCliente;
        this.st = st;
    }

    public int getCodMesa() {
        return codMesa;
    }

    public void setCodMesa(int codMesa) {
        this.codMesa = codMesa;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }
}
