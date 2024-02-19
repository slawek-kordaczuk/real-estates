package com.real.estate.price.external.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailSender;

    @Value("${spring.mail.receiver}")
    private String emailReceiver;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(long numberOfReties){
        try {
            SimpleMailMessage mailMsg = new SimpleMailMessage();
            mailMsg.setFrom(emailSender);
            mailMsg.setTo(emailReceiver);
            mailMsg.setText("Can't get real estates details after number of retries" + numberOfReties);
            mailMsg.setSubject("Real estate refresh");
            javaMailSender.send(mailMsg);
        }catch (MailException exception){
            log.error("Failure occurred while sending email" + exception);
        }
    }
}
