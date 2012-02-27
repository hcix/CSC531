package utilities;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerModel;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

public class SwingHelper {
//-----------------------------------------------------------------------------
	/** length in columns = 5 */
	public static final int EXTRA_SMALL_TEXT_FIELD_LENGTH = 5;
	/** length in columns = 12 */
	public static final int SMALL_TEXT_FIELD_LENGTH = 12;
	/** length in columns = 15 */
	public static final int MEDIUM_TEXT_FIELD_LENGTH = 15;
	/** length in columns = 25 */
	public static final int LARGE_TEXT_FIELD_LENGTH = 25;
	/** length in columns = 35 */
	public static final int EXTRA_LARGE_TEXT_FIELD_LENGTH = 35;
	/** length in columns = 20 */
	public static final int DEFAULT_TEXT_FIELD_LENGTH = 20;
//-----------------------------------------------------------------------------
	/** 
	 * <b> addLabeledSpinner </b>
	 * <pre>public static JSpinner <b>addLabeledSpinner</b>(Container c,String label,SpinnerModel model)</pre> 
	 * <blockquote> 
	 * Creates a <code>JSpinner</code> with the specified label text from the <code>SpinnerModel</code>  
	 * and adds the <code>JSpinner</code> to the given <code>Container</code>.
	 * </blockquote>
	 * @param c - the container to add the spinner to
	 * @param label - the text which to add to the spinner
	 * @param model - the <code>SpinnerModel</code> to make the spinner from
	 * @return a JSpinner created from the <code>SpinnerModel</code> with the 
	 * specified label text
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
	/** 
	 * <b> createLabeledTextBox </b>
	 * <pre>public static JPanel <b>createLabeledTextBox</b>(String label, int length)</pre> 
	 * <blockquote> 
	 * Creates a <code>JTextField</code> with the specified text as a label
	 * appearing before the text field.
	 * </blockquote>
	 * @param label - the text to appear in front of the text field
	 * @param length - the length in columns of the text field 
	 * @return a <code>JPanel</code> with the labeled text field as the only element
	 */
	public static JPanel createLabeledTextBox(String label, int length) {
		JPanel labeledTextBox = new JPanel();

		JLabel l = new JLabel(label);
		labeledTextBox.add(l);
		
		JTextField text = new JTextField(length);
		labeledTextBox.add(text);

		return labeledTextBox;
	}
//-----------------------------------------------------------------------------
	/** 
	 * <b> createDateRangePanel </b>
	 * <pre>public JPanel createDateRangePanel()</pre> 
	 * <blockquote> 
	 * Creates a <code>JPanel</code> containing two labeled date spinners 
	 * labeled "To" and "From". Used to specify a specific date range. The 
	 * default range is -10 years from today's date through today's date. 
	 * </blockquote>
	 * @return a JPanel containing two date spinners used for specifying a date range
	 */
	public static JPanel createDateRangePanel(){
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
	
	/** 
	 * <b> createDateRangePanel </b>
	 * <pre>public JPanel createDateRangePanel()</pre> 
	 * <blockquote> 
	 * Creates a <code>JPanel</code> same as DateRange, but for only one date. The 
	 * default range is -10 years from today's date through today's date. 
	 * </blockquote>
	 * @return a JPanel containing one date spinner used for specifying a date
	 */
	public static JPanel createDatePanel(){
		JPanel datePanel = new JPanel();
		Calendar calendar = Calendar.getInstance();
		JSpinner dateSpinner;
		
		//Set up dates
		Date initDate = calendar.getTime();
		Date latestDate = calendar.getTime();		
	    calendar.add(Calendar.YEAR, -10);        
	    Date earliestDate = calendar.getTime();
		
	   //Date Spinner
	    
	    SpinnerModel toModel = new SpinnerDateModel(initDate,earliestDate,latestDate,
	            Calendar.DAY_OF_MONTH);
	    dateSpinner = SwingHelper.addLabeledSpinner(datePanel, "", toModel);       
	    dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "MM/dd/yyyy"));
	    
	    return datePanel;

	}
//-----------------------------------------------------------------------------				
	/** 
	 * <b> makeTextPanel </b>
	 * <pre>public static JPanel makeTextPanel(String text)</pre> 
	 * <blockquote> 
	 * Creates a <code>JPanel</code> containing the given text placed in the
	 * center as the only component.
	 * </blockquote>
	 * @param text - the text to put on the panel
	 * @return a JPanel containing only the given text
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
	/** 
	 * <b> addLineBorder </b>
	 * <pre>public static void addLineBorder(JComponent c)</pre> 
	 * <blockquote> 
	 * Adds a basic black line border around the given component.
	 * </blockquote>
	 * @param c - the component to put the border around
	 */
	public static void addLineBorder(JComponent c){
		Border lineBorder;
		lineBorder = BorderFactory.createLineBorder(Color.black);
		c.setBorder(lineBorder);
	}
//-----------------------------------------------------------------------------	
	/** 
	 * <b> addVerticalButtons </b>
	 * <pre>addVerticalButtons(JPanel panel, JButton[] buttonArray)</pre> 
	 * <blockquote> 
	 * Adds a line of vertical buttons to the given panel.
	 * </blockquote>
	 * @param panel - the <code>JPanel</code> to add the buttons to
	 * @param buttonArray - an array filled with buttons to add to panel
	 */
	public static void addVerticalButtons(JPanel panel, JButton[] buttonArray){
		panel.setLayout(new MigLayout());
		
		System.out.println("array length = " + buttonArray.length);
		for(int i=0; i<buttonArray.length; i++){
			panel.add(buttonArray[i], "align, wrap");
		}
	}
//-----------------------------------------------------------------------------
	/** 
	 * <b> addVerticalLabeledButtons </b>
	 * <pre>addVerticalButtons(JPanel panel, JButton[] buttonArray, String[] labels)</pre> 
	 * <blockquote> 
	 * Adds a line of vertical buttons with accompaning text to the given panel.
	 * The text will precede the panel and both buttons and panels will be left 
	 * aligned.
	 * </blockquote>
	 * @param panel - the <code>JPanel</code> to add the buttons to
	 * @param buttonArray - an array filled with buttons to add to panel
	 * @param labels - an array filled with the text to precede each button
	 * 
	 * <u>Note</u>: buttons and accompaning labels should be in the same 
	 * order w/in their respective arrays
	 */
	public static void addVerticalLabeledButtons(JPanel panel, JButton[] buttonArray, String[] labels){
		panel.setLayout(new MigLayout());
		
		JLabel label;
		
		System.out.println("array length = " + buttonArray.length);
		for(int i=0; i<buttonArray.length; i++){
			panel.add(buttonArray[i], "align");
			label = new JLabel(labels[i]);
			panel.add(label, "aligny top, wrap");
		}
	}
//-----------------------------------------------------------------------------	
}
