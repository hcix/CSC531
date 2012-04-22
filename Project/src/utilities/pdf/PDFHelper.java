package utilities.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

//-----------------------------------------------------------------------------
/**
  * The <code>PDFHelper</code> class to assist with reading and writing 
  * information to and from a PDF form. <code>PDFHelper</code> works with the
  * iText API to create and edit PDF forms for use throughout the UMPD Management
  * System.
  */
public class PDFHelper {
//-----------------------------------------------------------------------------
	PDFHelper(String fileToRead){ }
//-----------------------------------------------------------------------------
/**
  * Gets the AcroFields of the PDF form specified by the given absolute 
  * filename. AcroFields can be used to read information from an existing PDF
  * form. If the file specified does not exist or is not a PDF form, the 
  * exception is caught and null is returned.
  * @return the AcroFields of the specified PDF form
  */
	public static AcroFields getFormFields(String formFile){
		PdfReader reader = null;

        try {
			reader = new PdfReader(formFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

        AcroFields form = reader.getAcroFields();
        return form;
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets a PdfStamper for a PDF form created from the given template form.
	 * The newly created PDF form is saved with the absolute filename specified
	 * by the newForm parameter. The PdfStamper object can be used to write 
	 * information to the PDF form. The original form is preserved and any
	 * changes made with the PdfStamper are applied to the form saved at the
	 * absolute filename given by newForm. If the originalForm file specified 
	 * does not exist or is not a PDF form, the exception is caught and null 
	 * is returned. This getPdfStamper() method should be used to create new 
	 * PDF forms that are based on a given template form.
	 * 
	 * @return a PdfStamper for the newly created PDF form 
	 */
	public static PdfStamper getPdfStampler(String originalForm, String newForm){
		PdfReader reader = null;
		PdfStamper stamper = null;
		
        try {
			reader = new PdfReader(originalForm);
			stamper = new PdfStamper(reader, new FileOutputStream(newForm));
		} catch (Exception e) {
			e.printStackTrace();
		}

        
        return stamper;   
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets a PdfStamper for the given PDF specified by the absolute filename
	 * given by the form parameter. The PdfStamper object can be used to write 
	 * information to the PDF form. If the form specified does not exist or is
	 * not a PDF form, the exception is caught and null is returned. This 
	 * getPdfStampler() method should be used to to edit existing PDF forms.
	 * 
	 * @return a PdfStamper for the newly created PDF form 
	 */
	public static PdfStamper getPdfStampler(String form){
		PdfReader reader = null;
		PdfStamper stamper = null;
		
        try {
			reader = new PdfReader(form);
			stamper = new PdfStamper(reader, new FileOutputStream(form));
		} catch (Exception e) {
			e.printStackTrace();
		}

        return stamper;   
	}
//-----------------------------------------------------------------------------
	/**
	 * Gets a PdfStamper for the given PDF specified by the absolute filename
	 * given by the form parameter. The PdfStamper object can be used to write 
	 * information to the PDF form. This version of getPdfStampler() writes data
	 * to a ByteArrayOutputStream rather than straight to the file, thus it can
	 * be further manipulated after the initial data is filled in. If the form 
	 * specified does not exist or is not a PDF form, the exception is caught 
	 * and null is returned.
	 * 
	 * @return a PdfStamper for the newly created PDF form 
	 */
	public static PdfStamper getPdfStampler(String form, ByteArrayOutputStream baos){
		PdfReader reader = null;
		PdfStamper stamper = null;
		
        try {
			reader = new PdfReader(form);
			stamper = new PdfStamper(reader, new FileOutputStream(form));
		} catch (Exception e) {
			e.printStackTrace();
		}

        return stamper;   
	}
//-----------------------------------------------------------------------------
	/**
	 * Draws a rectangle
	 * @param content the direct content layer
	 * @param width the width of the rectangle
	 * @param height the height of the rectangle
	 */
	public static void drawRectangle(PdfContentByte content, float width, float height) {
	    content.saveState();
	    PdfGState state = new PdfGState();
	    state.setFillOpacity(0.6f);
	    content.setGState(state);
	    content.setRGBColorFill(0xFF, 0xFF, 0xFF);
	    content.setLineWidth(3);
	    content.rectangle(0, 0, width, height);
	    content.fillStroke();
	    content.restoreState();
	}
//-----------------------------------------------------------------------------
}