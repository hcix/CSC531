package userinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import utilities.DatabaseHelper;
import utilities.ui.ImageHandler;
import utilities.ui.SwingHelper;
//-----------------------------------------------------------------------------
/**
 * Panel that displays at the top of the main frame.
 */
public class DashboardPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JButton logoutButton;
//-----------------------------------------------------------------------------	
	public DashboardPanel(){
		super();
		
		this.setLayout(new MigLayout("ins 20"));

		ImageIcon logoIcon = ImageHandler.getProgramImgIcon("images/ProgramLogo.png");
		JLabel logo = new JLabel(logoIcon);
		
		add(logo, "dock center");
		
		logoutButton = SwingHelper.createImageButton("Logout", "icons/logout64.png");
		logoutButton.addActionListener(this);
		logoutButton.setActionCommand("logout");
		add(logoutButton);
	}
//-----------------------------------------------------------------------------	
	public void actionPerformed(ActionEvent e) 
	{
		String command = e.getActionCommand();
		if(command.equals("logout"))
		{
			try {
				DatabaseHelper.removeOldEntries();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String args[] = new String[1];
			// calls public method in Core that disposes main frame
			program.Core.invalidateFrames();
			// garbage collect
			System.gc();
			// runs main in Core again
			program.Core.main(args);
		}
	}
}
