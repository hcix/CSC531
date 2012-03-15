package userinterface;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import net.miginfocom.swing.MigLayout;
import utilities.ImageHandler;
import utilities.SwingHelper;
//-----------------------------------------------------------------------------
public class BlueBookForm extends JDialog {

	private static final long serialVersionUID = 1L;
	//-----------------------------------------------------------------------------
	public BlueBookForm(JFrame parent) {
		super(parent, "New Blue Book Entry", true);
		//Set the size of the form
		this.setPreferredSize(new Dimension(800,900));
		this.setSize(new Dimension(800,900));
		
		//Create the form
		JPanel inputPanel = createInputPanel();
		
		//Make the form scrollable
		JScrollPane inputScrollPane = new JScrollPane(inputPanel);
		inputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//Change to use parent's dimensions
		inputScrollPane.setPreferredSize(new Dimension(600, 600)); 
		
		//Put the dialog in the middle of the screen
		this.setLocationRelativeTo(null);
		
		 /*Add buttons panel to top of scroll panel as the row header
	     *(the buttons panel stays at the top of the screen even if the top of the form isn't
	     *currently visible) */
		JPanel buttonsPanel = createButtonsPanel();
		inputScrollPane.setColumnHeaderView(buttonsPanel);
	    

		
		//Add the Blue Book form dialog to the screen
	    Container contentPane = getContentPane();
	    contentPane.add(inputScrollPane);
	    
	}
//-----------------------------------------------------------------------------
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
		JTextField caseNumField = new JTextField(20);
		JTextField nameField = new JTextField(20);
		JTextField affiliField = new JTextField(20);
		JTextField addressField = new JTextField(20);

		/*
		 * create text areas, embed them in a scroll
		 * pand and set the line wrap and scroll 
		 * properties
		 */
		
		JTextArea locationField = new JTextArea(5, 20);
		locationField.setLineWrap(true);
		JScrollPane locationScrollPane = new JScrollPane(locationField);
		locationScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JTextArea descriptionField = new JTextArea(10, 20);
		descriptionField.setLineWrap(true);
		JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
		locationScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JTextArea reasonField = new JTextArea(10, 20);
		reasonField.setLineWrap(true);
		JScrollPane reasonScrollPane = new JScrollPane(reasonField);
		descriptionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JTextField ifYesField = new JTextField(20);

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
	private JPanel createButtonsPanel(){
		JPanel buttonsPanel = new JPanel(new MigLayout("fillx", "push"));
		
		//Add cancel button
		JButton cancelButton = SwingHelper.createImageButton("Cancel", "icons/cancel_48.png");
		cancelButton.setToolTipText("Cancel and do not save");

	    // Add save button
	    JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_48.png");
	    saveButton.setToolTipText("Save BOLO");

	    // Add preview button
	    JButton previewButton = new JButton("<html>Preview<br>  Entry</html>");
	    previewButton.setToolTipText("Preview and print final Blue Book entry");
	    	    
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
	private JPanel createPhotoPanel(){
		JPanel photoPanel = new JPanel(new MigLayout());
		
		//Create initial no-photo placeholder photo
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoPanel.add(noPhotoLabel, "span, wrap");
		
		JButton addPhotoButton = SwingHelper.createImageButton("Add a Photo", "icons/camera.png");
		addPhotoButton.setToolTipText("Attach a photo to this BOLO");
		photoPanel.add(addPhotoButton);
		
		return photoPanel;
	}
//-----------------------------------------------------------------------------
	
//-----------------------------------------------------------------------------
}
