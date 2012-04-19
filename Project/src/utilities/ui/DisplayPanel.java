package utilities.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import net.miginfocom.swing.MigLayout;
//-----------------------------------------------------------------------------
/**
 * This is a panel that allows the user to toggle between different views to
 * see items within the program and select/"click" items. This panel will 
 * notify the given action listener when a <code>JPanel</code> is "clicked".
 * 
 * TODO: Test if this shit works
 * TODO: Adds a border around the entire thing somewhere, but where? 
 * Here? In BOLOtab? In BlueBookTab? Does it happen in the scroll dialogs too?
 */
public class DisplayPanel extends JScrollPane implements MouseListener {
private static final long serialVersionUID = 1L;
	private static final int DEFAULT_GAP_VAL = 15;
	private static final int DEFAULT_WRAP_VAL = 4;
	/** the gap to place between components **/
	private int gap=DEFAULT_GAP_VAL;
	/** the wrap value to indicate the number of components per row **/
	private int wrap=DEFAULT_WRAP_VAL;
	int itemWidthPerc=0, itemHeightPerc=0;
	/** the main panel displayed in the <code>DisplayPanel</code>'s viewport */
	JPanel mainPanel;
	/** the ActionListener to be notified if a panel is 'clicked' on **/
	ActionListener l;
	/** reference to the JPanel's original color **/
	Color originalColor;
	/** the color that should be displayed when usr clicks panel **/
	Color pressedColor;
//-----------------------------------------------------------------------------
	public DisplayPanel(JPanel[] items, ActionListener l){
		super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.l=l;
		mainPanel = createMainPanel(items);
		this.setViewportView(mainPanel);
	}
//-----------------------------------------------------------------------------
	public DisplayPanel(JPanel[] items, ActionListener l, int wrap){
		super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.wrap=wrap;
		this.l=l;
		mainPanel = createMainPanel(items);
		//createMainPanel(items);
		this.setViewportView(mainPanel);
	}
//-----------------------------------------------------------------------------
	public DisplayPanel(JPanel[] items, ActionListener l, int wrap, int gap){
		super(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.wrap=wrap;
		this.l=l;
		this.gap=gap;
		mainPanel = createMainPanel(items);
		//createMainPanel(items);
		this.setViewportView(mainPanel);
	}
//-----------------------------------------------------------------------------
	/**
	 * JDOC
	 */
	private JPanel createMainPanel(JPanel items[]){
		String gapString = "gap " + gap;
		String wrapString = "wrap " + wrap;
		//create the main panel to display in scroller
		//mainPanel = new JPanel(new MigLayout(gapString + ", " + wrapString));		
		JPanel panel = new JPanel(new MigLayout(gapString + ", " + wrapString));	
		addItemsToPanel(items, panel);
		
		//save a reference to the original color & set the pressed color
		if(items.length>0){
			originalColor = items[0].getBackground();
			pressedColor = originalColor.darker();
		} else { 
			originalColor = this.getBackground();
			pressedColor = originalColor.darker(); 
		}
		return panel;
	}
//-----------------------------------------------------------------------------
	public void addItemsToPanel(JPanel items[], JPanel panel){
		for(int i=0; i<items.length; i++){
			items[i].setBorder(BorderFactory.createRaisedBevelBorder());
			items[i].addMouseListener(this);
			panel.add(items[i]);
		}
	}
//-----------------------------------------------------------------------------
	/**
	 * Refresh this <code>DisplayPanel</code>'s contents based on the new
	 * items passed in.
	 */
	public void refreshContents(JPanel[] items){
		mainPanel.removeAll();
		mainPanel = createMainPanel(items);
		this.setViewportView(mainPanel);
	} 
//-----------------------------------------------------------------------------
	/**
	 * Sets the value indicating what percentage of the total width of the panel
	 * each item should occupy. 
	 * For example, if setItemWidthPercentageVal was called with the parameter
	 * value itemWidth equal to 10, the each item within the panel would occupy 10%
	 * of the total panel's width.
	 * @param itemWidth - the percentage of the panel's total width that each item
	 * should occupy
	 */
	public void setItemWidthPercentageVal(int itemWidthPerc){
		this.itemWidthPerc=itemWidthPerc;
	}
//-----------------------------------------------------------------------------
	/**
	 * Sets the value indicating what percentage of the total height of the panel
	 * each item should occupy. 
	 * For example, if setItemHeightPercentageVal was called with the parameter
	 * value itemHeight equal to 10, the each item within the panel would occupy 10%
	 * of the total panel's height.
	 * @param itemHeight - the percentage of the panel's total height that each item
	 * should occupy
	 */
	public void setItemHeightPercentageVal(int itemHeightPerc){
		this.itemHeightPerc=itemHeightPerc;
	}
//-----------------------------------------------------------------------------
	/**
	 * If a component is clicked, notify the listener given in construction of this
	 * obj JDOC
	 */
	public void mouseClicked(MouseEvent e) {
		//let the ActionListener know who's been clicked
		
		String name = ((Component) e.getSource()).getName();
//DEBUG System.out.println("ItemsViewerPanel: mouseClicked(): name = "+name);
		
		ActionEvent ev = new ActionEvent((e.getSource()), 
				ActionEvent.ACTION_PERFORMED, name);
		l.actionPerformed(ev);
	}
//-----------------------------------------------------------------------------
	public void mousePressed(MouseEvent e) {
		//make panel appear pressed like a button would
	
		((Component)(e.getSource())).setBackground(pressedColor);
		((JComponent)(e.getSource())).setBorder(BorderFactory.createLoweredBevelBorder());
	}
//-----------------------------------------------------------------------------
	public void mouseReleased(MouseEvent e) {
		//make panel appear normal again
		((Component)(e.getSource())).setBackground(originalColor);
		((JComponent)(e.getSource())).setBorder(BorderFactory.createRaisedBevelBorder());
	}
//-----------------------------------------------------------------------------
	public void mouseEntered(MouseEvent e) {
		//TODO highlight outline on panel so user knows it's "selected" ?
	}
//-----------------------------------------------------------------------------
	public void mouseExited(MouseEvent e) {
		//make panel appear normal again
		((Component)(e.getSource())).setBackground(originalColor);
		((JComponent)(e.getSource())).setBorder(BorderFactory.createRaisedBevelBorder());
	}
//-----------------------------------------------------------------------------
}