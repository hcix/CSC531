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
import net.miginfocom.swing.MigLayout;
//-----------------------------------------------------------------------------
/**
 * This is a panel that allows the user to toggle between different views to
 * see items within the program and select/"click" items. This panel will 
 * notify the given action listener when a <code>JPanel</code> is "clicked".
 */
public class ItemsViewerPanel extends JPanel implements MouseListener {
private static final long serialVersionUID = 1L;
	private static final int DEFAULT_GAP_VAL = 15;
	private static final int DEFAULT_WRAP_VAL = 4;
	
	JPanel mainPanel;
	int itemWidthPerc=0, itemHeightPerc=0;
	/** the ActionListener to be notified if a panel is clicked on **/
	ActionListener l;
	/** reference to the JPanel's original color **/
	Color originalColor;
	/** the color that should be displayed when usr clicks panel **/
	Color pressedColor;
	/** the gap to place between components **/
	int gap=DEFAULT_GAP_VAL;
	/** the wrap value to indicate the number of components per row **/
	int wrap=DEFAULT_WRAP_VAL;
//-----------------------------------------------------------------------------
	public ItemsViewerPanel(JPanel[] items, ActionListener l){
		this.l=l;
		createMainPanel(items);
	}
//-----------------------------------------------------------------------------
	public ItemsViewerPanel(JPanel[] items, ActionListener l, int wrap){
		this.wrap=wrap;
		this.l=l;
		createMainPanel(items);
	}
//-----------------------------------------------------------------------------
	public ItemsViewerPanel(JPanel[] items, ActionListener l, int wrap, int gap){
		this.wrap=wrap;
		this.l=l;
		this.gap=gap;
		createMainPanel(items);
	}
//-----------------------------------------------------------------------------
	private void createMainPanel(JPanel items[]){
		String gapString = "gap " + gap;
		String wrapString = "wrap " + wrap;
		mainPanel = new JPanel(new MigLayout(gapString + ", " + wrapString));		
		this.add(mainPanel);
		this.addItemsToPanel(items);
		
		//save a reference to the original color & set the pressed color
		if(items.length>0){
			originalColor = items[0].getBackground();
			pressedColor = originalColor.darker();
		} else { 
			originalColor = this.getBackground();
			pressedColor = originalColor.darker(); 
		}
	}
//-----------------------------------------------------------------------------
	public void addItemsToPanel(JPanel items[]){
		for(int i=0; i<items.length; i++){
			items[i].setBorder(BorderFactory.createRaisedBevelBorder());
			items[i].addMouseListener(this);
			mainPanel.add(items[i]);
		}
	}
//-----------------------------------------------------------------------------
	public void addItemToPanel(JPanel item){
		String constraints="";
		
		if(itemWidthPerc!=0){
			constraints = "width " + itemWidthPerc;
			//if there's also an itemHeight to be set, add a separator to the string
			if(itemHeightPerc!=0){ constraints = constraints.concat(", "); }
		}
		if(itemHeightPerc!=0){
			constraints=constraints.concat("height" + itemHeightPerc);
		}
		mainPanel.add(item);
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
	@Override
	public void mouseClicked(MouseEvent e) {
		//let the ActionListener know who's been clicked
		
		String name = ((Component) e.getSource()).getName();
//DEBUG
//System.out.println("ItemsViewerPanel: mouseClicked(): name = "+name);
		
		ActionEvent ev = new ActionEvent((e.getSource()), ActionEvent.ACTION_PERFORMED, name);
	
		l.actionPerformed(ev);
	}
//-----------------------------------------------------------------------------
	@Override
	public void mousePressed(MouseEvent e) {
		//make panel appear pressed like a button would
	
		((Component)(e.getSource())).setBackground(pressedColor);
		((JComponent)(e.getSource())).setBorder(BorderFactory.createLoweredBevelBorder());
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseReleased(MouseEvent e) {
		//make panel appear normal again
		((Component)(e.getSource())).setBackground(originalColor);
		((JComponent)(e.getSource())).setBorder(BorderFactory.createRaisedBevelBorder());
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseEntered(MouseEvent e) {
		//TODO highlight outline on panel so user knows it's "selected"
		
	}
//-----------------------------------------------------------------------------
	@Override
	public void mouseExited(MouseEvent e) {
		//make panel appear normal again
		((Component)(e.getSource())).setBackground(originalColor);
		((JComponent)(e.getSource())).setBorder(BorderFactory.createRaisedBevelBorder());
	}
//-----------------------------------------------------------------------------
}