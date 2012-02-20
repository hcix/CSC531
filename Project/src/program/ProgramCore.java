package program;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import userinterface.Dashboard;
import userinterface.MainInterface;

public class ProgramCore extends JFrame {
	private static final long serialVersionUID = 1L;
//-----------------------------------------------------------------------------	 
	/**
	 * Main method to start the thread that will run the main program.
	 */
	public static void main(String[] args) {
	    //Schedule a job for the event dispatch thread: creating and showing the GUI.
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	
	        	//Use the native systems look and feel
	        	try{
	        		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());            		
	        	} catch(Exception e){
	        		e.printStackTrace();
	        	}
	        	
	        	//Set up the UI
	        	createAndShowGUI();

	        }
	    });
	}
//-----------------------------------------------------------------------------	
	/**
	 * Create the GUI and show it.  For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
	    //Create and set up the main window	
		JFrame frame = new JFrame("UMPD");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(1275,1000));
		frame.setResizable(true);
		
		//Add the dashboard area to the window   
		frame.add(new Dashboard(), BorderLayout.PAGE_START);

		//Add the main panel to the window
		frame.add(new MainInterface(frame), BorderLayout.CENTER);
		
		//Display the window
	    frame.pack();
	    frame.setVisible(true);  
	}
//-----------------------------------------------------------------------------	
}
