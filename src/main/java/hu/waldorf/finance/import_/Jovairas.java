package hu.waldorf.finance.import_;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "JOVAIRAS")
public class Jovairas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "SZERZODES_ID")
    private long szerzodesId;

    @Column(name = "MEGNEVEZES")
    private String megnevezes;

    @Column(name = "tipus")
    @Enumerated(EnumType.STRING)
    private TetelTipus tipus;

    @Column(name = "OSSZEG")
    private int osszeg;

    @Column(name = "BEFIZETES_ID")
    private long befizetesId;

    @Column(name = "KONYVELESI_NAP")
    @Temporal(TemporalType.DATE)
    private Date konyvelesiNap;

    public Jovairas() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSzerzodesId() {
        return szerzodesId;
    }

    public void setSzerzodesId(long szerzodesId) {
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

    public long getBefizetesId() {
        return befizetesId;
    }

    public void setBefizetesId(long befizetesId) {
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
