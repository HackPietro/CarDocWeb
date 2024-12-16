package com.example.backendcardoc.Persistence.Model;

public class Cart {
    private String cf;
    private String idProdotto;

    private int quantity;

    private double prezzo;

    public Cart(String cf, String idProdotto, int quantita, Double prezzo) {
        this.cf = cf;
        this.idProdotto = idProdotto;
        this.quantity = quantita;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getPrezzo() {
        return prezzo;
    }
}