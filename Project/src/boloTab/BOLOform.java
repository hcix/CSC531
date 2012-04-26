package boloTab;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import net.miginfocom.swing.MigLayout;
import progAdmin.itemsToReview.ItemToReview;
import program.ResourceManager;
import utilities.FileHelper;
import utilities.calendar.JCalendarPanel;
import utilities.calendar.TimePanel;
import utilities.ui.ImageHandler;
import utilities.ui.ImagePreview;
import utilities.ui.ResizablePhotoDialog;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * The <code>BOLOform</code> class is where the information of a given <code>Bolo</code>
 * is entered by the user. 
 */
public class BOLOform extends JDialog {
	private static final long serialVersionUID = 1L;
	JTextField ageField,raceField,sexField,heightField,weightField,buildField;
	JTextField eyesField,hairField;
	JTextField toiField,referenceField,caseNumField,ifYesField;
	JTextField preparedByField,approvedByField,dateField,timeField;
	JTextArea otherDescriptField,narrativeText; 
	JCheckBox toReview;
	JComboBox<String> statusField;
	JCheckBox[] boxes;
	JSpinner incidentDate, incidentTime, preparedDate, preparedTime;
	Bolo bolo;
	ResourceManager rm;
	JFrame parent;
	JPanel photoArea;
	JPanel dialogPanel;
	JCalendarPanel jcal;
	TimePanel time;
	/** a reference to the main <code>BOLOtab</code> used to tell 
	 * <code>BOLOtab</code> to refresh its contents after a delete operation */
	BOLOtab bolotab;
	/** lets the main BOLOtab know if a new BOLO was created during the last
	 * invocation of this dialog */
	boolean newBOLOWascreated; 
//-----------------------------------------------------------------------------
	/**
	 * Creates a new window, sets the window and creates a new <code>Bolo</code> instance
	 * 
	 * @param parent
	 */
	public BOLOform(ResourceManager rm, BOLOtab bolotab){
		super(rm.getGuiParent(), "BOLO", true);
		this.rm=rm;
		this.bolotab=bolotab;

		//Create the BOLO object to add info to
		bolo = new Bolo();

		//Set the size of the form
		this.setPreferredSize(new Dimension(1050,900));
		this.setSize(new Dimension(1050,900));

		dialogPanel = new JPanel(new MigLayout("ins 20", "[]5%[]", "[][][][]"));

		//Make the form scrollable
		JScrollPane dialogPanelScroller = new JScrollPane(dialogPanel);
		dialogPanelScroller.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeAndCancel();
			}
		});

		/*Set up the BOLO form*/

		//Add the BOLO "letter head" image to the top
		ImageIcon boloHeaderIcon = 
				ImageHandler.createImageIcon("images/boloHeader2.png");
		JPanel boloHeaderPanel = new JPanel();
		boloHeaderPanel.add(new JLabel(boloHeaderIcon));
		dialogPanel.add(boloHeaderPanel, "dock north, wrap");

		//Photo/video panel
		JPanel photoVideoPanel = createPhotoVideoPanel();
		dialogPanel.add(photoVideoPanel, "align left, wrap");

		//Physical description panel
		JPanel physicalDescriptPanel = createPhysicalDescriptionPanel();
		dialogPanel.add(physicalDescriptPanel, "align left");//, wrap");

		//Incident info panel
		JPanel incidentInfoPanel = createIncidentInfoPanel();
		dialogPanel.add(incidentInfoPanel ,"align left, wrap");//,"align left, growx");

		//Narrative area
		JPanel narrativePanel = createNarrativePanel();
		dialogPanel.add(narrativePanel, "align left, wrap, growx");

		//Administrative panel
		JPanel adminPanel = createAdministrativePanel();
		dialogPanel.add(adminPanel, "align left, growx");
	
		dialogPanelScroller.setColumnHeaderView(createToolbar());	    

		//Add the BOLO form scrolling pane dialog to the screen
		Container contentPane = getContentPane();
		contentPane.add(dialogPanelScroller);

	}
//-----------------------------------------------------------------------------	
	/**
	 * Creates a new <code>BOLOform</code> with the info from the given 
	 * <code>Bolo</code> object loaded in.
	 * 
	 * @param parent
	 * @param bolo
	 * @see loadFromExistingBOLO()
	 */
	public BOLOform(ResourceManager rm, BOLOtab bolotab, Bolo bolo){
		this(rm, bolotab);
		this.bolo = bolo;
		loadFromExistingBOLO();
	}
//-----------------------------------------------------------------------------	
	/**
	 * Creates the panel to input the physical description information.
	 */
	private JPanel createPhysicalDescriptionPanel(){
		JPanel infoPanel = new JPanel(new MigLayout("","","[][][][][][nogrid]"));

		SwingHelper.addTitledBorder(infoPanel, "Physical Description");

		// create labels
		JLabel ageLabel = new JLabel("<html>Approx.<br>Age</html>");
		JLabel raceLabel = new JLabel("Race");
		JLabel sexLabel = new JLabel("Sex");
		JLabel heightLabel = new JLabel("<html>Approx.<br>Height</html>");
		JLabel weightLabel = new JLabel("<html>Approx.<br>Weight</html>");
		JLabel buildLabel = new JLabel("Build");
		JLabel eyesLabel = new JLabel("Eyes");
		JLabel hairLabel = new JLabel("Hair");
		JLabel otherDescriptionLabel = new JLabel("Other Description/Info");

		// create fields
		ageField = new JTextField(4);
		raceField = new JTextField(SwingHelper.EXTRA_SMALL_TEXT_FIELD_LENGTH);
		sexField = new JTextField(SwingHelper.ONE_CHAR_TEXT_FIELD_LENGTH);
		heightField = new JTextField(SwingHelper.EXTRA_SMALL_TEXT_FIELD_LENGTH);
		weightField = new JTextField(SwingHelper.EXTRA_SMALL_TEXT_FIELD_LENGTH);
		buildField = new JTextField(10);
		eyesField = new JTextField(10);
		hairField = new JTextField(10);
		otherDescriptField = new JTextArea(5, 40);
		otherDescriptField.setLineWrap(true);
		JScrollPane otherDescriptScrollPane = new JScrollPane(otherDescriptField);
		otherDescriptScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		ifYesField = new JTextField(SwingHelper.MEDIUM_TEXT_FIELD_LENGTH);

		//add row 1 fields
		infoPanel.add(ageLabel, "align left");
		infoPanel.add(ageField, "align left");
		infoPanel.add(raceLabel, "align left");
		infoPanel.add(raceField, "align left");
		infoPanel.add(sexLabel, "align left");
		infoPanel.add(sexField, "align left, wrap");
		//add row 2 fields
		infoPanel.add(heightLabel, "align left");
		infoPanel.add(heightField, "align left");
		infoPanel.add(weightLabel, "align left");
		infoPanel.add(weightField, "align left");
		infoPanel.add(buildLabel, "align left");
		infoPanel.add(buildField, "align left, wrap");
		//add row 3 fields
		infoPanel.add(eyesLabel, "align left");
		infoPanel.add(eyesField, "align left");
		infoPanel.add(hairLabel, "align left");
		infoPanel.add(hairField, "align left, wrap");
		//add other description area
		infoPanel.add(otherDescriptionLabel, "spanx");
		infoPanel.add(otherDescriptScrollPane, "spanx, wrap");
		//add "armed?" area
		boxes = SwingHelper.addArmedQuestionCheckboxes(infoPanel, ifYesField);

		return infoPanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * Creates the panel to input the incident information.
	 */
	private JPanel createIncidentInfoPanel(){
		JPanel infoPanel = new JPanel(new MigLayout());

		SwingHelper.addTitledBorder(infoPanel, "Incident Info");

		//create labels
		JLabel referenceLabel = new JLabel("Reference");
		JLabel caseNumLabel = new JLabel("Case #");
		JLabel statusLabel = new JLabel("Status");


		//create fields
		String[] statusStrings = { "", "Need to Identify", "Identified", "Apprehended", "Cleared" };
		referenceField = new JTextField(15);
		caseNumField = new JTextField(15);
		statusField = new JComboBox<String>(statusStrings);

		//row 1
		//incidentDate = SwingHelper.addDateSpinner(infoPanel, "Date of Incident");
		//DEBUG incidentTime = SwingHelper.addTimeSpinner(infoPanel, "Time of Incident");
		//infoPanel.add(new JCalendarPanel(rm.getGuiParent()), "spanx, wrap");
		
		jcal = new JCalendarPanel(rm.getGuiParent(), infoPanel);
		time = new TimePanel(infoPanel, "Time of Incident");

		infoPanel.add(referenceLabel, "align");
		infoPanel.add(referenceField, "align, wrap");
		infoPanel.add(caseNumLabel, "align");
		infoPanel.add(caseNumField, "align, wrap");
		infoPanel.add(statusLabel, "align");
		infoPanel.add(statusField, "align, wrap");

		return infoPanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * Creates the panel to input the narrative information.
	 */
	private JPanel createNarrativePanel(){
		JPanel narrativePanel = new JPanel(new MigLayout());

		SwingHelper.addTitledBorder(narrativePanel, "Narrative/Remarks");

		// create fields
		narrativeText = new JTextArea(10, 30);
		narrativeText.setLineWrap(true);
		JScrollPane otherDescriptScrollPane = new JScrollPane(narrativeText);
		otherDescriptScrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		//add other description area
		narrativePanel.add(otherDescriptScrollPane, "growx, align center");

		return narrativePanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * Creates the panel to input the administrative information.
	 */
	private JPanel createAdministrativePanel(){
		JPanel adminPanel = new JPanel(new MigLayout());

		SwingHelper.addTitledBorder(adminPanel, "Administrative Info");

		// create labels
		JLabel preparedByLabel = new JLabel("BOLO prepared by");
		JLabel approvedByLabel = new JLabel("BOLO approved by");
		

		// create fields
		preparedByField = new JTextField(15);
		approvedByField = new JTextField(15);

		toReview = new JCheckBox("Send BOLO to Items to Review?");

		//add labels & text fields to  panel
		adminPanel.add(preparedByLabel, "align");
		adminPanel.add(preparedByField, "align, wrap");
		adminPanel.add(approvedByLabel, "align");
		adminPanel.add(approvedByField, "align, wrap");
		preparedDate = SwingHelper.addDateSpinner(adminPanel, "Date BOLO prepared");
		//DEBUG:	preparedTime = SwingHelper.addTimeSpinner(adminPanel, "Time BOLO prepared");
		
		
		adminPanel.add(toReview);

		return adminPanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * Creates the panel to allow user to add a photo or video.
	 */
	private JPanel createPhotoVideoPanel(){
		JPanel photoVideoPanel = new JPanel(new MigLayout("fillx"));
		
//		this.setPreferredSize(new Dimension(1050,900));
//		this.setSize(new Dimension(1050,900));
		
		//Create initial no-photo place holder photo
		final JPanel photoPanel = new JPanel();
		photoArea = photoPanel;
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoPanel.add(noPhotoLabel);
		photoVideoPanel.add(photoPanel, "spanx,grow,wrap");

		JButton addPhotoButton = SwingHelper.createImageButton("Add a Photo", 
				"icons/camera.png");
		addPhotoButton.setToolTipText("Attach a photo to this BOLO");
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

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(addPhotoButton);
		buttonsPanel.add(addVideoButton);

		photoVideoPanel.add(buttonsPanel, "dock south");

		return photoVideoPanel;
	}
//-----------------------------------------------------------------------------
	/**
	 * Loads a video.
	 */
	private void loadVideo() {
		// show choose photo dialog
			final JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(parent);

			// if a photo was selected, add it to BOLO and load into photo area
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// copy the chosen photo into the program's 'Photos' directory
				File file = fc.getSelectedFile();
				bolo.setVideoFilePath(Paths.get(file.getAbsolutePath()));
			}	
	}	
//-----------------------------------------------------------------------------
	/**
	 * Creates the toolbar.
	 */
	private JToolBar createToolbar(){
		//JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBorder(BorderFactory.createLineBorder(Color.black));
		toolbar.setLayout(new MigLayout("fillx", "[][]push[]", null));
		
		//Cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", 
				"icons/cancel_48.png");
		cancelButton.setToolTipText("Cancel and do not save");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				closeAndCancel();
			}
		});

	    //Save button
	    JButton saveButton = SwingHelper.createImageButton("Save", 
	    		"icons/save_48.png");
	    saveButton.setToolTipText("Save BOLO");
	    saveButton.addActionListener(new ActionListener( ) {
			public void actionPerformed(ActionEvent e) {
	    		saveAndClose();
	    	}
	    });

	    //Preview button
	    JButton previewButton = new JButton("Preview");
	    previewButton.setToolTipText("Preview and print final BOLO document");
	    previewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	    		//setVisible(false);
	    		putInfoIntoBoloObject();
	    		BOLOpreview preview = new BOLOpreview(rm, bolotab, bolo);
	    		preview.setVisible(true);
	    		preview.setModal(true);
	    		if(preview.isNewBoloWasCreated()){
	    			setVisible(false);
	    			newBOLOWascreated=true;
	    			eraseForm();
	    		} else{
	    			newBOLOWascreated=false;
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
	 * Saves the information input into this form and close the dialog.
	 */
	private void saveAndClose(){

		//place the info from the fields into a BOLO object
		putInfoIntoBoloObject();
		
		if (this.toReview.isSelected()) {
			createItemToReview(bolo);
		}

		//add the BOLO object's info to the database
		try {
			bolo.addToDB();
		} catch (Exception e) {
			System.out.println("error: unable to add BOLO to DB");
			e.printStackTrace();
		}
		
		//reset the form
		eraseForm();

		newBOLOWascreated=true;

		//close the window
		this.dispose();	
	}
//-----------------------------------------------------------------------------
	 /**
	  * Places the info from the input fields into the global BOLO object.
	  */
	 private void putInfoIntoBoloObject(){
		 String age, race, sex, height, weight, build, eyes, hair;
		 String reference, caseNum, status, weapon;
		 String preparedBy, approvedBy;
		 String otherDescrip, narrative;

		 //set the filled in fields in the global BOLO object
		 age = ageField.getText();
		 if(!age.isEmpty()){ bolo.setAge(age); }
		 race = raceField.getText();
		 if(!race.isEmpty()){ bolo.setRace(race); }
		 sex = sexField.getText();
		 if(!sex.isEmpty()){ bolo.setSex(sex); }
		 height = heightField.getText();
		 if(!height.isEmpty()){ bolo.setHeight(height); }
		 weight=weightField.getText();
		 if(!weight.isEmpty()){ bolo.setWeight(weight); }
		 build=buildField.getText();
		 if(!build.isEmpty()){ bolo.setBuild(build); }
		 eyes=eyesField.getText();
		 if(!eyes.isEmpty()){ bolo.setEyes(eyes); }
		 hair=hairField.getText();
		 if(!hair.isEmpty()){ bolo.setHair(hair); }
		 reference=referenceField.getText();
		 if(!reference.isEmpty()){ bolo.setReference(reference); }
		 caseNum=caseNumField.getText();
		 if(!caseNum.isEmpty()){ bolo.setCaseNum(caseNum); }
		 status=(String)statusField.getSelectedItem();
		 if(!status.isEmpty()){ bolo.setStatus(status); }
		 weapon=ifYesField.getText();
		 if(!weapon.isEmpty()){ bolo.setWeapon(weapon); }
		 preparedBy= preparedByField.getText();
		 if(!preparedBy.isEmpty()){ bolo.setPreparedBy(preparedBy); }
		 approvedBy= approvedByField.getText();
		 if(!approvedBy.isEmpty()){ bolo.setApprovedBy(approvedBy); }
		 otherDescrip= otherDescriptField.getText();
		 if(!otherDescrip.isEmpty()){ bolo.setOtherDescrip(otherDescrip); }
		 narrative=narrativeText.getText();
		 if(!narrative.isEmpty()){ bolo.setNarrative(narrative); }

		 
		 //set the times
		 Date incidentDate = jcal.getDateSet();
		 long dateVal = incidentDate.getTime();
		 bolo.setincidentDate(dateVal);
		 long timeVal = time.getTimeEpoch();
		 bolo.setincidentTime(timeVal);
		 
		 
//HAD TO COMMENT OUT TO WMAKE WORK
		// bolo.setincidentDate(getIncidentDateEpoch());
		// bolo.setincidentTime(getIncidentTimeEpoch());


	}
//-----------------------------------------------------------------------------
	 /**
	  * Places the info from the input fields into the global BOLO object.
	  */
	 private void loadFromExistingBOLO(){
		 //set the filled in fields in the global BOLO object
		 ageField.setText(bolo.getAge());

		 raceField.setText(bolo.getRace());		
		 sexField.setText(bolo.getSex());		 
		 heightField.setText(bolo.getHeight());
		 weightField.setText(bolo.getWeight());
		 buildField.setText(bolo.getBuild());
		 eyesField.setText(bolo.getEyes());
		 hairField.setText(bolo.getHair());
		 referenceField.setText(bolo.getReference());
		 caseNumField.setText(bolo.getCaseNum());
		 statusField.setSelectedItem(bolo.getStatus());
		 ifYesField.setText(bolo.getWeapon());
		 preparedByField.setText(bolo.getPreparedBy());
		 approvedByField.setText(bolo.getApprovedBy());
		 otherDescriptField.setText(bolo.getOtherDescrip());
		 narrativeText.setText(bolo.getNarrative());

		 //TODO: set the times
		 //incidentDate.setValue(new Date (bolo.getincidentDate()));
//DEBUG incidentTime.setValue(new Date (bolo.getincidentTime()));
		 		 
		 jcal.setDate(bolo.getincidentDate());
		 time.setTime(bolo.getincidentTime());
		 
		 //set picture
		 if(bolo.getPhotoFilePath()!=null){
			 ImageIcon photo = ImageHandler.getScaledImageIcon(
				 bolo.getPhotoFilePath(), 200, 299);

			photoArea.removeAll();
			photoArea.add(new JLabel(photo));
		}
		dialogPanel.validate();

	}
//-----------------------------------------------------------------------------
	/**
	 * Brings up a file chooser and allows user to choose and add a photo.
	 */
	private void chooseAndAddPhoto(final JPanel photoPanel){
		//show choose photo dialog
		final JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(FileHelper.getImageFilter());
		fc.setAcceptAllFileFilterUsed(false);
		fc.setAccessory(new ImagePreview(fc));
		int returnVal = fc.showOpenDialog(parent);

		//if a photo was selected, add it to BOLO and load into photo area
		if(returnVal==JFileChooser.APPROVE_OPTION){
			//copy the chosen photo into the program's 'Photos' directory
			final File file = fc.getSelectedFile();

			//DEBUG System.out.printf("filepath = %s\n", file.getPath());

			ImageIcon chosenPhoto = new ImageIcon(file.getPath());

			final ResizablePhotoDialog resizeDialog = new ResizablePhotoDialog(
					chosenPhoto, this, file.getName());

			//if the user pressed the set photo button
			if(resizeDialog.getNewPhotoFilePath()!=null){
				bolo.setPhotoFilePath(resizeDialog.getNewPhotoFilePath());
				photoPanel.removeAll();

				photoPanel.add(new JLabel(resizeDialog.getResizedImgIcon()));

				(photoPanel.getParent()).validate();
			}

		}

	}
//-----------------------------------------------------------------------------
	/**
	 * Erase any fields in the form that have been filled in and close the
	 * dialog.
	 */
	private void closeAndCancel() {
		//reset the form
		eraseForm();

		//delete the photo(if any)
		/*if(bolo.getPhotoFilePath()!=null){

			//DEBUG
			System.out.printf("\nBOLOform: closeAndCancel(): deleting " +
					"bolo.getPhotoFilePath().toString() " +
					"= %s\n", bolo.getPhotoFilePath().toString());

			File f=new File(bolo.getPhotoFilePath().toString());
			if(f.exists() && f.isFile()){
				f.delete();
			}
		}*/


		newBOLOWascreated=false;
		//close the dialog
		this.dispose();	
	}
//-----------------------------------------------------------------------------
	/**
	 * Erases <code>BOLOfrom</code>
	 */
	private void eraseForm(){
		//set the text of all the form's fields to null
		ageField.setText(null);
		raceField.setText(null);
		sexField.setText(null);
		heightField.setText(null);
		weightField.setText(null);
		buildField.setText(null);
		eyesField.setText(null);
		hairField.setText(null);
		referenceField.setText(null);
		caseNumField.setText(null);
		statusField.setSelectedItem("");
		ifYesField.setText(null);
		//ifYesField.setVisible(false);
		preparedByField.setText(null);
		approvedByField.setText(null);
		otherDescriptField.setText(null);
		narrativeText.setText(null);
		boxes[0].setSelected(false);
		boxes[1].setSelected(false);
		
		//recreate the photo/video section
		photoArea.removeAll();
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoArea.add(noPhotoLabel);
		(photoArea.getParent()).validate();
	 }
//-----------------------------------------------------------------------------
	 /**
	  *  
	  * @return preparedCal.getTimeInMillis()/1000
	  */
	 public long getPrepDateEpoch(){
		  Date day = new Date();
		  Date time = new Date();

		  Calendar preparedCal = Calendar.getInstance();
		  Calendar timeCal = Calendar.getInstance();

		  day = ((SpinnerDateModel) preparedDate.getModel()).getDate();
		  time = ((SpinnerDateModel) preparedTime.getModel()).getDate();
		  timeCal.setTime(time);

		  preparedCal.setTime(day);
		  preparedCal.set(Calendar.HOUR, timeCal.get(Calendar.HOUR));
		  preparedCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		  preparedCal.set(Calendar.AM_PM, timeCal.get(Calendar.AM_PM));

		  return (preparedCal.getTimeInMillis()/1000);
	  }
//-----------------------------------------------------------------------------
	  /**
	   * 
	   * @return 
	   */
	  public long getIncidentDateEpoch(){
		  Date incidentDateLong; //to be converted

		  incidentDate.getAccessibleContext(); //grabs from the JSpinner
		  incidentDateLong = (Date)(incidentDate.getValue());
			  
		  //error checking
		  SimpleDateFormat df = new SimpleDateFormat();
		  df.applyPattern("dd/MM/yyyy hh:mm a");
	      System.out.println("getIncidentDate: "+ df.format((Date)incidentDate.getValue()));
	      System.out.println("getIncidentDate: "+ incidentDateLong.getTime());
	      
		  
		  return (incidentDateLong.getTime());
	  }
//-----------------------------------------------------------------------------	
	  public long getIncidentTimeEpoch() {
		  Date incidentTimeLong;
		  
	      incidentTime.getAccessibleContext();//to be converted
	      incidentTimeLong = (Date)incidentTime.getValue();
	      
	      //error checking
	      SimpleDateFormat df1 = new SimpleDateFormat();
		  df1.applyPattern("dd/MM/yyyy hh:mm a");
	      System.out.println("getIncidentTime: "+ df1.format((Date)incidentTime.getValue()));
	      System.out.println("getIncidentTime: "+ incidentTimeLong.getTime());
		  
		  return (incidentTimeLong.getTime());
	  }
//-----------------------------------------------------------------------------

	/**
	 * JDOC
	 * @return this.newBOLOWascreated
	 */
	public boolean isNewBOLOWascreated(){
		return this.newBOLOWascreated;
	}
//-----------------------------------------------------------------------------
	public void createItemToReview(Bolo bolo) {

		this.bolo = bolo;
		StringBuilder title = new StringBuilder();

		title.append("BOLO #");
		title.append(bolo.getCaseNum());


		String stringTitle = title.toString();
		ItemToReview newItem = new ItemToReview(stringTitle,"");
		rm.addItem(newItem);
	}
//-----------------------------------------------------------------------------	
}
