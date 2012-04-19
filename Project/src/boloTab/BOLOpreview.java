package boloTab;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import program.ResourceManager;
import net.miginfocom.swing.MigLayout;
import utilities.ui.ImageHandler;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * The <code>BOLOpreview</code> class displays a summary of the 
 * <code>BOLOform</code> for a given <code>Bolo</code> that can be edited, 
 * saved, printed, and emailed.
 */
public class BOLOpreview extends JDialog implements ActionListener {
private static final long serialVersionUID = 1L;
	String age, race, sex, height, weight, build, eyes, hair;
	String reference, caseNum, status, weapon;
	String preparedBy, approvedBy;
	String otherDescrip, narrative;
	/** the BOLO holding the info currently displayed in this dialog **/
	Bolo bolo;
	/** a reference to the main <code>BOLOtab</code> used to tell 
	 * <code>BOLOtab</code> to refresh its contents after a delete operation */
	BOLOtab bolotab;
	JPanel dialogPanel;
	ResourceManager rm;
	boolean newBOLOWascreated;
//-----------------------------------------------------------------------------
	/**
	 * Generates the <code>BOLOpreview</code> window  with all necessary fields 
	 * to view a given <code>Bolo</code>
	 * 
	 * @param parent
	 * @param bolo
	 */
	public BOLOpreview(ResourceManager rm, BOLOtab bolotab, Bolo bolo){
		super(rm.getGuiParent(), "BOLO", true);

		//BOLO object to load info from
		this.bolo = bolo;
		this.bolotab=bolotab;
		this.rm=rm;
		
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
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});

		/*Set up the BOLO page*/
		
		//Add the BOLO "letter head" image to the top
		ImageIcon boloHeaderIcon = 
				ImageHandler.createImageIcon("images/boloHeader2.png");
		JPanel boloHeaderPanel = new JPanel();
		boloHeaderPanel.setBackground(Color.WHITE);
		boloHeaderPanel.add(new JLabel(boloHeaderIcon));
		dialogPanel.add(boloHeaderPanel, "dock north");
	
		//Add photo/video panel
		JPanel photoVideoPanel = createPhotoVideoPanel();
		dialogPanel.add(photoVideoPanel, "align left");

		//Add physical description panel
	    JPanel physicalDescriptPanel = createPhysicalDescriptionPanel();
	    dialogPanel.add(physicalDescriptPanel, "align left, wrap");

	    //Add incident info panel
	    JPanel incidentInfoPanel = createIncidentInfoPanel();
	    dialogPanel.add(incidentInfoPanel, "align left, growx");
	    
	    //Add narrative area
	    JPanel narrativePanel = createNarrativePanel();
	    dialogPanel.add(narrativePanel, "align left, wrap, growx");
	    
	    //Add administrative panel
	    JPanel adminPanel = createAdministrativePanel();
	    dialogPanel.add(adminPanel, "align left");

		
		//TODO: Add standard footer
		
	    //add buttons panel to top of scroll panel
	    JPanel buttonsPanel = createButtonsPanel();
	    dialogPanelScroller.setColumnHeaderView(buttonsPanel);
	    
	    //Add the BOLO form scrolling pane dialog to the screen
	    Container contentPane = getContentPane();
	    contentPane.setLayout(new MigLayout());
	    contentPane.add(dialogPanelScroller);

	 //   JScrollPane pdfv = PDFViewHelper.createZoomablePDFDisplay(
	 //   		"/Users/heatherciechowski/CSC531/Project/Boloex.pdf", createButtonsPanel());
	  //  pdfv.setColumnHeaderView(buttonsPanel);
	 //   contentPane.add(pdfv, "align center");
	    
	}
//-----------------------------------------------------------------------------	
	/**
	 * Creates a description Panel for a <code>Bolo</code> in the <code>BOL
	 * 
	 * @return infoPanel
	 */
	private JPanel createPhysicalDescriptionPanel(){
		JPanel infoPanel = new JPanel(new MigLayout());
		infoPanel.setBackground(Color.WHITE);
		String[] labels = { "Approx. Age: ", "Race", "Sex", "Approx. Height: ", 
				"Approx. Weight: ", "Build: ", "Eyes: ", "Hair: ", 
				"Other Description/Info: " 
				};
		
		//get the info from the BOLO object
		String[] fieldsArray = bolo.getStringFields();
		JLabel label;
		
		//put each existing attribute's name and value into the panel(bolo fields 0-8)
		for(int i=0; i<9; i++){
			if(fieldsArray[i]!=null){ 
//DEBUG System.out.printf("index %d of fieldsArray = %s\n", i, fieldsArray[i]);
				labels[i] = labels[i].concat(fieldsArray[i]); 
//DEBUG System.out.printf("index %d of labels = %s\n", i, labels[i]);
				label = new JLabel(labels[i]);
				infoPanel.add(label, "align, wrap"); 
			}
		}

		return infoPanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * Creates the incident info panel.
	 */
	private JPanel createIncidentInfoPanel(){
		JPanel infoPanel = new JPanel(new MigLayout());
		infoPanel.setBackground(Color.WHITE);
		String[] labels = { "Reference: ", "Case #: ", "Status: ", 
				"Date of Incident", "Time of Incident"};
		
		//get the info from the BOLO object
		String[] fieldsArray = bolo.getStringFields();
		JLabel label;
		
		//put each existing attribute's name and value into the panel(bolo fields 9-11)
		for(int i=9; i<12; i++){
			if(fieldsArray[i]!=null){ 
//DEBUG
				//System.out.printf("index %d of fieldsArray = %s\n", i, fieldsArray[i]);
				labels[i-9] = labels[i-9].concat(fieldsArray[i]); 
//DEBUG
				//System.out.printf("index %d of labels = %s\n", i, labels[i-9]);
				label = new JLabel(labels[i-9]);
				infoPanel.add(label, "align, wrap"); 
			}
		}

		return infoPanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return narrativePanel
	 */
	private JPanel createNarrativePanel(){
		JPanel narrativePanel = new JPanel(new MigLayout());
		narrativePanel.setBackground(Color.WHITE);
		
		JLabel narrative = new JLabel(bolo.getNarrative());
		
		narrativePanel.add(narrative, "align center");
		
		return narrativePanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return adminPanel
	 */
	private JPanel createAdministrativePanel(){
		JPanel adminPanel = new JPanel(new MigLayout());
		adminPanel.setBackground(Color.WHITE);
        // create labels
		
		JLabel fieldLabel;
		JLabel valueLabel;
		
		if(bolo.getPreparedBy()!=null){
			fieldLabel = new JLabel("BOLO prepared by: ");
			valueLabel = new JLabel(bolo.getPreparedBy());
			adminPanel.add(fieldLabel, "align");
			adminPanel.add(valueLabel, "align, wrap");
		}
		if(bolo.getApprovedBy()!=null){
			fieldLabel = new JLabel("BOLO approved by: ");
			valueLabel = new JLabel(bolo.getApprovedBy());
			adminPanel.add(fieldLabel, "align");
			adminPanel.add(valueLabel, "align, wrap");
		}
		
		return adminPanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return photoVideoPanel
	 */
	private JPanel createPhotoVideoPanel(){
		JPanel photoVideoPanel = new JPanel(new MigLayout());
		photoVideoPanel.setBackground(Color.WHITE);
		
		if(bolo.getPhotoFilePath()!=null){
			Path photoPath = bolo.getPhotoFilePath();
			ImageIcon photo = ImageHandler.getScaledImageIcon(photoPath, 200, 299);
			photoVideoPanel.add(new JLabel(photo));
		}
		
		return photoVideoPanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return newBOLOWascreated
	 */
	public boolean isNewBOLOWascreated(){
		  return newBOLOWascreated;
	}
//-----------------------------------------------------------------------------	
	/**
	 * 
	 * @return buttonsPanel
	 */
	private JPanel createButtonsPanel(){
	
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		
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
	    saveButton.setToolTipText("Save BOLO");
	    saveButton.addActionListener(new ActionListener() {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		saveAndClose();
	    	}
	    });
	    
	    //Delete button
	    JButton deleteButton = SwingHelper.createImageButton("Delete", 
	    		"icons/delete_32.png");
	    deleteButton.setToolTipText("Delete BOLO");
	    deleteButton.addActionListener(this);
	    
	    //Edit button
	    JButton editButton = SwingHelper.createImageButton("Edit",
	    		"icons/edit_32.png");
	    editButton.setToolTipText("Edit this BOLO");
	    editButton.addActionListener(new ActionListener( ) {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	    		//BOLO form dialog
				BOLOform formDialog = new BOLOform(rm, bolotab, bolo);
				setVisible(false);
				formDialog.setVisible(true);
	    	}
	    });
	    
	    //Add print button
	    JButton printButton = 
	    		SwingHelper.createImageButton("Print", "icons/print_32.png");
	    printButton.setToolTipText("Print this BOLO document");
	    printButton.addActionListener(new ActionListener( ) {
	    	@Override
			public void actionPerformed(ActionEvent e) {
	 //   		PrintHelper ph = new PrintHelper(dialogPanel);
	    	}
	    });
	    
	    //Add email button
	    JButton emailButton = SwingHelper.createImageButton("Email", "icons/email32.png");
	    emailButton.setToolTipText("Email this BOLO document");
	    emailButton.addActionListener(new ActionListener( ) {
	    	@Override
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
		 //add the BOLO object's info to the database
		 try {
			bolo.addToDB();
		 } catch (Exception e) {
			 //TODO change println below into error message for user
			System.out.println("error: unable to add BOLO to DB");
			e.printStackTrace();
		 }
		 //close the window
		 this.dispose();	
	}
//-----------------------------------------------------------------------------

	@Override
	public void actionPerformed(ActionEvent ev) {
		//attempt to delete the currently displayed BOLO & close this dialog
		deleteBOLOAndClose();	
	}
//-----------------------------------------------------------------------------
	/**
	 * Called when the delete button is 'clicked'. Attempts to delete the 
	 * currently displayed BOLO from the database and the file system.
	 */
	private void deleteBOLOAndClose(){
		try{
			bolo.deleteFromDB();
		} catch (Exception ex) {
			//delete unsuccesssful, show error message and close
			ex.printStackTrace();
			JOptionPane.showMessageDialog(rm.getGuiParent(), "Error occured while " +
					"attempting to delete BOLO from database", "Database Error",
					JOptionPane.ERROR_MESSAGE);
			this.setVisible(false);
			return;
		}
		
		//TODO: Delete BOLO from BOLO directory w/in program 
		// ( delete file: CSC531/Documents/BOLOs/thisBolo.pdf )
		//close and show message confirming delete was successful
		bolotab.refreshRecentBOLOsTab();
		
		this.setVisible(false);
		
		JOptionPane.showMessageDialog(rm.getGuiParent(), "This BOLO has been deleted.", 
				"BOLO Deleted", JOptionPane.INFORMATION_MESSAGE);
		
	}
//-----------------------------------------------------------------------------
}