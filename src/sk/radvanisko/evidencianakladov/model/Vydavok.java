package sk.radvanisko.evidencianakladov.model;


import java.sql.Date;

public class Vydavok {

    private String popisVydavku;
    private String kategoria;
    private double suma;
    private java.sql.Date datum;



    // Gettery Settery
    public String getPopisVydavku() {
        return popisVydavku;
    }

    public void setPopisVydavku(String popisVydavku) {
        this.popisVydavku = popisVydavku;
    }

    public String getKategoria() {
        return kategoria;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }
}
