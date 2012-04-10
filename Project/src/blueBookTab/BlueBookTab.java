package blueBookTab;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import userinterface.ItemsViewerPanel;
import utilities.SwingHelper;
/**
 * Configures UI for the Blue Book Tab
 */
//-----------------------------------------------------------------------------	
public class BlueBookTab extends JPanel implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------	
	public BlueBookTab(final JFrame parent){
		this.setLayout(new BorderLayout());
		
		//Create Blue Book tabbed display area
		JTabbedPane tabbedPane = new JTabbedPane();
		//Add recent Blue Book Entries tab

		//tabbedPane.addTab("Blue Book", recentBolosTab);
		//tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
				
		//Create a button to create a new Blue Book entry 
		JButton newEntryButton = 
				SwingHelper.createImageButton("Create new Blue Book entry", 
						"icons/plusSign_48.png");
		newEntryButton.addActionListener(new ActionListener() {
			//Create new Blue Book entry form dialog
			BlueBookForm formDialog = new BlueBookForm(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});

		//Create search button
		JButton searchButton = SwingHelper.createImageButton("Search Blue Book", 
				"icons/search.png");
		searchButton.addActionListener(new ActionListener() {
			//Search dialog
			JDialog searchDialog = createSearchDialog(parent);
			public void actionPerformed(ActionEvent e){
				searchDialog.setVisible(true);
			}
		});
		 
		//add the components to this panel
		this.add(tabbedPane, BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(newEntryButton);
		buttonsPanel.add(searchButton);
		this.add(buttonsPanel, BorderLayout.PAGE_END);
	}
//-----------------------------------------------------------------------------
	JDialog createSearchDialog(JFrame parent){
		//Create the dialog and set the size
		JDialog searchDialog = new JDialog(parent, "Search Blue Book Database", true);
		searchDialog.setPreferredSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
		searchDialog.setSize(SwingHelper.SEARCH_DIALOG_DIMENSION);
		
		//Put the dialog in the middle of the screen
		searchDialog.setLocationRelativeTo(null);
	
		//Create the various search fields and add them to the dialog
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new MigLayout("align left"));
		SwingHelper.addLineBorder(searchPanel);

		JLabel caseNumLabel = new JLabel("Case #: ");
		JLabel locationLabel = new JLabel("Location: ");
		JLabel nameLabel = new JLabel("Name: ");
		
		JTextField caseNumField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		JTextField locationField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		JTextField nameField = new JTextField(SwingHelper.DEFAULT_TEXT_FIELD_LENGTH);
		
		String[] statusStrings = { "Need to Identify", "Identified", "Arrested" };
		JComboBox<String> statusList = new JComboBox<String>(statusStrings);
		statusList.setSelectedIndex(0);

		searchPanel.add(caseNumLabel, "alignx left");
		searchPanel.add(caseNumField, "alignx left, wrap");
		searchPanel.add(nameLabel, "alignx left");
		searchPanel.add(nameField, "alignx left, wrap");
		searchPanel.add(locationLabel,"alignx left");
		searchPanel.add(locationField, "alignx left, wrap");
	
		SwingHelper.addDateRangePanel(searchPanel);
		
		Container contentPane = searchDialog.getContentPane();
		contentPane.add(searchPanel);
		return searchDialog;
	}
//-----------------------------------------------------------------------------		
	@Override
	public void actionPerformed(ActionEvent ev) {
		
		
	}
//-----------------------------------------------------------------------------	
}