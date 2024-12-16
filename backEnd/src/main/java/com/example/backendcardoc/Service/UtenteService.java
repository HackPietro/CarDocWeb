package com.example.backendcardoc.Service;

import com.example.backendcardoc.Persistence.DBManager;
import com.example.backendcardoc.Persistence.Model.Utente;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class UtenteService {
    public void createUtente(Utente utente) {
        DBManager.getInstance().getUtenteDAO().saveOrUpdate(utente);
    }

    public ResponseEntity<Utente> getByID(String cf) {
        Utente utente = DBManager.getInstance().getUtenteDAO().findByPrimaryKey(cf);
        if (utente == null) return ResponseEntity.notFound().build(); // 404 page
        return ResponseEntity.ok(utente); // Genera un entità di risposta positiva
    }

    public ResponseEntity<Utente> getByEmail(String email) {
        Utente utente = DBManager.getInstance().getUtenteDAO().findByEmail(email);
        if (utente == null) return ResponseEntity.notFound().build(); // 404 page
        return ResponseEntity.ok(utente); // Genera un entità di risposta positiva
    }

    public ResponseEntity<Object> deleteByID(String cf) {
        Utente utente = DBManager.getInstance().getUtenteDAO().findByPrimaryKey(cf);
        if (utente == null) return ResponseEntity.notFound().build();
        DBManager.getInstance().getUtenteDAO().delete(utente);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Utente> updateUtente(String cf, Utente utente) {
        Utente utente1 = DBManager.getInstance().getUtenteDAO().findByPrimaryKey(cf);
        if (utente == null) return ResponseEntity.notFound().build();
        else {
            utente1.setNome(utente.getNome());
            utente1.setCognome(utente.getCognome());
            utente1.setEmail(utente.getEmail());
            utente1.setTelefono(utente.getTelefono());
            utente1.setPassword(utente.getPassword());
            utente1.setTipologia(utente.getTipologia());
            if(DBManager.getInstance().getUtenteDAO().saveOrUpdate(utente1))
                return ResponseEntity.ok(utente1);
            else return ResponseEntity.internalServerError().build();
        }
    }

    public void setUserType(String cf, String tipologia) {
        DBManager.getInstance().getUtenteDAO().setUserType(cf, tipologia);
    }

    public ResponseEntity<Object> sendEmail(Utente utente){
        final String username = "CarDocMarketPlace@gmail.com";
        final String password = "rgpt etjy pnnz fxsi";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("CarDocMarketPlace@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(utente.getEmail()));
            message.setSubject("Recupero password account");
            message.setText("La password associata al seguente indirizzo email è: " + utente.getPassword() + "\n\nCordiali saluti,\nAssistenza tecnica CarDoc.");

            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


}
