package com.mobile2.projeto2.entidades;

import java.io.Serializable;

public class Template implements Serializable{
    private String caminhoFoto;


    public String getCaminhoFoto() {
        return caminhoFoto;
    }

    public void setCaminhoFoto(String caminhoFoto) {
        this.caminhoFoto = caminhoFoto;
    }
}