package hu.waldorf.finance.screens.befizetesek_jovairasa;

import javafx.beans.property.SimpleStringProperty;

public class BefizetesJovairasaRow {
    private final SimpleStringProperty forras = new SimpleStringProperty("");
    private final SimpleStringProperty beerkezesIdopontja = new SimpleStringProperty("");
    private final SimpleStringProperty befizeto = new SimpleStringProperty("");
    private final SimpleStringProperty osszeg = new SimpleStringProperty("");

    public BefizetesJovairasaRow(String forras, String beerkezesIdopontja, String befizeto, String osszeg) {
        setForras(forras);
        setBeerkezesIdopontja(beerkezesIdopontja);
        setBefizeto(befizeto);
        setOsszeg(osszeg);
    }

    public String getForras() {
        return forras.get();
    }

    public void setForras(String forras) {
        this.forras.set(forras);
    }

    public String getBeerkezesIdopontja() {
        return beerkezesIdopontja.get();
    }

    public void setBeerkezesIdopontja(String beerkezesIdopontja) {
        this.beerkezesIdopontja.set(beerkezesIdopontja);
    }

    public String getBefizeto() {
        return befizeto.get();
    }

    public void setBefizeto(String befizeto) {
        this.befizeto.set(befizeto);
    }

    public String getOsszeg() {
        return osszeg.get();
    }

    public void setOsszeg(String osszeg) {
        this.osszeg.set(osszeg);
    }
}
