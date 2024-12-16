package com.example.backendcardoc.Controller;

import com.example.backendcardoc.Persistence.DAO.UtenteDao;
import com.example.backendcardoc.Persistence.DBManager;
import com.example.backendcardoc.Persistence.Model.Utente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {
    @PostMapping("/doRegistration")
    public String doRegistration(@RequestParam("name") String name,
                                 @RequestParam("surname") String surname,
                                 @RequestParam("cf") String cf,
                                 @RequestParam("email") String email,
                                 @RequestParam("phone") String phone,
                                 @RequestParam("password") String password,
                                 Model model) {

        UtenteDao udao = DBManager.getInstance().getUtenteDAO();
        Utente utente = udao.findByEmail(email);

        if (utente == null) {
            boolean reg = udao.saveOrUpdate(new Utente(cf, name, surname, email, phone, password, "utente"));
            if (reg) {
                // Registrazione andata a buon fine: popup
                String successMessage = "Utente registrato con successo! Effettua il login per procedere.";
                model.addAttribute("successMessage", successMessage);
            } else {
                // Registrazione non andata a buon fine: popup
                String errorMessage = "Server error!";
                model.addAttribute("errorMessage", errorMessage);
            }
        } else {
            // Utente già esistente: popup
            String errorMessage = "Utente già esistente!";
            model.addAttribute("errorMessage", errorMessage);
        }
        return "registration";
    }
}
