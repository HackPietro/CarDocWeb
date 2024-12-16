package com.example.backendcardoc.Controller;

import com.example.backendcardoc.Persistence.Model.Utente;
import com.example.backendcardoc.Service.UtenteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
@RestController
@CrossOrigin("http://localhost:4200") // Porta di angular
@RequestMapping("/api/utenti")
@RequiredArgsConstructor // Crea in automatico un'istanza di UtenteService
public class UtenteController {
    private final UtenteService i;
    private final HttpServletRequest request;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUtente(@RequestBody Utente utente) {
        i.createUtente(utente);
    }

    @GetMapping("/{string}")
    public ResponseEntity<Utente> findByID(@PathVariable String string) {
        if (string.contains("@")) return i.getByEmail(string);
        return i.getByID(string);
    }

    @DeleteMapping("/{cf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteUtente(@PathVariable String cf) {
        return i.deleteByID(cf);
    }

    @PutMapping("/{cf}")
    public ResponseEntity<Utente> updateUtente(@PathVariable String cf, @RequestBody Utente utente) {
        return i.updateUtente(cf, utente);
    }

    @GetMapping("/user-details")
    public ResponseEntity<Utente> getUserDetails(@RequestParam String sessionId) {
        HttpSession session = (HttpSession) request.getServletContext().getAttribute(sessionId);
        if(session != null) {
            Utente utente = (Utente) session.getAttribute("utente");
            return ResponseEntity.ok(utente);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/sendEmail")
    public void sendEmail(@RequestBody Utente utente) {
        i.sendEmail(utente);
    }

    @PostMapping("/setType")
    public void setUserType(@RequestBody Utente utente){
        i.setUserType(utente.getCf(), utente.getTipologia());
    }
}
