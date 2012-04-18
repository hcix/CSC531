package debuggerTools;

import java.io.FileOutputStream;
import java.io.StringReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
//-----------------------------------------------------------------------------
/**
 * 
 */
public class OliviasExampleClass {
	/*Start Tags*/
	private static final String HTML_START="<html>";
	private static final String HEAD_START="<head>";
	private static final String BODY_START="<body>";
	private static final String H1_START="<h1>";
	private static final String H2_START="<h2>";
	private static final String H3_START="<h3>";
	/*End Tags*/
	private static final String HTML_END="</html>";
	private static final String HEAD_END="</head>";
	private static final String BODY_END="</body>";
	private static final String H1_END="</h1>";
	private static final String H2_END="</h2>";
	private static final String H3_END="</h3>";
	private static final String BREAK="<br>";
//-----------------------------------------------------------------------------
	  public static void main(String args[]) {
	    try {
	      Document document = new Document(PageSize.LETTER);
	      PdfWriter pdfWriter = PdfWriter.getInstance(document, 
	    		  new FileOutputStream("src/progAdmin/itemsToReview/PDFoutputFile.pdf"));
	      
	      document.open();
	      document.addCreationDate();
	      document.addTitle("Hey Look, I'm a PDF!");
	
	      HTMLWorker htmlWorker = new HTMLWorker(document);
	      //html to write to PDF
		   /*   String headText = "Here is the Head";
		      String bodyText = "I'm a paragraph of text blah blah blah. I'm a paragraph " +
		      		"of text blah blah blah. I'm a paragraph of text blah blah blah. I'm" +
		      		" a paragraph of text blah blah blah. I'm a paragraph of text blah blah blah." +
		      		"I'm a paragraph of text blah blah blah.";
		      
		      */
	      
	      String aBunchOfHtml = "<html><head>Here is the Head</head><body>"+
	        "<a href='http://www.rgagnon.com/howto.html'><b>Real's HowTo</b></a>" +
	        
	        "<h1>Show your support</h1>" +
	        
	        "<p>It DOES cost a lot to produce this site - in ISP storage and transfer fees, " +
	        "in personal hardware and software costs to set up test environments, and above all," +
	        "the huge amounts of time it takes for one person to design and write the actual content." +
	        "<p>If you feel that effort has been useful to you, perhaps you will consider giving something back?" +
	        "<p>Donate using PayPal¨ to real@rgagnon.com." +
	        "<p>Contributions via PayPal are accepted in any amount " +
	        "<P><br>" +
	        "<table border='1'>" +
	        "<tr><td>Java HowTo<tr>" +
	        "<td bgcolor='red'>" +
	        "Javascript HowTo<tr><td>Powerbuilder HowTo</table>" +
	        "</body></html>";
	      
	      htmlWorker.parse(new StringReader(aBunchOfHtml));
	      document.close();
	      
	      System.out.println("PDF created: in " +
	      		"Project/src/progAdmin/itemsToReview/PDFoutputFile.pdf");
	      
	      }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	}
//-----------------------------------------------------------------------------
