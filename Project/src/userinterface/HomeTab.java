package userinterface;
import javax.swing.*;
import java.util.LinkedList;

public class HomeTab extends JPanel
{
	JScrollPane jsp = new JScrollPane();
	JPanel jp = new JPanel();
	JSeparator jsep = new JSeparator(SwingConstants.HORIZONTAL);
	LinkedList<Object> llo = new LinkedList<Object>();
	
	public HomeTab(boolean load)
	{
		//llo = getAssets(); // for getting the data from the DB
		makeGUI();
	}
	public void makeGUI()
	{
		jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS)); // sets the layout of the scroll pane to a BoxLayout
		
		JLabel jl = new JLabel("Welcome, Officer Mendez");
		jp.add(jl);
		jp.add(jsep);
		
		JTextArea jta1 = new JTextArea("Hello all, this is a message from the Chief.  I just wanted to say good work on apprehending that BOLO last week.  Keep it up!");
		jp.add(jta1);
		jp.add(jsep);
		
		/*for(int i = 0; i < llo.size(); i++)
		{
			// gets content from the linked list
			// jsp.add(llo.next)
			// this isnt how you use linked lists but whatever i'll fix it later
			jp.add(jsep);
		}*/
		
		jsp.add(jp);
		JPanel jp2 = new JPanel();
		jp2.add(jsp);
		this.add(jp2);
	}
}
