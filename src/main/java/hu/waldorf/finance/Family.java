package hu.waldorf.finance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FAMILY")
public class Family {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "SIGNEE")
    private String signee;

    public Family() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSignee() {
        return signee;
    }

    public void setSignee(String signee) {
        this.signee = signee;
    }
}
