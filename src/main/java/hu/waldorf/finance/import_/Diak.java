package hu.waldorf.finance.import_;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DIAKOK")
public class Diak {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "NEV")
    private String nev;

    @Column(name = "OSZTALY")
    private String osztaly;

    public Diak() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
