/**
 * A JPanel with a right side panel. Classes which wish to have a right side panel to
 * display items should extend this class rather than JPanel.
 */
package userinterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import net.miginfocom.swing.MigLayout;

public class JPanelWithSidePane extends JPanel{
private static final long serialVersionUID = 1L;
	final static int GAP = 10;
	JPanel scrollPanel, mainPanel;
//-----------------------------------------------------------------------------
	JPanelWithSidePane(){
		super(new MigLayout("fill"));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout("fill"));
		
		JPanel sidePanel = new JPanel();
		scrollPanel = new JPanel();
		
		JScrollPane scroller = new JScrollPane(scrollPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		sidePanel.setPreferredSize(new Dimension(270,625));		
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));		
		sidePanel.setBorder(makeSidePanelBorder());
		
		sidePanel.add(scroller);
		super.add(mainPanel, "top");
		super.add(sidePanel, "dock east");
		
	}
//-----------------------------------------------------------------------------
	JPanelWithSidePane(String label){
		super(new MigLayout("fill"));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout("fill"));
		
		JPanel sidePanel = new JPanel();
		scrollPanel = new JPanel();
		
		JScrollPane scroller = new JScrollPane(scrollPanel,
			ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JLabel titleLabel = new JLabel(label,JLabel.CENTER);
		
		sidePanel.setPreferredSize(new Dimension(270,625));		
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));		
		sidePanel.setBorder(makeSidePanelBorder());
		
		sidePanel.add(titleLabel);
		sidePanel.add(scroller);
		
		super.add(mainPanel, "top");
		super.add(sidePanel, "dock east");
	}
//-----------------------------------------------------------------------------
	public Border makeSidePanelBorder(){
		Border spaceBorder = BorderFactory.createEmptyBorder(GAP/2,GAP,GAP/2,GAP);
		Border lineBorder = BorderFactory.createLineBorder(new Color(0x000000));
		Border border = BorderFactory.createCompoundBorder(spaceBorder, lineBorder);
		
		return border;
	}
//-----------------------------------------------------------------------------
	public void addContentItem(JComponent c){
		
		scrollPanel.add(c);
		
		//TODO: use MigLayout to make this format all pretty like
	
		
	}
//-----------------------------------------------------------------------------
	@Override
	public Component add(Component comp){
		
		mainPanel.add(comp);
		this.validate();
		return comp;
	}
//-----------------------------------------------------------------------------
	@Override
	public void add(Component comp, Object constraints){
		
		mainPanel.add(comp, constraints);
		this.validate();
	}
//-----------------------------------------------------------------------------
}
