package userinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

import utilities.ImageHandler;
import utilities.SwingHelper;

public class BOLOform extends JDialog implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
	BOLOform(JFrame parent){
		super(parent, "New BOLO", true);
		this.setPreferredSize(new Dimension(800,900));
		this.setSize(new Dimension(800,900));
		
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
		
		//put the form in the middle of the screen
		this.setLocationRelativeTo(null);

		// Make sure that if the user cancels, the window does the right thing
		this.addWindowListener(new WindowAdapter( ) {
			public void windowClosing(WindowEvent e) {
				closeAndCancel( );
			}
		});
	    
	    
	    //Set up form interface
	    Container contentPane = getContentPane();
        
	    
		//Add the BOLO "letter head" image
		ImageIcon boloHeaderIcon = ImageHandler.createImageIcon("images/boloHeader.png");
		JPanel boloHeaderPanel = new JPanel();
		boloHeaderPanel.add(new JLabel(boloHeaderIcon));
		
	
		//Add photo/video Panel
		
		//Add info panel
	    JPanel basicInfo = new JPanel();
		basicInfo.add(createPhysicalDescriptionPanel());
	    dialogPanel.add(basicInfo);
		//Add remarks area
		
		//Add standard footer
		


	    


	    JButton cancelButton = new JButton("Cancel");
	    cancelButton.addActionListener(new ActionListener( ) {
	      public void actionPerformed(ActionEvent ae) {
	        closeAndCancel( );
	      }
	    });
	    
	    // Add in the OK and Cancel buttons for our dialog box.
	    JButton saveButton = new JButton("Save");
	    saveButton.addActionListener(new ActionListener( ) {
	      public void actionPerformed(ActionEvent e) {
	        closeAndSave( );
	      }
	    });
	    
	

	    JPanel controlPanel = new JPanel();
	    
	    controlPanel.add(saveButton);
	    controlPanel.add(cancelButton);

		dialogPanel.add(boloHeaderPanel);
	    dialogPanel.add(basicInfo);
	    dialogPanel.add(controlPanel);
	    
	    contentPane.add(dialogPanel);
	 //   contentPane.add(boloHeaderPanel);
	 //   contentPane.add(basicInfo);
	 //   contentPane.add(controlPanel);
	    
	//    this.pack();
	}
//-----------------------------------------------------------------------------	
	public JPanel createPhysicalDescriptionPanel(){
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new MigLayout("flowy"));
		
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
		JTextField ageField = new JTextField(SwingHelper.EXTRA_SMALL_TEXT_FIELD_LENGTH);
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
		
		//Armed checkboxs
		JLabel armedLabel = new JLabel("Armed?");
		JLabel ifYes = new JLabel("If Yes: ");
		JCheckBox armedField1 = new JCheckBox("No");
		JCheckBox armedField2 = new JCheckBox("Yes");
		JTextField ifYesField = new JTextField(SwingHelper.MEDIUM_TEXT_FIELD_LENGTH);

		// add entry fields to panel
		JPanel entryFields = new JPanel(new MigLayout());
		//row 1
		entryFields.add(ageLabel, "align");
		entryFields.add(ageField, "align");
		entryFields.add(raceLabel, "align");
		entryFields.add(raceField, "align");
		entryFields.add(sexLabel, "align");
		entryFields.add(sexField, "align, wrap");
		//row 2
		entryFields.add(heightLabel, "align");
		entryFields.add(heightField, "align");
		entryFields.add(weightLabel, "align");
		entryFields.add(weightField, "align");
		entryFields.add(buildLabel, "align");
		entryFields.add(buildField, "align, wrap");
		//row 3
		entryFields.add(eyesLabel, "align");
		entryFields.add(eyesField, "align, spanx 3");
		entryFields.add(hairLabel, "align");
		entryFields.add(hairField, "align, wrap");
		//other description area
		entryFields.add(otherDescriptionLabel, "spanx");
		entryFields.add(otherDescriptScrollPane, "spanx, growx");
		//armed area
		entryFields.add(armedLabel, "align, newline");
		entryFields.add(armedField1);
		entryFields.add(armedField2);
		//TODO: make the weapon description field appear only if armed is checked
		entryFields.add(ifYes);
		entryFields.add(ifYesField, "span, shrink");
		infoPanel.add(entryFields);
		
		infoPanel.add(entryFields);
		
		return infoPanel;
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
