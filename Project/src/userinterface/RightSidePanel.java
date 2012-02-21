package userinterface;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

public class RightSidePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	final static int GAP = 10;
//-----------------------------------------------------------------------------
	public RightSidePanel(){
		
		/*
		 * Set the initial contents of this panel to be the side panel 
		 * corresponding to the home screen.
		 */
		makeAnnouncementsPanel();

	}
//-----------------------------------------------------------------------------
	public void makeAnnouncementsPanel(){
		this.removeAll();
		
		JPanel announcements = new JPanel();
		JPanel scrollPanel = new JPanel();
		
		//Add all new announcements to the top of the scroll pane
		//
		
		JScrollPane scroller = new JScrollPane(scrollPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JLabel announcementsLabel = new JLabel(
				"<html><font size=\"5\">Announcements</font>",JLabel.CENTER);
		
		announcements.setPreferredSize(new Dimension(270,625));		
		announcements.setLayout(new BoxLayout(announcements, BoxLayout.Y_AXIS));		
		Border spaceBorder = BorderFactory.createEmptyBorder(GAP/2,GAP,GAP/2,GAP);
		Border lineBorder = BorderFactory.createLineBorder(new Color(0x000000));
		Border border = BorderFactory.createCompoundBorder(spaceBorder, lineBorder);
		announcements.setBorder(border);
		
		announcements.add(announcementsLabel);
		announcements.add(scroller);
		this.add(announcements);
	}
//-----------------------------------------------------------------------------
	public void makeMapsPanel(){
		this.removeAll();
		
		JPanel maps = new JPanel();
		JPanel scrollPanel = new JPanel();
		
		//Add all new maps announcements to the top of the scroll pane
		//addInfo(scrollPanel);
		
		JScrollPane scroller = new JScrollPane(scrollPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel mapsLabel = new JLabel(
				"<html><font size=\"5\">Maps</font>",JLabel.CENTER);

		maps.setPreferredSize(new Dimension(270,625));
		maps.setLayout(new BoxLayout(maps, BoxLayout.Y_AXIS));
		
		
		maps.setBorder(makeSidePanelBorder());

		maps.add(mapsLabel);
		maps.add(scroller);
		this.add(maps);
	}
//-----------------------------------------------------------------------------
	public Border makeSidePanelBorder(){
		
		Border spaceBorder = BorderFactory.createEmptyBorder(GAP/2,GAP,GAP/2,GAP);
		Border lineBorder = BorderFactory.createLineBorder(new Color(0x000000));
		Border border = BorderFactory.createCompoundBorder(spaceBorder, lineBorder);
		
		return border;
	}
//-----------------------------------------------------------------------------
}
