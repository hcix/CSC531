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
public class BlueBookPanel extends JPanel implements ActionListener {
//-----------------------------------------------------------------------------	
	BlueBookPanel(final JFrame parent){
		this.setLayout(new MigLayout("nogrid"));
		
		//Create the search fields panel
		JPanel searchPanel = createSearchFieldsPanel();
	
		//Create search button
		JButton searchButton = SwingHelper.createImageButton("Search Blue Book", "icons/search.png");
				
		//Create a button to create a new BOLO 
		JButton newBOLOButton = SwingHelper.createImageButton("Create new Blue Book entry", "icons/plusSign.png");
		newBOLOButton.addActionListener(new ActionListener() {
			//BOLO form dialog
			BlueBookForm formDialog = new BlueBookForm(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});
	
		//add the components to this panel
		this.add(newBOLOButton, "gapy para, wrap");
		this.add(searchPanel, "shrink, gapy para");
		this.add(searchButton, "wrap, aligny bottom");

	}
//-----------------------------------------------------------------------------
	JPanel createSearchFieldsPanel(){
		JPanel searchFieldsPanel = new JPanel();
		searchFieldsPanel.setLayout(new MigLayout("align left"));

		JLabel caseNumLabel = new JLabel("Case #: ");
		JLabel locationLabel = new JLabel("Location: ");
		JLabel statusLabel = new JLabel("Name: ");
		
		JTextField caseNumField = new JTextField(15);
		JTextField locationField = new JTextField(20);
		JTextField statusField = new JTextField(20);
		
		JPanel dateRange = SwingHelper.createDateRangePanel();
		
		SwingHelper.addLineBorder(searchFieldsPanel);
		
		searchFieldsPanel.add(caseNumLabel, "alignx left");
		searchFieldsPanel.add(caseNumField, "alignx left, wrap");
		searchFieldsPanel.add(locationLabel,"alignx left");
		searchFieldsPanel.add(locationField, "alignx left, wrap");
	
		createDateRangePanel(searchFieldsPanel);
	
		searchFieldsPanel.add(statusLabel, "alignx left");
		searchFieldsPanel.add(statusField, "alignx left, wrap");
	
		return searchFieldsPanel;
	}
//-----------------------------------------------------------------------------
	public static JSpinner addLabeledSpinner(Container c,String label,SpinnerModel model, boolean wrap) {
		JLabel l = new JLabel(label);
		c.add(l, "align left");
		
		JSpinner spinner = new JSpinner(model);

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