package utilities.dateAndTime;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import utilities.ui.SwingHelper;

//-----------------------------------------------------------------------------
/**
 *
 */
public class TimePanel extends JPanel implements ActionListener{
private static final long serialVersionUID = 1L;
	private JSpinner spinner;
	private SpinnerDateModel sm;
	JSpinner.DateEditor de;
	private JButton btnClearDate = SwingHelper.createImageButton("icons/delete.gif");
//-----------------------------------------------------------------------------
	public TimePanel(String label){

		//create time spinner
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		Date date = cal.getTime();
		sm =  new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		spinner = new JSpinner(sm);
		de = new JSpinner.DateEditor(spinner, "HH:mm");
		spinner.setEditor(de);
		
		//add the label
		JLabel l = new JLabel(label);
		l.setLabelFor(spinner);
		
		//add the spinner
		this.add(spinner);
		
		//add the clear button
		btnClearDate.setSize(new Dimension(20, 20));
		btnClearDate.addActionListener(this);
		this.add(btnClearDate);
	}
//-----------------------------------------------------------------------------
	public TimePanel(JPanel panel, String label){

		//create time spinner
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		Date date = cal.getTime();
		SpinnerDateModel sm =  new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		spinner = new JSpinner(sm);
		de = new JSpinner.DateEditor(spinner, "HH:mm");
		spinner.setEditor(de);
		
		//add the label
		JLabel l = new JLabel(label);
		panel.add(l);
		l.setLabelFor(spinner);
		
		//add the spinner
		panel.add(spinner, "split 2");
		
		//add the clear button
		btnClearDate.setSize(new Dimension(20, 20));
		btnClearDate.addActionListener(this);
		panel.add(btnClearDate, "wrap");
	}
//-----------------------------------------------------------------------------
	/**
	 * Returns the time currently displayed in string form.
	 * @return the time currently displayed in string form
	 */
	public String getTimeString(){
		JFormattedTextField tf = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		return tf.getText();
	}
//-----------------------------------------------------------------------------
	/**
	 * Returns the time currently displayed converted into its epoch equivalent.
	 * @return the time currently displayed converted into its epoch equivalent
	 */
	public long getTimeEpoch(){
		JFormattedTextField tf = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		String time = tf.getText();
		
		String[] hourMin = time.split(":");
		int hour, min;
		
		try{
			hour = Integer.valueOf(hourMin[0]);
			min = Integer.valueOf(hourMin[1]);
		} catch (NumberFormatException e){
			return 0;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE,min);
		
		Date date = cal.getTime();
		
		return (date.getTime());
		
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the time to be displayed. 
	 * @param epoch - the time to be displayed given in seconds since epoch
	 * format
	 */
	public void setTime(long epoch){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(epoch));

		//get time hour and minute in string format
		String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
		String min = "" + cal.get(Calendar.MINUTE);
		
		String timeText = hour + ":" + min;
		
		//set the time text
		JFormattedTextField tf = 
				((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		
		tf.setText(timeText);
	}
//-----------------------------------------------------------------------------	
	/**
	 * Called when the 'delete time' button is clicked. Erases the text field
	 * w/in this component.
	 */
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == btnClearDate){
        	JFormattedTextField tf = 
        			((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
    		tf.setText("--:--");
        }
    }
//-----------------------------------------------------------------------------	
	/**
	 * Resets the time displayed to 00:00.
	 */
	public void resetTime(){
		String timeText = "00" + ":" + "00";
		
		//set the time text
		JFormattedTextField tf = 
				((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		
		tf.setText(timeText);
	}
//-----------------------------------------------------------------------------
}