package reviewItems;

import java.util.ArrayList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

//-----------------------------------------------------------------------------

/**
 *
 */
public class ItemsListModel implements ListModel  {
	private ArrayList<ItemToReview> itemsList;
//-----------------------------------------------------------------------------
	public ItemsListModel(ArrayList<ItemToReview> itemsList) {
	    this.itemsList = itemsList;
	}


	public Object getElementAt(int index) {
		return(itemsList.get(index));
	}

	public int getSize() {
		return(itemsList.size());
	}

	public void addListDataListener(ListDataListener l){
		//TODO write addListDataListener to add items to the list and call XmlParser
		//to add them to the xml file
	}

	public void removeListDataListener(ListDataListener l){
		//TODO write removeListDataListener to remove items to the list and call XmlParser
		//to remove them from the xml file
	}

//-----------------------------------------------------------------------------
}


