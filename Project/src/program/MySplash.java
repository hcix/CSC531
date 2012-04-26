package program;

import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import utilities.ui.ImageHandler;

//-----------------------------------------------------------------------------

/**
 *
 */
public class MySplash extends JDialog {
//-----------------------------------------------------------------------------
	MySplash(JFrame parent){
		super(parent, "", false);
	    this.setUndecorated(true);
	    JLabel label = new JLabel(ImageHandler.getProgramImgIcon("images/newSplash.png"));
	    this.setLocationByPlatform(true);
	    //this.setLocationRelativeTo(null);
	    Container contentPane = getContentPane();
	    contentPane.add(label);
	    pack();
	}
	
}


//-----------------------------------------------------------------------------
