package com.example.backendcardoc.Service;

import com.example.backendcardoc.Persistence.DBManager;
import com.example.backendcardoc.Persistence.Model.DettagliOrdine;
import com.example.backendcardoc.Persistence.Model.Ordine;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdineService {

    public ResponseEntity<List<Ordine>> getOrderById(String cf) {
        List<Ordine> ordini = DBManager.getInstance().getOrderDao().getOrderById(cf);
        if (ordini == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ordini);
    }

    public ResponseEntity<List<DettagliOrdine>> getDetailOrderByNumber(DettagliOrdine dettagliOrdine) {
        List<DettagliOrdine> dettagliOrdines = DBManager.getInstance().getOrderDao().getDetailOrderByNumber(dettagliOrdine);
        if (dettagliOrdines == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dettagliOrdines);
    }


    public ResponseEntity<Integer> findLastNumberOrder(String cf) {
        int lastNumeroVenduti = DBManager.getInstance().getOrderDao().findLastNumberOrder(cf);
        if (lastNumeroVenduti == 0)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(lastNumeroVenduti);
    }

    public ResponseEntity<Object> insertOrderDetail(DettagliOrdine dettagliOrdine){
        boolean value = DBManager.getInstance().getOrderDao().insertDettagliOrdine(dettagliOrdine);
        if (!value)
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

    public void insertOrder(Ordine ordine){
        DBManager.getInstance().getOrderDao().insertOrdine(ordine);
    }


}