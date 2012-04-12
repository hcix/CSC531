package utilities.pdf;

import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

//-----------------------------------------------------------------------------
/**
 * Helper class to assist with reading and writing information to and from 
 * a PDF form.
 */
public class PDFHelper {
//-----------------------------------------------------------------------------
	PDFHelper(String fileToRead){
		
	}
//-----------------------------------------------------------------------------
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
}