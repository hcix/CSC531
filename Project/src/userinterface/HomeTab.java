package userinterface;

import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class HomeTab extends JPanel{
private static final long serialVersionUID = 1L;

	LinkedList<Object> llo = new LinkedList<Object>();
	
	public HomeTab(boolean load)
	{
		JPanel homePanel = new JPanel();
		
		//llo = getAssets(); // for getting the data from the DB
		
		homePanel = makeGUI();
		
		this.add(homePanel);
		
	}
	public JPanel makeGUI()
	{
		//JScrollPane scrollPane = new JScrollPane();
		JPanel panel = new JPanel(new MigLayout("flowy"));
	//	JSeparator jsep = new JSeparator(SwingConstants.HORIZONTAL);
		
	//	JPanel homePanel = new JPanel();
		
		JLabel welcomeLabel = new JLabel("<html><font size=\"8\">Welcome, Officer xyz</font>",JLabel.CENTER);
		panel.add(welcomeLabel);
	//	panel.add(jsep);
		
		JLabel welcomeTextLabel = new JLabel("<html><font size = \"4\">Hello all, this is a message from the Chief.<br>" +
				"I just wanted to say good work on apprehending that BOLO last week.<br>  Keep it up!</font>", JLabel.CENTER);
		//welcomeText.setPreferredSize(new Dimension(100, 100));
		panel.add(welcomeTextLabel);
	//	panel.add(jsep);
		
		
		/*for(int i = 0; i < llo.size(); i++)
		{
			// gets content from the linked list
			// jsp.add(llo.next)
			// this isnt how you use linked lists but whatever i'll fix it later
			jp.add(jsep);
		}*/
		
		
		return panel;
	}
}
