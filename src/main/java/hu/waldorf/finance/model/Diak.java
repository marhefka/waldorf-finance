package hu.waldorf.finance.model;

public class Diak {
    private Integer id;
    private String nev;
    private String osztaly;
    private int csaladId;

    public Diak() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getOsztaly() {
        return osztaly;
    }

    public void setOsztaly(String osztaly) {
        this.osztaly = osztaly;
    }

    public int getCsaladId() {
        return csaladId;
    }

    public void setCsaladId(int csaladId) {
        this.csaladId = csaladId;
    }
}
