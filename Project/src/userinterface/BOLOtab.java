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

public class BOLOtab extends JPanel  implements ActionListener {
private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------
	public BOLOtab(final JFrame parent){
		this.setLayout(new MigLayout("nogrid"));
		
		//Create the search fields panel
		JPanel searchPanel = createSearchFieldsPanel();

		//Create search button
		JButton searchButton = SwingHelper.createImageButton("Search Records", "icons/search.png");
				
		//Create a button to create a new BOLO 
		JButton newBOLOButton = SwingHelper.createImageButton("Create BOLO", "icons/plusSign.png");
		newBOLOButton.addActionListener(new ActionListener() {
			//BOLO form dialog
			BOLOform formDialog = new BOLOform(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});

		//Create a button to import an existing BOLO
		JButton importBOLOButton = SwingHelper.createImageButton("Import Existing BOLO", "icons/Import.png");
		importBOLOButton.addActionListener(new ActionListener() {
			//file chooser dialog
			public void actionPerformed(ActionEvent e){
				//file chooser dialog .setVisable(true);
				//Create a file chooser
				final JFileChooser fc = new JFileChooser();

				//In response to a button click:
				int returnVal = fc.showOpenDialog(parent);
			}
		});

		//add the components to this panel
		this.add(newBOLOButton, "gapy para");
		this.add(importBOLOButton, "wrap, gapy para");
		this.add(searchPanel, "shrink, gapy para");
		this.add(searchButton, "wrap, aligny bottom");

	//	this.add(buttonPanel);
		
	}
//-----------------------------------------------------------------------------
	JPanel createSearchFieldsPanel(){
		JPanel searchFieldsPanel = new JPanel();
		searchFieldsPanel.setLayout(new MigLayout("align left"));

		JLabel caseNumLabel = new JLabel("Case #: ");
		JLabel locationLabel = new JLabel("Location: ");
		JLabel statusLabel = new JLabel("Status: ");
		
		JTextField caseNumField = new JTextField(15);
		JTextField locationField = new JTextField(20);

		searchFieldsPanel.add(caseNumLabel, "align left");
		searchFieldsPanel.add(caseNumField, "align left, wrap");
		String[] statusStrings = { "Need to Identify", "Identified", "Arrested" };
		JComboBox statusList = new JComboBox(statusStrings);
		statusList.setSelectedIndex(0);
		//statusList.addActionListener(this);
		
		SwingHelper.addLineBorder(searchFieldsPanel);
		
		searchFieldsPanel.add(caseNumLabel, "alignx left");
		searchFieldsPanel.add(caseNumField, "alignx left, wrap");
		searchFieldsPanel.add(locationLabel,"alignx left");
		searchFieldsPanel.add(locationField, "alignx left, wrap");

		createDateRangePanel(searchFieldsPanel);

		searchFieldsPanel.add(statusLabel, "alignx left");
		searchFieldsPanel.add(statusList, "alignx left, wrap");

		return searchFieldsPanel;
	}
//-----------------------------------------------------------------------------
	public static JSpinner addLabeledSpinner(Container c,String label,SpinnerModel model, boolean wrap) {
		JLabel l = new JLabel(label);
		c.add(l, "align left");
		
		JSpinner spinner = new JSpinner(model);
		
/*		SwingHelper.addLineBorder(spinner);
		SwingHelper.addLineBorder(l);
		*/
		l.setLabelFor(spinner);
		if(wrap){
			c.add(spinner, "align left, wrap");
		} else{
			c.add(spinner, "align left, split");
		}
		
		return spinner;
	}
//-----------------------------------------------------------------------------
	public static void createDateRangePanel(JComponent c){
	//	JPanel datePanel = new JPanel();
		Calendar calendar = Calendar.getInstance();
		JSpinner dateSpinner;
		
		//Set up dates
		Date initDate = calendar.getTime();
		Date latestDate = calendar.getTime();		
	    calendar.add(Calendar.YEAR, -10);        
	    Date earliestDate = calendar.getTime();
		
	   //Date Spinners
	    SpinnerModel fromModel = new SpinnerDateModel(initDate,earliestDate,latestDate,
	    		Calendar.DAY_OF_MONTH);
	    dateSpinner = addLabeledSpinner(c, "From: ", fromModel, false);       
	    dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));
	    
	    SpinnerModel toModel = new SpinnerDateModel(initDate,earliestDate,latestDate,
	            Calendar.DAY_OF_MONTH);
	    dateSpinner = addLabeledSpinner(c, "To: ", toModel, true);       
	    dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));

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
