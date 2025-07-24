package util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {
    public static void sendEmail(String to, String subject, String content) {
        final String username = "tuongpxhe186663@fpt.edu.vn";
        final String password = "cvgb sueq laaw wbiz";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String SMTP_USER;
    public static String SMTP_PASS;

    public static void sendHtmlEmail(String to, String subject, String htmlContent) {
        // Optional: debug
        System.out.println(">>> [EmailUtil] SMTP_USER=" + SMTP_USER);
        System.out.println(">>> [EmailUtil] SMTP_PASS set? " + (SMTP_PASS != null));

        Properties props = new Properties();
        props.put("mail.smtp.auth",           "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host",           "smtp.gmail.com");
        props.put("mail.smtp.port",           "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASS);
            }
        });
        session.setDebug(true);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(SMTP_USER));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject, "UTF-8");
            msg.setContent(htmlContent, "text/html; charset=UTF-8");
            Transport.send(msg);
            System.out.println(">>> [EmailUtil] Email sent to " + to);
        } catch (MessagingException e) {
            System.err.println(">>> [EmailUtil] Error sending email: " + e.getMessage());
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }
}

