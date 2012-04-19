package desktop;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

//-----------------------------------------------------------------------------
public class emailOnly extends JFrame {
//-----------------------------------------------------------------------------
    JButton btnLaunchEmail = new JButton();
    private Desktop desktop;
    //private Desktop.Action action = Desktop.Action.OPEN;
//-----------------------------------------------------------------------------
    /**
     * Creates new form DesktopDemo
     */
    public emailOnly() {
        //init gui
    	btnLaunchEmail.setText("Launch Mail");
        btnLaunchEmail.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                onLaunchMail(evt);
            }
        });
        
        
        //get desktop
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();

        }
        
        //add stuff
        Container contentPane = this.getContentPane();
        contentPane.add(btnLaunchEmail);
        pack();
    }
//-----------------------------------------------------------------------------
    public static void main(String args[]) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new emailOnly().setVisible(true);
            }
        });
    }
//-----------------------------------------------------------------------------
    /**
     * Launch the default email client using the "mailto"
     * protocol and the text supplied by the user.
     *
     */
    private void onLaunchMail(ActionEvent evt) {
        String mailTo = " ";//txtMailTo.getText();
        URI uriMailTo = null;
        try {
            if (mailTo.length() > 0) {
                uriMailTo = new URI("mailto", mailTo, null);
                desktop.mail(uriMailTo);
            } else {
                desktop.mail();
            }
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch(URISyntaxException use) {
            use.printStackTrace();
        }
    }
//-----------------------------------------------------------------------------
}
