package blueBookTab;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import program.ResourceManager;
import utilities.FileHelper;
import utilities.dateAndTime.JCalendarPanel;
import utilities.dateAndTime.TimePanel;
import utilities.ui.ButtonHelper;
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
	JTextField dobField;
	/** Field describing the crime */
	JTextArea descriptionField;
	JTextArea reasonField;
	JPanel photoArea;
	JPanel infoPanel;
	/** a reference to the main <code>BlueBooktab</code> used to tell 
	 * <code>BlueBooktab</code> to refresh its contents after a delete operation */
	BlueBookTab bbTab;
	ArrayList<String> photoFilePaths = new ArrayList<String>();
	JPanel photoOuterPanel;
	JLabel noPhotoLabel;
	ResourceManager rm;
	JCheckBox[] boxes;
	JCalendarPanel jcal;
	TimePanel time;
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
		
		JPanel inputPanel = new JPanel(new MigLayout()); 
		//Create & add photo panel
		JPanel photoPanel = createPhotoPanel();
		inputPanel.add(photoPanel, "wrap");
		
		//Create & add the input fields
		infoPanel = createInputPanel();
		inputPanel.add(infoPanel);
		
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
		
		//Add toolbar
		inputScrollPane.setColumnHeaderView(createToolbar());
	    
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
		infoPanel.validate(); 
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
		//SwingHelper.addLineBorder(inputPanel);
		
        // create labels
		JLabel caseNumber = new JLabel("Case #: ");
		JLabel name = new JLabel("Name of offender (last, first, middle): ");
		JLabel Affili = new JLabel("Affili: ");
		JLabel Address = new JLabel("Last known address: ");
		JLabel Location = new JLabel("Location of incident: ");
		JLabel Description = new JLabel("Crime description: ");
		JLabel Reason = new JLabel("Narrative/Reason: ");
		JLabel dob = new JLabel("DOB/Age");
		
		// create fields
		caseNumField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		nameField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		affiliField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		addressField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		dobField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		ifYesField = new JTextField(20);
		
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
		
				
		// add to panel
		//create time and date panels
		jcal = new JCalendarPanel(rm.getGuiParent(), inputPanel, "Date of Incident");
		time = new TimePanel(inputPanel, "Time of Incident");
		inputPanel.add(caseNumber, "alignx left");
		inputPanel.add(caseNumField, "align left,wrap");
		inputPanel.add(name, "alignx left");
		inputPanel.add(nameField, "align left, wrap");
		inputPanel.add(dob, "alignx left");
		inputPanel.add(dobField, "align left, wrap");
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
		boxes = SwingHelper.addArmedQuestionCheckboxes(inputPanel, ifYesField);
		
		return inputPanel;
	 }
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return buttonsPanel
	 */
	 private JToolBar createToolbar(){
		//JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		toolbar.setLayout(new MigLayout("fillx", "[][]push[]", null));
		
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
	    JButton previewButton = ButtonHelper.createPreviewButton(ButtonHelper.LARGE, "");
	    		//new JButton("<html>Preview<br>  Entry</html>");
	    previewButton.setToolTipText("Preview and print final Blue Book entry");
	    previewButton.addActionListener(new ActionListener() {
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

	    toolbar.add(saveButton);
	    toolbar.add(cancelButton);
	    toolbar.add(previewButton);
	    
	    return toolbar;
	 }
//-----------------------------------------------------------------------------
	/**
	 * 
	 * @return photoPanel
	 */ 
	 private JPanel createPhotoPanel(){
		photoOuterPanel = new JPanel(new MigLayout("fill"));
		
		final JPanel photoPanel = new JPanel(new MigLayout());
		photoArea = photoPanel;
		//Create initial no-photo place holder photo
		ImageIcon noPhotoImage = ImageHandler.getProgramImgIcon("images/unknownPerson.jpeg");
		noPhotoLabel = new JLabel(noPhotoImage);
		photoPanel.add(noPhotoLabel, "span, wrap");
		photoPanel.setSize(800, 300);
		photoOuterPanel.add(photoPanel, "spanx,grow,wrap");
		photoOuterPanel.setSize(800, 300); //testing
		
		JButton addPhotoButton = ButtonHelper.createPhotoButton(ButtonHelper.LARGE, "Add a Photo");
		addPhotoButton.setToolTipText("Attach a photo to this bbEntry");
		addPhotoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				chooseAndAddPhoto(photoPanel);
			}
		});
		
		JButton addVideoButton = ButtonHelper.createVideoButton(ButtonHelper.LARGE,	"Add a Video");
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

		//if a photo was selected, add it to Entry and load into photo area
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			//copy the chosen photo into the program's 'Photos' directory
			File file = fc.getSelectedFile();
			bbEntry.setVideoFilePath(Paths.get(file.getAbsolutePath()));
		}		
	}
//-----------------------------------------------------------------------------
	/**
	 * Save the information input into this form and close the dialog.
	 */
	 private void saveAndClose() {
		//place the info from the fields into a bbEntry object
		 putInfoIntoBlueBookEntry();
		 
		 //add all current photos to entry
		 bbEntry.setPhotoFilePaths(photoFilePaths);
		 
		 //add the bbEntry object's info to the database
		 try {
			bbEntry.addToDB();
		 } catch (Exception e) {
			System.out.println("error: unable to add BlueBook Entry to DB");
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
		 String locationText, descritionText, reasonText, dobText;
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
		 dobText=dobField.getText();
		 if(!dobText.isEmpty()){ bbEntry.setDob(dobText); }
		 
		 //set the date & time
		 Date incidentDate = jcal.getDate();
		 long dateVal = incidentDate.getTime();
		 bbEntry.setIncidentDate(dateVal);
		 long timeVal = time.getTimeEpoch();
		 bbEntry.setIncidentTime(timeVal);
	
		 
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
		 dobField.setText(bbEntry.getDob());
		 
		 jcal.setDate(bbEntry.getIncidentDate());
		 time.setTime(bbEntry.getIncidentTime());
		 
		 //set picture; remove placeholder pic
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
		dobField.setText(null);
		boxes[0].setSelected(false);
		boxes[1].setSelected(false);
		
		//reset time and date
		jcal.clearDate();
		time.resetTime();
		
		//recreate the photo/video section
		photoArea.removeAll();
		ImageIcon noPhotoImage = ImageHandler.getProgramImgIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoArea.add(noPhotoLabel);
		(photoArea.getParent()).validate();
		
	 }
//-----------------------------------------------------------------------------
}
