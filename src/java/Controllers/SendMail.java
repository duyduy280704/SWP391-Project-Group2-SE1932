/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 *
 * @author Quang
 */
public class SendMail {
    public static void send(String to, String subject, String messageText) throws UnsupportedEncodingException {
        
        final String username = "hhduong2305@gmail.com"; // thay bằng email gửi
        final String password = "ahzx suyt ummv lprd";     // app password hoặc mật khẩu

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));       // From
            message.setRecipients(Message.RecipientType.TO,      // To
                    InternetAddress.parse(to));
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            message.setContent(messageText, "text/plain; charset=UTF-8");

            Transport.send(message);

            System.out.println("Gửi mail thành công đến " + to);

        } catch (MessagingException e) {
            System.out.println("Gửi mail thất bại: " + e.getMessage());
        }
    }
}
