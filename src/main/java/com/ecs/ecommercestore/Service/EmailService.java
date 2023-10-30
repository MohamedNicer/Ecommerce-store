package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Entity.VerificationToken;
import com.ecs.ecommercestore.Exception.EmailFailureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("{email.from}")
    private String fromAddress;
    @Value("{app.frontend.url}")
    private String url;

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }
    public void sendVerificationToken(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(verificationToken.getUser().getEmail());
        mailMessage.setSubject("Email Verification");
        mailMessage.setText("Please follow the link below to activate your account\n"
        + url + "/auth/verify?token=" + verificationToken.getToken());
        try {
            javaMailSender.send(mailMessage);
        } catch (MailException exception){
            throw new EmailFailureException();
        }
    }
}
