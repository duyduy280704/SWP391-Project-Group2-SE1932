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
 * @author Dwight
 */
public class SendMail {

    private static final String USERNAME = "hhduong2305@gmail.com";      // ✅ Gmail gửi đi
    private static final String PASSWORD = "ahzx suyt ummv lprd";        // ✅ Mật khẩu ứng dụng

    // Gửi mail cho 1 người
    public static void send(String to, String subject, String Content) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME, MimeUtility.encodeText("Trung tâm năng khiếu BIGDREAM", "UTF-8", "B")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            message.setContent(Content, "text/plain; charset=UTF-8");

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
