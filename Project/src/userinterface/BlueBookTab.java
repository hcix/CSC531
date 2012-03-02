package userinterface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import net.miginfocom.swing.MigLayout;
import utilities.SwingHelper;

//-----------------------------------------------------------------------------	
public class BlueBookTab extends JPanel implements ActionListener {
//-----------------------------------------------------------------------------	
	BlueBookTab(final JFrame parent){
		this.setLayout(new MigLayout("nogrid"));
		
		//Create the search fields panel
		JPanel searchPanel = createSearchFieldsPanel();
	
		//Create search button
		JButton searchButton = SwingHelper.createImageButton("Search Blue Book", "icons/search.png");
				
		//Create a button to create a new Blue Book entry 
		JButton newEntryButton = 
				SwingHelper.createImageButton("Create new Blue Book entry", "icons/plusSign.png");
		newEntryButton.addActionListener(new ActionListener() {
			//Create new Blue Book entry form dialog
			BlueBookForm formDialog = new BlueBookForm(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});
	
		//add the components to this panel
		this.add(newEntryButton, "gapy para, wrap");
		this.add(searchPanel, "shrink, gapy para");
		this.add(searchButton, "wrap, aligny bottom");

	}
//-----------------------------------------------------------------------------
	private JPanel createSearchFieldsPanel(){
		JPanel searchFieldsPanel = new JPanel();
		searchFieldsPanel.setLayout(new MigLayout("align left"));

		JLabel caseNumLabel = new JLabel("Case #: ");
		JLabel locationLabel = new JLabel("Location: ");
		JLabel nameLabel = new JLabel("Name: ");
		
		JTextField caseNumField = new JTextField(15);
		JTextField locationField = new JTextField(20);
		JTextField nameField = new JTextField(20);
	
		SwingHelper.addLineBorder(searchFieldsPanel);
		
		searchFieldsPanel.add(caseNumLabel, "alignx left");
		searchFieldsPanel.add(caseNumField, "alignx left, wrap");
		searchFieldsPanel.add(nameLabel, "alignx left");
		searchFieldsPanel.add(nameField, "alignx left, wrap");
		searchFieldsPanel.add(locationLabel,"alignx left");
		searchFieldsPanel.add(locationField, "alignx left, wrap");
	
		SwingHelper.addDateRangePanel(searchFieldsPanel);
	
		return searchFieldsPanel;
	}
//-----------------------------------------------------------------------------		
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
//-----------------------------------------------------------------------------	
}