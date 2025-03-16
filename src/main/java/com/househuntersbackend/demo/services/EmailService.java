package com.househuntersbackend.demo.services;

import com.househuntersbackend.demo.enumerations.TipoAnnuncio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}") private String email;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public String creaCorpoMail(String nomeAgente, TipoAnnuncio tipoAnnuncio, double prezzoAnnuncio, String indirizzoAnnuncio) {
        return String.format("""
            Gentile %s,

            Abbiamo una nuova prenotazione per il tuo annuncio!

            Un cliente ha prenotato una visita per il tuo annuncio in %s con prezzo di %.2f€ situato in %s.

            Per ulteriori informazioni, accedi all'app per visualizzare l'annuncio e i dettagli della prenotazione

            Questo è un messaggio generato automaticamente. Non rispondere direttamente a questa mail.

            Il team di HouseHunters
            """, nomeAgente, tipoAnnuncio, prezzoAnnuncio, indirizzoAnnuncio);
    }
}
