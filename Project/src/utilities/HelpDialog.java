package utilities;

import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

//-----------------------------------------------------------------------------

/**
 *
 */
public class HelpDialog extends JDialog {
	private static final String helpText = "Help!";
//-----------------------------------------------------------------------------
	HelpDialog(JFrame frame){
		JLabel helpLabel = new JLabel(helpText);
		Container contentPane = getContentPane();
		contentPane.add(helpLabel);
	}
//-----------------------------------------------------------------------------
}