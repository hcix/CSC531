package blueBookTab;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import utilities.ui.ImageHandler;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * The <code>BlueBookPreview</code> class displays a summary of the 
 * <code>BlueBookForm</code> for a given <code>BlueBookEntry</code> that 
 * can be edited, saved, printed, and emailed.
 */
public class BlueBookPreview extends JDialog implements ActionListener {
private static final long serialVersionUID = 1L;
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
//-----------------------------------------------------------------------------
	/**
	 * Generates the <code>BlueBookPreview</code> window  with all necessary fields 
	 * to view a given <code>BlueBookEntry</code>
	 * 
	 * @param parent - JDOC
	 * @param bbEntry - JDOC
	 */
	public BlueBookPreview(JFrame parent, BlueBookTab bbTab, BlueBookEntry bbEntry){
			super(parent, "BlueBook Entry", true);

			//BlueBookEntry object to load info from
			this.bbEntry = bbEntry;
			this.parent = parent;
			this.bbTab=bbTab;
			
			//Set the size of the page
			this.setPreferredSize(new Dimension(800,900));
			this.setSize(new Dimension(800,900));
			
			dialogPanel = new JPanel(new MigLayout("ins 20"));
			dialogPanel.setBackground(Color.WHITE);
			
			//Make the page scrollable
			JScrollPane dialogPanelScroller = new JScrollPane(dialogPanel);
			dialogPanelScroller.setVerticalScrollBarPolicy(
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

			//Put the form in the middle of the screen
			this.setLocationRelativeTo(null);

			//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
			this.addWindowListener(new WindowAdapter( ) {
				public void windowClosing(WindowEvent e) {
					closeAndCancel();
				}
			});

			/*Set up the BlueBookEntry page*/
			
			//Add the BlueBookEntry "letter head" image to the top
			ImageIcon BlueBookEntryHeaderIcon = 
					ImageHandler.createImageIcon("images/.png");
			JPanel headerPanel = new JPanel();
			headerPanel.setBackground(Color.WHITE);
			headerPanel.add(new JLabel(BlueBookEntryHeaderIcon));
			dialogPanel.add(headerPanel, "dock north");
		
		    
		    //Add the BlueBookEntry form scrolling pane dialog to the screen
		    Container contentPane = getContentPane();
		    contentPane.setLayout(new MigLayout());
		    contentPane.add(dialogPanelScroller);

		 //   JScrollPane pdfv = PDFViewHelper.createZoomablePDFDisplay(
		 //   		"/Users/heatherciechowski/CSC531/Project/BlueBookEntryex.pdf", 
		    //createButtonsPanel());
		  //  pdfv.setColumnHeaderView(buttonsPanel);
		 //   contentPane.add(pdfv, "align center");
		    
		}
//-----------------------------------------------------------------------------	
	/**
	 * JDOC
	 */
	private JPanel createToolbar(){
	
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		
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
	    deleteButton.addActionListener(this);
	    
	    //Edit button
	    JButton editButton = SwingHelper.createImageButton("Edit",
	    		"icons/edit_32.png");
	    editButton.setToolTipText("Edit this BlueBookEntry");
	    editButton.addActionListener(new ActionListener( ) {
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
	    printButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	 //   		PrintHelper ph = new PrintHelper(dialogPanel);
	    	}
	    });
	    
	    //Email button
	    JButton emailButton = SwingHelper.createImageButton("Email", "icons/email32.png");
	    emailButton.setToolTipText("Email this BlueBookEntry document");
	    emailButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		//TODO implement email
	    	}
	    });
	    
	    JPanel saveAndCancelButtonsPanel = new JPanel();
	    saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
	    saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
	    JPanel printAndEmailButtonPanel = new JPanel(new MigLayout("rtl"));
	    printAndEmailButtonPanel.add(printButton);
	    printAndEmailButtonPanel.add(emailButton);
	    printAndEmailButtonPanel.add(editButton);
	    buttonsPanel.add(saveAndCancelButtonsPanel, "shrinky");
	    buttonsPanel.add(printAndEmailButtonPanel, "growx, shrinky");
	    buttonsPanel.add(deleteButton);
	   
	    return buttonsPanel;
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
	public void actionPerformed(ActionEvent ev) {
		//attempt to delete the currently displayed BlueBookEntry & close this dialog
		deleteEntryAndClose();	
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



