package com.telappoint.apptadmin.utils;

import java.util.Properties;

import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Murali G
 *
 */

@Component
public class EmailComponent {

	private static final Logger logger = Logger.getLogger(EmailComponent.class);

	@Async("asyncMailExecutor")
	public void sendEmail(String clientCode,String requestURI,HttpServletRequest request, Exception ex) {
		try{
			String sendErrorEmail = PropertyUtils.getMailProperty("error.mail.send");
			if ("Y".equalsIgnoreCase(sendErrorEmail)) {			
				Multipart multipart = new MimeMultipart("alternative");

				StringBuilder errorMsg = getErrorMailBody(ex, request,requestURI);

				JavaMailSenderImpl sender = new JavaMailSenderImpl();	
				MimeMessage message = sender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message);

				helper.setFrom(PropertyUtils.getMailProperty("mail.fromaddress"));

				MimeBodyPart emailBodyPart = new MimeBodyPart();
				emailBodyPart.setContent(errorMsg.toString(),"text/html");
				multipart.addBodyPart(emailBodyPart);

				sender.setHost(PropertyUtils.getMailProperty("mail.smtp.hostname"));
				sender.setPort(Integer.valueOf(PropertyUtils.getMailProperty("mail.smtp.port")));
				sender.setUsername(PropertyUtils.getMailProperty("mail.smtp.user"));
				sender.setPassword(PropertyUtils.getMailProperty("mail.smtp.password"));
				sender.setJavaMailProperties(getSMTPMailProperties());
				helper.setTo(PropertyUtils.getMailProperty("error.mail.to"));
				
				String emailCC = PropertyUtils.getMailProperty("error.mail.cc");
				if (emailCC != null && emailCC.length()>0) {
					String[] emailCCArr = new String[]{};			
					if (emailCCArr != null && emailCCArr.length > 0) {
						helper.setCc(emailCCArr);
					}
				}
				helper.setSubject("Error in ApptOnline for clientCode - " + clientCode);
				helper.setText(errorMsg.toString());
				message.setContent(multipart);
				sender.send(message);			
			}else{
				logger.debug("Send Eror Email :: "+sendErrorEmail+" , so discarding sending error email!!");
			}
		} catch(Exception e){
			logger.error("Send Eror Email :: Error ::::::  "+e.getMessage());
		}
	}

	private StringBuilder getErrorMailBody(Exception e, HttpServletRequest request,String requestURI) {
		StringBuilder errorMsg = new StringBuilder();
		errorMsg.append("Request URI : ").append(requestURI);
		errorMsg.append("<br/> ");
		errorMsg.append("InputParameters : ").append(AdminUtils.getRequestParameters(request));
		errorMsg.append("<br/> ");
		errorMsg.append(getMethodAndClassName(e));
		errorMsg.append("<br/>");
		errorMsg.append("Root Cause of Exception : ").append("Error  : " + e.getMessage());
		errorMsg.append("<br/><br/>");
		errorMsg.append("Stack Trace : ");
		errorMsg.append("<br/>");
		errorMsg.append("<br/>" + getStackTrace(e));
		return errorMsg;
	}

	private Properties getSMTPMailProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "false");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.debug", "false");
		return properties;
	}

	private String getMethodAndClassName(final Throwable cause) {
		Throwable rootCause = cause;
		while(rootCause.getCause() != null &&  rootCause.getCause() != rootCause) {
			rootCause = rootCause.getCause();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<br>").append("ClassName: ").append(rootCause.getStackTrace()[0].getClassName()); 
		sb.append("<br>").append("MethodName: ").append(rootCause.getStackTrace()[0].getMethodName()); 
		return sb.toString();
	}

	private String getStackTrace(Exception e) {
		final StringBuilder result = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace() ){
			result.append(element);
			result.append("</br>");
		}
		return result.toString();
	}
}
