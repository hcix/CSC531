package blueBookTab;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import boloTab.BOLOpreview;
import program.ResourceManager;
import net.miginfocom.swing.MigLayout;
import utilities.FileHelper;
import utilities.ui.ImageHandler;
import utilities.ui.ImagePreview;
import utilities.ui.ResizablePhotoDialog;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * The <code>BlueBookForm</code> class is where the information of a given 
 * <code>BlueBookEntry</code> is obtained.  The data can be gathered via user 
 * input or from an existing <code>BlueBookEntry</code>. <p>The 
 * <code>BlueBookForm</code> creates a pop-up window from which the user 
 * can enter BlueBookForm's data. <p>When the <code>BlueBookForm</code> 
 * is saved, its data is transfered to a <code>BlueBookEntry</code> and stores
 * in the BlueBook database. The <code>BlueBookForm</code> class is where the
 * information of a given <code>BlueBookEntry</code> is entered by the user. 
 */
public class BlueBookForm extends JDialog {
	private static final long serialVersionUID = 1L;
	/** BlueBookEntry where the data gathered in the BlueBookForm will be stored. */
	BlueBookEntry bbEntry;
	/** Field where the case number is entered. */
	JTextField caseNumField;
	/** Field where the subject's name is entered. */
	JTextField nameField;
	/** Field where the subject's affiliations are entered. */
	JTextField affiliField;
	/** Field where the subject's last known address is entered */
	JTextField addressField;
	/** Field where the subject's weapon is entered.  Only appears 
	 * if subject is marked as having a weapon */
	JTextField ifYesField;
	/** Field containing the location of the crime */
	JTextArea locationField;
	/** Field describing the crime */
	JTextArea descriptionField;
	JTextArea reasonField;
	JPanel photoArea;
	JPanel inputPanel;
	/** a reference to the main <code>BlueBooktab</code> used to tell 
	 * <code>BlueBooktab</code> to refresh its contents after a delete operation */
	BlueBookTab bbTab;
	ArrayList<String> photoFilePaths = new ArrayList<String>();
	JPanel photoOuterPanel;
	JLabel noPhotoLabel;
	ResourceManager rm;
//-----------------------------------------------------------------------------
	/**
	 * Creates a pop-up window, sets the window and creates a new 
	 * <code>BlueBookEntry</code> instance.  Method is called when the "Create" 
	 * button is clicked in the BlueBook tab. 
	 * 
	 * @param parent
	 */
	public BlueBookForm(ResourceManager rm, BlueBookTab bbTab) {
		super(rm.getGuiParent(), "New Blue Book Entry", true);
		this.bbTab=bbTab;
		this.rm=rm;
		
		//Set the size of the form
		this.setPreferredSize(new Dimension(800,900));
		this.setSize(new Dimension(800,900));
		
		//Create the BlueBookEntry object to add info to
		bbEntry = new BlueBookEntry();
		
		//Create the form
		inputPanel = createInputPanel();
		
		//Make the form scrollable
		JScrollPane inputScrollPane = new JScrollPane(inputPanel);
		inputScrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//Change to use parent's dimensions
		inputScrollPane.setPreferredSize(new Dimension(600, 600)); 
		
		//Put the dialog in the middle of the screen
		this.setLocationRelativeTo(null);
		
		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});
		
		/*Add buttons panel to top of scroll panel as the row header
		 * (the buttons panel stays at the top of the screen even if the top of the form isn't
		 *  currently visible) */
		JPanel buttonsPanel = createButtonsPanel();
		inputScrollPane.setColumnHeaderView(buttonsPanel);
	    
		//Add the Blue Book form dialog to the screen
	    Container contentPane = getContentPane();
	    contentPane.add(inputScrollPane);
	}
//-----------------------------------------------------------------------------
	/**
	 * Constructor method JDOC
	 * 
	 * @param parent - 
	 * @param bbTab -
	 * @param entry -
	 */
	public BlueBookForm(ResourceManager rm, BlueBookTab bbTab, BlueBookEntry entry){
		this(rm, bbTab);
		this.bbEntry = entry;
		loadFromExistingEntry();
	}
//-----------------------------------------------------------------------------
	 /**
	  * Places the info from the input fields into the global 
	  * <code>BlueBookEntry</code> object.
	  */
	 public void loadFromExistingEntry(){
		 //set the filled in fields in the global BlueBookEntry object
			
		caseNumField.setText(bbEntry.getCaseNum()); 
		nameField.setText(bbEntry.getName());
		affiliField.setText(bbEntry.getAffili());
		addressField.setText(bbEntry.getAddress());
		locationField.setText(bbEntry.getLocation());
		descriptionField.setText(bbEntry.getNarrative());
	
		 //set picture
		if(bbEntry.getPhotoFilePath()!=null){
		 ImageIcon photo = ImageHandler.getScaledImageIcon(
				 bbEntry.getPhotoFilePath(), 200, 299);
			if(photo!=null){
				photoArea.removeAll();
				photoArea.add(new JLabel(photo));
			}
		}
		 inputPanel.validate(); 
	 }
//-----------------------------------------------------------------------------
	/**
	 * Create and set the <code>inputPanel</code>.
	 * @return inputPanel
	 */
	 private JPanel createInputPanel() {
		
		// create and set panels
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new MigLayout());
		SwingHelper.addLineBorder(inputPanel);
		
        // create labels
		JLabel caseNumber = new JLabel("Case #: ");
		JLabel name = new JLabel("Name of offender (last, first, middle): ");
		JLabel Affili = new JLabel("Affili: ");
		JLabel Address = new JLabel("Last known address: ");
		JLabel Location = new JLabel("Location of incident: ");
		JLabel Description = new JLabel("Crime description: ");
		JLabel Reason = new JLabel("Narrative/Reason: ");
		
		// create fields
		caseNumField = new JTextField(20);
		nameField = new JTextField(20);
		affiliField = new JTextField(20);
		addressField = new JTextField(20);

		/*
		 * create text areas, embed them in a scroll
		 * pane and set the line wrap and scroll 
		 * properties
		 */
		locationField = new JTextArea(5, 20);
		locationField.setLineWrap(true);
		JScrollPane locationScrollPane = new JScrollPane(locationField);
		locationScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		descriptionField = new JTextArea(10, 20);
		descriptionField.setLineWrap(true);
		JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
		locationScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		reasonField = new JTextArea(10, 20);
		reasonField.setLineWrap(true);
		JScrollPane reasonScrollPane = new JScrollPane(reasonField);
		descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		ifYesField = new JTextField(20);

		//Create and add photo panel
		JPanel photoPanel = createPhotoPanel();
		inputPanel.add(photoPanel, "wrap");
				
		// add to panel
		SwingHelper.addDateSpinner(inputPanel, "Date of Incident: ");
		inputPanel.add(caseNumber, "alignx left");
		inputPanel.add(caseNumField, "align left,wrap");
		inputPanel.add(name, "alignx left");
		inputPanel.add(nameField, "align left, wrap");
		SwingHelper.addDateSpinner(inputPanel, "DOB: ");
		inputPanel.add(Affili, "alignx left");
		inputPanel.add(affiliField, "align left,wrap");
		inputPanel.add(Address, "alignx left");
		inputPanel.add(addressField, "align left,wrap");
		
		inputPanel.add(Location, "align left");
		inputPanel.add(locationScrollPane, "align left, growx, wrap");
		inputPanel.add(Description, "alignx left");
		inputPanel.add(descriptionScrollPane, "align left, growx,  wrap");
		inputPanel.add(Reason, "alignx left");
		inputPanel.add(reasonScrollPane, "align left, growx, wrap");
		
		//add the armed question check boxes
		SwingHelper.addArmedQuestionCheckboxes(inputPanel, ifYesField);
		
		return inputPanel;
	 }
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return buttonsPanel
	 */
	 private JPanel createButtonsPanel(){
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		
		//Add cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_48.png");
		cancelButton.setToolTipText("Cancel and do not save");
		cancelButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		closeAndCancel();
	    	}
	    });
		
	 // Add save button
	    JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_48.png");
	    saveButton.setToolTipText("Save bbEntry");
	    saveButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		saveAndClose();
	    	}
	    });
	    
	    // Add preview button
	    JButton previewButton = new JButton("<html>Preview<br>  Entry</html>");
	    previewButton.setToolTipText("Preview and print final Blue Book entry");
	    previewButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		
	    		//setVisible(false);
	  
	    		putInfoIntoBlueBookEntry();
	    		BlueBookPreview preview = new BlueBookPreview(rm, bbTab, bbEntry);
	    		preview.setVisible(true);
	    		preview.setModal(true);
	    		if(preview.isNewEntryWasCreated()){
	    			setVisible(false);
	    			eraseForm();
	    		} else{
	    			setVisible(true);
	    		}
	    	}
	    });   	    
	    
	    JPanel saveAndCancelButtonsPanel = new JPanel();
	    saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
	    saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
	    JPanel previewButtonPanel = new JPanel(new MigLayout("rtl"));
	    previewButtonPanel.add(previewButton, "tag right");
	    buttonsPanel.add(saveAndCancelButtonsPanel, "shrinky");
	    buttonsPanel.add(previewButtonPanel, "growx, shrinky");
	    SwingHelper.addLineBorder(buttonsPanel);
	    return buttonsPanel;
	 }
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return photoPanel
	 */ 
	 private JPanel createPhotoPanel(){
		photoOuterPanel = new JPanel(new MigLayout("fill"));
		//photoOuterPanel.setSize(500, 250);
		
		final JPanel photoPanel = new JPanel(new MigLayout());
		photoArea = photoPanel;
		//Create initial no-photo place holder photo
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		noPhotoLabel = new JLabel(noPhotoImage);
		photoPanel.add(noPhotoLabel, "span, wrap");
		photoPanel.setSize(800, 300);
		photoOuterPanel.add(photoPanel, "spanx,grow,wrap");
		photoOuterPanel.setSize(800, 300); //testing
		
		JButton addPhotoButton = SwingHelper.createImageButton("Add a Photo", "icons/camera.png");
		addPhotoButton.setToolTipText("Attach a photo to this bbEntry");
		addPhotoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				chooseAndAddPhoto(photoPanel);
			}
		});
		
		JButton addVideoButton = SwingHelper.createImageButton("Add a Video", 
				"icons/videoCamera.png");
		addVideoButton.setToolTipText("Attach a video to this BOLO");
		addVideoButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				loadVideo();
				
			}
		});
		
		photoOuterPanel.add(addPhotoButton);
		photoOuterPanel.add(addVideoButton);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.add(photoOuterPanel);
		return photoOuterPanel;
	}
	 
//-----------------------------------------------------------------------------
	private void loadVideo() {
	    // show choose photo dialog
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);

		// if a photo was selected, add it to BOLO and load into photo area
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// copy the chosen photo into the program's 'Photos' directory
			File file = fc.getSelectedFile();
			bbEntry.setVideoFilePath(Paths.get(file.getAbsolutePath()));
		}		
	}
//-----------------------------------------------------------------------------
	/**
	 * Save the information input into this form and close the dialog.
	 */
	 private void saveAndClose( ) {
		//place the info from the fields into a bbEntry object
		 putInfoIntoBlueBookEntry();
		 
		 //add all current photos to entry
		 bbEntry.setPhotoFilePaths(photoFilePaths);
		 
		 //add the bbEntry object's info to the database
		 try {
			bbEntry.addToDB();
		 } catch (Exception e) {
			System.out.println("error: unable to add bbEntry to DB");
			e.printStackTrace();
		 }
		 
		 this.setVisible(false);
		 
		 //reset the form
		 eraseForm();
		 
		 //close the window
		 this.dispose();	
	}		
//-----------------------------------------------------------------------------
	 /**
	  * Places the info from the input fields into the global BlueBook object.
	  */
	 private void putInfoIntoBlueBookEntry(){
		 String caseNumText, nameText, affiliText, addressText, weapon;
		 String locationText, descritionText, reasonText;
		 String preparedBy;
		 
		 //get the filled in fields in the global bbEntry object
		 caseNumText = caseNumField.getText();
		 if(!caseNumText.isEmpty()){ bbEntry.setCaseNum(caseNumText); }
		 nameText = nameField.getText();
		 if(!nameText.isEmpty()){ bbEntry.setName(nameText); }
		 affiliText = affiliField.getText();
		 if(!affiliText.isEmpty()){ bbEntry.setAffili(affiliText); }
		 addressText = addressField.getText();
		 if(!addressText.isEmpty()){ bbEntry.setAddress(addressText); }
		 weapon=ifYesField.getText();
		 if(!weapon.isEmpty()){ bbEntry.setWeapon(weapon); }
		 locationText=locationField.getText();
		 if(!locationText.isEmpty()){ bbEntry.setLocation(locationText); }
		 descritionText=descriptionField.getText();
		 if(!descritionText.isEmpty()){ bbEntry.setCrimeDescrip(descritionText); }
		 reasonText=reasonField.getText();
		 if(!reasonText.isEmpty()){ bbEntry.setNarrative(reasonText); }
		 
		 if(bbEntry.getPhotoFilePath()==null){
			 bbEntry.setPhotoFilePath(Paths.get(
					 FileHelper.getImageResourcePathName("unknownPerson.jpeg")));
	 }
		 
	 }
//-----------------------------------------------------------------------------
	 /**
	  * Places info from a <code>bbEntry</code> object into the BlueBookForm's data fields.
	  */
	 public void loadFromExistingbbEntry(){

		 //set the form fields to contain info from the bbEntry
		 caseNumField.setText(bbEntry.getCaseNum());
		 nameField.setText(bbEntry.getName());
		 affiliField.setText(bbEntry.getAffili());
		 addressField.setText(bbEntry.getAddress());
		 ifYesField.setText(bbEntry.getWeapon());
		 locationField.setText(bbEntry.getLocation());
		 descriptionField.setText(bbEntry.getCrimeDescrip());
		 reasonField.setText(bbEntry.getNarrative());

		 //set picture
		//remove placeholder
			photoArea.remove(noPhotoLabel);
			JPanel newPanel = new JPanel();
			newPanel.add(new JLabel(bbEntry.getPhoto()), "span");
			newPanel.setVisible(true);
			photoArea.add(newPanel);
		    (photoArea.getParent()).validate();
		 
	 }
//-----------------------------------------------------------------------------
	 /**
	  * Add a photo to the respective entry 
	  * 
	  * @param photoPanel - panel containing the photo. 
	  */
	 public void chooseAndAddPhoto(final JPanel photoPanel){
			//show choose photo dialog
			final JFileChooser fc = new JFileChooser();
			System.out.println("Bluebook form chooseAndAddPhoto after file chooser");
			fc.addChoosableFileFilter(FileHelper.getImageFilter());
			fc.setAcceptAllFileFilterUsed(false);
			fc.setAccessory(new ImagePreview(fc));
			int returnVal = fc.showOpenDialog(getParent());

			//if a photo was selected, add it to BlueBookEntry and load into photo area
			if(returnVal==JFileChooser.APPROVE_OPTION){
				//copy the chosen photo into the program's 'Photos' directory
				final File file = fc.getSelectedFile();
				
				System.out.printf("BlueBookForm: chooseAndAddPhoto: filepath = %s\n", file.getPath());

				ImageIcon chosenPhoto = new ImageIcon(file.getPath());
				
				final ResizablePhotoDialog resizeDialog = new ResizablePhotoDialog(
							chosenPhoto, this, file.getName());

				//if the user pressed the set photo button
				if(resizeDialog.getNewPhotoFilePath()!=null){
					bbEntry.setPhotoFilePath(resizeDialog.getNewPhotoFilePath());
					photoFilePaths.add(resizeDialog.getNewPhotoFilePath().toString());
					
					//remove placeholder
					photoArea.remove(noPhotoLabel);
					JPanel newPanel = new JPanel();
					newPanel.add(new JLabel(resizeDialog.getResizedImgIcon()), "span");
					newPanel.setVisible(true);
					photoArea.add(newPanel);
					//photoArea.add(new JLabel(resizeDialog.getResizedImgIcon()));
				    (photoArea.getParent()).validate();
				}

			}
			
		 }
//-----------------------------------------------------------------------------
	/**
	 * Erase any fields in the form that have been filled in and close the
	 * dialog.
	 */
	 public void closeAndCancel( ) {
		//reset the form
		eraseForm();
		
		//close the dialog
		this.dispose();	
	 }
//-----------------------------------------------------------------------------
	 /**
	  * Sets the form to its default with all fields null
	  */
	 public void eraseForm(){
		//set the text of all the form's fields to null
		caseNumField.setText(null);
		nameField.setText(null);
		affiliField.setText(null);
		addressField.setText(null);
		ifYesField.setText(null);
		locationField.setText(null);
		descriptionField.setText(null);
		reasonField.setText(null);
		
		//recreate the photo/video section
		photoArea.removeAll();
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoArea.add(noPhotoLabel);
		(photoArea.getParent()).validate();
		
	 }
//-----------------------------------------------------------------------------
	 
	 
//-----------------------------------------------------------------------------
}
