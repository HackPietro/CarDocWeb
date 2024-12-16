package com.example.backendcardoc.Persistence.DAO;

import com.example.backendcardoc.Persistence.Model.Cart;
import com.example.backendcardoc.Persistence.Model.Prodotto;

import java.util.List;

public interface CartDao {
    List<Prodotto> findByCf(String cf);

    List<Cart> getCartByCf(String cf);

    void removewithid_prodotto(String cf, String id_prodotto);

    void removeAll(String cf);

    int getProductQuantity(String cf, String id_prodotto);
}