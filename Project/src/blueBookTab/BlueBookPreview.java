package blueBookTab;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import utilities.pdf.PDFView;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * The <code>BlueBookPreview</code> class displays a summary of the 
 * <code>BlueBookForm</code> for a given <code>BlueBookEntry</code> that 
 * can be edited, saved, printed, and emailed.
 */
public class BlueBookPreview extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static String DELETE_ACTION = "delete";
	private static String PRINT_ACTION = "print";
	private static String EMAIL_ACTION = "email";
	private String name, dob, affili, address, crimeDescrip, narrative;
	private String date, time, location, caseNum, status, weapon;
	private Integer bbID=null;
	private Path photoFilePath = null;
	private Path videoFilePath = null;
	private boolean newEntryWasCreated=false;
	/** the BlueBookEntry holding the info currently displayed in this dialog **/
	BlueBookEntry bbEntry;
	/** a reference to the main JFrame used to create & display this dialog */
	JFrame parent;
	/** reference to the main <code>BlueBookTab</code> used to tell 
	 * <code>BlueBookTab</code> to refresh its contents after a delete operation */
	BlueBookTab bbTab;
	/** JDOC */
	JPanel dialogPanel;
	PDFView pdfv;
	ResourceManager rm;
	String filename="";
	
//-----------------------------------------------------------------------------
	/**
	 * Generates the <code>BlueBookPreview</code> window  with all necessary fields 
	 * to view a given <code>BlueBookEntry</code>
	 * 
	 * @param parent - JDOC
	 * @param bbEntry - JDOC
	 */
	public BlueBookPreview(ResourceManager rm, BlueBookTab bbTab, BlueBookEntry bbEntry){
			super(rm.getGuiParent(), "BlueBook Entry", true);
			this.bbEntry = bbEntry;
			this.rm=rm;
			this.bbTab=bbTab;
			
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
				filename = bbEntry.createPdf(bbEntry.createNewUniqueFilename());
			} catch(Exception e){ e.printStackTrace(); }
			
			//Display the newly created pdf
			JScrollPane scroller = new JScrollPane();
			pdfv = new PDFView(filename, scroller, createButtons());
			Container contentPane = this.getContentPane();
			contentPane.add(scroller);
		}
//-----------------------------------------------------------------------------	
	/**
	 * Create the buttons that will go on the toolbar.
	 */
	private JPanel createButtons(){
		JPanel panel = new JPanel(new MigLayout());
		
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
				BlueBookForm formDialog = new BlueBookForm(rm, bbTab, bbEntry);
				setVisible(false);
				formDialog.setVisible(true);
			}
		});
		
		//Add print button
		JButton printButton = 
				SwingHelper.createImageButton("Print", "icons/print_32.png");
		printButton.setToolTipText("Print this BlueBookEntry document");
		printButton.setActionCommand(bbEntry.getFilename());
		printButton.addActionListener(this);
		
		//Email button
		JButton emailButton = SwingHelper.createImageButton("Email", "icons/email_32.png");
		emailButton.setToolTipText("Email this BlueBookEntry document");
		emailButton.setActionCommand(EMAIL_ACTION);
		emailButton.addActionListener(this);

		//add buttons to toolbar
		panel.add(saveButton, "tag ok, split 2, sg");
		panel.add(cancelButton, "tag cancel, sg");
		panel.add(editButton, "sg");
		panel.add(printButton, "sg");
		panel.add(emailButton, "sg");
		
		//return toolbar;
		return panel;
	}
//-----------------------------------------------------------------------------
	 /**
	  * Close and cancel; input info is wiped without saving.
	  */
	private void closeAndCancel() {
		//delete this file from the file system
		File file = new File(filename);
		if(file.exists()){
			file.delete();
		}
				
		setNewEntryWasCreated(false);
		setVisible(false);
	}
//-----------------------------------------------------------------------------
	/**
	* Save the information input into this form and close the dialog.
	*/
	private void saveAndClose(){	 
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		 //add the BlueBookEntry object's info to the database
		 try {
			bbEntry.addToDB();
		 } catch (Exception e) {
			 //TODO change println below into error message for user
			System.out.println("error: unable to add BlueBookEntry to DB");
			e.printStackTrace();
		 }
		 
		 setNewEntryWasCreated(true);
		 
		 this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		 //close the window
		 this.dispose();	
	}
//-----------------------------------------------------------------------------
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand().equals(DELETE_ACTION)){
			//attempt to delete the currently displayed BlueBookEntry & close this dialog
			deleteEntryAndClose();	
		} else if(ev.getActionCommand().equals(bbEntry.getFilename())){
			rm.onPrintFile(ev);
		} else if(ev.getActionCommand().equals(EMAIL_ACTION)){
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			rm.onLaunchMail(bbEntry.getFilename());
			this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
			bbEntry.deleteFromDB();
		} catch (Exception ex) {
			//delete unsuccesssful, show error message and close
			ex.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Error occured while " +
					"attempting to delete BlueBook Entry from database", 
					"Database Error", JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
			return;
		}

		//refesh so delete is shown
		bbTab.refreshBBtab();
		
		this.setVisible(false);
		
		JOptionPane.showMessageDialog(parent, "This BlueBook Entry has been deleted.", 
				"Entry Deleted", JOptionPane.INFORMATION_MESSAGE);
		
	}
//-----------------------------------------------------------------------------
	public boolean isNewEntryWasCreated(){
		return newEntryWasCreated;
	}
//-----------------------------------------------------------------------------
	private void setNewEntryWasCreated(boolean createdEntry){
		this.newEntryWasCreated = createdEntry;
	}
//-----------------------------------------------------------------------------
}



