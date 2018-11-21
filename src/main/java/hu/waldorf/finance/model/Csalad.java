package hu.waldorf.finance.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CSALADOK")
public class Csalad {
    @Id
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
