package desktop;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;


public class PrintOnly extends JFrame {
    
//    JButton btnLaunchApplication = new JButton("Launch Application");
//    JButton btnLaunchBrowser = new JButton("Launch Browser");
//    JButton btnLaunchEmail = new JButton();
//    JRadioButton rbEdit = new JRadioButton("Edit");
//    JRadioButton rbOpen = new JRadioButton("Open", true);
//    JRadioButton rbPrint = new JRadioButton("Print");
    JButton print = new JButton("Print");
//    JTextField txtBrowserURI = new JTextField();
//    JTextField txtMailTo = new JTextField();
//    JTextField txtFile = new JTextField();
//    ButtonGroup bgAppAction = new ButtonGroup();
//    JLabel lblMailRecipient = new JLabel("E-mail:");
//    JLabel lblBrowserUri = new JLabel("URI:");
//    JLabel lblFile = new JLabel("File:");
//    JButton btnFile = new JButton("...");
//    JLabel emptyLabel = new JLabel(" ");
//    JPanel conLeft = new JPanel();
//    JPanel conCenter = new JPanel();
//    JPanel conRight = new JPanel();
//    JFileChooser fc = new JFileChooser();
    File file;
    
    private Desktop desktop;
//    private Desktop.Action action = Desktop.Action.OPEN;
    
    
    /**
     * Creates new form DesktopDemo
     */
    public PrintOnly() {
        // init all gui components
        initComponents();

        //disableActions();

        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
            // now enable buttons for actions that are supported.
            //enableSupportedActions();
        }
        //loadFrameIcon();
        //setResizable(false);
    }
    
    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PrintOnly().setVisible(true);
            }
        });
    }
    
    /** Create and show components
     */
    private void initComponents() {
//
    	print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	
            	
            }
        });
    	
        Container contentPane = this.getContentPane();
        contentPane.add(print);
//        
        pack();
    }
//-----------------------------------------------------------------------------
    /**
     * Launch the default application associated with a specific
     * filename and use it to print the file. The filename is to be
     * set as the action command on the ActionEvent so it can be retrieved
     * by the onPrintFile() method. 
     *
     */
    private void onPrintFile(ActionEvent evt) {
        String fileName = evt.getActionCommand();
        File file = new File(fileName);
        
        try {
        	desktop.print(file);
        } catch (IOException ioe) {
            //ioe.printStackTrace();
        	//JOptionPane
            System.out.println("Cannot perform the given operation to the " + file + " file");
        }
    }
  //-----------------------------------------------------------------------------
}
