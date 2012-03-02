package userinterface;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilities.ImageHandler;

public class DashboardPanel extends JPanel {
	private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------	
	public DashboardPanel(){
		super();
		
		ImageIcon logoIcon = ImageHandler.createImageIcon("images/umpdLogo.gif");
		JLabel logo = new JLabel(logoIcon);
		
		add(logo);
	}
//-----------------------------------------------------------------------------	
}