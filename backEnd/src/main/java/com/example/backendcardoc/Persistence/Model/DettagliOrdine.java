package com.example.backendcardoc.Persistence.Model;

public class DettagliOrdine {
    String cf, idProdotto, nomeProdotto;
    int numeroOrdine, quantita;
    double prezzo;

    public DettagliOrdine(){}

    public DettagliOrdine(String cf, String idProdotto, int numeroOrdine, int quantita, int prezzo, String nomeProdotto){
        this.cf = cf;
        this.idProdotto = idProdotto;
        this.numeroOrdine = numeroOrdine;
        this.quantita = quantita;
        this.prezzo = prezzo;
        this.nomeProdotto = nomeProdotto;
    }

    public int getNumeroOrdine() {
        return numeroOrdine;
    }

    public void setNumeroOrdine(int numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }

    public void setIdProdotto(String idProdotto) {
        this.idProdotto = idProdotto;
    }

    public String getIdProdotto() {
        return idProdotto;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getCf() {
        return cf;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }
}