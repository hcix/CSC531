/**
 * Temp debugging class to use with Google Web Toolkit's WindowBuilder Pro Eclipse Plug-in 
 * to get a visual on what's happening with a layout. Copy & paste code of the JPanel
 * that needs debugging and click the design tab at the bottom to see what's going on.
 */
package debuggerTools;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import utilities.ui.ImageHandler;
import utilities.ui.SwingHelper;
import boloTab.Bolo;


public class LayoutTester extends JPanel {
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
	public LayoutTester() {
		
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

	    

		//Set up the new BOLO form
	
	
		//Add photo/video panel
		JPanel photoVideoPanel = createPhotoVideoPanel();
		dialogPanel.add(photoVideoPanel, "align left");
		
		//Add physical description panel
	    JPanel physicalDescriptPanel = createPhysicalDescriptionPanel();
	    dialogPanel.add(physicalDescriptPanel, "align left, wrap");
	
	    //Add incident info panel
	    JPanel incidentInfoPanel = createIncidentInfoPanel();
	    dialogPanel.add(incidentInfoPanel, "align left, growx");
	    

	    //Add administrative panel
	   // JPanel adminPanel = createAdministrativePanel();
	   // dialogPanel.add(adminPanel, "align left");

		//TODO: Add standard footer
		


	    //Add the BOLO form scrolling pane dialog to the screen
	 //   Container contentPane = getContentPane();
	 //   contentPane.add(dialogPanelScroller);
	
				this.add(dialogPanelScroller);
	}
//-----------------------------------------------------------------------------
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
	public JPanel createPhotoVideoPanel(){
		JPanel photoVideoPanel = new JPanel(new MigLayout());
		
		//Create initial no-photo place holder photo
		final JPanel photoPanel = new JPanel();
		photoArea = photoPanel;
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoPanel.add(noPhotoLabel);
		photoVideoPanel.add(photoPanel, "spanx,grow,wrap");
		
		JButton addPhotoButton = SwingHelper.createImageButton("Add a Photo", "icons/camera.png");
		addPhotoButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae) {
				//chooseAndAddPhoto(photoPanel);
			}
		});
		
		addPhotoButton.setToolTipText("Attach a photo to this BOLO");
		JButton addVideoButton = SwingHelper.createImageButton("Add a Video", "icons/videoCamera.png");
		addVideoButton.setToolTipText("Attach a video to this BOLO");
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(addPhotoButton);
		buttonsPanel.add(addPhotoButton);
		
		photoVideoPanel.add(buttonsPanel, "dock south");

		return photoVideoPanel;
	}
//-----------------------------------------------------------------------------


}
