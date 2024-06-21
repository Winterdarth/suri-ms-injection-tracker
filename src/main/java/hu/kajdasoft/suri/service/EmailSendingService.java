package hu.kajdasoft.suri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendingService {

    @Value("${email.from}")
    private String emailFrom;

    @Value("${email.to}")
    private String emailTo;

    private final JavaMailSender javaMailSender;

    //@Autowired
    public EmailSendingService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail() {
        javaMailSender.send(templateSimpleMessage());
    }

    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom); // Use the configured 'emailFrom' value
        message.setSubject("Szuri Idő");
        message.setTo(emailTo); // Use the configured 'emailTo' value
        message.setText("Ne felejsd el jelölni az alkalmazásban!");
        return message;
    }
}
