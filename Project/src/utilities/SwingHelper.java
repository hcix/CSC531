package utilities;

import java.awt.Container;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;

public class SwingHelper {
//-----------------------------------------------------------------------------
	/** <b> addLabeledSpinner </b>
	 * <pre>public static JSpinner <b>addLabeledSpinner</b>(Container c,String label,SpinnerModel model)</pre> 
	 * <blockquote> 
	 * Creates a JSpinner from the SpinnerModel with the specified label text 
	 * and adds the spinner to the given container.
	 * </blockquote>
	 * @param c the container which to add the spinner to
	 * @param label the text label which to add to the spinner
	 * @param model the spinnerModel of which to make the spinner from
	 * @return JSpinner created from SpinnerModel with the specified label text
	 */
	public static JSpinner addLabeledSpinner(Container c,String label,SpinnerModel model) {
		JLabel l = new JLabel(label);
		c.add(l);
		
		JSpinner spinner = new JSpinner(model);
		l.setLabelFor(spinner);
		c.add(spinner);
		
		return spinner;
	}
//-----------------------------------------------------------------------------
	public static JPanel createLabeledTextBox(String label) {
		JPanel labeledTextBox = new JPanel();
		
		JLabel l = new JLabel(label);
		labeledTextBox.add(l);
		
		JTextField text = new JTextField();
		labeledTextBox.add(text);
		
		return labeledTextBox;
	}
//-----------------------------------------------------------------------------
	JPanel createDateRangePanel(){
		JPanel datePanel = new JPanel();
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
	    dateSpinner = SwingHelper.addLabeledSpinner(datePanel, "From: ", fromModel);       
	    dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));
	    
	    SpinnerModel toModel = new SpinnerDateModel(initDate,earliestDate,latestDate,
	            Calendar.DAY_OF_MONTH);
	    dateSpinner = SwingHelper.addLabeledSpinner(datePanel, "To: ", toModel);       
	    dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));
	    
	    return datePanel;
	}
//-----------------------------------------------------------------------------				
	/**
	 * Makes and returns a JPanel with the given text as the only component.
	 */
	public static JPanel makeTextPanel(String text) {
	    JPanel panel = new JPanel(false);
	    JLabel filler = new JLabel(text);
	    filler.setHorizontalAlignment(JLabel.CENTER);
	    panel.setLayout(new GridLayout(1, 1));
	    panel.add(filler);
	    return panel;
	}
//-----------------------------------------------------------------------------
}
