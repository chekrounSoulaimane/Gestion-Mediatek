/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Soulaimane
 */
public class EmailUtil {

    public static int sendPasswordMail(String destination) {

        String host = "smtp.gmail.com";
        final String user = "madiatechentreprise@gmail.com";
        final String password = "gxmtxietcymkeosh";

        String to = destination;

        //Get the session object  
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        int code = 0; 
        try {
            double x = Math.random() * 1000000;
            code = (int) x;
            
            String content = "<div>\n"
                    + "        <div style=\"margin: auto; justify-content: center; text-align: center; width: 40%; height: auto; background-color:  #05071F; padding: 20px 30px;\">\n"
                    + "            <div style=\"font-size: 25px;\">\n"
                    + "                <h2 style=\"color: #9b59b6;\">Media <span style=\"color: #6770bf;\">Tech</span></h2>\n"
                    + "            </div>\n"
                    + "            <div style=\"padding: 0 0 5px 0;\">\n"
                    + "                <p style=\"font-size: 20px; color: #cccccc; flex-wrap: wrap;\">Vous avez récemment demandé la réinitialisation du mot de passe pour le compte associé à cet e-mail.</p>\n"
                    + "            </div>\n"
                    + "            <div>\n"
                    + "                <p style=\"font-size: 20px; color: #cccccc; flex-wrap: wrap;\">Entrez ce code sur l'écran de réinitialisation du mot de passe.</p>\n"
                    + "            </div>\n"
                    + "            <div style=\"padding: 0 0 1px 0;\">\n"
                    + "                <p style=\"font-size: 20px; color: #9b59b6; flex-wrap: wrap; font-weight: bold;\">" + code + "</p>\n"
                    + "            </div>\n"
                    + "            <div style=\"padding: 0 0 3px 0;\">\n"
                    + "                <p style=\"font-size: 20px; color: #cccccc; flex-wrap: wrap; font-weight: bold;\">Valable jusqu'à 2 heures suivantes</p>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "    </div>";

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Récupération de mot de passe");
            message.setContent(content, "text/html");
            //message.setText("This is simple program of sending email using JavaMail API");

            //send the message  
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return code;
    }

}
