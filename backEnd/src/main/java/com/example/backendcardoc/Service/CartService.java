package com.example.backendcardoc.Service;

import com.example.backendcardoc.Persistence.DBManager;
import com.example.backendcardoc.Persistence.Model.Cart;
import com.example.backendcardoc.Persistence.Model.Prodotto;
import com.example.backendcardoc.Persistence.Model.Utente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    public ResponseEntity<List<Prodotto>> getByCf(String cf) {
        List<Prodotto> prodotti = DBManager.getInstance().getCartDAO().findByCf(cf);
        if (prodotti == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(prodotti);
    }

    public ResponseEntity<Object> removewithid_prodotto(String cf, String id_prodotto){
        DBManager.getInstance().getCartDAO().removewithid_prodotto(cf, id_prodotto);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Object> removeAll(String cf){
        DBManager.getInstance().getCartDAO().removeAll(cf);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Integer> getProductQuantity(String cf, String id_prodotto){
        int quantity = DBManager.getInstance().getCartDAO().getProductQuantity(cf, id_prodotto);
        return ResponseEntity.ok(quantity);
    }

    public ResponseEntity<List<Cart>> getCartByCf(String cf) {
        List<Cart> cart = DBManager.getInstance().getCartDAO().getCartByCf(cf);
        if (cart == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(cart);
    }

}