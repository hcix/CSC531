package debuggerTools;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import net.miginfocom.swing.MigLayout;
import utilities.ui.SwingHelper;

//-----------------------------------------------------------------------------

/**
 *
 */
public class LayoutDebugger extends JPanel {
	private JTextField textField;	
	/**
	 * Create the panel.
	 */
	public LayoutDebugger() {
		
	
		JToolBar toolbar = new JToolBar("Items To Review");
		toolbar.setFloatable(false);
		toolbar.setEnabled(false);
		add(toolbar);
		toolbar.setLayout(new MigLayout("fillx"));
		JButton addItemButton = SwingHelper.createImageButton("icons/plusSign_16.png");
		
		toolbar.add(addItemButton);
		
	
		//String title = "Items to Review";
	//	JLabel titleLabel = new JLabel(title, JLabel.CENTER);
	//	toolbar.add(titleLabel);
		
	}
//-----------------------------------------------------------------------------	
}

