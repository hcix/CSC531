package utilities.calendar;

import java.util.Calendar;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

//-----------------------------------------------------------------------------
/**
 *
 */
public class TimePanel extends JPanel{
	JSpinner spinner;
	SpinnerDateModel sm;
//-----------------------------------------------------------------------------
	public TimePanel(String label){

		Date date = new Date();
		sm =  new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		spinner = new JSpinner(sm);
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "hh:mm");
		spinner.setEditor(de);
		
		JLabel l = new JLabel(label);
		l.setLabelFor(spinner);
		
		this.add(spinner);
	}
//-----------------------------------------------------------------------------
	public TimePanel(JPanel panel, String label){

		Date date = new Date();
		SpinnerDateModel sm =  new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		spinner = new JSpinner(sm);
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "hh:mm");
		spinner.setEditor(de);
		
		JLabel l = new JLabel(label);
		l.setLabelFor(spinner);
		
		panel.add(spinner);
	}
//-----------------------------------------------------------------------------
	public String getTimeString(){
		JFormattedTextField tf = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		return tf.getText();
	}
//-----------------------------------------------------------------------------
	public long getTimeEpoch(){
		JFormattedTextField tf = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		String time = tf.getText();
		
		String[] hourMin = time.split(":");
		
		int hour = Integer.valueOf(hourMin[0]);
		int min = Integer.valueOf(hourMin[1]);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY,hour);
		cal.set(Calendar.MINUTE,min);
		
		Date date = cal.getTime();
		
		return (date.getTime());
		
	}
//-----------------------------------------------------------------------------
	public void setTime(long epoch){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(epoch));

		
		String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
		String min = "" + cal.get(Calendar.MINUTE);
		
		String timeText = hour + ":" + min;
		
		JFormattedTextField tf = ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField();
		
		tf.setText(timeText);
		
	}
//-----------------------------------------------------------------------------
}