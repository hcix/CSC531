package userinterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import net.miginfocom.swing.MigLayout;
import utilities.FileHelper;
import utilities.ui.SwingHelper;

//import uk.co.caprica.vlcj.binding.LibVlc;
//import uk.co.caprica.vlcj.runtime.RuntimeUtil;

//import com.sun.jna.NativeLibrary;

public class HomeTab extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String LAUNCH = "launch";
	private static JButton videoButton;
	private static JLabel videoLabel;
	private static final Path ANNOUN_DIR = Paths.get(
			FileHelper.getProgramDirPathName(), "Videos", "Announcements");
	LinkedList<Object> llo = new LinkedList<Object>();
	
	JTabbedPane homeTabs;
	JPanel homePanel, recentItemsPanel, messagesPanel;
	Dimension tabSize;
	JFrame parent;

	public HomeTab(final JFrame parent, boolean load) 
	{
		this.parent = parent;
		tabSize = new Dimension(1200, 1000); //TODO: FIX THIS LATER
		homePanel = new JPanel();

		// llo = getAssets(); // for getting the data from the DB
		
		homePanel.add(makeGUI()); // add tabbed pane created in make GUI

		this.add(homePanel);

	}

	public JTabbedPane makeGUI() {
		// JScrollPane scrollPane = new JScrollPane();
		homeTabs = new JTabbedPane();
		homeTabs.setPreferredSize(tabSize);
		recentItemsPanel = new JPanel(new MigLayout());
		messagesPanel = new JPanel(new MigLayout());
		
		recentItemsPanel.setPreferredSize(homeTabs.getSize());
		messagesPanel.setPreferredSize(homeTabs.getSize());
		
		homeTabs.addTab("Recent Activity", recentItemsPanel);
		homeTabs.addTab("Messages and Video", messagesPanel);
		
//		// JSeparator jsep = new JSeparator(SwingConstants.HORIZONTAL);
//
//		// JPanel homePanel = new JPanel();
//
		JLabel welcomeLabel = new JLabel(
				"<html><font size=\"8\">Welcome, Officer xyz</font>",
				SwingConstants.CENTER);
		messagesPanel.add(welcomeLabel);
		// panel.add(jsep);

		JLabel welcomeTextLabel = new JLabel(
				"<html><font size = \"4\">Hello all, this is a message from the Chief.<br>"
						+ "I just wanted to say good work on apprehending that BOLO last week.<br>  Keep it up!</font>",
				SwingConstants.CENTER);
		// welcomeText.setPreferredSize(new Dimension(100, 100));
		messagesPanel.add(welcomeTextLabel);
		// panel.add(jsep);

		/*
		 * for(int i = 0; i < llo.size(); i++) { // gets content from the linked
		 * list // jsp.add(llo.next) // this isnt how you use linked lists but
		 * whatever i'll fix it later jp.add(jsep); }
		 */

		videoLabel = new JLabel(
				"<html><br /><br /><font size =\"6\"> Click here to "
						+ "launch video announcement");
		messagesPanel.add(videoLabel);

		videoButton = SwingHelper.createImageButton("Launch",
				"icons/launcher_48.png");
		videoButton.addActionListener(this);
		videoButton.setActionCommand(LAUNCH);

		//System.getenv("UMPD.latestVideo").equals(null)) ||
		// if no video, set button and label to be invisible
		if ((System.getenv("UMPD.latestVideo")) == null
			||(System.getenv("UMPD.latestVideo")).equals("none")) {
			videoButton.setVisible(false);
			videoLabel.setVisible(false);
		}

		messagesPanel.add(videoButton, "align center");

		return homeTabs;
	}

	// -----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == LAUNCH)
			launchMostRecentVideo();

	}

	// -----------------------------------------------------------------------------
	private static void launchMostRecentVideo() {

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
	public static void setVideoVisability(boolean visable) {
    	if (visable == true) {
    		videoButton.setVisible(true);
    		videoLabel.setVisible(true);
    	} else {
    		videoButton.setVisible(false);
    		videoLabel.setVisible(false);
    	}
    		
    }
}
