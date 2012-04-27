package progAdmin.itemsToReview;

import javax.swing.DefaultListModel;
import program.ResourceManager;
//-----------------------------------------------------------------------------
/**
 * The 
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
	@Override
	public Object getElementAt(int index) {
		return(rm.getItems().get(index));
	}
//-----------------------------------------------------------------------------
	@Override
	public int getSize() {
		//return(itemsList.size());
		return(rm.getItems().size());
	}
//-----------------------------------------------------------------------------
}


