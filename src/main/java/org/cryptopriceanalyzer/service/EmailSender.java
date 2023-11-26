package org.cryptopriceanalyzer.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    public static void sendEmail(String to, String subject, String body) {
        Properties properties = loadProperties();

        assert properties != null;
        String from = properties.getProperty("email.sender");
        String password = properties.getProperty("email.password");

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "587");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(mailProperties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setSubject(subject);
            message.setText(body);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = EmailSender.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + "application.properties");
                return null;
            }

            properties.load(input);
            System.out.println("Properties loaded successfully!");
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
        return properties;
    }
}
