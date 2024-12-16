package com.example.backendcardoc.Controller;


import com.example.backendcardoc.Persistence.Model.Recensione;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backendcardoc.Service.RecensioneService;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/recensioni")
@RequiredArgsConstructor
public class RecensioneController {

    private final RecensioneService r;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createRecensione(@RequestBody Recensione recensione) {
        r.saveOrUpdateRecensione(recensione);
    }

    @GetMapping("/findByProduct/{id}")
    public ResponseEntity<List<Recensione>> findByProductID(@PathVariable String id) {
        return r.getByProductID(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteRecensione(@PathVariable int id) {
        return r.deleteByID(id);
    }



}

