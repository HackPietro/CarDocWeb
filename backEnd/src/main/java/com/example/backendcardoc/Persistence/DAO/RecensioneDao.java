package com.example.backendcardoc.Persistence.DAO;

import com.example.backendcardoc.Persistence.Model.Recensione;

import java.util.List;

public interface RecensioneDao {

    Recensione findByPrimaryKey(int id); //restituisce una recensione data la chiave primaria

    List<Recensione> findByProduct(String idProdotto); // Restituisce tutte le recensioni dato l'ID del prodotto

    boolean saveOrUpdate(Recensione recensione); //salva se non esiste o aggiorna se esiste

    void delete(Recensione recensione);//cancella una recensione

}
