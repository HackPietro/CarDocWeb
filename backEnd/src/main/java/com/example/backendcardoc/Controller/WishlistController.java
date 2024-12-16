package com.example.backendcardoc.Controller;

import com.example.backendcardoc.Persistence.Model.Wishlist;
import com.example.backendcardoc.Persistence.Model.Prodotto;
import com.example.backendcardoc.Service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService i;

    @GetMapping("findByCf/{cf}")
    public ResponseEntity<List<Prodotto>> getWishlistProduct(@PathVariable String cf) {return i.getByCf(cf);}

    @DeleteMapping("/{cf}/{id_prodotto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> removeItemForWishlist(@PathVariable String cf, @PathVariable String id_prodotto) {
        return i.removewithid_prodotto(cf, id_prodotto);
    }

    @DeleteMapping("/{cf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> removeAll(@PathVariable String cf) {
        return i.removeAll(cf);
    }

    @GetMapping("getWishlist/{cf}")
    public ResponseEntity<List<Wishlist>> getWishlist(@PathVariable String cf) {return i.getWishlistByCf(cf);}
}