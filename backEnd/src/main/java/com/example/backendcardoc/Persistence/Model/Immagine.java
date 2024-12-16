package com.example.backendcardoc.Persistence.Model;

public class Immagine {
    private String id_prodotto;
    private Integer id;
    private String img;

    public Immagine(String id_prodotto, String img, Integer id ){
        this.id_prodotto = id_prodotto;
        this.img = img;
        this.id = id;
    }

    public Immagine() {}

    public String getId_prodotto() { return id_prodotto; }
    public void setId_prodotto(String id_prodotto) { this.id_prodotto = id_prodotto; }
    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
}