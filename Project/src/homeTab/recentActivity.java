package homeTab;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class RecentActivity extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel dayLabel;
	private ArrayList<Change> changeList = new ArrayList<Change>();
		
	public RecentActivity()
	{
		panel = new JPanel(new MigLayout());		
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(panel);
	}
	public void addToList(Change c)
	{
		changeList.add(c);
		JLabel info = new JLabel(c.getInfo());
		panel.add(info, "align left, wrap");
	}
	public JPanel getPanel()
	{
		return panel;
	}
	public void setdayLabel(JLabel dayLabel)
	{
		this.dayLabel = dayLabel;
		panel.add(this.dayLabel, "align left, wrap");
	}
}
