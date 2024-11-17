package com.demo.easylang.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import java.util.Random;

@Service
public class EmailService {

    // Configure your email account details
    private final String fromEmail = "guruvyaas97@gmail.com"; // Replace with your email
    private final String password = "owyvawvoapxgldyq";   // Replace with your email password

    // Method to send the email
    public void sendTemporaryPassword(String toEmail,   String tempPassword) {
        // Generate a random temporary password

        // Email subject and body
        String subject = "Your Temporary Password";
        String body = "Hello,\n\nYour temporary password is: " + tempPassword + "\n\nPlease change it after logging in.";

        // Set up properties for the mail session
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create the session with an authenticator
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);

            System.out.println("Temporary password sent successfully!");
            System.out.println("Temporary password: " + tempPassword); // Log the password for debugging purposes

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Utility method to generate a random temporary password
    public String generateTemporaryPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@!#$%^&*";
        StringBuilder tempPassword = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            tempPassword.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return tempPassword.toString();
    }
}
