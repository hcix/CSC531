package progAdmin.itemsToReview;

import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;
import program.ResourceManager;
//-----------------------------------------------------------------------------
/**
 * 
 */
public class ItemsListModel extends DefaultListModel  {
	//private ArrayList<ItemToReview> itemsList;
	ResourceManager rm;
//-----------------------------------------------------------------------------
	public ItemsListModel(ResourceManager rm) {
		this.rm=rm;
	    //this.itemsList = itemsList;
	}
//-----------------------------------------------------------------------------
	public Object getElementAt(int index) {
		return(rm.getItems().get(index));
	}
//-----------------------------------------------------------------------------
	public int getSize() {
		//return(itemsList.size());
		return(rm.getItems().size());
	}
//-----------------------------------------------------------------------------
}


