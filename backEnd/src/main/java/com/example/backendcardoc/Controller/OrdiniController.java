package com.example.backendcardoc.Controller;

import com.example.backendcardoc.Persistence.Model.DettagliOrdine;
import com.example.backendcardoc.Persistence.Model.Ordine;
import com.example.backendcardoc.Service.OrdineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200") //utile per frontend in quanto Angular usa la porta 4200
@RequestMapping("/api/ordini") //tutti i metodi avranno nell'URL questa path come path principale
@RequiredArgsConstructor
public class OrdiniController {
    private final OrdineService o;

    @GetMapping("/getOrderById/{cf}")
    public ResponseEntity<List<Ordine>> getOrderById(@PathVariable String cf){
        return o.getOrderById(cf);
    }

    @PostMapping("getDetailOrderByNumber")
    public ResponseEntity<List<DettagliOrdine>> getDetailOrderByNumber(@RequestBody DettagliOrdine dettagliOrdine) {
        return o.getDetailOrderByNumber(dettagliOrdine);
    }

    @GetMapping("/findLastNumberOrder/{cf}")
    public ResponseEntity<Integer> findLastNumberOrder(@PathVariable String cf){
        return o.findLastNumberOrder(cf);
    }

    @PostMapping("/insertOrderDetail")
    public ResponseEntity<Object> insertOrderDetail(@RequestBody DettagliOrdine dettagliOrdine){
        return o.insertOrderDetail(dettagliOrdine);
    }

    @PostMapping("insertOrder")
    public void insertOrder(@RequestBody Ordine ordine){
        o.insertOrder(ordine);
    }

}
