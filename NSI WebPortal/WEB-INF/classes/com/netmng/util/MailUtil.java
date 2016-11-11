package com.netmng.util;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.internet.AddressException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	public static boolean sendMail(String mailServer, String sFrom, String sTo, String sTitle, String sContents) throws Exception {
		//public static String sendMail() throws Exception {
		boolean result = false;
		 
		try {
			Properties prop = new Properties();
			prop.put("mail.smtp.host", mailServer);
			 
			Session mailSession = Session.getDefaultInstance(prop, (Authenticator)null);
			 
			MimeMessage msg = new MimeMessage(mailSession);
			msg.setFrom(new InternetAddress(sFrom));
			 
			InternetAddress[] address = new InternetAddress[]{new InternetAddress(sTo)};
			 
			msg.setRecipients(RecipientType.TO, address);
			msg.setSubject(sTitle);
			msg.setSentDate(new Date());
			msg.setContent(sContents, "text/html;charset=utf-8");
	         
			Transport.send(msg);
			result = true;
	         
		} catch (AddressException e) {
			e.printStackTrace();
			result = false;
		} catch (MessagingException e) {
			result = false;
		}
		 
		return result;
	}
}