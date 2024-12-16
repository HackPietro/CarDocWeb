package com.example.backendcardoc.Service;

import com.example.backendcardoc.Persistence.DBManager;
import com.example.backendcardoc.Persistence.Model.Wishlist;
import com.example.backendcardoc.Persistence.Model.Prodotto;
import com.example.backendcardoc.Persistence.Model.Utente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    public ResponseEntity<List<Prodotto>> getByCf(String cf) {
        List<Prodotto> prodotti = DBManager.getInstance().getWishlistDAO().findByCf(cf);
        if (prodotti == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(prodotti);
    }

    public ResponseEntity<Object> removewithid_prodotto(String cf, String id_prodotto){
        DBManager.getInstance().getWishlistDAO().removewithid_prodotto(cf, id_prodotto);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Object> removeAll(String cf){
        DBManager.getInstance().getWishlistDAO().removeAll(cf);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<List<Wishlist>> getWishlistByCf(String cf) {
        List<Wishlist> wishlist = DBManager.getInstance().getWishlistDAO().getWishlistByCf(cf);
        if (wishlist == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(wishlist);
    }
}