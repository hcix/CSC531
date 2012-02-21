package userinterface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import net.miginfocom.swing.MigLayout;
import utilities.SwingHelper;

public class BOLOPanel extends JPanel  implements ActionListener {
private static final long serialVersionUID = 1L;
private static final int TEXT_FIELD_LENGTH = 20;
//-----------------------------------------------------------------------------
	public BOLOPanel(final JFrame parent){
	//	super(new BorderLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	/*	JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
		toolbar.setFloatable(true);
		toolbar.setRollover(true);
		toolbar.setBorderPainted(true);
		toolbar.
		toolbar.setLayout(new MigLayout("fill"));*/
		
		//Create the search panel
		JPanel searchPanel = createSearchPanel();

		
		//Create a button to create a new BOLO 
		JButton newBOLO = new JButton("Create new BOLO");
		newBOLO.addActionListener(new ActionListener() {
			//BOLO form dialog
			BOLOform formDialog = new BOLOform(parent);
			public void actionPerformed(ActionEvent e){
				formDialog.setVisible(true);
			}
		});

		//Create a button to import an existing BOLO
		JButton importBOLO = new JButton("Import an existing BOLO");
		importBOLO.addActionListener(new ActionListener() {
			//file chooser dialog
			public void actionPerformed(ActionEvent e){
				//file chooser dialog .setVisable(true);
			}
		});
		
	//	toolbar.add(newBOLO);
	//	toolbar.add(importBOLO);
		
		//Create a button panel & add the buttons to it
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(newBOLO);
		buttonPanel.add(importBOLO);
		
		//add the components to this panel
	//	this.add(toolbar, BorderLayout.PAGE_START);
	//	this.add(searchPanel, BorderLayout.CENTER);
		this.add(searchPanel);
		this.add(buttonPanel);
		//this.add(buttonPanel, BorderLayout.CENTER);
		
	}
//-----------------------------------------------------------------------------
	JPanel createSearchPanel(){
		JPanel searchPanel = new JPanel();
		JPanel searchFields = new JPanel();
		searchFields.setLayout(new MigLayout("align left"));
		searchPanel.setLayout(new MigLayout());

		JLabel caseNumLabel = new JLabel("Case #: ");
		JLabel locationLabel = new JLabel("Location: ");
		JLabel statusLabel = new JLabel("Status: ");
		
		JTextField caseNumField = new JTextField(15);
		JTextField locationField = new JTextField(20);
		String[] statusStrings = { "Need to Identify", "Identified", "Arrested" };
		JComboBox statusList = new JComboBox(statusStrings);
		statusList.setSelectedIndex(0);
		//statusList.addActionListener(this);
		
	//	JPanel dateRange = SwingHelper.createDateRangePanel();
		
/*		SwingHelper.addLineBorder(caseNumLabel);
		SwingHelper.addLineBorder(locationLabel);
		SwingHelper.addLineBorder(statusLabel);
		SwingHelper.addLineBorder(caseNumField);
		SwingHelper.addLineBorder(locationField);
		SwingHelper.addLineBorder(statusList);
		SwingHelper.addLineBorder(dateRange);
		SwingHelper.addLineBorder(searchPanel);*/
		SwingHelper.addLineBorder(searchFields);
		
		searchFields.add(caseNumLabel, "alignx left");
		searchFields.add(caseNumField, "alignx left, wrap");
		searchFields.add(locationLabel,"alignx left");
		searchFields.add(locationField, "alignx left, wrap");

		createDateRangePanel(searchFields);

		searchFields.add(statusLabel, "alignx left");
		searchFields.add(statusList, "alignx left, wrap");
		
		//Create search button
		JButton searchButton = new JButton("Search Records");
	//	SwingHelper.addLineBorder(searchButton);
		//add the search field components to the search field panel

		
		//add the search fields and search button to the search panel
		searchPanel.add(searchFields);
		searchPanel.add(searchButton);

		return searchPanel;
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
