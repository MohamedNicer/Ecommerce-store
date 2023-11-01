package com.ecs.ecommercestore.Service;

import com.ecs.ecommercestore.Entity.LocalUser;
import com.ecs.ecommercestore.Entity.VerificationToken;
import com.ecs.ecommercestore.Exception.EmailFailureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service for handling emails being sent.
 * @author mohamednicer
 */
@Service
public class EmailService {

    /** The from address to use on emails. */
    @Value("${email.from}")
    private String fromAddress;

    /** The url of the front end for links. */
    @Value("${app.frontend.url}")
    private String url;

    /** The JavaMailSender instance. */
    private JavaMailSender javaMailSender;

    /**
     * Constructor for spring injection.
     * @param javaMailSender
     */
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * Makes a SimpleMailMessage for sending.
     * @return The SimpleMailMessage created.
     */
    private SimpleMailMessage makeMailMessage(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        return simpleMailMessage;
    }

    /**
     * Sends a verification email to the user.
     * @param verificationToken The verification token to be sent.
     * @throws EmailFailureException Thrown if are unable to send the email.
     */
    public void sendVerificationToken(VerificationToken verificationToken) throws EmailFailureException {
        SimpleMailMessage mailMessage = makeMailMessage();
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

    /**
     * Sends a password reset request email to the user.
     * @param user The user to send to.
     * @param token The token to send the user for reset.
     * @throws EmailFailureException
     */
    public void sendPasswordResetEmail(LocalUser user, String token) throws EmailFailureException {
        SimpleMailMessage mailMessage = makeMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Password Reset Email");
        mailMessage.setText("You've requested a Password Reset, Please follow the link below to reset your Email\n"
        + url + "/auth/reset?token=" + token);
        try{
            javaMailSender.send(mailMessage);
        } catch (MailException exception){
            throw new EmailFailureException();
        }
    }
}
