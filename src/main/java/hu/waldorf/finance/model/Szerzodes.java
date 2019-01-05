package hu.waldorf.finance.model;

public class Szerzodes {
    private Integer id;
    private String tamogato;
    private int mukodesiKoltsegTamogatas;
    private int epitesiHozzajarulas;
    private int csaladId;
    private int mukodesiKoltsegTamogatasInduloEgyenleg;
    private int epitesiHozzajarulasInduloEgyenleg;

    public Szerzodes() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTamogato() {
        return tamogato;
    }

    public void setTamogato(String tamogato) {
        this.tamogato = tamogato;
    }

    public int getMukodesiKoltsegTamogatas() {
        return mukodesiKoltsegTamogatas;
    }

    public void setMukodesiKoltsegTamogatas(int mukodesiKoltsegTamogatas) {
        this.mukodesiKoltsegTamogatas = mukodesiKoltsegTamogatas;
    }

    public int getEpitesiHozzajarulas() {
        return epitesiHozzajarulas;
    }

    public void setEpitesiHozzajarulas(int epitesiHozzajarulas) {
        this.epitesiHozzajarulas = epitesiHozzajarulas;
    }

    public int getCsaladId() {
        return csaladId;
    }

    public void setCsaladId(int csaladId) {
        this.csaladId = csaladId;
    }

    public int getMukodesiKoltsegTamogatasInduloEgyenleg() {
        return mukodesiKoltsegTamogatasInduloEgyenleg;
    }

    public void setMukodesiKoltsegTamogatasInduloEgyenleg(int mukodesiKoltsegTamogatasInduloEgyenleg) {
        this.mukodesiKoltsegTamogatasInduloEgyenleg = mukodesiKoltsegTamogatasInduloEgyenleg;
    }

    public int getEpitesiHozzajarulasInduloEgyenleg() {
        return epitesiHozzajarulasInduloEgyenleg;
    }

    public void setEpitesiHozzajarulasInduloEgyenleg(int epitesiHozzajarulasInduloEgyenleg) {
        this.epitesiHozzajarulasInduloEgyenleg = epitesiHozzajarulasInduloEgyenleg;
    }
}
