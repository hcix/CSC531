package boloTab;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import utilities.FileHelper;
import utilities.pdf.PDFView;
import utilities.ui.SwingHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
//-----------------------------------------------------------------------------
/**
 *
 */
public class BOLOpreview extends JDialog implements ActionListener {
private static final long serialVersionUID = 1L;
	private static String DELETE_ACTION = "delete";
	private static String PRINT_ACTION = "print";
	private static String EMAIL_ACTION = "email";
	private String name, dob, affili, address, crimeDescrip, narrative;
	private String date, time, location, caseNum, status, weapon;
	private Integer bbID=null;
	private Path photoFilePath = null;
	private Path videoFilePath = null;
	private boolean newBoloWasCreated=false;
	/** the BlueBookEntry holding the info currently displayed in this dialog **/
	Bolo bolo;
	/** a reference to the main JFrame used to create & display this dialog */
	JFrame parent;
	/** reference to the main <code>BlueBookTab</code> used to tell 
	 * <code>BlueBookTab</code> to refresh its contents after a delete operation */
	BOLOtab boloTab;
	/** JDOC */
	JPanel dialogPanel;
	PDFView pdfv;
	ResourceManager rm;
	String filename="";
//-----------------------------------------------------------------------------
	/**
	 * Generates the <code>BOLOPreview</code> window  with all necessary fields 
	 * to view a given <code>Bolo</code>
	 * 
	 * @param parent - JDOC
	 * @param bolo - JDOC
	 */
	public BOLOpreview(ResourceManager rm, BOLOtab boloTab, Bolo bolo){
			super(rm.getGuiParent(), "BOLO", true);
			this.rm=rm;
			this.boloTab=boloTab;
			this.bolo = bolo;
			
			//Set the size of the page
			this.setPreferredSize(new Dimension(800,900));
			this.setSize(new Dimension(800,900));
			
			//Put the form in the middle of the screen
			this.setLocationRelativeTo(null);

			//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
			this.addWindowListener(new WindowAdapter( ) {
				@Override
				public void windowClosing(WindowEvent e) {
					closeAndCancel();
				}
			});
			
			dialogPanel = new JPanel(new MigLayout("ins 20"));
			dialogPanel.setBackground(Color.WHITE);
			
			//Make the page scrollable
			JScrollPane dialogPanelScroller = new JScrollPane(dialogPanel);
			dialogPanelScroller.setVerticalScrollBarPolicy(
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		    
			//Create a PDF from the given BlueBookEntry based on the form template
			try{
				filename = bolo.createNewUniqueFilename();
				createPdf(filename);
			} catch(Exception e){ e.printStackTrace(); }
			
			//Display the newly created pdf
			JScrollPane scroller = new JScrollPane();
			pdfv = new PDFView(filename, scroller, createButtonsPanel());
			Container contentPane = this.getContentPane();
			contentPane.add(scroller);
		}
//-----------------------------------------------------------------------------	
	/**
	 * Create the toolbar buttons.
	 */
	private JPanel createButtonsPanel(){
		JToolBar toolbar = new JToolBar(SwingConstants.HORIZONTAL);
		toolbar.setFloatable(false);
		
		JPanel panel = new JPanel();
		
		//Cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_32.png");
		cancelButton.setToolTipText("Cancel and do not save");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				closeAndCancel();
			}
		});
		
		//Save button
		JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_32.png");
		saveButton.setToolTipText("Save BlueBookEntry");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAndClose();
			}
		});
		
		//Delete button
		JButton deleteButton = SwingHelper.createImageButton("Delete", 
				"icons/delete_32.png");
		deleteButton.setToolTipText("Delete BlueBookEntry");
		deleteButton.setActionCommand(DELETE_ACTION);
		deleteButton.addActionListener(this);
		
		//Edit button
		JButton editButton = SwingHelper.createImageButton("Edit",
				"icons/edit_32.png");
		editButton.setToolTipText("Edit this BlueBookEntry");
		editButton.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
				//BlueBookEntry form dialog
				BOLOform formDialog = new BOLOform(rm, boloTab, bolo);
				setVisible(false);
				formDialog.setVisible(true);
			}
		});
		
		//Add print button
		JButton printButton = 
				SwingHelper.createImageButton("Print", "icons/print_32.png");
		printButton.setToolTipText("Print this BlueBookEntry document");
		printButton.setActionCommand(bolo.getFilename());
		printButton.addActionListener(this);
		
		//Email button
		JButton emailButton = SwingHelper.createImageButton("Email", "icons/email_32.png");
		emailButton.setToolTipText("Email this BlueBookEntry document");
		emailButton.setActionCommand(EMAIL_ACTION);
		emailButton.addActionListener(this);
		
		JPanel saveAndCancelButtonsPanel = new JPanel();
		saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
		saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
		JPanel printAndEmailButtonPanel = new JPanel(new MigLayout("rtl"));
		printAndEmailButtonPanel.add(printButton);
		printAndEmailButtonPanel.add(emailButton);
		printAndEmailButtonPanel.add(editButton);
		panel.add(saveAndCancelButtonsPanel, "shrinky");
		panel.add(printAndEmailButtonPanel, "growx, shrinky");
		panel.add(deleteButton);
		
		//return toolbar;
		return panel;
	}
//-----------------------------------------------------------------------------
	 /**
	  * Close and cancel.
	  */
	private void closeAndCancel() {
		setNewBoloWasCreated(false);
		setVisible(false);
	}
//-----------------------------------------------------------------------------
	/**
	* Save the information input into this form and close the dialog.
	*/
	private void saveAndClose(){	 
		 //add the BlueBookEntry object's info to the database
		 try {
			bolo.addToDB();
		 } catch (Exception e) {
			 //TODO change println below into error message for user
			System.out.println("error: unable to add BlueBookEntry to DB");
			e.printStackTrace();
		 }
		 
		 setNewBoloWasCreated(true);
		 //close the window
		 this.dispose();	
	}
//-----------------------------------------------------------------------------
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand().equals(DELETE_ACTION)){
			//attempt to delete the currently displayed BlueBookEntry & close this dialog
			deleteEntryAndClose();	
		} else if(ev.getActionCommand().equals(bolo.getFilename())){
			rm.onPrintFile(ev);
		} else if(ev.getActionCommand().equals(EMAIL_ACTION)){
			rm.onLaunchMail(ev);
			
		}
	}
//-----------------------------------------------------------------------------
	/**
	 * Called when the delete button is 'clicked'. Attempts to delete the 
	 * currently displayed <code>BlueBookEntry</code> from the database and 
	 * the file system.
	 */
	private void deleteEntryAndClose(){
		try{
			bolo.deleteFromDB();
		} catch (Exception ex) {
			//delete unsuccesssful, show error message and close
			ex.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Error occured while " +
					"attempting to delete BOLO from database", 
					"Database Error", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
			return;
		}
		
		//TODO: Delete BOLO from BOLO directory w/in program 
		// ( delete file: CSC531/Documents/BOLO/thisBOLO.pdf )
		//close and show message confirming delete was successful
		boloTab.refreshRecentBOLOsTab();
		
		this.setVisible(false);
		
		JOptionPane.showMessageDialog(parent, "This BOLO has been deleted.", 
				"BOLO Deleted", JOptionPane.INFORMATION_MESSAGE);
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Save this BlueBookEntry's information in a pdf on the file system.
	 */
	public String createPdf(String fn) throws IOException, DocumentException{
		//put text into the form
		// step 1
	    Document document = new Document();
	    // step 2
	    PdfWriter.getInstance(document, new FileOutputStream(filename));
	    // step 3
	    document.open();
	    // step 4
	    
	    Image header = Image.getInstance(FileHelper.getImageResourcePathName("boloHeader2.png"));
	    header.setAlignment(Image.MIDDLE);
	    header.setBorder(Image.BOX);
	    header.setBorderWidth(10);
	    header.setSpacingAfter(100);
	    header.setBorderColor(BaseColor.WHITE);
	    document.add(header);
	     
	    if(bolo.getPhotoFilePath()!=null){
	    	Image img = Image.getInstance(bolo.getPhotoFilePath().toString());
	    	img.setAlignment(Image.LEFT | Image.TEXTWRAP);
	    	img.setBorder(Image.BOX);
	    	img.setBorderWidth(10);
	    	img.setSpacingAfter(20);
	    	img.setBorderColor(BaseColor.WHITE);
	    	document.add(img);
	    }
	
	    document.add(createPhysDescripTable());
	    document.add(createIncidentInfoTable());
	    document.add(new Paragraph(bolo.getNarrative()));
	    // step 5
	    document.close();
	    
		return fn;
	}
//-----------------------------------------------------------------------------
		/**
		 * Creates a description Table for the current <code>Bolo</code>.
		 * 
		 * @return 
		 */
		private PdfPTable createPhysDescripTable(){
			PdfPTable table = new PdfPTable(2);
			table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
			table.setTotalWidth(200);
		
			String[] fields = { "Approx. Age: ", "Race: ", "Sex: ", "Approx. Height: ", 
					"Approx. Weight: ", "Build: ", "Eyes: ", "Hair: " };
			
			//get the info from the BOLO object
			String[] valsArray = bolo.getStringFields();
			
			//put each existing attribute's name and value into the panel(bolo fields 0-8)
			PdfPCell cell;
			for(int i=0; i<8; i++){
				if(valsArray[i]!=null){ 
					table.addCell(fields[i]);
					table.addCell(valsArray[i]);
				}
			}
			
			if(bolo.getOtherDescrip()!=null){
				table.addCell("Other Description/Info:");
				table.addCell(new Phrase(bolo.getOtherDescrip()));
			}
		
			table.setSpacingAfter(25);
			table.setComplete(true);
			return table;
		}
//-----------------------------------------------------------------------------	
		/**
		 * Creates the incident info panel.
		 */
		private PdfPTable createIncidentInfoTable(){
			PdfPTable table = new PdfPTable(2);
			
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			String[] fields = { "Reference: ", "Case #: ", "Status: ", 
					"Date of Incident", "Time of Incident"};
			
			//get the info from the BOLO object
			String[] valsArray = bolo.getStringFields();

			//put each existing attribute's name and value into the panel(bolo fields 9-11)
			for(int i=9; i<12; i++){
				if(valsArray[i]!=null){ 
					table.addCell(fields[i-9]);
					table.addCell(valsArray[i]);
				}
			}

			if(bolo.getPreparedBy()!=null){
				table.addCell("BOLO prepared by: ");
				table.addCell(bolo.getPreparedBy());
			}
			if(bolo.getApprovedBy()!=null){
				table.addCell("BOLO approved by: ");
				table.addCell(bolo.getApprovedBy());
			}
			
			table.setSpacingAfter(25);
			 return table;
		}
//-----------------------------------------------------------------------------
	public boolean isNewBoloWasCreated(){
		return newBoloWasCreated;
	}
//-----------------------------------------------------------------------------
	private void setNewBoloWasCreated(boolean createdEntry){
		this.newBoloWasCreated = createdEntry;
	}
//-----------------------------------------------------------------------------
}

   


