package hu.waldorf.finance.import_;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
@Table(name = "BEFIZETESEK")
public class Befizetes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "IMPORT_FORRAS")
    private String importForras;

    @Column(name = "IMPORT_IDOPONT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date importIdopont;

    @Column(name = "KONYVELESI_NAP")
    @Temporal(TemporalType.DATE)
    private Date konyvelesiNap;

    @Column(name = "BEFIZETO_NEV")
    private String befizetoNev;

    @Column(name = "BEFIZETO_SZAMLASZAM")
    private String befizetoSzamlaszam;

    @Column(name = "OSSZEG")
    private int osszeg;

    @Column(name = "KOZLEMENY")
    private String kozlemeny;

    @Column(name = "STATUSZ")
    @Enumerated(EnumType.STRING)
    private FeldolgozasStatusza statusz;

    public Befizetes() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImportForras() {
        return importForras;
    }

    public void setImportForras(String importForras) {
        this.importForras = importForras;
    }

    public Date getImportIdopont() {
        return importIdopont;
    }

    public void setImportIdopont(Date importIdopont) {
        this.importIdopont = importIdopont;
    }

    public Date getKonyvelesiNap() {
        return konyvelesiNap;
    }

    public void setKonyvelesiNap(Date konyvelesiNap) {
        this.konyvelesiNap = konyvelesiNap;
    }

    public String getBefizetoNev() {
        return befizetoNev;
    }

    public void setBefizetoNev(String befizetoNev) {
        this.befizetoNev = befizetoNev;
    }

    public String getBefizetoSzamlaszam() {
        return befizetoSzamlaszam;
    }

    public void setBefizetoSzamlaszam(String befizetoSzamlaszam) {
        this.befizetoSzamlaszam = befizetoSzamlaszam;
    }

    public int getOsszeg() {
        return osszeg;
    }

    public void setOsszeg(int osszeg) {
        this.osszeg = osszeg;
    }

    public String getKozlemeny() {
        return kozlemeny;
    }

    public void setKozlemeny(String kozlemeny) {
        this.kozlemeny = kozlemeny;
    }

    public FeldolgozasStatusza getStatusz() {
        return statusz;
    }

    public void setStatusz(FeldolgozasStatusza statusz) {
        this.statusz = statusz;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("importForras", importForras)
                .append("importIdopont", importIdopont)
                .append("konyvelesiNap", konyvelesiNap)
                .append("befizetoNev", befizetoNev)
                .append("befizetoSzamlaszam", befizetoSzamlaszam)
                .append("osszeg", osszeg)
                .append("kozlemeny", kozlemeny)
                .append("statusz", statusz)
                .toString();
    }
}
