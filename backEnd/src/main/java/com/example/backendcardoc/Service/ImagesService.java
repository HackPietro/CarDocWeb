package com.example.backendcardoc.Service;

import com.example.backendcardoc.Persistence.DBManager;
import com.example.backendcardoc.Persistence.Model.Immagine;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesService {

    public void createImage(Immagine image) {
        DBManager.getInstance().getImmagineDao().save(image);
    }
    public ResponseEntity<Immagine> getById_prodotto(String id_prodotto) {
        Immagine immagine = DBManager.getInstance().getImmagineDao().findById_prodotto(id_prodotto);
        if (immagine == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(immagine);
    }

}