package com.example.backendcardoc.Controller;

import com.example.backendcardoc.Persistence.DAO.UtenteDao;
import com.example.backendcardoc.Persistence.DBManager;
import com.example.backendcardoc.Persistence.Model.Utente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
public class LoginController {

    @PostMapping("/doLogin")
    protected String doLogin(HttpServletRequest req, HttpServletResponse resp, Model model) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UtenteDao udao = DBManager.getInstance().getUtenteDAO();
        Utente utente = udao.findByEmail(email);
        boolean logged = false;
        HttpSession session = req.getSession();

        if (utente != null && password.equals(utente.getPassword())) {
            logged = true;
            session.setAttribute("utente", utente);
            session.setAttribute("sessionId", session.getId());
            req.getServletContext().setAttribute(session.getId(), session);
        }

        if (logged) {
            resp.sendRedirect("http://localhost:4200/home?sessionId=" + session.getId());
            DBManager.getInstance().getUtenteDAO().recoveryNullCart(utente);
            return null;
        } else {
            String errorMessage = "Le credenziali fornite non sono valide";
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        }
    }



}
