package com.example.backendcardoc.Service;

import com.example.backendcardoc.Persistence.DBManager;
import com.example.backendcardoc.Persistence.Model.Recensione;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RecensioneService {
    public void saveOrUpdateRecensione(Recensione recensione){
        DBManager.getInstance().getRecensioneDAO().saveOrUpdate(recensione);
    }


    public ResponseEntity<List<Recensione>> getByProductID(String id) {
        List<Recensione> recensioni = DBManager.getInstance().getRecensioneDAO().findByProduct(id);
        if(recensioni == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(recensioni);
    }

    public ResponseEntity<Object> deleteByID(Integer id){
        Recensione recensione = DBManager.getInstance().getRecensioneDAO().findByPrimaryKey(id);
        if (recensione == null)
            return ResponseEntity.notFound().build();
        DBManager.getInstance().getRecensioneDAO().delete(recensione);
        return ResponseEntity.noContent().build();
    }

}
