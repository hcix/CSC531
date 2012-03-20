package boloTab;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import net.miginfocom.swing.MigLayout;
import utilities.FileHelper;
import utilities.ImageHandler;
import utilities.ImagePreview;
import utilities.SwingHelper;

public class BOLOform extends JDialog implements ChangeListener {
	private static final long serialVersionUID = 1L;
	JTextField ageField,raceField,sexField,heightField,weightField,buildField,eyesField,hairField;
	JTextField toiField,referenceField,caseNumField,statusField,ifYesField;
	JTextField preparedByField,approvedByField,dateField,timeField;
	JTextArea otherDescriptField,narrativeText; 
	JSpinner incidentDate, incidentTime, preparedDate, preparedTime;
	Bolo bolo;
	JFrame parent;
	JPanel photoArea;
	JPanel dialogPanel;
//-----------------------------------------------------------------------------
	BOLOform(JFrame parent){
		super(parent, "New BOLO", true);
		
		this.parent = parent;
		//Create the BOLO object to add info to
		bolo = new Bolo();
		
		//Set the size of the form
		this.setPreferredSize(new Dimension(1050,900));
		this.setSize(new Dimension(950,900));
		
		dialogPanel = new JPanel(new MigLayout("ins 20", "[]5%[]", ""));
		
		//Make the form scrollable
		JScrollPane dialogPanelScroller = new JScrollPane(dialogPanel);
		dialogPanelScroller.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		//Put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		//Make sure that if the user hits the 'x', the window calls the closeAndCancel method
		this.addWindowListener(new WindowAdapter( ) {
			public void windowClosing(WindowEvent e) {
				closeAndCancel( );
			}
		});
	    

		//Set up the new BOLO form
		
		//Add the BOLO "letter head" image to the top
		ImageIcon boloHeaderIcon = ImageHandler.createImageIcon("images/boloHeader.png");
		JPanel boloHeaderPanel = new JPanel();
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
		


	    /*Add buttons panel to top of scroll panel as the row header
	     *(the buttons panel stays at the top of the screen even if the top of the form isn't
	     *currently visible) */
	    JPanel buttonsPanel = createButtonsPanel();
	    dialogPanelScroller.setColumnHeaderView(buttonsPanel);
	    
	    
	    //Add the BOLO form scrolling pane dialog to the screen
	    Container contentPane = getContentPane();
	    contentPane.add(dialogPanelScroller);

	}
//-----------------------------------------------------------------------------	
	BOLOform(JFrame parent, Bolo bolo){
		this(parent);
		this.bolo = bolo;
		loadFromExistingBOLO();
	}
//-----------------------------------------------------------------------------	
	public JPanel createPhysicalDescriptionPanel(){
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
		//add row 3 fiels
		infoPanel.add(eyesLabel, "align left");
		infoPanel.add(eyesField, "align left");
		infoPanel.add(hairLabel, "align left");
		infoPanel.add(hairField, "align left, wrap");
		//add other description area
		infoPanel.add(otherDescriptionLabel, "spanx");
		infoPanel.add(otherDescriptScrollPane, "spanx, wrap");
		//add "armed?" area
		SwingHelper.addArmedQuestionCheckboxes(infoPanel, ifYesField);
		
		return infoPanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createIncidentInfoPanel(){
		JPanel infoPanel = new JPanel(new MigLayout());
					
		SwingHelper.addTitledBorder(infoPanel, "Incident Info");

        // create labels
		JLabel referenceLabel = new JLabel("Reference");
		JLabel caseNumLabel = new JLabel("Case #");
		JLabel statusLabel = new JLabel("Status");
		
		
		// create fields
		referenceField = new JTextField(15);
		caseNumField = new JTextField(15);
		statusField = new JTextField(15);

		//row 1
		incidentDate = SwingHelper.addDateSpinner(infoPanel, "Date of Incident");
		incidentTime = SwingHelper.addTimeSpinner(infoPanel, "Time of Incident");
		infoPanel.add(referenceLabel, "align");
		infoPanel.add(referenceField, "align, wrap");
		infoPanel.add(caseNumLabel, "align");
		infoPanel.add(caseNumField, "align, wrap");
		infoPanel.add(statusLabel, "align");
		infoPanel.add(statusField, "align, wrap");
		
		return infoPanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createNarrativePanel(){
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
	public JPanel createAdministrativePanel(){
		JPanel adminPanel = new JPanel(new MigLayout());
		
		SwingHelper.addTitledBorder(adminPanel, "Administrative Info");

        // create labels
		JLabel preparedByLabel = new JLabel("BOLO prepared by");
		JLabel approvedByLabel = new JLabel("BOLO approved by");
		
		// create fields
		preparedByField = new JTextField(15);
		approvedByField = new JTextField(15);
		
		//add labels & text fields to  panel
		adminPanel.add(preparedByLabel, "align");
		adminPanel.add(preparedByField, "align, wrap");
		adminPanel.add(approvedByLabel, "align");
		adminPanel.add(approvedByField, "align, wrap");
		preparedDate = SwingHelper.addDateSpinner(adminPanel, "Date BOLO prepared");
		preparedTime = SwingHelper.addTimeSpinner(adminPanel, "Time BOLO prepared");
		

		return adminPanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createPhotoVideoPanel(){
		JPanel photoVideoPanel = new JPanel(new MigLayout());
		
		//Create initial no-photo place holder photo
		final JPanel photoPanel = new JPanel();
		photoArea = photoPanel;
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoPanel.add(noPhotoLabel);
		photoVideoPanel.add(photoPanel, "span, wrap");
		
		JButton addPhotoButton = SwingHelper.createImageButton("Add a Photo", "icons/camera.png");
		addPhotoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				chooseAndAddPhoto(photoPanel);
			}
		});
		
		addPhotoButton.setToolTipText("Attach a photo to this BOLO");
		JButton addVideoButton = SwingHelper.createImageButton("Add a Video", "icons/videoCamera.png");
		addVideoButton.setToolTipText("Attach a video to this BOLO");
		photoVideoPanel.add(addPhotoButton);
		photoVideoPanel.add(addVideoButton);

		return photoVideoPanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createButtonsPanel(){
	
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		
		//Add cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_48.png");
		cancelButton.setToolTipText("Cancel and do not save");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				closeAndCancel();
			}
		});
	
	    // Add save button
	    JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_48.png");
	    saveButton.setToolTipText("Save BOLO");
	    saveButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		saveAndClose();
	    	}
	    });
	    
	    // Add preview button
	    JButton previewButton = new JButton("<html>Preview<br>  BOLO</html>");
	    previewButton.setToolTipText("Preview and print final BOLO document");
	    previewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		setVisible(false);
	    		putInfoIntoBoloObject();
	    		BOLOpreview preview = new BOLOpreview(parent, bolo);
	    		preview.setVisible(true);
	    	}
	    });
	    
	    
	    JPanel saveAndCancelButtonsPanel = new JPanel();
	    saveAndCancelButtonsPanel.add(saveButton, "tag ok, dock west");
	    saveAndCancelButtonsPanel.add(cancelButton, "tag cancel, dock west");
	    JPanel previewButtonPanel = new JPanel(new MigLayout("rtl"));
	    previewButtonPanel.add(previewButton, "tag right");
	    buttonsPanel.add(saveAndCancelButtonsPanel, "shrinky");
	    buttonsPanel.add(previewButtonPanel, "growx, shrinky");
	   // SwingHelper.addLineBorder(buttonsPanel);
	    return buttonsPanel;
	}
//-----------------------------------------------------------------------------
	/**
	* Save the information input into this form and close the dialog.
	*/
	public void saveAndClose(){

		//place the info from the fields into a BOLO object
		 putInfoIntoBoloObject();
		 
		 //add the BOLO object's info to the database
		 try {
			bolo.addToDB();
		 } catch (Exception e) {
			System.out.println("error: unable to add BOLO to DB");
			e.printStackTrace();
		 }

		 //reset the form
		 eraseForm();
		 
		 //close the window
		 this.dispose();	
	}
//-----------------------------------------------------------------------------
	 /**
	  * Places the info from the input fields into the global BOLO object.
	  */
	 public void putInfoIntoBoloObject(){
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
		 status=statusField.getText();
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
		 bolo.setprepDate(getPrepDateEpoch());
		 bolo.setincidentDate(getIncidentDateEpoch());
		 
	}
//-----------------------------------------------------------------------------
	 /**
	  * Places the info from the input fields into the global BOLO object.
	  */
	 public void loadFromExistingBOLO(){
		 String age, race, sex, height, weight, build, eyes, hair;
		 String reference, caseNum, status, weapon;
		 String preparedBy, approvedBy;
		 String otherDescrip, narrative;
		 
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
		 statusField.setText(bolo.getStatus());
		 ifYesField.setText(bolo.getWeapon());
		 preparedByField.setText(bolo.getPreparedBy());
		 approvedByField.setText(bolo.getApprovedBy());
		 otherDescriptField.setText(bolo.getOtherDescrip());
		 narrativeText.setText(bolo.getNarrative());
		 
		 //set the times
		 
		 //set picture
		 ImageIcon photo = ImageHandler.getResizableImageIcon(bolo.getPhotoFilePath(), 200, 299);
		 if(photo!=null){
			photoArea.removeAll();
			photoArea.add(new JLabel(photo));
		 }
		 dialogPanel.validate();
		 
	}
//-----------------------------------------------------------------------------
	 public void chooseAndAddPhoto(JPanel photoPanel){
		//show choose photo dialog
		final JFileChooser fc = new JFileChooser();
		fc.addChoosableFileFilter(FileHelper.getImageFilter());
		fc.setAcceptAllFileFilterUsed(false);
		fc.setAccessory(new ImagePreview(fc));
		int returnVal = fc.showOpenDialog(parent);
			
		//if a photo was selected, add it to BOLO and load into photo area
		if(returnVal==JFileChooser.APPROVE_OPTION){
			//copy the choosen photo into the program's 'Photos' directory
			File file = fc.getSelectedFile();
			System.out.printf("filepath = %s\n", file.getName(), file.getPath());
			Path photoPath = FileHelper.copyPhoto(file);
			//load the image into photo area
			ImageIcon choosenPhoto = ImageHandler.getResizableImageIcon(photoPath, 200, 299);
			if(choosenPhoto!=null){
				bolo.setPhotoFilePath(photoPath);
				photoPanel.removeAll();
				photoPanel.add(new JLabel(choosenPhoto));
				(photoPanel.getParent()).validate();
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
	 public void eraseForm(){
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
		statusField.setText(null);
		ifYesField.setText(null);
		preparedByField.setText(null);
		approvedByField.setText(null);
		otherDescriptField.setText(null);
		narrativeText.setText(null);
		
		//recreate the photo/video section
		photoArea.removeAll();
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoArea.add(noPhotoLabel);
		(photoArea.getParent()).validate();
	 }
//-----------------------------------------------------------------------------
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
  public long getIncidentDateEpoch(){
		  Date day = new Date();
		  Date time = new Date();
		
		  Calendar incidentCal = Calendar.getInstance();
		  Calendar timeCal = Calendar.getInstance();
		
		  day = ((SpinnerDateModel) preparedDate.getModel()).getDate();
		  time = ((SpinnerDateModel) preparedTime.getModel()).getDate();
		  timeCal.setTime(time);
		
		  incidentCal.setTime(day);
		  incidentCal.set(Calendar.HOUR, timeCal.get(Calendar.HOUR));
		  incidentCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
		  incidentCal.set(Calendar.AM_PM, timeCal.get(Calendar.AM_PM));
			 
		  return (incidentCal.getTimeInMillis()/1000); 
		}
//-----------------------------------------------------------------------------	
/* (non-Javadoc)
 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
 */
@Override
public void stateChanged(ChangeEvent arg0) {
	// TODO Auto-generated method stub
	
}
//-----------------------------------------------------------------------------	
}
