package hu.waldorf.finance.model;

import java.util.Date;

public class Jovairas {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
    private int id;

//    @Column(name = "SZERZODES_ID")
    private int szerzodesId;

//    @Column(name = "MEGNEVEZES")
    private String megnevezes;

//    @Column(name = "tipus")
//    @Enumerated(EnumType.STRING)
    private TetelTipus tipus;

//    @Column(name = "OSSZEG")
    private int osszeg;

//    @Column(name = "BEFIZETES_ID")
    private Integer befizetesId;

//    @Column(name = "KONYVELESI_NAP")
//    @Temporal(TemporalType.DATE)
    private Date konyvelesiNap;

    public Jovairas() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
