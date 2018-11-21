package hu.waldorf.finance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SZERZODESEK")
public class Szerzodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "TAMOGATO")
    private String tamogato;

    @Column(name = "MUKODESI_KOLTSEG_TAMOGAS")
    private int mukodesiKoltsegTamogatas;

    @Column(name = "EPITESI_HOZZAJARULAS")
    private int epitesiHozzajarulas;

    @Column(name = "CSALAD_ID")
    private long csaladId;

    @Column(name = "MUKODESI_KOLTSEG_TAMOGAS_INDULO_EGYENLEG")
    private int mukodesiKoltsegTamogatasInduloEgyenleg;

    @Column(name = "EPITESI_HOZZAJARULAS_INDULO_EGYENLEG")
    private int epitesiHozzajarulasInduloEgyenleg;

    public Szerzodes() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getCsaladId() {
        return csaladId;
    }

    public void setCsaladId(long csaladId) {
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
