package hu.waldorf.finance.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Date;

public class Befizetes {
    private Integer id;
    private String importForras;
    private Date importIdopont;
    private Date konyvelesiNap;
    private String befizetoNev;
    private String befizetoSzamlaszam;
    private int osszeg;
    private String kozlemeny;
    private FeldolgozasStatusza statusz;

    public Befizetes() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
