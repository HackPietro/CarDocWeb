package com.example.backendcardoc.Persistence.DAO;

import com.example.backendcardoc.Persistence.Model.DettagliOrdine;
import com.example.backendcardoc.Persistence.Model.Ordine;
import com.example.backendcardoc.Persistence.Model.Prodotto;

import java.util.List;

public interface OrdineDao {
    List<Ordine> getOrderById(String cf);
    List<DettagliOrdine> getDetailOrderByNumber(DettagliOrdine dettagliOrdine);

    int findLastNumberOrder(String lastNumeroOrdine);

    boolean insertDettagliOrdine(DettagliOrdine dettagliOrdine);

    void insertOrdine(Ordine ordine);

}