/**
 * 
 */
package utilities.pdf;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import org.jpedal.PdfDecoder;
//-----------------------------------------------------------------------------
public class PDFViewHelper extends JPanel {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
	public static void addZoomablePDFViewToDialog(JDialog dialog, String fileName, JPanel otherButtons){
		PdfDecoder pdfDecoder = new PdfDecoder(true);
		 
		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		    
		try{
		  //opens and read the specified PDF 
		  pdfDecoder.openPdfFile(fileName);
		  
		  //open page 1 at 100% scaling
		  pdfDecoder.decodePage(0);
		  pdfDecoder.setPageParameters(1,1);
		}catch(Exception e){
		  e.printStackTrace();
		}
		
		try {
			boolean em = pdfDecoder.PDFContainsEmbeddedFonts();
	//		pdfDecoder.isRunningOnMac = true;
			if(em){ System.out.println("admitting it"); }
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JScrollPane pdfDisplay = initPDFDisplay(pdfDecoder, otherButtons);
		
		JPanel buttonsPanel = new JPanel();
	    buttonsPanel.add(new ZoomControls(pdfDecoder));
	    buttonsPanel.add(otherButtons);
	    
	    pdfDisplay.setColumnHeaderView(buttonsPanel);
	    
	    contentPane.add(pdfDisplay,BorderLayout.CENTER);
	    
	    dialog.pack();
		
	}
//-----------------------------------------------------------------------------
	  private static JScrollPane initPDFDisplay(PdfDecoder pdfDecoder, JPanel otherButtons) {
	    
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    
	    scrollPane.setViewportView(pdfDecoder);
  
	    return scrollPane;
	  }
//-----------------------------------------------------------------------------
	public static JScrollPane createPDFDisplay(String fileName) {
		PdfDecoder pdfDecoder = new PdfDecoder(true);
		
		try{
			//opens and read the specified PDF 
			pdfDecoder.openPdfFile(fileName);
			  
			//open page 1 at 100% scaling
			pdfDecoder.decodePage(1);
			pdfDecoder.setPageParameters(1,1);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    
	    scrollPane.setViewportView(pdfDecoder);
	        
	    return scrollPane;
	}
//-----------------------------------------------------------------------------
	public static JScrollPane createZoomablePDFDisplay(String fileName) {
		PdfDecoder pdfDecoder = new PdfDecoder(true);
		
		try{
			//opens and read the specified PDF 
			pdfDecoder.openPdfFile(fileName);
			  
			//open page 1 at 100% scaling
			pdfDecoder.decodePage(1);
			pdfDecoder.setPageParameters(1,1);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    
	    scrollPane.setViewportView(pdfDecoder);
	        
	    scrollPane.setColumnHeaderView(new ZoomControls(pdfDecoder));
	    
	    return scrollPane;
	}	
//-----------------------------------------------------------------------------
	public static JScrollPane createZoomablePDFDisplay(String fileName, JPanel otherButtons) {
		PdfDecoder pdfDecoder = new PdfDecoder(true);
		
		try{
			//opens and read the specified PDF 
			pdfDecoder.openPdfFile(fileName);
			  
			//open page 1 at 100% scaling
			pdfDecoder.decodePage(1);
			pdfDecoder.setPageParameters(1,1);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setPreferredSize(new Dimension(660, 900));
	    
	    JPanel pdfView = new JPanel(new MigLayout());
	    pdfView.add(pdfDecoder, "alignx center");		
	    scrollPane.setViewportView(pdfView);
	        
	    JPanel topPanel = new JPanel(new MigLayout("nogrid"));  
	    topPanel.add(new ZoomControls(pdfDecoder));
	    topPanel.add(otherButtons, "spanx");
	    
	    
	    scrollPane.setColumnHeaderView(topPanel);

	    return scrollPane;
	}	
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------

}