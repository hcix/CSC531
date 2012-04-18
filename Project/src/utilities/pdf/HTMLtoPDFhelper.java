package utilities.pdf;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

//-----------------------------------------------------------------------------

/**
 *
 */
public class HTMLtoPDFhelper {
	/*Start HTML Tags*/
	private static final String HTML_START="<html>";
	private static final String HEAD_START="<head>";
	private static final String BODY_START="<body>";
	private static final String H1_START="<h1>";
	private static final String H2_START="<h2>";
	private static final String H3_START="<h3>";
	/*End HTML Tags*/
	private static final String HTML_END="</html>";
	private static final String HEAD_END="</head>";
	private static final String BODY_END="</body>";
	private static final String H1_END="</h1>";
	private static final String H2_END="</h2>";
	private static final String H3_END="</h3>";
	private static final String BREAK="<br>";
	
	PdfWriter pdfWriter;
	Document document;
//-----------------------------------------------------------------------------
	/**
	 * Creates a new <code>HTMLtoPDFhelper</code> to be used to create a PDF
	 * based on <code>HTML</code> elements.
	 * @param pdfToCreate - the absolute filename of the PDF file to create
	 */
	public HTMLtoPDFhelper(String pdfToCreate) {
		
	}
	
//-----------------------------------------------------------------------------
	private void createDocument(String pdfToCreate) {
		document = new Document(PageSize.LETTER);
		try {
			pdfWriter = PdfWriter.getInstance(document, 
				  new FileOutputStream(pdfToCreate));
		} catch (Exception e){
			e.printStackTrace();
			//TODO: do something... but what?...
		}
	    
	    document.open();
	    document.addCreationDate();
	}
//-----------------------------------------------------------------------------
	public void addTitle(){
		document.addTitle("Hey Look, I'm a PDF!");
	}	
//-----------------------------------------------------------------------------
}



