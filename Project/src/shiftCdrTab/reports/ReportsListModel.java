package shiftCdrTab.reports;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
//-----------------------------------------------------------------------------

/**
 *
 */
public class ReportsListModel extends DefaultListModel implements ListModel{
	private ArrayList<ShiftCdrReport> reportList;
//-----------------------------------------------------------------------------
	public ReportsListModel(ArrayList<ShiftCdrReport> reportList) {
	    this.reportList = reportList;
	}
//-----------------------------------------------------------------------------
	public Object getElementAt(int index) {
		return(reportList.get(index));
	}
//-----------------------------------------------------------------------------
	public int getSize() {
		return(reportList.size());
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
//=============================================================================
	//Report
	
//=============================================================================
}


