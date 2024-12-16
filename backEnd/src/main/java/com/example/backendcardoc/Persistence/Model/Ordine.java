package com.example.backendcardoc.Persistence.Model;

public class Ordine {
    private String cf, data;
    private int numeroOrdine, numeroVenduti;
    private double prezzoTotale;

    public Ordine(){}

    public Ordine(String cf, int numeroOrdine, int numeroVenduti, double prezzoTotale, String data){
        this.cf = cf;
        this.numeroOrdine = numeroOrdine;
        this.numeroVenduti = numeroVenduti;
        this.prezzoTotale = prezzoTotale;
        this.data = data;
    }

    public void setNumeroOrdine(int numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public int getNumeroOrdine() {
        return numeroOrdine;
    }

    public void setNumeroVenduti(int numeroVenduti) {
        this.numeroVenduti = numeroVenduti;
    }

    public int getNumeroVenduti() {
        return numeroVenduti;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getCf() {
        return cf;
    }

    public double getPrezzoTotale() {
        return prezzoTotale;
    }

    public void setPrezzoTotale(double prezzoTotale) {
        this.prezzoTotale = prezzoTotale;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}