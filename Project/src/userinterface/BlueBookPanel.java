package userinterface;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;

import utilities.ImageHandler;
import utilities.SwingHelper;

public class BlueBookPanel extends JPanel {
	public BlueBookPanel() {

		JPanel inputPanel = createInputPanel();
		
		JPanel buttonPanel = new JPanel();
		JButton save = new JButton("Save");
		buttonPanel.add(save);
		
		this.add(inputPanel);
		this.add(buttonPanel);
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
		JLabel Description = new JLabel("Crime description: ");
		JLabel Location = new JLabel("Location of incident: ");
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
		JTextField descriptionField = new JTextField(20);
		JTextField locationField = new JTextField(20);
		JTextArea reasonField = new JTextArea(10, 20);
		JScrollPane scrollPane = new JScrollPane(reasonField);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
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
		inputPanel.add(Description, "alignx left");
		inputPanel.add(descriptionField, "align left, wrap");
		inputPanel.add(Location, "align left");
		inputPanel.add(locationField, "align left, wrap");
		inputPanel.add(Reason, "alignx left");
		inputPanel.add(scrollPane, "align left, wrap");
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
