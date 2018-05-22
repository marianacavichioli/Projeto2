package com.mobile2.projeto2.entity;

import java.io.Serializable;

public class Atividade implements Serializable {

    // Em breve serão adicionados mais atributos e o adapter será mais trabalhado
    private String nomeAtv;
    private String descAtv;
    private String tag1;
    private String tag2;
    private String tag3;

    public String getNomeAtv() {
        return nomeAtv;
    }
    public String getDescAtv() {
        return descAtv;
    }
    public String getTag1() {
        return tag1;
    }
    public String getTag2() {
        return tag2;
    }
    public String getTag3() {
        return tag3;
    }

    public void setNomeAtv(String nomeAtv) {
        this.nomeAtv = nomeAtv;
    }
    public void setDescAtv(String descAtv) {
        this.descAtv = descAtv;
    }
    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }
    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }
    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public Atividade(){}
}
