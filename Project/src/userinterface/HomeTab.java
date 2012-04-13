package userinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilities.FileHelper;
import utilities.ui.SwingHelper;
import net.miginfocom.swing.MigLayout;

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

	public HomeTab(boolean load) {
		JPanel homePanel = new JPanel();

		// llo = getAssets(); // for getting the data from the DB

		homePanel = makeGUI();

		this.add(homePanel);

	}

	public JPanel makeGUI() {
		// JScrollPane scrollPane = new JScrollPane();
		JPanel panel = new JPanel(new MigLayout("flowy"));
		// JSeparator jsep = new JSeparator(SwingConstants.HORIZONTAL);

		// JPanel homePanel = new JPanel();

		JLabel welcomeLabel = new JLabel(
				"<html><font size=\"8\">Welcome, Officer xyz</font>",
				JLabel.CENTER);
		panel.add(welcomeLabel);
		// panel.add(jsep);

		JLabel welcomeTextLabel = new JLabel(
				"<html><font size = \"4\">Hello all, this is a message from the Chief.<br>"
						+ "I just wanted to say good work on apprehending that BOLO last week.<br>  Keep it up!</font>",
				JLabel.CENTER);
		// welcomeText.setPreferredSize(new Dimension(100, 100));
		panel.add(welcomeTextLabel);
		// panel.add(jsep);

		/*
		 * for(int i = 0; i < llo.size(); i++) { // gets content from the linked
		 * list // jsp.add(llo.next) // this isnt how you use linked lists but
		 * whatever i'll fix it later jp.add(jsep); }
		 */

		videoLabel = new JLabel(
				"<html><br /><br /><font size =\"6\"> Click here to "
						+ "launch video announcement");
		panel.add(videoLabel);

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

		panel.add(videoButton, "align center");

		return panel;
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
