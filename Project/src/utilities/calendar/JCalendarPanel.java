package utilities.calendar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 *
 */
public class JCalendarPanel extends JPanel implements ActionListener {
private static final long serialVersionUID = 1L;
	private JLabel lblDate = new JLabel("Date :");
    //private JDateTextField txtDate = new JDateTextField(JDateTextField.LONG_DATE);
	private JDateTextField txtDate = new JDateTextField(JDateTextField.LONG_DATE);
	private JButton btnDate = SwingHelper.createImageButton("icons/date.gif");
			//new JButton(ImageHandler.getProgramImgIcon("date.gif"));
    private JButton btnClearDate = SwingHelper.createImageButton("icons/delete.gif");
    		//new JButton(ImageHandler.getProgramImgIcon("delete.gif"));
    JCalendar jCalendar = null;
//-----------------------------------------------------------------------------
    public JCalendarPanel(JFrame parent){
    	lblDate.setSize(new Dimension(50, 20));
    	txtDate.setSize(new Dimension(200, 20));
        txtDate.setEditable(false);
        btnDate.setSize(new Dimension(20, 20));
        btnClearDate.setSize(new Dimension(20, 20));
        
        this.add(lblDate);
        this.add(txtDate);
        this.add(btnDate);
        this.add(btnClearDate);
         
        jCalendar = new JCalendar(parent, "JCalendar", true);

        btnDate.addActionListener(this);
        btnClearDate.addActionListener(this);

    }
//-----------------------------------------------------------------------------
	public JCalendarPanel(JFrame parent, JPanel panel){
		lblDate.setSize(new Dimension(50, 20));
		txtDate.setSize(new Dimension(200, 20));
	    txtDate.setEditable(false);
	    btnDate.setSize(new Dimension(20, 20));
	    btnClearDate.setSize(new Dimension(20, 20));
	    
	    panel.add(lblDate);
	    panel.add(txtDate, "growx");
	    panel.add(btnDate, "split 2");
	    panel.add(btnClearDate, "wrap");
	     
	    jCalendar = new JCalendar(parent, "JCalendar", true);
	
	    btnDate.addActionListener(this);
	    btnClearDate.addActionListener(this);
	
	}
//-----------------------------------------------------------------------------
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == btnDate)
            doActionForDate();
        else if(source == btnClearDate){
            txtDate.setDate((Date) null);
            jCalendar.setDate(null);
        }
    }
//-----------------------------------------------------------------------------
    private void doActionForDate(){
		Calendar cDate = jCalendar.getCalendar();
		
		if(cDate == null)
		    cDate = Calendar.getInstance();
		
		jCalendar.setCalendar(cDate);
		jCalendar.setLocationRelativeTo(txtDate);
		jCalendar.setVisible(true);
		
		if(jCalendar.isOkPressed())
		    txtDate.setDate(jCalendar.getDate());
		
		jCalendar.setVisible(false);        
    }
//-----------------------------------------------------------------------------
    public Date getDateSet(){
    	return jCalendar.getDate();
    }
//-----------------------------------------------------------------------------
    public void setDate(long date){
    	txtDate.setDate(new Date(date));
    }
//-----------------------------------------------------------------------------
}