package com.BlogWebsite.BlogWebsite.config;


import org.springframework.context.annotation.Configuration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

@Configuration
public class ForgetPassowrd {
    public static void sendMail(String userEmail) throws Exception{
        Properties properties=new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        Session session=Session.getInstance(properties,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("rennymittal1234@gmail.com","myselflove");
            }
        });

        Message message=new
                MimeMessage(session);
        message.setFrom(new
                InternetAddress("rennymittal1234@gmail.com",false));

        message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(userEmail));
        message.setSubject("Scholar - you clicked on forget password");
        message.setContent("Scholar Email","text/html");
        message.setSentDate(new Date());

        MimeBodyPart mimeBodyPart=new MimeBodyPart();
        mimeBodyPart.setContent("we are sorry to hear you forgot your password here is a new one : Scholar123" +
                "<br> <b>PLEASE DO NOT SHARE THIS WITH ANYONE</b>","text/html");


        Multipart multipart=new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);


        Transport.send(message);
    }
}
