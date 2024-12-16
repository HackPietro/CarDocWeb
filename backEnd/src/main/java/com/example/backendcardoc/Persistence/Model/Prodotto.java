package com.example.backendcardoc.Persistence.Model;

public class Prodotto {

    private String id, nome, venditore, descrizione, categoria;
    private double prezzo;
    private int numeroVenduti;

    public Prodotto(){}

    public Prodotto(String id, String nome, String venditore, String descrizione, String categoria, double prezzo, int numeroVenduti){
        this.id = id;
        this.nome = nome;
        this.venditore = venditore;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.numeroVenduti = numeroVenduti;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getNumeroVenduti() {
        return numeroVenduti;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getVenditore() {
        return venditore;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNumeroVenduti(int numeroVenduti) {
        this.numeroVenduti = numeroVenduti;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public void setVenditore(String venditore) {
        this.venditore = venditore;
    }

}
