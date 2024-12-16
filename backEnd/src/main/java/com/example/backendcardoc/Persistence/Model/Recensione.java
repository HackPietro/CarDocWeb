package com.example.backendcardoc.Persistence.Model;

public class Recensione {

    private int id, rating;

    private String contenuto, utente, prodotto;


    public Recensione(int id, String contenuto, int rating, String utente, String prodotto) {
        this.id = id;
        this.utente = utente;
        this.contenuto = contenuto;
        this.rating = rating;
        this.prodotto = prodotto;
    }

    public Recensione() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }

    public int getRating() {return rating;}

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getProdotto() {
        return prodotto;
    }

    public void setProdotto(String prodotto) {
        this.prodotto = prodotto;
    }
}
