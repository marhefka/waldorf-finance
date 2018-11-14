package hu.waldorf.finance.import_;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CSALADOK")
public class Csalad {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    public Csalad() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
