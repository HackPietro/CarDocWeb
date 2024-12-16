package com.example.backendcardoc.Controller;

import com.example.backendcardoc.Persistence.Model.*;
import com.example.backendcardoc.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:4200") //utile per frontend in quanto Angular usa la porta 4200
@RequestMapping("/api/prodotti") //tutti i metodi avranno nell'URL questa path come path principale
@RequiredArgsConstructor //crea in automatico un'istanza di ProductService
public class ProductController {
    private final ProductService i;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveOrUpdateProduct(@RequestBody Prodotto prodotto) {
        i.saveOrUpdateProdotto(prodotto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Prodotto>> findAll(){
        return i.getAllEntries();
    }


    @GetMapping("findCategoryProduct/{category}")
    public ResponseEntity<List<Prodotto>> findCategoryProduct(@PathVariable String category) { return i.getCategoryProduct(category);}

    @GetMapping("findSearchedProduct/{searchedWord}")
    public ResponseEntity<List<Prodotto>> findSearchedProduct(@PathVariable String searchedWord){return i.getSearchedProduct(searchedWord);}

    @GetMapping("getProduct/{id}")
    ResponseEntity<Prodotto> getProduct(@PathVariable String id){return i.getProduct(id);}

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> removeProduct(@PathVariable String id) {
        return i.deleteByID(id);
    }

    @PostMapping("addToCart")
    public ResponseEntity<Object> addToCart(@RequestBody Cart cart) {
        return i.addToCart(cart);
    }

    @PostMapping("addToWishlist")
    public ResponseEntity<Object> addToWishlist(@RequestBody Wishlist wishlist) {
        return i.addToWishlist(wishlist);
    }
}

