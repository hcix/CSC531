package userinterface;

import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilities.ImageHandler;

public class Dashboard extends JPanel {
	private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------	
	public Dashboard(){
		super(new FlowLayout());
		
		ImageIcon logoIcon = ImageHandler.createImageIcon("images/umpdLogo.gif");
		JLabel logo = new JLabel(logoIcon);
		
		add(logo);
	}
//-----------------------------------------------------------------------------	
}
