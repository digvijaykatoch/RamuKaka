package com.schein.utils;

import javax.mail.*;
import javax.mail.internet.*;

import com.schein.dao.TicketDao;

import javax.activation.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;


/**
 * @author Dilip Nair This is simple utility to send emails
 */
public class EmailSender {

  /**
   * Method sendEmail.
   * 
   * @param to
   * @param from
   * @param subject
   * @param msgBody This method sends an email to the address specified in the parameter. Multiple
   *        email addresses has to be separated by semicolon
   */
  public static void sendEmail(String to, String from, String subject, String msgBody) {
    // invoke the method that has the logic to send the email , specify attachment file string as
    // null
    sendEmailWithAttachment(to, from, subject, msgBody, null);
  }

  /**
   * Method sendOperEmail.
   * 
   * @param msgBody This method sends message to the admin/opertator whose email id is set in the
   *        properties table.
   */
  public static void sendOperEmail(String msgBody) {
    String to = CachingManager.getProperty("OPER.EMAIL");
    // LoggerUtil.log("debug", to);
    // String from="BOSYSTEM@HENRYSCHEIN.COM";
    String from = CachingManager.getProperty("FROM_EMAIL");
    String subject = "Email from BO application, Please do not reply";
    String msgHeader = "";
    msgHeader += "Message from server :" + CachingManager.getServerIpAddr() + "\n";
    msgHeader += "Application         :" + CachingManager.getProperty("COMPANY") + "\n";
    msgHeader += "Date                :" + ScheinUtils.getToday_YMD('-') + "\n";
    msgBody = msgHeader + msgBody;
    sendEmail(to, from, subject, msgBody);
  }

  /**
   * Method sendEmailWithAttachment
   * 
   * @param to
   * @param from
   * @param subject
   * @param msgBody
   * @param fileAttachment
   * 
   *        This method send an email to the list of addresses specified in "to" separated by commas
   *        FileAttachment can contain a list of absolute paths of file names to be attached,
   *        separated by commas. If the attachment string is null , no attachment will be sent in
   *        the email.
   */
  public static void sendEmailWithAttachment(String to, String from, String subject, String msgBody,
      String fileAttachment) {
    Properties props = System.getProperties();
    props.put("mail.smtp.host", CachingManager.getProperty("MAIL.SMTP.HOST"));
    Session session = Session.getDefaultInstance(props, null);
    Message msg = new MimeMessage(session);
    if ((from == null) || (from.trim().equals(""))) {
      from = CachingManager.getProperty("FROM_EMAIL");
      subject = subject.trim() + ",  Please do not reply";
    }

    try {
      msg.setFrom(new InternetAddress(from));
      if (to.indexOf(',') > -1) {
        int index = 0;
        index = to.indexOf(',');
        String s = to.substring(0, index);
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(s, false));
        s = to.substring(index + 1);
        if (s.trim().length() > 0)
          msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(s, false));
      } else {
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
      }
      subject = subject.startsWith(",") ? subject.substring(1) : subject;
      msg.setSubject(subject);
      msg.setText(msgBody);
      /*
       * if the file attachment is not null ,read thru the list of comma separated files ,attach the
       * files one by one. if the file is missing or an error occurs send an email to the system
       * oper.
       */
      if (fileAttachment != null) {
        DataSource source = null;
        Multipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(msgBody);
        // messageBodyPart.setContent(msgBody, "text/html");
        multipart.addBodyPart(messageBodyPart);
        StringTokenizer st = new StringTokenizer(fileAttachment, ",");
        while (st.hasMoreTokens()) {
          String s = st.nextToken().trim();
          messageBodyPart = new MimeBodyPart();
          try {
            source = new FileDataSource(s);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(s.substring((s.lastIndexOf('\\') + 1)));
            multipart.addBodyPart(messageBodyPart);
          } catch (Exception fileNotFound) {
            sendOperEmail("Error attaching file in email " + fileNotFound.toString());
          }
        }
        msg.setContent(multipart);
      }

      msg.setSentDate(new Date());
      msg.setContent(msgBody, "text/html; charset=UTF-8");
      Transport.send(msg);
    } catch (Exception ex) {
      ex.printStackTrace();
      LoggerUtil.log("debug", "Error in sending email " + ex.toString());
    } finally {
      // do nothing
    }
  }


  /**
   * Method sendEmailWithAttachment
   * 
   * @param to
   * @param from
   * @param subject
   * @param msgBody
   * @param fileAttachment
   * 
   *        This method send an email to the list of addresses specified in "to" separated by commas
   *        FileAttachment can contain a list of absolute paths of file names to be attached,
   *        separated by commas. If the attachment string is null , no attachment will be sent in
   *        the email.
   */
  public static void sendEmailWithAttachment(String to, String from, String subject, String msgBody,
      String fileAttachment, String contentType) {
    Properties props = System.getProperties();
    props.put("mail.smtp.host", CachingManager.getProperty("MAIL.SMTP.HOST"));
    Session session = Session.getDefaultInstance(props, null);
    Message msg = new MimeMessage(session);
    if ((from == null) || (from.trim().equals(""))) {
      from = CachingManager.getProperty("FROM_EMAIL");
      subject = subject.trim() + ", please do not reply.";
    }
    try {
      msg.setFrom(new InternetAddress(from));
//      // Take the first email id and set it "TO" option, the rest send it with CC
//      if (to.indexOf(',') > -1) {
//        int index = 0;
//        index = to.indexOf(',');
//        String s = to.substring(0, index);
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(s, false));
//        s = to.substring(index + 1);
//        if (s.trim().length() > 0) {
//          msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(s, false));
//        }
//      } else {
//        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
//      }
      
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
      
      if(subject.indexOf("Vendor Recieving Observations")!=-1){
    	  String ccAdd = "";
    	  TicketDao ticketDao = new TicketDao();
    	  Connection con = AS400Utils.getAS400DBConnection();
    	  ccAdd = ticketDao.checkcc(con);
    	  msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccAdd, false));
      }
      
      msg.setSubject(subject);
      msg.setText(msgBody);
      /*
       * if the file attachment is not null, read thru the list of comma separated files, attach the
       * files one by one. if the file is missing or an error occurs send an email to the system
       * oper.
       */
      if (fileAttachment != null) {
        DataSource source = null;
        Multipart multipart = new MimeMultipart();
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        if (contentType.equals("html")) {
          messageBodyPart.setContent(msgBody, "text/html");
        } else {
          messageBodyPart.setText(msgBody);
        }
        multipart.addBodyPart(messageBodyPart);
        StringTokenizer st = new StringTokenizer(fileAttachment, ",");
        while (st.hasMoreTokens()) {
          String s = st.nextToken().trim();
          messageBodyPart = new MimeBodyPart();
          try {
            source = new FileDataSource(s);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(s.substring((s.lastIndexOf('\\') + 1)));
            multipart.addBodyPart(messageBodyPart);
          } catch (Exception fileNotFound) {
            sendOperEmail("Error attaching file in email " + fileNotFound.toString());
          }
        }
        msg.setContent(multipart);
      }
      msg.setSentDate(new Date());
      Transport.send(msg);
    } catch (Exception ex) {
      ex.printStackTrace();
      LoggerUtil.log("debug", "Error in sending email " + ex.toString());
    } finally {
      // do nothing
    }
  }

  // Digvijay - temp
  public static void sendEmailMultiple(String host, String port, final String userName,
      final String password, String toAddress, String subject, String message)
      throws AddressException, MessagingException {

    String from = "";
    Properties properties = System.getProperties();
    properties.put("mail.smtp.host", CachingManager.getProperty("MAIL.SMTP.HOST"));
    Session session = Session.getDefaultInstance(properties, null);
    Message msg = new MimeMessage(session); // creates a new e-mail message
    if ((from == null) || (from.trim().equals(""))) {
      from = CachingManager.getProperty("CVEMAIL");
      subject = subject.trim() + ", please do not reply.";
    }
    LoggerUtil.log("debug", "From set to: " + from.toString());
    // Session session = Session.getInstance(properties, auth);

    msg.setFrom(new InternetAddress(from));
    InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
    msg.setRecipients(Message.RecipientType.TO, toAddresses);
    msg.setSubject(subject);
    msg.setSentDate(new Date());
    msg.setText(message);

    Transport.send(msg);// sends the e-mail
    LoggerUtil.log("debug", "Email sent");

    // String to = "digvijay.katoch@henryschein.com";
    // // LoggerUtil.log("debug", to);
    // // String from="BOSYSTEM@HENRYSCHEIN.COM";
    // String from = CachingManager.getProperty("FROM_EMAIL");
    // subject = "Email from BO application, Please do not reply";
    // String msgHeader = "";
    // msgHeader += "Message from server :" + CachingManager.getServerIpAddr() + "\n";
    // msgHeader += "Application :" + CachingManager.getProperty("COMPANY") + "\n";
    // msgHeader += "Date :" + ScheinUtils.getToday_YMD('-') + "\n";
    // String msgBody = msgHeader + message;
    // sendEmail(to, from, subject, msgBody);
  }



}


