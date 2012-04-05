package scheduleTab;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utilities.SwingHelper;
/**
 * Generates UI for the schedule tab
 */
public class ScheduleTab extends JPanel implements ActionListener {

	static final int BUTTON_PANEL_WIDTH = 200;
	static final int BUTTON_PANEL_HEIGHT = 200;
	
	JFrame parent;
	String currentDir = System.getProperty("user.dir");
	
	/**
	 * Creates JFram for the ScheduleTab
	 * 
	 * @param parent
	 */
	public ScheduleTab(JFrame parent) {
		this.setLayout(new BorderLayout());
		this.parent = parent;
		
	    //create the launch button
		JButton launchButton = SwingHelper.createImageButton("Launch Scheduler", "icons/launcher_small.png");
		launchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				// on click, launch the scheduler
				launchScheduler();
			}
		});
		
		JPanel buttonPanel = new JPanel();
        buttonPanel.add(launchButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Launches scheduler program
	 */
	private void launchScheduler() {
		//launch schedule program
		try {
			Runtime.getRuntime().exec(currentDir + "/PatrolScheduler/UMPatrolScheduler.exe");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not launch Scheduler");
			System.out.println("Attempted to launch in directory currentDir/bin");
		}
	}
}
