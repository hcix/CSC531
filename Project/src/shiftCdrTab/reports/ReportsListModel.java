package shiftCdrTab.reports;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 * JDOC
 */
public class ReportsListModel extends DefaultListModel implements ListModel{
	private ArrayList<ReportFile> reportFileList;
//-----------------------------------------------------------------------------
	public ReportsListModel(ArrayList<ReportFile> reportFileList) {
	    this.reportFileList = reportFileList;
	}
//-----------------------------------------------------------------------------
	@Override
	public Object getElementAt(int index) {
		return(reportFileList.get(index));
	}
//-----------------------------------------------------------------------------
	@Override
	public int getSize() {
		return(reportFileList.size());
	}
//-----------------------------------------------------------------------------
}


