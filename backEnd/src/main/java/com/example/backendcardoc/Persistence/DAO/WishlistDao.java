package com.example.backendcardoc.Persistence.DAO;

import com.example.backendcardoc.Persistence.Model.Prodotto;
import com.example.backendcardoc.Persistence.Model.Wishlist;

import java.util.List;

public interface WishlistDao {
    List<Prodotto> findByCf(String cf);

    List<Wishlist> getWishlistByCf(String cf);

    void removewithid_prodotto(String cf, String id_prodotto);

    void removeAll(String cf);
}