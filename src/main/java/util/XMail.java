package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.Multipart;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMultipart;

public class XMail {
	private static Session getSession() {
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		props.setProperty("account.email", "25112002abcas@gmail.com");
		props.setProperty("account.appas", "*********************");
		
		return Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				String username = props.getProperty("account.email");
				String password = props.getProperty("account.appas");
				return new PasswordAuthentication(username, password);
			}
		});
	}

	public static void sendAttachFile(String tieude, String noidung,
			String to,String[] cc, String[] bcc, List<String> filenames) {
		MimeMessage message = new MimeMessage(XMail.getSession());
        try {
            message.setFrom(new InternetAddress("25112002abcas@gmai.com"));
            message.setReplyTo(message.getFrom());
            message.setRecipients(Message.RecipientType.TO, to);
            if (cc.length != 0) {
				List<InternetAddress> addressesCC = new ArrayList<>();
				int id = 0;
				for (String mail : cc) {
					if (!mail.isEmpty()) {
						addressesCC.add(new InternetAddress(mail.trim()));
						id++;
					}
				}
				message.setRecipients(Message.RecipientType.CC, addressesCC.toArray(new InternetAddress[0]));
			}
			if (bcc.length != 0) {
				List<InternetAddress> addressesBCC = new ArrayList<>();
				int idd = 0;
				for (String mail : bcc) {
					if (!mail.isEmpty()) {
						addressesBCC.add(new InternetAddress(mail.trim()));
						idd++;
					}
				}
				message.setRecipients(Message.RecipientType.BCC, addressesBCC.toArray(new InternetAddress[0]));
			}
            message.setSubject(tieude, "utf-8");
            message.setText(noidung, "utf-8", "html");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(noidung, "utf-8", "html");
            multipart.addBodyPart(textBodyPart);
            for(String filename: filenames) {
                MimeBodyPart fileBodyPart = new MimeBodyPart();
                System.out.println(filename);
    			fileBodyPart.attachFile(new File(filename));
    			multipart.addBodyPart(fileBodyPart);
            }
            
            message.setContent(multipart);

            Transport.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	} 
	public static void sendSimpleText(String tieude, String noidung, String to, String[] cc,
			List<String> list) {
		MimeMessage message = new MimeMessage(XMail.getSession());
		try {
			message.setFrom(new InternetAddress("25112002abcas@gmai.com"));
			message.setReplyTo(message.getFrom());
			if (to != null) {
				message.setRecipients(Message.RecipientType.TO, to.trim());
			}
			if (cc!= null) {
				List<InternetAddress> addressesCC = new ArrayList<>();
				int id = 0;
				for (String mail : cc) {
					if (!mail.isEmpty()) {
						addressesCC.add(new InternetAddress(mail.trim()));
						id++;
					}
				}
				message.setRecipients(Message.RecipientType.CC, addressesCC.toArray(new InternetAddress[0]));
			}
			if (list!= null) {
				List<InternetAddress> addressesBCC = new ArrayList<>();
				int idd = 0;
				for (String mail : list) {
					if (!mail.isEmpty()) {
						addressesBCC.add(new InternetAddress(mail.trim()));
						idd++;
					}
				}
				message.setRecipients(Message.RecipientType.BCC, addressesBCC.toArray(new InternetAddress[0]));
			}
			message.setSubject(tieude, "utf-8");
			message.setText(noidung, "utf-8", "html");
			Transport.send(message);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
