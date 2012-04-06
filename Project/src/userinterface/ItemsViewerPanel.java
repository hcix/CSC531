package userinterface;

import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

//-----------------------------------------------------------------------------

/**
 * This is a panel that allows the user to toggle between different views to
 * see items within the program and select items to open in a separate dialog.
 * 
 */
public class ItemsViewerPanel extends JPanel {
private static final long serialVersionUID = 1L;
	JPanel mainPanel;
	int itemWidth=0, itemHeight=0;
//-----------------------------------------------------------------------------
	public ItemsViewerPanel(){
		mainPanel = new JPanel(new MigLayout("wrap 5"));	
		this.add(mainPanel);
	}
//-----------------------------------------------------------------------------
	public void addItemsToPanel(JPanel itemPanels[]){
		for(int i=0; i<itemPanels.length; i++){
			mainPanel.add(itemPanels[i]);
		}
	}
//-----------------------------------------------------------------------------
	public void addItemToPanel(JButton item){
		String constraints="";
		
		if(itemWidth!=0){
			constraints = "width " + itemWidth;
			//if there's also an itemHeight to be set, add a separator to the string
			if(itemHeight!=0){ constraints = constraints.concat(", "); }
		}
		if(itemHeight!=0){
			constraints=constraints.concat("height" + itemHeight);
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
	public void setItemWidthPercentageVal(int itemWidth){
		this.itemWidth=itemWidth;
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
	public void setItemHeightPercentageVal(int itemHeight){
		this.itemHeight=itemHeight;
	}
//-----------------------------------------------------------------------------
}