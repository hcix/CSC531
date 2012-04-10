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
import utilities.SwingHelper;
import net.miginfocom.swing.MigLayout;

//import uk.co.caprica.vlcj.binding.LibVlc;
//import uk.co.caprica.vlcj.runtime.RuntimeUtil;

//import com.sun.jna.NativeLibrary;

public class HomeTab extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	static final String LAUNCH="launch";
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

		JLabel videoLabel = new JLabel(
				"<html><br /><br /><font size =\"6\"> Click here to "
						+ "launch video announcement");
		panel.add(videoLabel);

		JButton videoButton = SwingHelper.createImageButton("Launch",
				"icons/launcher_48.png");
		videoButton.addActionListener(this);
		videoButton.setActionCommand(LAUNCH);
		panel.add(videoButton, "align center");

		return panel;
	}
//-----------------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == LAUNCH)
			launchMostRecentVideo();
		
	}
//-----------------------------------------------------------------------------
	private static void launchMostRecentVideo() {
		/*
		 * get a list of the files in a directory checking if each file
		 * is a file
		 */
		File videoDir = new File(ANNOUN_DIR.toString());
		File[] listOfFiles = videoDir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		// check that the directory is not empty
		if (listOfFiles.length == 0) {
			// DEBUG
			System.out.println("No videos in announcement dir");
			return;
		}

		// get the last modified (most recently added file)
		File newest = null;
		long lastMod = Long.MIN_VALUE;
		for (File file : listOfFiles) {
			System.out.println("\n\n last modified : " + file.lastModified()
					+ "\n\n");
			if (file.lastModified() > lastMod) {
				newest = file;
				lastMod = file.lastModified();
			}
		}

		try {
			Runtime.getRuntime()
					.exec(new String[] { "cmd.exe", "/C",
							newest.toString() });
		} catch (IOException e1) {
			System.out.println("Could not open video player");
			// e1.printStackTrace();
		}
	}
}
