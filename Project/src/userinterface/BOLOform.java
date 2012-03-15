package userinterface;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class BOLOform extends JDialog implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
	BOLOform(JFrame parent){
		super(parent, "New BOLO", true);
		//Set the size of the form
		this.setPreferredSize(new Dimension(950,900));
		this.setSize(new Dimension(950,900));
		
		JPanel dialogPanel = new JPanel(new MigLayout("ins 20", "[]5%[]", ""));
		
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
	    
	    //this.pack();
	}
//-----------------------------------------------------------------------------	
	public JPanel createPhysicalDescriptionPanel(){
		JPanel infoPanel = new JPanel(new MigLayout());
				
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
		JTextField ageField = new JTextField(4);
		JTextField raceField = new JTextField(SwingHelper.EXTRA_SMALL_TEXT_FIELD_LENGTH);
		JTextField sexField = new JTextField(SwingHelper.ONE_CHAR_TEXT_FIELD_LENGTH);
		JTextField heightField = new JTextField(SwingHelper.EXTRA_SMALL_TEXT_FIELD_LENGTH);
		JTextField weightField = new JTextField(SwingHelper.EXTRA_SMALL_TEXT_FIELD_LENGTH);
		JTextField buildField = new JTextField(10);
		JTextField eyesField = new JTextField(10);
		JTextField hairField = new JTextField(10);
		JTextArea otherDescriptField = new JTextArea(5, 20);
		otherDescriptField.setLineWrap(true);
		JScrollPane otherDescriptScrollPane = new JScrollPane(otherDescriptField);
		otherDescriptScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		JTextField ifYesField = new JTextField(SwingHelper.MEDIUM_TEXT_FIELD_LENGTH);

		
		//add row 1 fields
		infoPanel.add(ageLabel, "align");
		infoPanel.add(ageField, "align");
		infoPanel.add(raceLabel, "align");
		infoPanel.add(raceField, "align");
		infoPanel.add(sexLabel, "align");
		infoPanel.add(sexField, "align, wrap");
		//add row 2 fields
		infoPanel.add(heightLabel, "align");
		infoPanel.add(heightField, "align");
		infoPanel.add(weightLabel, "align");
		infoPanel.add(weightField, "align");
		infoPanel.add(buildLabel, "align");
		infoPanel.add(buildField, "align, wrap");
		//add row 3 fiels
		infoPanel.add(eyesLabel, "align");
		infoPanel.add(eyesField, "align, spanx 3");
		infoPanel.add(hairLabel, "align");
		infoPanel.add(hairField, "align, wrap");
		//add other description area
		infoPanel.add(otherDescriptionLabel, "spanx");
		infoPanel.add(otherDescriptScrollPane, "spanx, growx");
		//add "armed?" area
		SwingHelper.addArmedQuestionCheckboxes(infoPanel, ifYesField);
		
		return infoPanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createIncidentInfoPanel(){
		JPanel infoPanel = new JPanel(new MigLayout());
					
		SwingHelper.addTitledBorder(infoPanel, "Incident Info");

        // create labels
		JLabel doiLabel = new JLabel("Date of Incident");
		JLabel toiLabel = new JLabel("Time of Incident");
		JLabel referenceLabel = new JLabel("Reference");
		JLabel caseNumLabel = new JLabel("Case #");
		JLabel statusLabel = new JLabel("Status");
		
		// create fields
		JTextField doiField = new JTextField(15);
		JTextField toiField = new JTextField(15);
		JTextField referenceField = new JTextField(15);
		JTextField caseNumField = new JTextField(15);
		JTextField statusField = new JTextField(15);

		//row 1
		infoPanel.add(doiLabel, "align");
		infoPanel.add(doiField, "align, wrap");
		infoPanel.add(toiLabel, "align");
		infoPanel.add(toiField, "align, wrap");
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
		JTextArea textField = new JTextArea(10, 30);
		textField.setLineWrap(true);
		JScrollPane otherDescriptScrollPane = new JScrollPane(textField);
		otherDescriptScrollPane.setVerticalScrollBarPolicy(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		//add other description area
		narrativePanel.add(otherDescriptScrollPane, "growx, align center");
		
		return narrativePanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createAdministrativePanel(){
		JPanel adminPanel = new JPanel(new MigLayout());
		//adminPanel.setToolTipText("Administrative information pertaining to this BOLO");
		
		SwingHelper.addTitledBorder(adminPanel, "Administrative Info");

        // create labels
		JLabel preparedByLabel = new JLabel("BOLO prepared by");
		JLabel approvedByLabel = new JLabel("BOLO approved by");
		JLabel dateLabel = new JLabel("Date BOLO prepared");
		JLabel timeLabel = new JLabel("Time BOLO prepared");
		
		// create fields
		JTextField preparedByField = new JTextField(15);
		JTextField approvedByField = new JTextField(15);
		JTextField dateField = new JTextField(15);
		JTextField timeField = new JTextField(15);
		
		//add labels & text fields to  panel
		adminPanel.add(preparedByLabel, "align");
		adminPanel.add(preparedByField, "align, wrap");
		adminPanel.add(approvedByLabel, "align");
		adminPanel.add(approvedByField, "align, wrap");
		adminPanel.add(dateLabel, "align");
		adminPanel.add(dateField, "align, wrap");
		adminPanel.add(timeLabel, "align");
		adminPanel.add(timeField, "align, wrap");

		return adminPanel;
	}
//-----------------------------------------------------------------------------
	public JPanel createPhotoVideoPanel(){
		JPanel photoVideoPanel = new JPanel(new MigLayout());
		
		//Create initial no-photo placeholder photo
		ImageIcon noPhotoImage = ImageHandler.createImageIcon("images/unknownPerson.jpeg");
		JLabel noPhotoLabel = new JLabel(noPhotoImage);
		photoVideoPanel.add(noPhotoLabel, "span, wrap");
		
		JButton addPhotoButton = SwingHelper.createImageButton("Add a Photo", "icons/camera.png");
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
				closeAndCancel( );
			}
		});
	
	    // Add save button
	    JButton saveButton = SwingHelper.createImageButton("Save", "icons/save_48.png");
	    saveButton.setToolTipText("Save BOLO");
	    saveButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		closeAndSave( );
	    	}
	    });
	    
	    // Add preview button
	    JButton previewButton = new JButton("<html>Preview<br>  BOLO</html>");
	    previewButton.setToolTipText("Preview and print final BOLO document");
	    saveButton.addActionListener(new ActionListener( ) {
	    	public void actionPerformed(ActionEvent e) {
	    		closeAndSave( );
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
	 // Something in the font changed, so figure out what and make a
	  // new font for the preview label.
	  public void actionPerformed(ActionEvent e) {
	    // Check the name of the font.
/*	    if (!StyleConstants.getFontFamily(attributes)
	                       .equals(fontName.getSelectedItem( ))) {
	      StyleConstants.setFontFamily(attributes, (String)fontName.getSelectedItem( ));
	    }
	    // Check the font size (no error checking yet).
	    if (StyleConstants.getFontSize(attributes) != 
	                                   Integer.parseInt(fontSize.getText( ))) {
	      StyleConstants.setFontSize(attributes, Integer.parseInt(fontSize.getText( )));
	    }
	    // Check to see if the font should be bold.
	    if (StyleConstants.isBold(attributes) != fontBold.isSelected( )) {
	      StyleConstants.setBold(attributes, fontBold.isSelected( ));
	    }
	    // Check to see if the font should be italic.
	    if (StyleConstants.isItalic(attributes) != fontItalic.isSelected( )) {
	      StyleConstants.setItalic(attributes, fontItalic.isSelected( ));
	    }
	    // And update our preview label
	    updatePreviewFont( );*/
	  }
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------

  
	//  public Font getNewFont( ) { return newFont; }
	//  public Color getNewColor( ) { return newColor; }
	//  public AttributeSet getAttributes( ) { return attributes; }
//-----------------------------------------------------------------------------
	 public void closeAndSave( ) {
	    // Save font and color information.
	    //newFont = previewLabel.getFont( );
	   // newColor = previewLabel.getForeground( );

	    // Close the window.
	    setVisible(false);
	  }
//-----------------------------------------------------------------------------
	  public void closeAndCancel( ) {
	    // Erase any font information and then close the window.
	//    newFont = null;
	 //   newColor = null;
	    setVisible(false);
	  }
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------	

}
