package program;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import progAdmin.LoginDialog;
import userinterface.DashboardPanel;
import userinterface.MainInterfaceWindow;
import utilities.ui.SwingHelper;
/**
 * The main class that runs and starts the UMPD Management System
 *
 */
public class Core extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static ResourceManager rm;
	private static MySplash splash;
	//Background task for creating the main GUI
    static SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>(){
        @Override
        public Boolean doInBackground() {
        	createAndShowMainGUI();
        	return true;
        }
        
        @Override
        public void done() {
        	splash.setVisible(false);
        }
    };
//-----------------------------------------------------------------------------	 
	/**
	 * Main method to start the thread that will run the main program
	 */
	public static void main(String[] args) {
	    //Schedule a job for the event dispatch thread: creating and showing the GUI
	    SwingUtilities.invokeLater(new Runnable() {
//			@SuppressWarnings("deprecation")
			public void run() {
	        	
	        	//Use the native systems look and feel
	        	try{
	        		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());            		
	        	} catch(Exception e){
	        		e.printStackTrace();
	        	}
	        	
	        	//Set up and show the login GUI
	        	//COMMENT NEXT LINE OUT TO GET RID OF THE LOGIN GUI FOR DEBUGGING PURPOSES
	        	//MUST ALSO COMMENT OUT 6 LINES IN MAININTERFACEWINDOW AS INDICATED THERE
	        	createAndShowLoginGUI();
	        
	        	rm = new ResourceManager(frame);
	        	
	        	//create splash screen
	        	splash = new MySplash(frame);
	        	splash.setVisible(true);
	        	splash.setLocationRelativeTo(frame);
	        	worker.execute();
	        }
	    });
	}
//-----------------------------------------------------------------------------	
	/**
	 * Create the GUI and show it.  For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowMainGUI() { 
		String osName = System.getProperty("os.name").toUpperCase();
		
	    //Create and set up the main window	
		frame = new JFrame("UMPD");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setResizable(true);
		
		//Uses platform specific method for opening frame
		frame.setLocationByPlatform(true);
		
		//Do platform specific setup
		if(osName.contains("MAC")){ macSetup(); }
		else{ windowsSetup(); }
		
		//Add the dashboard area to the window   
		frame.add(new DashboardPanel(), BorderLayout.PAGE_START);

		//Add the main panel to the window
		frame.add(new MainInterfaceWindow(frame, rm), BorderLayout.CENTER);
		
		//Display the window
	    frame.pack();
	    frame.setVisible(true);
	}
//-----------------------------------------------------------------------------	
	/**
	 * Create the GUI and show it.  For thread safety, this method should be
	 * invoked from the event dispatch thread.
	 */
	private static void createAndShowLoginGUI() {
	    //Create and set up the frame to place the login window in
		JFrame loginFrame = new JFrame("UMPD Login");
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setPreferredSize(SwingHelper.LOGIN_DIALOG_DIMENSION);
		loginFrame.setResizable(true);

		//Display the login dialog
		LoginDialog loginDialog = new LoginDialog(loginFrame);
		loginDialog.setVisible(true);
		
		//If login attempt(s) was/were not successful, exit the program
		if(!loginDialog.isSuccessful()){
			System.exit(0);
		}
	}
//-----------------------------------------------------------------------------
	private static void macSetup(){
		Toolkit toolkit =  Toolkit.getDefaultToolkit();
		Dimension screenDim = toolkit.getScreenSize();
		frame.setLocationRelativeTo(null);
		frame.setPreferredSize(screenDim);
	}
//-----------------------------------------------------------------------------
	private static void windowsSetup(){
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
//-----------------------------------------------------------------------------	
	public static void invalidateFrames()
	{
		frame.dispose();
	}
//-----------------------------------------------------------------------------
}
