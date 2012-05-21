package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import progAdmin.Employee;
import program.CurrentUser;
//-----------------------------------------------------------------------------
/**
 * <code>EmailHandler</code> assists in creating an email with an attachment.
 */
public class EmailHandler {
	private static final String HOST = "smtp.umail.miami.edu";
//-----------------------------------------------------------------------------
	/**
	 * Creates a new email message with the given file attachment attached. 
	 * The message is then written to the file system as a .eml file. This
	 * .eml file can later be opened using the default program for this
	 * file type, which should be the system's default email client.
	 * @param attachment - the absolute filename of the document
	 *  to be attached to the generated email message
	 */
	public static String createMessage(String attachment) { 
		File emlFile;
		Employee currentUsr = CurrentUser.getCurrentUser();
		String fromEmail = currentUsr.getEmail();
		  
		//get system properties 
		Properties props = System.getProperties(); 
		props.put("mail.smtp.host", HOST);
		Session session = Session.getInstance(props, null); 
		
		try{
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail)); 
	         
			//Create the message part for an optional message
			BodyPart bp = new MimeBodyPart();
	        bp.setText(" "); 
			
			//Create a message body part for the attachment
			MimeBodyPart mbp = new MimeBodyPart();
	        
			//Attach the file to the message
			FileDataSource fds = new FileDataSource(attachment);
			mbp.setDataHandler(new DataHandler(fds));
			mbp.setFileName(fds.getName());
			
			//Create a multipart message
			Multipart mp = new MimeMultipart();	
			mp.addBodyPart(bp);
			mp.addBodyPart(mbp);
			msg.setContent(mp);
			
			//set the 'Date:' header
			msg.setSentDate(new Date());

			//write the message to a file
			emlFile = new File(FileHelper.getTempMessageName());
			emlFile.createNewFile();
			msg.writeTo(new FileOutputStream(emlFile)); 
		} catch (Exception e) {
			e.printStackTrace();
		    return null; 
	  	}
		
		return emlFile.toString();
		
	}
//-----------------------------------------------------------------------------
}