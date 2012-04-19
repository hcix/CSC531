package homeTab;

import java.awt.Dimension;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import program.CurrentUser;

import net.miginfocom.swing.MigLayout;
import utilities.DatabaseHelper;
import utilities.FileHelper;
import utilities.ui.SwingHelper;

//import uk.co.caprica.vlcj.binding.LibVlc;
//import uk.co.caprica.vlcj.runtime.RuntimeUtil;

//import com.sun.jna.NativeLibrary;

public class HomeTab extends JPanel implements ActionListener, FocusListener {
	private static final long serialVersionUID = 1L;
	private static final String LAUNCH = "launch";
	private static JButton videoButton;
	private static JLabel videoLabel;
	private static final Path ANNOUN_DIR = Paths.get(
			FileHelper.getProgramDirPathName(), "Videos", "Announcements");
	
	private static final int SEVEN = 7;
	JFrame parent;
	JTabbedPane homeTabs;
	JPanel rasp, videoPanel;
	JScrollPane rAP;
	
	Dimension tabSize;
	
	ArrayList<Change> changeList;
	
	public HomeTab(JFrame parent, boolean load) {
		this.parent = parent;
		JPanel homePanel = new JPanel();

		homePanel.add(makeGUI());
		
		this.add(homePanel);
	}

	public JTabbedPane makeGUI() 
	{	
		tabSize = new Dimension(1200, 1000);
		homeTabs = new JTabbedPane();
		homeTabs.setPreferredSize(tabSize);
		homeTabs.addFocusListener(this);
		
		rasp = new JPanel(new MigLayout()); // panel to go in JScrollPane
		rasp.setPreferredSize(this.parent.getSize());
		
		rAP = new JScrollPane(rasp);
		rAP.setPreferredSize(this.parent.getSize());
		
		videoPanel = new JPanel(new MigLayout());
		videoPanel.setPreferredSize(parent.getSize());
		
		populateVideoPanel();
		populateActivityPanel();
		
		homeTabs.addTab("Videos", videoPanel);
		homeTabs.addTab("Recent Activity", rAP);
		return homeTabs;
	}
	
	public void populateActivityPanel()
	{
		JLabel [] labels = new JLabel[SEVEN];
		long [] day_Starts = new long[SEVEN];
		recentActivity [] recArray = new recentActivity[SEVEN];
		
		try {
			databaseAction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long MILI_IN_DAY = 86400000;
		
//		Date now = new Date();
//		now.getTime();
		
		long currTime = System.currentTimeMillis();		
		
		// get the start of the current day by subtracting the remainder off the current time
		// have to deal with UDT to EDT timechange
		long startOfCurrentDay = (currTime - (currTime%MILI_IN_DAY) + (MILI_IN_DAY)/6);

		// day_Starts marks the long where the day starts in seconds
		// day_Starts[0] is current day; day_Starts[6] is 7 days ago
		// initialize recentActivity objects
		// loop to calculate day starts and make a JLabel
		
		for(int i = 0; i < SEVEN; i++)
		{
			// instantiate new rA
			recArray[i] = new recentActivity(tabSize);
			// fill in array with start of days of recent week in seconds
			day_Starts[i] = (startOfCurrentDay - (i*MILI_IN_DAY));
			// make labels based on day_Starts to show recent week
			Date d = new Date(day_Starts[i]);
			labels[i] = new JLabel(d.toString());
			// set the label in the recentActivity object
			recArray[i].setdayLabel(labels[i]);
		}
		
		for(Change c : changeList)
		{
			long l = c.getDate();
			if(l > day_Starts[0]) {recArray[0].addToList(c);}
			else if (l < day_Starts[0] && l > day_Starts[1]) {recArray[1].addToList(c);}
			else if (l < day_Starts[1] && l > day_Starts[2]) {recArray[2].addToList(c);}
			else if (l < day_Starts[2] && l > day_Starts[3]) {recArray[3].addToList(c);}
			else if (l < day_Starts[3] && l > day_Starts[4]) {recArray[4].addToList(c);}
			else if (l < day_Starts[4] && l > day_Starts[5]) {recArray[5].addToList(c);}
			else if (l < day_Starts[5] && l > day_Starts[6]) {recArray[6].addToList(c);}
		}
		assembleActivities(recArray);
	}

	// this method should take the array of ready to go recentActivities and add them
	private void assembleActivities(recentActivity[] recArray)
	{
		for(int i = 0; i < recArray.length; i++)
		{
			rasp.add(recArray[i].getPanel(), "align left, wrap");
		}
	}
	public void populateVideoPanel()
	{	
		JLabel wLabel = new JLabel("Welcome " + CurrentUser.getCurrentUser().getFirstname() + " " + CurrentUser.getCurrentUser().getLastname() + "!");
		videoPanel.add(wLabel, "align left, wrap");
		
		videoLabel = new JLabel("Click here to launch a video announcement.");
		videoPanel.add(videoLabel, "align left, wrap");

		videoButton = SwingHelper.createImageButton("Launch", "icons/launcher_48.png");
		videoButton.addActionListener(this);
		videoButton.setActionCommand(LAUNCH);

		//System.getenv("UMPD.latestVideo").equals(null)) ||
		// if no video, set button and label to be invisible
		if ((System.getenv("UMPD.latestVideo")) == null
				||(System.getenv("UMPD.latestVideo")).equals("none")) 
		{
			videoButton.setVisible(false);
			videoLabel.setVisible(false);
			
			JLabel noVidL = new JLabel("Sorry, there is no video to watch right now.  Check back again later!");
			videoPanel.add(noVidL, "align left, wrap");
		}
		
		JLabel checkRecent = new JLabel("Check the Recent Activity tab for information on changes made this week.");
		videoPanel.add(checkRecent, "align left, wrap");
		
		videoPanel.add(videoButton, "align right, wrap");
	}
	// -----------------------------------------------------------------------------
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == LAUNCH)
			launchMostRecentVideo();
		

	}

	// -----------------------------------------------------------------------------
	private void launchMostRecentVideo() {

		// check for null conditions
		if ((System.getProperty("UMPD.latestVideo") == null)
				|| (System.getProperty("UMPD.latestVideo").equals("none"))) {
			System.out.println("No video loaded");
			return;
		}

		File newest = new File(System.getProperty("UMPD.latestVideo"));
		// System.out.println("in launch newest video" + newest.toString());

		try {
			Runtime.getRuntime().exec(
					new String[] { "cmd.exe", "/C", newest.toString() });
		} catch (IOException e1) {
			System.out.println("Could not open video player");
			// e1.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------------
	public void setVideoVisability(boolean visable) {
		if (visable == true) {
			videoButton.setVisible(true);
			videoLabel.setVisible(true);
		} else {
			videoButton.setVisible(false);
			videoLabel.setVisible(false);
		}

	}
	public void databaseAction() throws Exception
	{
		// get the list of changes from the DB
		changeList = DatabaseHelper.HomeTabPullFromDB();
	}

	public void focusGained(FocusEvent e) 
	{
		
	}

	public void focusLost(FocusEvent e) 
	{
		
	}
}