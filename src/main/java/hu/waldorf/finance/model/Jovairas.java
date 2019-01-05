package hu.waldorf.finance.model;

import java.util.Date;

public class Jovairas {
    private Integer id;
    private int szerzodesId;
    private String megnevezes;
    private TetelTipus tipus;
    private int osszeg;
    private Integer befizetesId;
    private Date konyvelesiNap;

    public Jovairas() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSzerzodesId() {
        return szerzodesId;
    }

    public void setSzerzodesId(int szerzodesId) {
        this.szerzodesId = szerzodesId;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public void setMegnevezes(String megnevezes) {
        this.megnevezes = megnevezes;
    }

    public int getOsszeg() {
        return osszeg;
    }

    public void setOsszeg(int osszeg) {
        this.osszeg = osszeg;
    }

    public Integer getBefizetesId() {
        return befizetesId;
    }

    public void setBefizetesId(Integer befizetesId) {
        this.befizetesId = befizetesId;
    }

    public Date getKonyvelesiNap() {
        return konyvelesiNap;
    }

    public void setKonyvelesiNap(Date konyvelesiNap) {
        this.konyvelesiNap = konyvelesiNap;
    }

    public TetelTipus getTipus() {
        return tipus;
    }

    public void setTipus(TetelTipus tipus) {
        this.tipus = tipus;
    }
}
