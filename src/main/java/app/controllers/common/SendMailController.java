package app.controllers.common;

import org.javalite.activeweb.annotations.POST;

public class SendMailController {

	@POST
	public void sendMailTLS(){
		
	}
	
}


//package app.controllers.process;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Properties;
//
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//import org.javalite.activeweb.AppController;
//import org.javalite.activeweb.annotations.POST;
//import org.javalite.common.Util;
//
//import core.helper.JsonHelper;
//
//public class SendMailController extends AppController {
//
//	private final String _SMTP = "smtp.gmail.com";
//	private final String _PORTTSL = "587";
//	private final String _PORTSSL = "465";
//	
//	private String username;
//	private String password;
//	private String from;
//	private String to;
//	private String subject;
//	private String body;
//	
//	public String getUsername() {
//		return username;
//	}
//	
//	public String getPassword() {
//		return password;
//	}
//	
//	public String getFrom() {
//		return from;
//	}
//	
//	public String getTo() {
//		return to;
//	}
//	
//	public String getSubject() {
//		return subject;
//	}
//	
//	public String getBody() {
//		return body;
//	}
//	
//	public SendMailController() throws IOException {
//		initSendMail();		
//	}
//	
//	@SuppressWarnings("rawtypes")
//	public void initSendMail() throws IOException{
//		String request = Util.read(getRequestInputStream());			
//		Map mapRequest = JsonHelper.toMap(request);
//				
//		this.username = mapRequest.get("username").toString();
//		this.password = mapRequest.get("password").toString();
//		this.from = mapRequest.get("username").toString();
//		this.to = mapRequest.get("password").toString();
//		this.subject = mapRequest.get("password").toString();
//		this.body = mapRequest.get("password").toString();		
//	}
//	
//	@POST
//	public void sendMailTLS(){
//
//		Properties props = new Properties();
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", _SMTP);
//		props.put("mail.smtp.port", _PORTTSL);
//
//		Session session = Session.getInstance(props,
//				new javax.mail.Authenticator() {
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(getUsername(), getPassword());
//			}
//		});
//
//		try {
//
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(getFrom()));
//			message.setRecipients(Message.RecipientType.TO,
//					InternetAddress.parse(getTo()));
//			message.setSubject(getSubject());
//			message.setText(getBody());
//
//			Transport.send(message);
//
//			System.out.println("Mail has been sent");
//
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}
//	}
//	
//	
//	@POST
//	public void senMailSSL(){
//		
//		Properties props = new Properties();
//		props.put("mail.smtp.host", _SMTP);
//		props.put("mail.smtp.socketFactory.port", _PORTSSL);
//		props.put("mail.smtp.socketFactory.class",
//				"javax.net.ssl.SSLSocketFactory");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.port", _PORTSSL);
//
//		Session session = Session.getDefaultInstance(props,
//			new javax.mail.Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(getUsername(),getPassword());
//				}
//			});
//
//		try {
//
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(getFrom()));
//			message.setRecipients(Message.RecipientType.TO,
//					InternetAddress.parse(getTo()));
//			message.setSubject(getSubject());
//			message.setText(getBody());
//
//			Transport.send(message);
//
//			System.out.println("Done");
//
//		} catch (MessagingException e) {
//			throw new RuntimeException(e);
//		}		
//	}
//
//}
