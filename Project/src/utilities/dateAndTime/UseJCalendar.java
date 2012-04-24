package utilities.dateAndTime;

import java.io.*;
import java.util.*;
import java.text.*;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.sql.Timestamp;

import java.awt.*;
import java.awt.event.* ;
import javax.swing.*;
import javax.swing.table.*;

/**
 * This is the class shows how JCalendar and DateTextField classes can be used to create a date text field that can be set 
 * to any date by the user.
 * @author : Niraj Agarwal
 * @version : 1.0
 * @jdk version tested on : 1.4
 */
public class UseJCalendar extends JFrame implements ActionListener{
    private JLabel lblDate = new JLabel("Date :");
    private JDateTextField txtDate = new JDateTextField(JDateTextField.LONG_DATE);
    private JButton btnDate = new JButton(new ImageIcon("date.gif"));
    private JButton btnClearDate = new JButton(new ImageIcon("delete.gif"));

    JCalendar jCalendar = null;

    public UseJCalendar(){
        super("Use of JCalendar");

        getContentPane().setLayout(null);
        setBackground(java.awt.Color.lightGray);
        setForeground(java.awt.Color.black);
        setSize(200,70);
        setLocation(200,200);

        lblDate.setBounds(10,10,50,20);
        txtDate.setBounds(50,10,85,20);
        txtDate.setEditable(false);
        btnDate.setBounds(140,10,20,20);
        btnClearDate.setBounds(165,10,20,20);

        getContentPane().add(lblDate);
        getContentPane().add(txtDate);
        getContentPane().add(btnDate);
        getContentPane().add(btnClearDate);

        jCalendar = new JCalendar(this, "JCalendar", true);

        btnDate.addActionListener(this);
        btnClearDate.addActionListener(this);

        //show();
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();
        if( source == btnDate )
            doActionForDate();
        else if( source == btnClearDate )
        {
            txtDate.setDate((Date) null);
            jCalendar.setDate(null);
        }
    }

    public void doActionForDate()
    {
        Calendar cDate = jCalendar.getCalendar();

        if( cDate == null )
            cDate = Calendar.getInstance();

        jCalendar.setCalendar(cDate);
        jCalendar.setVisible(true);

        if( jCalendar.isOkPressed() )
            txtDate.setDate(jCalendar.getDate());

        jCalendar.setVisible(false);        
    }
/*
	public static void main(String[] args) 
	{
        UseJCalendar useCalendar = new UseJCalendar();
	}
	*/
}
