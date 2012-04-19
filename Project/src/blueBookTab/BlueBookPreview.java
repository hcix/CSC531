package blueBookTab;

import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	    private Desktop.Action action = Desktop.Action.OPEN;
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
//DEBUG
System.out.println("BlueBookPreview: constructor ");
			//BlueBookEntry object to load info from
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

			
			//Add the BlueBookEntry "letter head" image to the top
			//ImageIcon BlueBookEntryHeaderIcon = 
			//		ImageHandler.createImageIcon("images/.png");
			//JPanel headerPanel = new JPanel();
			//headerPanel.setBackground(Color.WHITE);
			//headerPanel.add(new JLabel(BlueBookEntryHeaderIcon));
			//dialogPanel.add(headerPanel, "dock north");
			
			
			//dialogPanel.add(new JLabel("" + bbEntry.getBbID()));
			
			//dialogPanelScroller.setColumnHeaderView(createToolbar());
		    
		    //Add the BlueBookEntry form scrolling pane dialog to the screen
		    //Container contentPane = getContentPane();
		    //contentPane.add(dialogPanelScroller);
		    
		    
			//Create a PDF from the given BlueBookEntry based on the form template
			String filename="";
			try{
				filename = bbEntry.createPdf(bbEntry.createNewUniqueFilename());
			} catch(Exception e){ e.printStackTrace(); }
			
			JScrollPane scroller = new JScrollPane();
		    
			pdfv = new PDFView(filename, scroller, createButtonsPanel());
			Container contentPane = this.getContentPane();
			contentPane.add(scroller);
		    
		}
//-----------------------------------------------------------------------------	
	/**
	 * JDOC
	 */
		private JPanel createButtonsPanel(){
		JToolBar toolbar = new JToolBar(SwingConstants.HORIZONTAL);
		toolbar.setFloatable(false);
		
		JPanel panel = new JPanel();
		
		//Cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_32.png");
		cancelButton.setToolTipText("Cancel and do not save");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				closeAndCancel();
			}
		});
	
	    //Save button
	    JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_32.png");
	    saveButton.setToolTipText("Save BlueBookEntry");
	    saveButton.addActionListener(new ActionListener() {
	    	@Override
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
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		//BlueBookEntry form dialog
				BlueBookForm formDialog = new BlueBookForm(parent, bbTab, bbEntry);
				setVisible(false);
				formDialog.setVisible(true);
	    	}
	    });
	    
	    //Add print button
	    JButton printButton = 
	    		SwingHelper.createImageButton("Print", "icons/print_32.png");
	    printButton.setToolTipText("Print this BlueBookEntry document");
	    printButton.setActionCommand(PRINT_ACTION);
	    printButton.addActionListener(this);
	
	    //Email button
	    JButton emailButton = SwingHelper.createImageButton("Email", "icons/email32.png");
	    emailButton.setToolTipText("Email this BlueBookEntry document");
	    emailButton.setActionCommand(EMAIL_ACTION);
	    emailButton.addActionListener(this);

	    /*
	    JPanel saveAndCancelButtonsPanel = new JPanel();
	    saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
	    saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
	    JPanel printAndEmailButtonPanel = new JPanel(new MigLayout("rtl"));
	    printAndEmailButtonPanel.add(printButton);
	    printAndEmailButtonPanel.add(emailButton);
	    printAndEmailButtonPanel.add(editButton);
	    toolbar.add(saveAndCancelButtonsPanel, "shrinky");
	    toolbar.add(printAndEmailButtonPanel, "growx, shrinky");
	    toolbar.add(deleteButton);
	    */
	    
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
		  setVisible(false);
	 }
//-----------------------------------------------------------------------------
	/**
	* Save the information input into this form and close the dialog.
	*/
	private void saveAndClose(){	 
		 //add the BlueBookEntry object's info to the database
		 try {
			bbEntry.addToDB();
		 } catch (Exception e) {
			 //TODO change println below into error message for user
			System.out.println("error: unable to add BlueBookEntry to DB");
			e.printStackTrace();
		 }
		 //close the window
		 this.dispose();	
	}
//-----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent ev) {
		if(ev.getActionCommand().equals(DELETE_ACTION)){
			//attempt to delete the currently displayed BlueBookEntry & close this dialog
			deleteEntryAndClose();	
		} else if(ev.getActionCommand().equals(PRINT_ACTION)){
			rm.launchDefaultApplication(bbEntry.filename);
		} else if(ev.getActionCommand().equals(EMAIL_ACTION)){
			Desktop.Action action = Desktop.Action.OPEN;
			//rm.launchMail(ev);
			
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
		
		//TODO: Delete BlueBookEntry from BlueBookEntry directory w/in program 
		// ( delete file: CSC531/Documents/BlueBookEntrys/thisBlueBookEntry.pdf )
		//close and show message confirming delete was successful
		bbTab.refreshBBtab();
		
		this.setVisible(false);
		
		JOptionPane.showMessageDialog(parent, "This BlueBook Entry has been deleted.", 
				"Entry Deleted", JOptionPane.INFORMATION_MESSAGE);
		
	}
//-----------------------------------------------------------------------------
	
}



