package progAdmin.itemsToReview;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import program.ResourceManager;
//-----------------------------------------------------------------------------
/**
 *
 */
public class ItemsListModel extends DefaultListModel implements ListModel  {
	//private ArrayList<ItemToReview> itemsList;
	ResourceManager rm;
//-----------------------------------------------------------------------------
	public ItemsListModel(ResourceManager rm) {
		this.rm=rm;
	    //this.itemsList = itemsList;
		//rm.getItems();
	}
//-----------------------------------------------------------------------------
	public Object getElementAt(int index) {
		//return(itemsList.get(index));
		return(rm.getItems().get(index));
	}
//-----------------------------------------------------------------------------
	public int getSize() {
		//return(itemsList.size());
		return(rm.getItems().size());
	}
//-----------------------------------------------------------------------------
	//public void addListDataListener(ListDataListener l){
		//TODO write addListDataListener to add items to the list and call XmlParser
		//to add them to the xml file
	//}
//-----------------------------------------------------------------------------
//	public void removeListDataListener(ListDataListener l){
		//TODO write removeListDataListener to remove items to the list and call XmlParser
		//to remove them from the xml file
//	}
//-----------------------------------------------------------------------------
}


