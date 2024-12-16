package com.example.backendcardoc.Persistence.DAO;

import com.example.backendcardoc.Persistence.Model.Utente;

import java.util.List;

public interface UtenteDao {

    Utente findByPrimaryKey(String cf); // Restituisce un utente dato il codice fiscale (id)

    Utente findByEmail(String email); // Restituisce un utente data l'email

    boolean saveOrUpdate(Utente utente); // Salva un nuovo utente se non esiste, altrimenti lo aggiorna

    void delete(Utente utente); // Cancella un utente

    void recoveryNullCart(Utente utente);

    void setUserType(String cf, String type);
}