package hu.waldorf.finance.model;

public class Diak {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
    private int id;

//    @Column(name = "NEV")
    private String nev;

//    @Column(name = "OSZTALY")
    private String osztaly;

//    @Column(name = "CSALAD_ID")
    private int csaladId;

    public Diak() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
