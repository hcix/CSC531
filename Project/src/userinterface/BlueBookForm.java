package userinterface;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class BlueBookForm extends JDialog {
	public BlueBookForm(JFrame parent) {
		super(parent, "New Blue Book Entry", true);
		//Set the size of the form
		this.setPreferredSize(new Dimension(800,900));
		this.setSize(new Dimension(800,900));
		
		JPanel inputPanel = createInputPanel();
		JScrollPane inputScrollPane = new JScrollPane(inputPanel);
		inputScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// change to use parents dimensions
		inputScrollPane.setPreferredSize(new Dimension(600, 600)); 
		
		
		JPanel buttonPanel = new JPanel();
		JButton save = new JButton("Save");
		buttonPanel.add(save);
		inputPanel.add(save);
		
		//Add the BOLO form scrolling pane dialog to the screen
	    Container contentPane = getContentPane();
	    contentPane.add(inputScrollPane);
		//contentPane.add(buttonPanel);
	//	this.add(inputScrollPane);
		//this.add(buttonPanel);
	}

	private JPanel createInputPanel() {
		
		// create and set panels
		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new MigLayout());
		SwingHelper.addLineBorder(inputPanel);
		
        // create labels
		JLabel Date = new JLabel("Date of Incident: ");
		JLabel caseNumber = new JLabel("Case #: ");
		JLabel name = new JLabel("Name of offender (last, first, middle): ");
		JLabel DOB = new JLabel("DOB: ");
		JLabel Affili = new JLabel("Affili: ");
		JLabel Address = new JLabel("Last known address: ");
		JLabel Location = new JLabel("Location of incident: ");
		JLabel Description = new JLabel("Crime description: ");
		JLabel Reason = new JLabel("Narrative/Reason: ");
		JLabel Picture = new JLabel("Picture(s): ");
		JLabel Armed = new JLabel("Armed: ");
		JLabel ifYes = new JLabel("If Yes: ");
		
		// create fields
		JPanel dateRange = SwingHelper.createDatePanel();
		JTextField caseNumField = new JTextField(20);
		JTextField nameField = new JTextField(20);
		JPanel DOBField = SwingHelper.createDatePanel();
		JTextField affiliField = new JTextField(20);
		JTextField addressField = new JTextField(20);
		//JTextField descriptionField = new JTextField(20);
		//JTextField locationField = new JTextField(20);
		
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
		
		/*
		 * create an imageicon, set it to a default value, 
		 * later this will be replaced with the possibility 
		 * choose and add photos?
		 */
		ImageIcon pictureField = ImageHandler.createImageIcon("images/badge.png");
		JLabel pictureLabel = new JLabel(pictureField);
		
		JCheckBox armedField1 = new JCheckBox("No");
		JCheckBox armedField2 = new JCheckBox("Yes");
		JTextField ifYesField = new JTextField(20);

		// add to panel
		inputPanel.add(Date, "align left");
		inputPanel.add(dateRange, "align left, wrap");
		inputPanel.add(caseNumber, "alignx left");
		inputPanel.add(caseNumField, "align left,wrap");
		inputPanel.add(name, "alignx left");
		inputPanel.add(nameField, "align left, wrap");
		inputPanel.add(DOB, "align left");
		inputPanel.add(DOBField, "align left, wrap");
		inputPanel.add(Affili, "alignx left");
		inputPanel.add(affiliField, "align left,wrap");
		inputPanel.add(Address, "alignx left");
		inputPanel.add(addressField, "align left,wrap");
		
		inputPanel.add(Location, "align left");
		inputPanel.add(locationScrollPane, "align left, wrap");
		inputPanel.add(Description, "alignx left");
		inputPanel.add(descriptionScrollPane, "align left, wrap");
		inputPanel.add(Reason, "alignx left");
		inputPanel.add(reasonScrollPane, "align left, wrap");
		inputPanel.add(Picture, "alignx left");
		inputPanel.add(pictureLabel, "align left, wrap");
		inputPanel.add(Armed, "align left");
		inputPanel.add(armedField1, "align left, split");
		inputPanel.add(armedField2, "wrap");
		inputPanel.add(ifYes, "alignx left");
		inputPanel.add(ifYesField, "align left, wrap");
		
		return inputPanel;
	}
}
