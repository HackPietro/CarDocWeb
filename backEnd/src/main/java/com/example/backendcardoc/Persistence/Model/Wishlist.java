package com.example.backendcardoc.Persistence.Model;

public class Wishlist {
    private String cf;
    private String idProdotto;
    private double prezzo;

    public Wishlist (String cf, String idProdotto, Double prezzo) {
        this.cf = cf;
        this.idProdotto = idProdotto;
        this.prezzo = prezzo;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(String idProdotto){
        this.idProdotto = idProdotto;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getPrezzo() {
        return prezzo;
    }
}